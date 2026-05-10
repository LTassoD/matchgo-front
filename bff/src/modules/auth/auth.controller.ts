import { Controller, Post, Get, Body, Headers, UseGuards } from '@nestjs/common'
import { AuthService } from './auth.service'
import { JwtAuthGuard } from '../../common/guards/jwt-auth.guard'

@Controller('auth')
export class AuthController {
  constructor(private readonly authService: AuthService) {}

  @Post('sign-in')
  signIn(@Body() body: { email: string; password: string }) {
    return this.authService.signIn(body.email, body.password)
  }

  @Post('sign-up')
  signUp(@Body() body: { email: string; password: string; nombre: string; tipo: string }) {
    return this.authService.signUp(body.email, body.password, body.nombre, body.tipo)
  }

  @Get('me')
  @UseGuards(JwtAuthGuard)
  getMe(@Headers('authorization') auth: string) {
    return this.authService.getMe(auth.replace('Bearer ', ''))
  }

  @Post('sign-out')
  signOut() {
    return this.authService.signOut()
  }
}
