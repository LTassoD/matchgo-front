import { Controller, Get, Post, Put, Body, Query, UseGuards } from '@nestjs/common'
import { EmpresasService } from './empresas.service'
import { JwtAuthGuard } from '../../common/guards/jwt-auth.guard'

@Controller('empresas')
@UseGuards(JwtAuthGuard)
export class EmpresasController {
  constructor(private readonly service: EmpresasService) {}

  @Post()
  create(@Body() body: any) { return this.service.create(body) }

  @Get()
  findOne(@Query('id') id?: string, @Query('usuario_id') usuarioId?: string) {
    return this.service.findOne(id, usuarioId)
  }

  @Put()
  update(@Query('id') id: string, @Body() body: any) { return this.service.update(id, body) }
}
