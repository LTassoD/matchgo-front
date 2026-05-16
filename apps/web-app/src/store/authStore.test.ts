import { describe, it, expect, beforeEach } from 'vitest'
import { useAuthStore } from './authStore'

describe('authStore', () => {
  beforeEach(() => {
    useAuthStore.setState({ user: null, empresa: null, trabajador: null, isLoading: true })
  })

  it('inicia con valores por defecto', () => {
    const state = useAuthStore.getState()
    expect(state.user).toBeNull()
    expect(state.empresa).toBeNull()
    expect(state.trabajador).toBeNull()
    expect(state.isLoading).toBe(true)
  })

  it('setUser actualiza el usuario', () => {
    const user = { id: '1', email: 'test@test.cl', nombre: 'Test', tipo: 'TRABAJADOR' as const }
    useAuthStore.getState().setUser(user)
    expect(useAuthStore.getState().user).toEqual(user)
  })

  it('setEmpresa actualiza la empresa', () => {
    const empresa = {
      id: '1', usuario_id: '1', razon_social: 'Test SA', rut: '76.123.456-7',
      direccion: 'Calle 123', telefono: '+56912345678', contacto_nombre: 'Test',
      region: 'RM', plan: 'BASICO' as const,
    }
    useAuthStore.getState().setEmpresa(empresa)
    expect(useAuthStore.getState().empresa).toEqual(empresa)
  })

  it('setTrabajador actualiza el trabajador', () => {
    const trabajador = {
      id: '1', usuario_id: '1', nombre_completo: 'Juan', rut: '12.345.678-5',
      telefono: '+56912345678', region: 'RM', comuna: 'Santiago',
      movilizacion_propia: false,
      disponibilidad: { dias: ['Lunes'], horarios: ['Mañana'] },
      pretension_renta: { min: 500000, max: 800000, tipo: 'jornada' },
      experiencia: [], certificaciones: [],
    }
    useAuthStore.getState().setTrabajador(trabajador)
    expect(useAuthStore.getState().trabajador).toEqual(trabajador)
  })

  it('logout limpia todo', () => {
    useAuthStore.setState({
      user: { id: '1', email: 'test@test.cl', tipo: 'TRABAJADOR' },
      empresa: null,
      trabajador: null,
      isLoading: false,
    })
    useAuthStore.getState().logout()
    const state = useAuthStore.getState()
    expect(state.user).toBeNull()
    expect(state.empresa).toBeNull()
    expect(state.trabajador).toBeNull()
  })
})
