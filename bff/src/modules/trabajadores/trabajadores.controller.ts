import { Controller, Get, Post, Put, Body, Query, UseGuards } from '@nestjs/common'
import { TrabajadoresService } from './trabajadores.service'
import { JwtAuthGuard } from '../../common/guards/jwt-auth.guard'

@Controller('trabajadores')
@UseGuards(JwtAuthGuard)
export class TrabajadoresController {
  constructor(private readonly service: TrabajadoresService) {}

  @Post()
  create(@Body() body: any) { return this.service.create(body) }

  @Get()
  findOne(@Query('id') id?: string, @Query('usuario_id') usuarioId?: string) {
    return this.service.findOne(id, usuarioId)
  }

  @Put()
  update(@Query('id') id: string, @Body() body: any) { return this.service.update(id, body) }

  @Get('search')
  search(@Query('empresa_id') empresaId: string, @Query() filters: any) {
    return this.service.search(empresaId, filters)
  }
}
