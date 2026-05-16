import { Test, TestingModule } from '@nestjs/testing'
import { PostulacionesService } from '../modules/postulaciones/postulaciones.service'
import { createMockSupabase, MockSupabase } from './mocks/supabase'

jest.mock('../common/supabase', () => ({
  getServerClient: jest.fn(),
  getAnonClient: jest.fn(),
}))

describe('PostulacionesService', () => {
  let service: PostulacionesService
  let mock: MockSupabase

  beforeEach(async () => {
    mock = createMockSupabase()
    jest.requireMock('../common/supabase').getServerClient.mockReturnValue(mock.supabase)
    jest.requireMock('../common/supabase').getAnonClient.mockReturnValue(mock.anonClient)

    const module: TestingModule = await Test.createTestingModule({
      providers: [PostulacionesService],
    }).compile()

    service = module.get<PostulacionesService>(PostulacionesService)
  })

  describe('create', () => {
    const body = { oferta_id: 'off-1', trabajador_id: 'trab-1', mensaje: 'Me interesa' }

    it('creates a postulacion when offer is open', async () => {
      mock.queryBuilder.setResult({ id: 'off-1', estado: 'ABIERTA', score_promedio: 75 })
      mock.queryBuilder.setResult(null)
      mock.queryBuilder.setResult({ id: 'post-1', score_match: 75, estado: 'PENDIENTE' })

      const result = await service.create(body)
      expect(result.score_match).toBe(75)
      expect(result.estado).toBe('PENDIENTE')
    })

    it('throws NotFoundException when offer does not exist', async () => {
      mock.queryBuilder.setResult(null)

      await expect(service.create(body)).rejects.toThrow('Oferta no encontrada')
    })

    it('throws BadRequestException when offer is closed', async () => {
      mock.queryBuilder.setResult({ id: 'off-1', estado: 'CERRADA' })

      await expect(service.create(body)).rejects.toThrow('La oferta no está disponible')
    })

    it('throws ConflictException when already applied', async () => {
      mock.queryBuilder.setResult({ id: 'off-1', estado: 'ABIERTA' })
      mock.queryBuilder.setResult({ id: 'post-1' })

      await expect(service.create(body)).rejects.toThrow('Ya postulaste a esta oferta')
    })

    it('allows postulacion when offer is CON_CANDIDATOS', async () => {
      mock.queryBuilder.setResult({ id: 'off-1', estado: 'CON_CANDIDATOS', score_promedio: 80 })
      mock.queryBuilder.setResult(null)
      mock.queryBuilder.setResult({ id: 'post-1', score_match: 80, estado: 'PENDIENTE' })

      const result = await service.create(body)
      expect(result.estado).toBe('PENDIENTE')
    })

    it('throws when insert fails', async () => {
      mock.queryBuilder.setResult({ id: 'off-1', estado: 'ABIERTA' })
      mock.queryBuilder.setResult(null)
      mock.queryBuilder.setError(new Error('Insert error'))

      await expect(service.create(body)).rejects.toThrow('Insert error')
    })
  })

  describe('list', () => {
    it('returns postulaciones for a trabajador', async () => {
      mock.queryBuilder.setResult([{ id: 'post-1', oferta: { id: 'off-1' } }])

      const result = await service.list('trab-1')
      expect(result.data).toHaveLength(1)
      expect(result.total).toBe(1)
    })

    it('filters by estado when provided', async () => {
      mock.queryBuilder.setResult([])

      await service.list('trab-1', 'ACEPTADO')
      expect(mock.queryBuilder.eq).toHaveBeenCalledWith('estado', 'ACEPTADO')
    })

    it('returns empty array when no postulaciones', async () => {
      mock.queryBuilder.setResult([])

      const result = await service.list('trab-1')
      expect(result.data).toEqual([])
      expect(result.total).toBe(0)
    })

    it('throws on supabase error', async () => {
      mock.queryBuilder.setError(new Error('DB error'))

      await expect(service.list('trab-1')).rejects.toThrow('DB error')
    })
  })

  describe('accept', () => {
    it('accepts a pending postulacion', async () => {
      mock.queryBuilder.setResult({ id: 'post-1', estado: 'PENDIENTE', oferta_id: 'off-1' })
      mock.queryBuilder.setResult({ id: 'post-1', estado: 'ACEPTADO' })
      mock.queryBuilder.setResult(null)

      const result = await service.accept('post-1')
      expect(result.postulacion.estado).toBe('ACEPTADO')
    })

    it('throws NotFoundException when postulacion does not exist', async () => {
      mock.queryBuilder.setResult(null)

      await expect(service.accept('post-1')).rejects.toThrow('Postulación no encontrada')
    })

    it('throws BadRequestException when already processed', async () => {
      mock.queryBuilder.setResult({ id: 'post-1', estado: 'ACEPTADO' })

      await expect(service.accept('post-1')).rejects.toThrow('Ya fue procesada')
    })
  })

  describe('reject', () => {
    it('rejects a pending postulacion', async () => {
      mock.queryBuilder.setResult({ id: 'post-1', estado: 'PENDIENTE', oferta_id: 'off-1', mensaje: 'Hola' })
      mock.queryBuilder.setResult({ id: 'post-1', estado: 'RECHAZADO' })

      const result = await service.reject('post-1')
      expect(result.postulacion.estado).toBe('RECHAZADO')
    })

    it('rejects with motivo when provided', async () => {
      mock.queryBuilder.setResult({ id: 'post-1', estado: 'PENDIENTE', oferta_id: 'off-1', mensaje: 'Hola' })
      mock.queryBuilder.setResult({ id: 'post-1', estado: 'RECHAZADO' })

      await service.reject('post-1', 'No cumple requisitos')
      expect(mock.queryBuilder.update).toHaveBeenCalled()
    })

    it('throws NotFoundException when postulacion does not exist', async () => {
      mock.queryBuilder.setResult(null)

      await expect(service.reject('post-1')).rejects.toThrow('Postulación no encontrada')
    })

    it('throws BadRequestException when already processed', async () => {
      mock.queryBuilder.setResult({ id: 'post-1', estado: 'ACEPTADO' })

      await expect(service.reject('post-1')).rejects.toThrow('Ya fue procesada')
    })
  })
})
