import { Test, TestingModule } from '@nestjs/testing'
import { OfertasService } from '../modules/ofertas/ofertas.service'
import { createMockSupabase, MockSupabase } from './mocks/supabase'

jest.mock('../common/supabase', () => ({
  getServerClient: jest.fn(),
  getAnonClient: jest.fn(),
}))

describe('OfertasService', () => {
  let service: OfertasService
  let mock: MockSupabase

  beforeEach(async () => {
    mock = createMockSupabase()
    jest.requireMock('../common/supabase').getServerClient.mockReturnValue(mock.supabase)
    jest.requireMock('../common/supabase').getAnonClient.mockReturnValue(mock.anonClient)

    const module: TestingModule = await Test.createTestingModule({
      providers: [OfertasService],
    }).compile()

    service = module.get<OfertasService>(OfertasService)
  })

  describe('create', () => {
    const body = { empresa_id: 'emp-1', titulo: 'Developer' }

    it('creates an offer when company has available slots', async () => {
      mock.queryBuilder.setResult({ id: 'emp-1', plan: 'PROFESIONAL', publicaciones_usadas: 0 })
      mock.queryBuilder.setResult({ id: 'off-1', ...body, estado: 'ABIERTA' })
      mock.queryBuilder.setResult(null)

      const result = await service.create(body)
      expect(result).toMatchObject({ id: 'off-1', estado: 'ABIERTA' })
    })

    it('throws NotFoundException when company does not exist', async () => {
      mock.queryBuilder.setResult(null)

      await expect(service.create(body)).rejects.toThrow('Empresa no encontrada')
    })

    it('throws ForbiddenException when company reached publication limit', async () => {
      mock.queryBuilder.setResult({ id: 'emp-1', plan: 'BASICO', publicaciones_usadas: 5 })

      await expect(service.create(body)).rejects.toThrow('Límite de publicaciones alcanzado')
    })

    it('throws when supabase insert fails', async () => {
      mock.queryBuilder.setResult({ id: 'emp-1', plan: 'PROFESIONAL', publicaciones_usadas: 0 })
      mock.queryBuilder.setResult(null, new Error('DB error'))

      await expect(service.create(body)).rejects.toThrow('DB error')
    })

    it('allows unlimited publications for ENTERPRISE plan', async () => {
      mock.queryBuilder.setResult({ id: 'emp-1', plan: 'ENTERPRISE', publicaciones_usadas: 999 })
      mock.queryBuilder.setResult({ id: 'off-1', ...body, estado: 'ABIERTA' })
      mock.queryBuilder.setResult(null)

      const result = await service.create(body)
      expect(result).toMatchObject({ id: 'off-1', estado: 'ABIERTA' })
    })
  })

  describe('findOne', () => {
    it('returns an offer with relations', async () => {
      mock.queryBuilder.setResult({ id: 'off-1', titulo: 'Developer', empresa: { id: 'emp-1' }, postulaciones: [] })

      const result = await service.findOne('off-1')
      expect(result).toMatchObject({ id: 'off-1' })
    })

    it('throws NotFoundException when offer does not exist', async () => {
      mock.queryBuilder.setResult(null, new Error('Not found'))

      await expect(service.findOne('off-1')).rejects.toThrow('Oferta no encontrada')
    })
  })

  describe('list', () => {
    it('returns paginated offers with filters', async () => {
      const offers = [{ id: 'off-1' }, { id: 'off-2' }]
      mock.queryBuilder.setResult(offers)

      const result = await service.list({ region: 'RM', page: '1', limit: '10' })

      expect(result.data).toHaveLength(2)
      expect(result.total).toBe(2)
    })

    it('applies all provided filters', async () => {
      mock.queryBuilder.setResult([])

      await service.list({ empresa_id: 'emp-1', region: 'RM', categoria: 'TI', estado: 'ABIERTA' })

      const eqCalls = (mock.queryBuilder.eq as jest.Mock).mock.calls
      expect(eqCalls).toContainEqual(['empresa_id', 'emp-1'])
      expect(eqCalls).toContainEqual(['region', 'RM'])
      expect(eqCalls).toContainEqual(['categoria', 'TI'])
      expect(eqCalls).toContainEqual(['estado', 'ABIERTA'])
    })

    it('returns empty result when no offers match', async () => {
      mock.queryBuilder.setResult([])

      const result = await service.list({ page: '1', limit: '10' })
      expect(result.data).toEqual([])
      expect(result.total).toBe(0)
    })
  })

  describe('update', () => {
    it('updates and returns the offer', async () => {
      mock.queryBuilder.setResult({ id: 'off-1', titulo: 'Updated' })

      const result = await service.update('off-1', { titulo: 'Updated' })
      expect(result).toMatchObject({ id: 'off-1', titulo: 'Updated' })
    })

    it('throws when update fails', async () => {
      mock.queryBuilder.setError(new Error('Update error'))

      await expect(service.update('off-1', { titulo: 'Updated' })).rejects.toThrow('Update error')
    })

    it('throws NotFoundException when offer not found after update', async () => {
      mock.queryBuilder.setResult(null)

      await expect(service.update('off-1', { titulo: 'Updated' })).rejects.toThrow('Oferta no encontrada')
    })
  })

  describe('remove', () => {
    it('deletes postulaciones and the offer', async () => {
      mock.queryBuilder.setResult(null)
      mock.queryBuilder.setResult(null)

      const result = await service.remove('off-1')
      expect(result).toEqual({ message: 'Oferta eliminada' })
    })

    it('throws when delete fails', async () => {
      mock.queryBuilder.setResult(null)
      mock.queryBuilder.setError(new Error('Delete error'))

      await expect(service.remove('off-1')).rejects.toThrow('Delete error')
    })
  })
})
