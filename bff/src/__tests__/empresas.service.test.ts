import { Test, TestingModule } from '@nestjs/testing'
import { EmpresasService } from '../modules/empresas/empresas.service'
import { createMockSupabase, MockSupabase } from './mocks/supabase'

jest.mock('../common/supabase', () => ({
  getServerClient: jest.fn(),
  getAnonClient: jest.fn(),
}))

describe('EmpresasService', () => {
  let service: EmpresasService
  let mock: MockSupabase

  beforeEach(async () => {
    mock = createMockSupabase()
    jest.requireMock('../common/supabase').getServerClient.mockReturnValue(mock.supabase)
    jest.requireMock('../common/supabase').getAnonClient.mockReturnValue(mock.anonClient)

    const module: TestingModule = await Test.createTestingModule({
      providers: [EmpresasService],
    }).compile()

    service = module.get<EmpresasService>(EmpresasService)
  })

  describe('create', () => {
    it('creates a company with TRIAL plan and 30-day trial', async () => {
      mock.queryBuilder.setResult({ id: 'emp-1', usuario_id: 'user-1', razon_social: 'Test SA', plan: 'TRIAL' })

      const result = await service.create({ usuario_id: 'user-1', razon_social: 'Test SA', rut: '76.123.456-7' })
      expect(result.plan).toBe('TRIAL')
      expect(result.id).toBe('emp-1')
    })

    it('throws ConflictException on duplicate RUT', async () => {
      mock.queryBuilder.setResult(null, { code: '23505', message: 'duplicate key' })

      await expect(service.create({})).rejects.toThrow('El RUT ya existe')
    })

    it('throws on other insert error', async () => {
      mock.queryBuilder.setError(new Error('Foreign key violation'))

      await expect(service.create({})).rejects.toThrow('Foreign key violation')
    })
  })

  describe('findOne', () => {
    it('finds company by id', async () => {
      mock.queryBuilder.setResult({ id: 'emp-1', razon_social: 'Test SA' })

      const result = await service.findOne('emp-1')
      expect(result.id).toBe('emp-1')
    })

    it('finds company by usuario_id', async () => {
      mock.queryBuilder.setResult({ id: 'emp-1', usuario_id: 'user-1' })

      const result = await service.findOne(undefined, 'user-1')
      expect(mock.queryBuilder.eq).toHaveBeenCalledWith('usuario_id', 'user-1')
    })

    it('throws NotFoundException when not found', async () => {
      mock.queryBuilder.setResult(null)

      await expect(service.findOne('nonexistent')).rejects.toThrow('Empresa no encontrada')
    })
  })

  describe('update', () => {
    it('updates and returns the company', async () => {
      mock.queryBuilder.setResult({ id: 'emp-1', razon_social: 'Updated SA' })

      const result = await service.update('emp-1', { razon_social: 'Updated SA' })
      expect(result.razon_social).toBe('Updated SA')
    })

    it('throws NotFoundException when company not found', async () => {
      mock.queryBuilder.setResult(null)

      await expect(service.update('emp-1', {})).rejects.toThrow('Empresa no encontrada')
    })
  })
})
