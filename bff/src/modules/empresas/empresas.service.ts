import { Injectable, NotFoundException, ConflictException } from '@nestjs/common'
import { getServerClient } from '../../common/supabase'

@Injectable()
export class EmpresasService {
  async create(body: any) {
    const supabase = getServerClient()
    const { data, error } = await supabase.from('empresa').insert({
      ...body,
      plan: 'TRIAL',
      fecha_trial_fin: new Date(Date.now() + 30 * 86400000).toISOString(),
    }).select().single()
    if (error?.code === '23505') throw new ConflictException('El RUT ya existe')
    if (error) throw new Error(error.message)
    return data
  }

  async findOne(id?: string, usuarioId?: string) {
    const supabase = getServerClient()
    let query = supabase.from('empresa').select('*')
    if (id) query = query.eq('id', id)
    else if (usuarioId) query = query.eq('usuario_id', usuarioId)
    const { data, error } = await query.single()
    if (error || !data) throw new NotFoundException('Empresa no encontrada')
    return data
  }

  async update(id: string, body: any) {
    const supabase = getServerClient()
    const { data, error } = await supabase.from('empresa').update(body).eq('id', id).select().single()
    if (error) throw new Error(error.message)
    if (!data) throw new NotFoundException('Empresa no encontrada')
    return data
  }
}
