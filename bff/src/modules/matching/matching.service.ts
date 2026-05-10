import { Injectable, NotFoundException } from '@nestjs/common'
import { getServerClient } from '../../common/supabase'

@Injectable()
export class MatchingService {
  async runMatching(ofertaId: string) {
    const url = `${process.env.SUPABASE_URL}/functions/v1/matching/run?oferta_id=${ofertaId}`
    const res = await fetch(url, {
      headers: { Authorization: `Bearer ${process.env.SUPABASE_SERVICE_ROLE_KEY}` },
    })
    if (!res.ok) throw new Error('Error al ejecutar matching')
    return res.json()
  }

  async getMatches(ofertaId: string, sortBy = 'score_match', order = 'desc') {
    const supabase = getServerClient()
    const { data, error } = await supabase.from('postulacion')
      .select('*, trabajador:trabajador(id, nombre_completo, region, comuna, disponibilidad, certificaciones, telefono)')
      .eq('oferta_id', ofertaId)
      .order(sortBy as any, { ascending: order === 'asc' })
    if (error) throw new NotFoundException('No se encontraron matches')
    return { data, total: data?.length || 0 }
  }
}
