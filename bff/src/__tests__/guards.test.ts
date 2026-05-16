import { UnauthorizedException } from '@nestjs/common'
import { JwtAuthGuard } from '../common/guards/jwt-auth.guard'
import { AdminGuard } from '../common/guards/admin.guard'

jest.mock('@supabase/supabase-js', () => ({
  createClient: jest.fn(() => ({
    auth: {
      getUser: jest.fn().mockResolvedValue({ data: { user: null }, error: new Error('Invalid token') }),
      signInWithPassword: jest.fn(),
      signUp: jest.fn(),
    },
    from: jest.fn(),
  })),
}))

describe('JwtAuthGuard', () => {
  let guard: JwtAuthGuard

  beforeEach(() => {
    guard = new JwtAuthGuard()
  })

  it('throws UnauthorizedException when no auth header', async () => {
    const context = {
      switchToHttp: () => ({
        getRequest: () => ({ headers: {} }),
      }),
    } as any

    await expect(guard.canActivate(context)).rejects.toThrow('Token requerido')
  })

  it('throws UnauthorizedException on invalid token', async () => {
    const context = {
      switchToHttp: () => ({
        getRequest: () => ({ headers: { authorization: 'Bearer invalid-token' } }),
      }),
    } as any

    await expect(guard.canActivate(context)).rejects.toThrow('Token inválido')
  })
})

describe('AdminGuard', () => {
  let guard: AdminGuard
  let originalEnv: NodeJS.ProcessEnv

  beforeAll(() => {
    originalEnv = process.env
    process.env = { ...originalEnv, ADMIN_EMAIL: 'admin@matchgo.cl' }
  })

  afterAll(() => {
    process.env = originalEnv
  })

  beforeEach(() => {
    guard = new AdminGuard()
  })

  it('allows access for user with admin email', async () => {
    const context = {
      switchToHttp: () => ({
        getRequest: () => ({
          user: { email: 'admin@matchgo.cl' },
        }),
      }),
    } as any

    const result = await guard.canActivate(context)
    expect(result).toBe(true)
  })

  it('allows access for user with admin rol in metadata', async () => {
    const context = {
      switchToHttp: () => ({
        getRequest: () => ({
          user: { email: 'user@test.cl', user_metadata: { rol: 'admin' } },
        }),
      }),
    } as any

    const result = await guard.canActivate(context)
    expect(result).toBe(true)
  })

  it('throws UnauthorizedException when user is not admin', async () => {
    const context = {
      switchToHttp: () => ({
        getRequest: () => ({
          user: { email: 'user@test.cl', user_metadata: { rol: 'trabajador' } },
        }),
      }),
    } as any

    await expect(guard.canActivate(context)).rejects.toThrow('Acceso solo para administradores')
  })

  it('throws UnauthorizedException when no user', async () => {
    const context = {
      switchToHttp: () => ({
        getRequest: () => ({ user: null }),
      }),
    } as any

    await expect(guard.canActivate(context)).rejects.toThrow('No autenticado')
  })
})
