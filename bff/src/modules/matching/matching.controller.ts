import { Controller, Get, Post, Query, UseGuards } from '@nestjs/common'
import { MatchingService } from './matching.service'
import { JwtAuthGuard } from '../../common/guards/jwt-auth.guard'

@Controller('matching')
@UseGuards(JwtAuthGuard)
export class MatchingController {
  constructor(private readonly service: MatchingService) {}

  @Post('run')
  run(@Query('oferta_id') ofertaId: string) { return this.service.runMatching(ofertaId) }

  @Get()
  getMatches(
    @Query('oferta_id') ofertaId: string,
    @Query('sort_by') sortBy?: string,
    @Query('order') order?: string,
  ) { return this.service.getMatches(ofertaId, sortBy, order) }
}
