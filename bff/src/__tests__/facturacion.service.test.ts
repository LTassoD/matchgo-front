import { Test, TestingModule } from '@nestjs/testing'
import { FacturacionService } from '../modules/facturacion/facturacion.service'
import { createMockSupabase, MockSupabase } from './mocks/supabase'

jest.mock('../common/supabase', () => ({
  getServerClient: jest.fn(),
  getAnonClient: jest.fn(),
}))

describe('FacturacionService', () => {
  let service: FacturacionService
  let mock: MockSupabase

  beforeEach(async () => {
    mock = createMockSupabase()
    jest.requireMock('../common/supabase').getServerClient.mockReturnValue(mock.supabase)
    jest.requireMock('../common/supabase').getAnonClient.mockReturnValue(mock.anonClient)

    const module: TestingModule = await Test.createTestingModule({
      providers: [FacturacionService],
    }).compile()

    service = module.get<FacturacionService>(FacturacionService)
  })

  describe('getPlanes', () => {
    it('returns all plans with formatted limits', async () => {
      const planes = await service.getPlanes()
      expect(planes).toHaveLength(3)
      expect(planes[0].id).toBe('BASICO')
      expect(planes[2].ofertas_limite).toBe('Ilimitado')
    })
  })

  describe('crearFactura', () => {
    it('creates a factura for a valid paid plan', async () => {
      mock.queryBuilder.setResult({ id: 'emp-1', razon_social: 'Test SA' })
      mock.queryBuilder.setResult({ id: 'fac-1', monto: 29900, plan: 'PROFESIONAL', estado: 'PENDIENTE' })

      const result = await service.crearFactura('emp-1', 'PROFESIONAL')
      expect(result.monto).toBe(29900)
      expect(result.estado).toBe('PENDIENTE')
    })

    it('throws BadRequestException for invalid plan', async () => {
      await expect(service.crearFactura('emp-1', 'INVALIDO')).rejects.toThrow('Plan no válido')
    })

    it('throws BadRequestException for free plan', async () => {
      await expect(service.crearFactura('emp-1', 'BASICO')).rejects.toThrow('El plan básico no genera facturación')
    })

    it('throws NotFoundException when company does not exist', async () => {
      mock.queryBuilder.setResult(null)

      await expect(service.crearFactura('emp-1', 'PROFESIONAL')).rejects.toThrow('Empresa no encontrada')
    })
  })

  describe('cobrarFactura', () => {
    it('marks factura as PAGADA and activates subscription', async () => {
      mock.queryBuilder.setResult({ id: 'fac-1', estado: 'PENDIENTE', empresa_id: 'emp-1', plan: 'PROFESIONAL' })
      mock.queryBuilder.setResult({ id: 'fac-1', estado: 'PAGADA' })
      mock.queryBuilder.setResult(null)

      const result = await service.cobrarFactura('fac-1')
      expect(result.estado).toBe('PAGADA')
    })

    it('throws NotFoundException when factura does not exist', async () => {
      mock.queryBuilder.setResult(null, new Error('Not found'))

      await expect(service.cobrarFactura('fac-1')).rejects.toThrow('Factura no encontrada')
    })

    it('throws BadRequestException when already processed', async () => {
      mock.queryBuilder.setResult({ id: 'fac-1', estado: 'PAGADA', empresa_id: 'emp-1', plan: 'PROFESIONAL' })

      await expect(service.cobrarFactura('fac-1')).rejects.toThrow('La factura ya fue procesada')
    })
  })

  describe('listarFacturas', () => {
    it('returns paginated facturas', async () => {
      mock.queryBuilder.setResult([{ id: 'fac-1', monto: 29900 }])

      const result = await service.listarFacturas('emp-1', 1, 10)
      expect(result.data).toHaveLength(1)
      expect(result.total).toBe(1)
    })
  })

  describe('webhookPago', () => {
    it('calls cobrarFactura when status is AUTHORIZED', async () => {
      mock.queryBuilder.setResult({ id: 'fac-1', estado: 'PENDIENTE', empresa_id: 'emp-1', plan: 'PROFESIONAL' })
      mock.queryBuilder.setResult({ id: 'fac-1', estado: 'PAGADA' })
      mock.queryBuilder.setResult(null)

      const result = await service.webhookPago({ status: 'AUTHORIZED', factura_id: 'fac-1' })
      expect(result.estado).toBe('PAGADA')
    })

    it('returns pending message for non-authorized status', async () => {
      const result = await service.webhookPago({ status: 'PENDING', factura_id: 'fac-1' })
      expect(result).toEqual({ message: 'Webhook recibido, pendiente de autorización' })
    })
  })

  describe('reporteFacturacion', () => {
    it('returns report with totals from paid invoices', async () => {
      mock.queryBuilder.setResult([
        { id: 'fac-1', monto: 29900, estado: 'PAGADA' },
        { id: 'fac-2', monto: 99900, estado: 'PAGADA' },
        { id: 'fac-3', monto: 29900, estado: 'PENDIENTE' },
      ])

      const result = await service.reporteFacturacion('2024-01-01', '2024-12-31')
      expect(result.total).toBe(129800)
      expect(result.count).toBe(3)
    })

    it('filters by date range', async () => {
      mock.queryBuilder.setResult([])

      await service.reporteFacturacion('2024-06-01')
      expect(mock.queryBuilder.gte).toHaveBeenCalledWith('created_at', '2024-06-01')
    })

    it('returns zero total when no facturas', async () => {
      mock.queryBuilder.setResult(null)

      const result = await service.reporteFacturacion()
      expect(result.total).toBe(0)
      expect(result.count).toBe(0)
    })
  })
})
