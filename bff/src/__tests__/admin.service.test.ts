import { Test, TestingModule } from '@nestjs/testing'
import { AdminService } from '../modules/admin/admin.service'
import { createMockSupabase, MockSupabase } from './mocks/supabase'

jest.mock('../common/supabase', () => ({
  getServerClient: jest.fn(),
  getAnonClient: jest.fn(),
}))

describe('AdminService', () => {
  let service: AdminService
  let mock: MockSupabase

  beforeEach(async () => {
    mock = createMockSupabase()
    jest.requireMock('../common/supabase').getServerClient.mockReturnValue(mock.supabase)
    jest.requireMock('../common/supabase').getAnonClient.mockReturnValue(mock.anonClient)

    const module: TestingModule = await Test.createTestingModule({
      providers: [AdminService],
    }).compile()

    service = module.get<AdminService>(AdminService)
  })

  describe('dashboard', () => {
    it('returns stats and recent records', async () => {
      // 7 count queries + 2 data queries = 9 awaited from() calls
      for (let i = 0; i < 7; i++) {
        mock.queryBuilder.setResult([])
      }
      mock.queryBuilder.setResult([{ id: 'user-1', email: 'test@test.cl' }])
      mock.queryBuilder.setResult([{ id: 'fac-1', monto: 29900 }])

      const result = await service.dashboard()
      expect(result.stats.totalUsuarios).toBe(0)
      expect(result.stats.totalEmpresas).toBe(0)
      expect(result.ultimosUsuarios).toHaveLength(1)
      expect(result.ultimasFacturas).toHaveLength(1)
    })
  })

  describe('listUsuarios', () => {
    it('lists usuarios with filters', async () => {
      mock.queryBuilder.setResult([{ id: 'user-1', email: 'test@test.cl' }])

      const result = await service.listUsuarios({ tipo: 'EMPRESA', page: '1', limit: '20' })
      expect(result.data).toHaveLength(1)
      expect(mock.queryBuilder.eq).toHaveBeenCalledWith('tipo', 'EMPRESA')
    })

    it('applies search filter when provided', async () => {
      mock.queryBuilder.setResult([])

      await service.listUsuarios({ search: 'test' })
      expect(mock.queryBuilder.or).toHaveBeenCalled()
    })
  })

  describe('getUsuario', () => {
    it('returns usuario with relations', async () => {
      mock.queryBuilder.setResult({ id: 'user-1', email: 'test@test.cl', empresa: null, trabajador: null })

      const result = await service.getUsuario('user-1')
      expect(result.id).toBe('user-1')
    })

    it('throws NotFoundException when not found', async () => {
      mock.queryBuilder.setResult(null, new Error('Not found'))

      await expect(service.getUsuario('nonexistent')).rejects.toThrow('Usuario no encontrado')
    })
  })

  describe('updateUsuario', () => {
    it('updates and returns usuario', async () => {
      mock.queryBuilder.setResult({ id: 'user-1', nombre: 'Updated' })

      const result = await service.updateUsuario('user-1', { nombre: 'Updated' })
      expect(result.nombre).toBe('Updated')
    })

    it('throws NotFoundException when not found', async () => {
      mock.queryBuilder.setResult(null)

      await expect(service.updateUsuario('user-1', {})).rejects.toThrow('Usuario no encontrado')
    })
  })

  describe('deleteUsuario', () => {
    it('deletes and returns message', async () => {
      mock.queryBuilder.setResult(null)

      const result = await service.deleteUsuario('user-1')
      expect(result).toEqual({ message: 'Usuario eliminado' })
    })
  })

  describe('listEmpresas', () => {
    it('lists empresas with filters', async () => {
      mock.queryBuilder.setResult([{ id: 'emp-1', razon_social: 'Test SA' }])

      const result = await service.listEmpresas({ plan: 'PROFESIONAL' })
      expect(result.data).toHaveLength(1)
      expect(mock.queryBuilder.eq).toHaveBeenCalledWith('plan', 'PROFESIONAL')
    })
  })

  describe('updateEmpresa', () => {
    it('updates empresa and returns it', async () => {
      mock.queryBuilder.setResult({ id: 'emp-1', plan: 'PROFESIONAL' })

      const result = await service.updateEmpresa('emp-1', { plan: 'PROFESIONAL' })
      expect(result.plan).toBe('PROFESIONAL')
    })
  })

  describe('listTrabajadores', () => {
    it('lists workers with filters', async () => {
      mock.queryBuilder.setResult([{ id: 'trab-1', nombre_completo: 'Juan' }])

      const result = await service.listTrabajadores({ region: 'RM' })
      expect(result.data).toHaveLength(1)
    })
  })

  describe('updateTrabajador', () => {
    it('updates worker and returns it', async () => {
      mock.queryBuilder.setResult({ id: 'trab-1', nombre_completo: 'Updated' })

      const result = await service.updateTrabajador('trab-1', { nombre_completo: 'Updated' })
      expect(result.nombre_completo).toBe('Updated')
    })
  })

  describe('listSuscripciones', () => {
    it('lists subscriptions with filters', async () => {
      mock.queryBuilder.setResult([{ id: 'sub-1', empresa_id: 'emp-1' }])

      const result = await service.listSuscripciones({ estado: 'ACTIVA' })
      expect(result.data).toHaveLength(1)
      expect(mock.queryBuilder.eq).toHaveBeenCalledWith('estado', 'ACTIVA')
    })
  })

  describe('updateSuscripcion', () => {
    it('updates subscription and returns it', async () => {
      mock.queryBuilder.setResult({ id: 'sub-1', estado: 'ACTIVA' })

      const result = await service.updateSuscripcion('sub-1', { estado: 'ACTIVA' })
      expect(result.estado).toBe('ACTIVA')
    })
  })

  describe('listFacturas', () => {
    it('lists facturas with filters', async () => {
      mock.queryBuilder.setResult([{ id: 'fac-1', monto: 29900 }])

      const result = await service.listFacturas({ estado: 'PAGADA' })
      expect(result.data).toHaveLength(1)
    })
  })

  describe('logs', () => {
    it('returns info message', async () => {
      const result = await service.logs()
      expect(result).toEqual({ message: 'Logs disponibles en Supabase Dashboard → Logs' })
    })
  })
})
