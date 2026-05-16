import { describe, it, expect } from 'vitest'
import { regiones, comunasPorRegion } from './ubicaciones'

describe('regiones', () => {
  it('tiene 16 regiones', () => {
    expect(regiones).toHaveLength(16)
  })

  it('incluye Región Metropolitana', () => {
    expect(regiones).toContainEqual({ value: 'RM', label: 'Región Metropolitana' })
  })

  it('incluye todas las regiones de Chile', () => {
    const labels = regiones.map((r) => r.label)
    expect(labels).toContain('Tarapacá')
    expect(labels).toContain('Antofagasta')
    expect(labels).toContain('Magallanes')
    expect(labels).toContain('Ñuble')
  })

  it('cada región tiene value y label', () => {
    regiones.forEach((r) => {
      expect(r.value).toBeTruthy()
      expect(r.label).toBeTruthy()
    })
  })

  it('los values son únicos', () => {
    const values = regiones.map((r) => r.value)
    expect(new Set(values).size).toBe(values.length)
  })
})

describe('comunasPorRegion', () => {
  it('tiene comunas para las 16 regiones', () => {
    expect(Object.keys(comunasPorRegion)).toHaveLength(16)
  })

  it('Región Metropolitana tiene más de 40 comunas', () => {
    expect(comunasPorRegion['RM'].length).toBeGreaterThan(40)
  })

  it('Arica y Parinacota tiene 4 comunas', () => {
    expect(comunasPorRegion['XV']).toHaveLength(4)
    expect(comunasPorRegion['XV']).toContain('Arica')
  })

  it('cada región tiene al menos una comuna', () => {
    Object.entries(comunasPorRegion).forEach(([region, comunas]) => {
      expect(comunas.length, `Región ${region} no tiene comunas`).toBeGreaterThan(0)
    })
  })

  it('no hay comunas vacías', () => {
    Object.entries(comunasPorRegion).forEach(([region, comunas]) => {
      comunas.forEach((c) => {
        expect(c.trim(), `Comuna vacía en región ${region}`).toBeTruthy()
      })
    })
  })

  it('Santiago está en RM', () => {
    expect(comunasPorRegion['RM']).toContain('Santiago')
  })

  it('Iquique está en Tarapacá', () => {
    expect(comunasPorRegion['I']).toContain('Iquique')
  })
})
