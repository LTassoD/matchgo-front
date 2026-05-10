import { Injectable, NotFoundException, ForbiddenException } from '@nestjs/common'
import { getServerClient } from '../../common/supabase'

const PLAN_LIMITS: Record<string, { publicaciones: number }> = {
  BASICO: { publicaciones: 5 },
  PROFESIONAL: { publicaciones: 20 },
  ENTERPRISE: { publicaciones: -1 },
  TRIAL: { publicaciones: 20 },
}

@Injectable()
export class OfertasService {
  async create(body: any) {
    const supabase = getServerClient()
    const { data: empresa } = await supabase.from('empresa').select('*').eq('id', body.empresa_id).single()
    if (!empresa) throw new NotFoundException('Empresa no encontrada')

    const limit = PLAN_LIMITS[empresa.plan]?.publicaciones ?? 5
    if (limit !== -1 && empresa.publicaciones_usadas >= limit) {
      throw new ForbiddenException('Límite de publicaciones alcanzado')
    }

    const { data, error } = await supabase.from('oferta').insert({ ...body, estado: 'ABIERTA' }).select().single()
    if (error) throw new Error(error.message)

    await supabase.from('empresa').update({ publicaciones_usadas: empresa.publicaciones_usadas + 1 }).eq('id', body.empresa_id)
    return data
  }

  async findOne(id: string) {
    const supabase = getServerClient()
    const { data, error } = await supabase.from('oferta')
      .select('*, empresa(id, razon_social, region), postulaciones(*)')
      .eq('id', id).single()
    if (error || !data) throw new NotFoundException('Oferta no encontrada')
    return data
  }

  async list(filters: any) {
    const supabase = getServerClient()
    let query = supabase.from('oferta')
      .select('*, empresa(id, razon_social, region)', { count: 'exact' })

    if (filters.empresa_id) query = query.eq('empresa_id', filters.empresa_id)
    if (filters.region) query = query.eq('region', filters.region)
    if (filters.categoria) query = query.eq('categoria', filters.categoria)
    if (filters.estado) query = query.eq('estado', filters.estado)

    const page = parseInt(filters.page || '1')
    const limit = parseInt(filters.limit || '10')
    query = query.order('created_at', { ascending: false })
      .range((page - 1) * limit, page * limit - 1)

    const { data, count } = await query
    return { data, total: count || 0, page, limit, totalPages: Math.ceil((count || 0) / limit) }
  }

  async update(id: string, body: any) {
    const supabase = getServerClient()
    const { data, error } = await supabase.from('oferta').update(body).eq('id', id).select().single()
    if (error) throw new Error(error.message)
    if (!data) throw new NotFoundException('Oferta no encontrada')
    return data
  }

  async remove(id: string) {
    const supabase = getServerClient()
    await supabase.from('postulacion').delete().eq('oferta_id', id)
    const { error } = await supabase.from('oferta').delete().eq('id', id)
    if (error) throw new Error(error.message)
    return { message: 'Oferta eliminada' }
  }
}
