import { Injectable, UnauthorizedException, ConflictException } from '@nestjs/common'
import { getServerClient, getAnonClient } from '../../common/supabase'

@Injectable()
export class AuthService {
  async signIn(email: string, password: string) {
    const supabase = getAnonClient()
    const { data, error } = await (supabase.auth as any).signInWithPassword({ email, password })
    if (error) throw new UnauthorizedException(error.message)
    return { user: data.user, session: data.session }
  }

  async signUp(email: string, password: string, nombre: string, tipo: string) {
    const supabase = getAnonClient()
    const { data, error } = await (supabase.auth as any).signUp({
      email, password,
      options: { data: { nombre, tipo } },
    })
    if (error) throw new UnauthorizedException(error.message)
    if (data.user) {
      const serverClient = getServerClient()
      await serverClient.from('usuario').insert({
        id: data.user.id, email, nombre, tipo,
      })
      if (tipo === 'EMPRESA') {
        await serverClient.from('empresa').insert({
          usuario_id: data.user.id, razon_social: nombre,
          rut: `rut-${Date.now()}`, telefono: '', region: 'RM', contacto_nombre: nombre,
        })
      } else {
        await serverClient.from('trabajador').insert({
          usuario_id: data.user.id, nombre_completo: nombre,
          rut: `rut-${Date.now()}`, telefono: '', region: 'RM', comuna: 'Santiago',
        })
      }
    }
    return { user: data.user, session: data.session }
  }

  async getMe(token: string) {
    const supabase = getAnonClient()
    const { data: { user }, error } = await (supabase.auth as any).getUser(token)
    if (error || !user) throw new UnauthorizedException('Token inválido')
    return user
  }

  async signOut() {
    return { message: 'Sesión cerrada' }
  }
}
