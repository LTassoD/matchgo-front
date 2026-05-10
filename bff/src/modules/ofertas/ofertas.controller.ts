import { Controller, Get, Post, Put, Delete, Body, Query, Param, UseGuards } from '@nestjs/common'
import { OfertasService } from './ofertas.service'
import { JwtAuthGuard } from '../../common/guards/jwt-auth.guard'

@Controller('ofertas')
@UseGuards(JwtAuthGuard)
export class OfertasController {
  constructor(private readonly service: OfertasService) {}

  @Post()
  create(@Body() body: any) { return this.service.create(body) }

  @Get()
  list(@Query() filters: any) { return this.service.list(filters) }

  @Get(':id')
  findOne(@Param('id') id: string) { return this.service.findOne(id) }

  @Put(':id')
  update(@Param('id') id: string, @Body() body: any) { return this.service.update(id, body) }

  @Delete(':id')
  remove(@Param('id') id: string) { return this.service.remove(id) }
}
