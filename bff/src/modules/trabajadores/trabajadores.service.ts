import { Injectable, NotFoundException, ConflictException, ForbiddenException } from '@nestjs/common'
import { getServerClient } from '../../common/supabase'

const PLAN_LIMITS: Record<string, { busquedas: number }> = {
  BASICO: { busquedas: 10 },
  PROFESIONAL: { busquedas: 50 },
  ENTERPRISE: { busquedas: -1 },
  TRIAL: { busquedas: 50 },
}

@Injectable()
export class TrabajadoresService {
  async create(body: any) {
    const supabase = getServerClient()
    const { data, error } = await supabase.from('trabajador').insert({
      movilizacion_propia: false,
      disponibilidad: { dias: [], horarios: [] },
      pretension_renta: { min: 0, max: 0, tipo: 'jornada' },
      experiencia: [],
      certificaciones: [],
      ...body,
    }).select().single()
    if (error?.code === '23505') throw new ConflictException('El RUT ya existe')
    if (error) throw new Error(error.message)
    return data
  }

  async findOne(id?: string, usuarioId?: string) {
    const supabase = getServerClient()
    let query = supabase.from('trabajador').select('*')
    if (id) query = query.eq('id', id)
    else if (usuarioId) query = query.eq('usuario_id', usuarioId)
    const { data, error } = await query.single()
    if (error || !data) throw new NotFoundException('Trabajador no encontrado')
    return data
  }

  async update(id: string, body: any) {
    const supabase = getServerClient()
    const { data, error } = await supabase.from('trabajador').update(body).eq('id', id).select().single()
    if (error) throw new Error(error.message)
    if (!data) throw new NotFoundException('Trabajador no encontrado')
    return data
  }

  async search(empresaId: string, filters: any) {
    const supabase = getServerClient()
    const { data: empresa } = await supabase.from('empresa').select('*').eq('id', empresaId).single()
    if (!empresa) throw new NotFoundException('Empresa no encontrada')

    const limit = PLAN_LIMITS[empresa.plan]?.busquedas ?? 10
    if (limit !== -1 && empresa.busquedas_usadas >= limit) {
      throw new ForbiddenException('Límite de búsquedas alcanzado')
    }

    let query = supabase.from('trabajador').select('*', { count: 'exact' })
    if (filters.region) query = query.eq('region', filters.region)
    if (filters.comuna) query = query.eq('comuna', filters.comuna)
    if (filters.movilizacion_propia) query = query.eq('movilizacion_propia', true)

    const page = filters.page || 1
    const pageLimit = filters.limit || 10
    query = query.range((page - 1) * pageLimit, page * pageLimit - 1)

    const { data, count } = await query
    await supabase.from('empresa').update({ busquedas_usadas: empresa.busquedas_usadas + 1 }).eq('id', empresaId)

    return { data, total: count || 0, page, limit: pageLimit }
  }
}
