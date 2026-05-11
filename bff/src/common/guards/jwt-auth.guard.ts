import { Injectable, CanActivate, ExecutionContext, UnauthorizedException } from '@nestjs/common'
import { createClient } from '@supabase/supabase-js'

@Injectable()
export class JwtAuthGuard implements CanActivate {
  async canActivate(context: ExecutionContext): Promise<boolean> {
    const request = context.switchToHttp().getRequest()
    const authHeader = request.headers['authorization']

    if (!authHeader) throw new UnauthorizedException('Token requerido')

    const token = authHeader.replace('Bearer ', '')
    const supabase = createClient(
      process.env.SUPABASE_URL!,
      process.env.SUPABASE_ANON_KEY!,
    )

    const { data: { user }, error } = await (supabase.auth as any).getUser(token)
    if (error || !user) throw new UnauthorizedException('Token inválido')

    request.user = user
    return true
  }
}
