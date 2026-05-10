import { Controller, Get, Post, Patch, Body, Query, Param, UseGuards } from '@nestjs/common'
import { PostulacionesService } from './postulaciones.service'
import { JwtAuthGuard } from '../../common/guards/jwt-auth.guard'

@Controller('postulaciones')
@UseGuards(JwtAuthGuard)
export class PostulacionesController {
  constructor(private readonly service: PostulacionesService) {}

  @Post()
  create(@Body() body: any) { return this.service.create(body) }

  @Get()
  list(@Query('trabajador_id') trabajadorId: string, @Query('estado') estado?: string) {
    return this.service.list(trabajadorId, estado)
  }

  @Patch(':id/accept')
  accept(@Param('id') id: string) { return this.service.accept(id) }

  @Patch(':id/reject')
  reject(@Param('id') id: string, @Body() body: { motivo?: string }) {
    return this.service.reject(id, body.motivo)
  }
}
