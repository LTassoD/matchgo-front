import { Injectable, CanActivate, ExecutionContext, UnauthorizedException } from '@nestjs/common'

@Injectable()
export class AdminGuard implements CanActivate {
  async canActivate(context: ExecutionContext): Promise<boolean> {
    const request = context.switchToHttp().getRequest()
    const user = request.user

    if (!user) throw new UnauthorizedException('No autenticado')

    const isAdmin = user.email === process.env.ADMIN_EMAIL
      || user.user_metadata?.rol === 'admin'

    if (!isAdmin) throw new UnauthorizedException('Acceso solo para administradores')

    return true
  }
}
