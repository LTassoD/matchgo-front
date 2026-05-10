import { supabase } from './supabase'

const BFF_URL = process.env.NEXT_PUBLIC_BFF_URL || 'http://localhost:4000'

async function getAuthHeaders(): Promise<Record<string, string>> {
  const headers: Record<string, string> = { 'Content-Type': 'application/json' }
  const { data: { session } } = await supabase!.auth.getSession()
  if (session?.access_token) {
    headers['Authorization'] = `Bearer ${session.access_token}`
  }
  return headers
}

async function bffFetch(endpoint: string, options: RequestInit = {}) {
  const headers = await getAuthHeaders()
  const response = await fetch(`${BFF_URL}/api/v1${endpoint}`, {
    ...options,
    headers: { ...headers, ...options.headers as Record<string, string> },
  })
  if (!response.ok) {
    const error = await response.json().catch(() => ({ message: response.statusText }))
    throw new Error(error.message || 'Error en la solicitud')
  }
  return response.json()
}

export const authApi = {
  signIn: (email: string, password: string) =>
    bffFetch('/auth/sign-in', { method: 'POST', body: JSON.stringify({ email, password }) }),
  signUp: (email: string, password: string, nombre: string, tipo: string) =>
    bffFetch('/auth/sign-up', { method: 'POST', body: JSON.stringify({ email, password, nombre, tipo }) }),
  me: () => bffFetch('/auth/me'),
  signOut: () => bffFetch('/auth/sign-out', { method: 'POST' }),
}

export const empresaApi = {
  get: (usuario_id?: string) =>
    bffFetch(`/empresas${usuario_id ? `?usuario_id=${usuario_id}` : ''}`),
  create: (data: any) =>
    bffFetch('/empresas', { method: 'POST', body: JSON.stringify(data) }),
  update: (id: string, data: any) =>
    bffFetch(`/empresas?id=${id}`, { method: 'PUT', body: JSON.stringify(data) }),
}

export const trabajadorApi = {
  get: (usuario_id?: string) =>
    bffFetch(`/trabajadores${usuario_id ? `?usuario_id=${usuario_id}` : ''}`),
  create: (data: any) =>
    bffFetch('/trabajadores', { method: 'POST', body: JSON.stringify(data) }),
  update: (id: string, data: any) =>
    bffFetch(`/trabajadores?id=${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  search: (empresa_id: string, filters?: any) => {
    const params = new URLSearchParams({ empresa_id, ...filters })
    return bffFetch(`/trabajadores/search?${params}`)
  },
}

export const ofertaApi = {
  list: (filters?: any) => {
    const params = filters ? '?' + new URLSearchParams(filters).toString() : ''
    return bffFetch(`/ofertas${params}`)
  },
  get: (id: string) => bffFetch(`/ofertas/${id}`),
  create: (data: any) =>
    bffFetch('/ofertas', { method: 'POST', body: JSON.stringify(data) }),
  update: (id: string, data: any) =>
    bffFetch(`/ofertas/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  delete: (id: string) =>
    bffFetch(`/ofertas/${id}`, { method: 'DELETE' }),
}

export const postulacionApi = {
  list: (trabajador_id?: string, estado?: string) => {
    const params = new URLSearchParams()
    if (trabajador_id) params.set('trabajador_id', trabajador_id)
    if (estado) params.set('estado', estado)
    return bffFetch(`/postulaciones?${params}`)
  },
  create: (data: any) =>
    bffFetch('/postulaciones', { method: 'POST', body: JSON.stringify(data) }),
  accept: (id: string) =>
    bffFetch(`/postulaciones/${id}/accept`, { method: 'PATCH' }),
  reject: (id: string, motivo?: string) =>
    bffFetch(`/postulaciones/${id}/reject`, { method: 'PATCH', body: JSON.stringify({ motivo }) }),
}

export const matchingApi = {
  run: (oferta_id: string) =>
    bffFetch(`/matching/run?oferta_id=${oferta_id}`, { method: 'POST' }),
  getMatches: (oferta_id: string, sort_by?: string, order?: string) => {
    const params = new URLSearchParams({ oferta_id })
    if (sort_by) params.set('sort_by', sort_by)
    if (order) params.set('order', order)
    return bffFetch(`/matching?${params}`)
  },
}

export const facturacionApi = {
  getPlanes: () => bffFetch('/facturacion/planes'),
  crearFactura: (empresa_id: string, plan_id: string) =>
    bffFetch('/facturacion/facturas', { method: 'POST', body: JSON.stringify({ empresa_id, plan_id }) }),
  cobrarFactura: (id: string) =>
    bffFetch(`/facturacion/facturas/${id}/cobrar`, { method: 'POST' }),
  listarFacturas: (empresa_id?: string, page?: number, limit?: number) => {
    const params = new URLSearchParams()
    if (empresa_id) params.set('empresa_id', empresa_id)
    if (page) params.set('page', page.toString())
    if (limit) params.set('limit', limit.toString())
    return bffFetch(`/facturacion/facturas?${params}`)
  },
  reportes: (desde?: string, hasta?: string) => {
    const params = new URLSearchParams()
    if (desde) params.set('desde', desde)
    if (hasta) params.set('hasta', hasta)
    return bffFetch(`/facturacion/reportes?${params}`)
  },
}
