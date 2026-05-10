import { Injectable, NotFoundException, ConflictException } from '@nestjs/common'
import { getServerClient } from '../../common/supabase'

@Injectable()
export class AdminService {
  async dashboard() {
    const supabase = getServerClient()
    const { count: totalUsuarios } = await supabase.from('usuario').select('*', { count: 'exact', head: true })
    const { count: totalEmpresas } = await supabase.from('empresa').select('*', { count: 'exact', head: true })
    const { count: totalTrabajadores } = await supabase.from('trabajador').select('*', { count: 'exact', head: true })
    const { count: totalOfertas } = await supabase.from('oferta').select('*', { count: 'exact', head: true })
    const { count: totalPostulaciones } = await supabase.from('postulacion').select('*', { count: 'exact', head: true })
    const { count: totalFacturas } = await supabase.from('factura').select('*', { count: 'exact', head: true })

    const { count: pendientes } = await supabase.from('factura')
      .select('*', { count: 'exact', head: true }).eq('estado', 'PENDIENTE')

    const { data: ultimosUsuarios } = await supabase.from('usuario')
      .select('*').order('created_at', { ascending: false }).limit(5)

    const { data: ultimasFacturas } = await supabase.from('factura')
      .select('*, empresa(razon_social)').order('created_at', { ascending: false }).limit(5)

    return {
      stats: {
        totalUsuarios, totalEmpresas, totalTrabajadores,
        totalOfertas, totalPostulaciones, totalFacturas, pendientes,
      },
      ultimosUsuarios,
      ultimasFacturas,
    }
  }

  async listUsuarios(filters: any) {
    const supabase = getServerClient()
    let query = supabase.from('usuario').select('*', { count: 'exact' })

    if (filters.tipo) query = query.eq('tipo', filters.tipo)
    if (filters.search) query = query.or(`email.ilike.%${filters.search}%,nombre.ilike.%${filters.search}%`)

    const page = parseInt(filters.page || '1')
    const limit = parseInt(filters.limit || '20')
    query = query.order('created_at', { ascending: false }).range((page - 1) * limit, page * limit - 1)

    const { data, count } = await query
    return { data, total: count || 0, page, limit }
  }

  async getUsuario(id: string) {
    const supabase = getServerClient()
    const { data, error } = await supabase.from('usuario').select('*, empresa(*), trabajador(*)').eq('id', id).single()
    if (error || !data) throw new NotFoundException('Usuario no encontrado')
    return data
  }

  async updateUsuario(id: string, body: any) {
    const supabase = getServerClient()
    const { data, error } = await supabase.from('usuario').update(body).eq('id', id).select().single()
    if (error) throw new Error(error.message)
    if (!data) throw new NotFoundException('Usuario no encontrado')
    return data
  }

  async deleteUsuario(id: string) {
    const supabase = getServerClient()
    await supabase.from('usuario').delete().eq('id', id)
    return { message: 'Usuario eliminado' }
  }

  async listEmpresas(filters: any) {
    const supabase = getServerClient()
    let query = supabase.from('empresa').select('*, usuario(email, nombre)', { count: 'exact' })

    if (filters.plan) query = query.eq('plan', filters.plan)
    if (filters.region) query = query.eq('region', filters.region)
    if (filters.search) query = query.ilike('razon_social', `%${filters.search}%`)

    const page = parseInt(filters.page || '1')
    const limit = parseInt(filters.limit || '20')
    query = query.order('created_at', { ascending: false }).range((page - 1) * limit, page * limit - 1)

    const { data, count } = await query
    return { data, total: count || 0, page, limit }
  }

  async updateEmpresa(id: string, body: any) {
    const supabase = getServerClient()
    const { data, error } = await supabase.from('empresa').update(body).eq('id', id).select().single()
    if (error) throw new Error(error.message)
    if (!data) throw new NotFoundException('Empresa no encontrada')
    return data
  }

  async listTrabajadores(filters: any) {
    const supabase = getServerClient()
    let query = supabase.from('trabajador').select('*, usuario(email, nombre)', { count: 'exact' })

    if (filters.region) query = query.eq('region', filters.region)
    if (filters.search) query = query.ilike('nombre_completo', `%${filters.search}%`)

    const page = parseInt(filters.page || '1')
    const limit = parseInt(filters.limit || '20')
    query = query.order('created_at', { ascending: false }).range((page - 1) * limit, page * limit - 1)

    const { data, count } = await query
    return { data, total: count || 0, page, limit }
  }

  async updateTrabajador(id: string, body: any) {
    const supabase = getServerClient()
    const { data, error } = await supabase.from('trabajador').update(body).eq('id', id).select().single()
    if (error) throw new Error(error.message)
    if (!data) throw new NotFoundException('Trabajador no encontrado')
    return data
  }

  async listSuscripciones(filters: any) {
    const supabase = getServerClient()
    let query = supabase.from('suscripcion').select('*, empresa(razon_social)', { count: 'exact' })

    if (filters.estado) query = query.eq('estado', filters.estado)
    if (filters.plan) query = query.eq('plan', filters.plan)

    const page = parseInt(filters.page || '1')
    const limit = parseInt(filters.limit || '20')
    query = query.order('created_at', { ascending: false }).range((page - 1) * limit, page * limit - 1)

    const { data, count } = await query
    return { data, total: count || 0, page, limit }
  }

  async updateSuscripcion(id: string, body: any) {
    const supabase = getServerClient()
    const { data, error } = await supabase.from('suscripcion').update(body).eq('id', id).select().single()
    if (error) throw new Error(error.message)
    if (!data) throw new NotFoundException('Suscripción no encontrada')
    return data
  }

  async listFacturas(filters: any) {
    const supabase = getServerClient()
    let query = supabase.from('factura').select('*, empresa(razon_social)', { count: 'exact' })

    if (filters.estado) query = query.eq('estado', filters.estado)
    if (filters.empresa_id) query = query.eq('empresa_id', filters.empresa_id)

    const page = parseInt(filters.page || '1')
    const limit = parseInt(filters.limit || '20')
    query = query.order('created_at', { ascending: false }).range((page - 1) * limit, page * limit - 1)

    const { data, count } = await query
    return { data, total: count || 0, page, limit }
  }

  async logs(desde?: string, hasta?: string) {
    return { message: 'Logs disponibles en Supabase Dashboard → Logs' }
  }
}
