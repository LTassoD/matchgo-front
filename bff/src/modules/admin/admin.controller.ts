import { Controller, Get, Post, Put, Delete, Body, Query, Param, UseGuards } from '@nestjs/common'
import { AdminService } from './admin.service'
import { JwtAuthGuard } from '../../common/guards/jwt-auth.guard'
import { AdminGuard } from '../../common/guards/admin.guard'

@Controller('admin')
@UseGuards(JwtAuthGuard, AdminGuard)
export class AdminController {
  constructor(private readonly service: AdminService) {}

  @Get('dashboard')
  dashboard() { return this.service.dashboard() }

  @Get('usuarios')
  listUsuarios(@Query() filters: any) { return this.service.listUsuarios(filters) }

  @Get('usuarios/:id')
  getUsuario(@Param('id') id: string) { return this.service.getUsuario(id) }

  @Put('usuarios/:id')
  updateUsuario(@Param('id') id: string, @Body() body: any) { return this.service.updateUsuario(id, body) }

  @Delete('usuarios/:id')
  deleteUsuario(@Param('id') id: string) { return this.service.deleteUsuario(id) }

  @Get('empresas')
  listEmpresas(@Query() filters: any) { return this.service.listEmpresas(filters) }

  @Put('empresas/:id')
  updateEmpresa(@Param('id') id: string, @Body() body: any) { return this.service.updateEmpresa(id, body) }

  @Get('trabajadores')
  listTrabajadores(@Query() filters: any) { return this.service.listTrabajadores(filters) }

  @Put('trabajadores/:id')
  updateTrabajador(@Param('id') id: string, @Body() body: any) { return this.service.updateTrabajador(id, body) }

  @Get('suscripciones')
  listSuscripciones(@Query() filters: any) { return this.service.listSuscripciones(filters) }

  @Put('suscripciones/:id')
  updateSuscripcion(@Param('id') id: string, @Body() body: any) { return this.service.updateSuscripcion(id, body) }

  @Get('facturas')
  listFacturas(@Query() filters: any) { return this.service.listFacturas(filters) }

  @Get('logs')
  logs(@Query('desde') desde?: string, @Query('hasta') hasta?: string) {
    return this.service.logs(desde, hasta)
  }
}
