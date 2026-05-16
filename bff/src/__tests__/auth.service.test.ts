import { Test, TestingModule } from '@nestjs/testing'
import { AuthService } from '../modules/auth/auth.service'
import { createMockSupabase, MockSupabase } from './mocks/supabase'

jest.mock('../common/supabase', () => ({
  getServerClient: jest.fn(),
  getAnonClient: jest.fn(),
}))

describe('AuthService', () => {
  let service: AuthService
  let mock: MockSupabase

  beforeEach(async () => {
    mock = createMockSupabase()
    const supabaseModule = jest.requireMock('../common/supabase')
    supabaseModule.getAnonClient.mockReturnValue(mock.anonClient)
    supabaseModule.getServerClient.mockReturnValue(mock.supabase)

    const module: TestingModule = await Test.createTestingModule({
      providers: [AuthService],
    }).compile()

    service = module.get<AuthService>(AuthService)
  })

  describe('signIn', () => {
    it('returns user and session on successful login', async () => {
      const user = { id: 'user-1', email: 'test@test.cl' }
      const session = { access_token: 'token-123' }
      mock.anonClient.auth.signInWithPassword.mockResolvedValue({
        data: { user, session }, error: null,
      })

      const result = await service.signIn('test@test.cl', 'password123')
      expect(result).toEqual({ user, session })
    })

    it('throws UnauthorizedException on invalid credentials', async () => {
      mock.anonClient.auth.signInWithPassword.mockResolvedValue({
        data: { user: null, session: null },
        error: new Error('Invalid login credentials'),
      })

      await expect(service.signIn('test@test.cl', 'wrong')).rejects.toThrow('Invalid login credentials')
    })
  })

  describe('signUp', () => {
    const email = 'new@test.cl'
    const password = 'Pass123!'

    it('creates user and inserts perfil for EMPRESA', async () => {
      const user = { id: 'user-1', email }
      mock.anonClient.auth.signUp.mockResolvedValue({
        data: { user, session: null }, error: null,
      })
      mock.queryBuilder.setResult(null)

      const result = await service.signUp(email, password, 'Mi Empresa', 'EMPRESA')
      expect(result.user).toEqual(user)
      expect(mock.supabase.from).toHaveBeenCalledWith('empresa')
    })

    it('creates perfil for TRABAJADOR', async () => {
      const user = { id: 'user-2', email }
      mock.anonClient.auth.signUp.mockResolvedValue({
        data: { user, session: null }, error: null,
      })

      const result = await service.signUp(email, password, 'Juan Pérez', 'TRABAJADOR')
      expect(result.user).toEqual(user)
      expect(mock.supabase.from).toHaveBeenCalledWith('trabajador')
    })

    it('throws UnauthorizedException when signup fails', async () => {
      mock.anonClient.auth.signUp.mockResolvedValue({
        data: { user: null, session: null },
        error: new Error('Email already registered'),
      })

      await expect(service.signUp(email, password, 'Test', 'TRABAJADOR')).rejects.toThrow('Email already registered')
    })
  })

  describe('getMe', () => {
    it('returns user from token', async () => {
      const user = { id: 'user-1', email: 'test@test.cl' }
      mock.anonClient.auth.getUser.mockResolvedValue({
        data: { user }, error: null,
      })

      const result = await service.getMe('valid-token')
      expect(result).toEqual(user)
    })

    it('throws UnauthorizedException on invalid token', async () => {
      mock.anonClient.auth.getUser.mockResolvedValue({
        data: { user: null },
        error: new Error('Invalid token'),
      })

      await expect(service.getMe('invalid-token')).rejects.toThrow('Token inválido')
    })
  })

  describe('signOut', () => {
    it('returns success message', async () => {
      const result = await service.signOut()
      expect(result).toEqual({ message: 'Sesión cerrada' })
    })
  })
})
