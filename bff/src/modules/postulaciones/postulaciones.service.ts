import { Injectable, NotFoundException, ConflictException, BadRequestException } from '@nestjs/common'
import { getServerClient } from '../../common/supabase'

@Injectable()
export class PostulacionesService {
  async create(body: any) {
    const supabase = getServerClient()
    const { data: oferta } = await supabase.from('oferta').select('*').eq('id', body.oferta_id).single()
    if (!oferta) throw new NotFoundException('Oferta no encontrada')
    if (!['ABIERTA', 'CON_CANDIDATOS'].includes(oferta.estado)) {
      throw new BadRequestException('La oferta no está disponible')
    }

    const { data: existing } = await supabase.from('postulacion').select('id')
      .eq('oferta_id', body.oferta_id).eq('trabajador_id', body.trabajador_id).single()
    if (existing) throw new ConflictException('Ya postulaste a esta oferta')

    const { data, error } = await supabase.from('postulacion').insert({
      ...body, score_match: oferta.score_promedio || 50, estado: 'PENDIENTE',
    }).select().single()
    if (error) throw new Error(error.message)
    return data
  }

  async list(trabajadorId: string, estado?: string) {
    const supabase = getServerClient()
    let query = supabase.from('postulacion')
      .select('*, oferta:oferta(id, titulo, categoria, region, remuneration, empresa:empresa(id, razon_social))')
      .eq('trabajador_id', trabajadorId)
    if (estado) query = query.eq('estado', estado)
    const { data, error } = await query.order('created_at', { ascending: false })
    if (error) throw new Error(error.message)
    return { data, total: data?.length || 0 }
  }

  async accept(id: string) {
    const supabase = getServerClient()
    const { data: p } = await supabase.from('postulacion').select('*').eq('id', id).single()
    if (!p) throw new NotFoundException('Postulación no encontrada')
    if (p.estado !== 'PENDIENTE') throw new BadRequestException('Ya fue procesada')

    const { data } = await supabase.from('postulacion').update({ estado: 'ACEPTADO' }).eq('id', id).select().single()
    await supabase.from('oferta').update({ estado: 'CON_CANDIDATOS' }).eq('id', p.oferta_id)
    return { postulacion: data, message: 'Postulación aceptada' }
  }

  async reject(id: string, motivo?: string) {
    const supabase = getServerClient()
    const { data: p } = await supabase.from('postulacion').select('*').eq('id', id).single()
    if (!p) throw new NotFoundException('Postulación no encontrada')
    if (p.estado !== 'PENDIENTE') throw new BadRequestException('Ya fue procesada')

    const mensaje = motivo ? `[Rechazado: ${motivo}]` : p.mensaje
    const { data } = await supabase.from('postulacion').update({ estado: 'RECHAZADO', mensaje }).eq('id', id).select().single()
    return { postulacion: data, message: 'Postulación rechazada' }
  }
}
