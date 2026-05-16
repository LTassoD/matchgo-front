import { Test, TestingModule } from '@nestjs/testing'
import { TrabajadoresService } from '../modules/trabajadores/trabajadores.service'
import { createMockSupabase, MockSupabase } from './mocks/supabase'

jest.mock('../common/supabase', () => ({
  getServerClient: jest.fn(),
  getAnonClient: jest.fn(),
}))

describe('TrabajadoresService', () => {
  let service: TrabajadoresService
  let mock: MockSupabase

  beforeEach(async () => {
    mock = createMockSupabase()
    jest.requireMock('../common/supabase').getServerClient.mockReturnValue(mock.supabase)
    jest.requireMock('../common/supabase').getAnonClient.mockReturnValue(mock.anonClient)

    const module: TestingModule = await Test.createTestingModule({
      providers: [TrabajadoresService],
    }).compile()

    service = module.get<TrabajadoresService>(TrabajadoresService)
  })

  describe('create', () => {
    it('creates a worker with defaults', async () => {
      mock.queryBuilder.setResult({ id: 'trab-1', usuario_id: 'user-1', nombre_completo: 'Juan Pérez', movilizacion_propia: false })

      const result = await service.create({ usuario_id: 'user-1', nombre_completo: 'Juan Pérez', rut: '1.234.567-8' })
      expect(result.id).toBe('trab-1')
      expect(result.movilizacion_propia).toBe(false)
    })

    it('throws ConflictException on duplicate RUT', async () => {
      mock.queryBuilder.setResult(null, { code: '23505', message: 'duplicate key' })

      await expect(service.create({})).rejects.toThrow('El RUT ya existe')
    })

    it('throws Error on other insert failure', async () => {
      mock.queryBuilder.setError(new Error('DB error'))

      await expect(service.create({})).rejects.toThrow('DB error')
    })
  })

  describe('findOne', () => {
    it('finds worker by id', async () => {
      mock.queryBuilder.setResult({ id: 'trab-1', nombre_completo: 'Juan' })

      const result = await service.findOne('trab-1')
      expect(result.id).toBe('trab-1')
    })

    it('finds worker by usuario_id', async () => {
      mock.queryBuilder.setResult({ id: 'trab-1', usuario_id: 'user-1' })

      const result = await service.findOne(undefined, 'user-1')
      expect(mock.queryBuilder.eq).toHaveBeenCalledWith('usuario_id', 'user-1')
    })

    it('throws NotFoundException when not found', async () => {
      mock.queryBuilder.setResult(null)

      await expect(service.findOne('nonexistent')).rejects.toThrow('Trabajador no encontrado')
    })
  })

  describe('update', () => {
    it('updates and returns the worker', async () => {
      mock.queryBuilder.setResult({ id: 'trab-1', nombre_completo: 'Juan Updated' })

      const result = await service.update('trab-1', { nombre_completo: 'Juan Updated' })
      expect(result.nombre_completo).toBe('Juan Updated')
    })

    it('throws NotFoundException when worker not found', async () => {
      mock.queryBuilder.setResult(null)

      await expect(service.update('trab-1', {})).rejects.toThrow('Trabajador no encontrado')
    })
  })

  describe('search', () => {
    it('searches workers applying filters and counting usage', async () => {
      mock.queryBuilder.setResult({ id: 'emp-1', plan: 'PROFESIONAL', busquedas_usadas: 5 })
      mock.queryBuilder.setResult([{ id: 'trab-1' }])
      mock.queryBuilder.setResult(null)

      const result = await service.search('emp-1', { region: 'RM', page: 1, limit: 10 })

      expect(result.data).toHaveLength(1)
      expect(mock.queryBuilder.eq).toHaveBeenCalledWith('region', 'RM')
    })

    it('throws NotFoundException when company does not exist', async () => {
      mock.queryBuilder.setResult(null)

      await expect(service.search('emp-1', {})).rejects.toThrow('Empresa no encontrada')
    })

    it('throws ForbiddenException when search limit reached', async () => {
      mock.queryBuilder.setResult({ id: 'emp-1', plan: 'BASICO', busquedas_usadas: 10 })

      await expect(service.search('emp-1', {})).rejects.toThrow('Límite de búsquedas alcanzado')
    })

    it('allows unlimited searches for ENTERPRISE', async () => {
      mock.queryBuilder.setResult({ id: 'emp-1', plan: 'ENTERPRISE', busquedas_usadas: 999 })
      mock.queryBuilder.setResult([])
      mock.queryBuilder.setResult(null)

      const result = await service.search('emp-1', {})
      expect(result.data).toEqual([])
    })
  })
})
