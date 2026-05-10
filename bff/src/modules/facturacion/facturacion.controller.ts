import { Controller, Get, Post, Body, Query, Param, UseGuards } from '@nestjs/common'
import { FacturacionService } from './facturacion.service'
import { JwtAuthGuard } from '../../common/guards/jwt-auth.guard'

@Controller('facturacion')
export class FacturacionController {
  constructor(private readonly service: FacturacionService) {}

  @Get('planes')
  getPlanes() { return this.service.getPlanes() }

  @Post('facturas')
  @UseGuards(JwtAuthGuard)
  crearFactura(@Body() body: { empresa_id: string; plan_id: string }) {
    return this.service.crearFactura(body.empresa_id, body.plan_id)
  }

  @Post('facturas/:id/cobrar')
  @UseGuards(JwtAuthGuard)
  cobrarFactura(@Param('id') id: string) {
    return this.service.cobrarFactura(id)
  }

  @Get('facturas')
  @UseGuards(JwtAuthGuard)
  listarFacturas(
    @Query('empresa_id') empresa_id?: string,
    @Query('page') page?: string,
    @Query('limit') limit?: string,
  ) {
    return this.service.listarFacturas(empresa_id, parseInt(page || '1'), parseInt(limit || '20'))
  }

  @Post('webhook')
  webhookPago(@Body() body: any) {
    return this.service.webhookPago(body)
  }

  @Get('reportes')
  reporteFacturacion(
    @Query('desde') desde?: string,
    @Query('hasta') hasta?: string,
  ) {
    return this.service.reporteFacturacion(desde, hasta)
  }
}
