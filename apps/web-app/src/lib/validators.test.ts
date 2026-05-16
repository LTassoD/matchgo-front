import { describe, it, expect } from 'vitest'
import { isValidRut, isValidChilePhone, isValidEmail } from './validators'

describe('isValidRut', () => {
  it('acepta RUT con formato completo', () => {
    expect(isValidRut('12.345.678-5')).toBe(true)
  })

  it('acepta RUT sin puntos ni guion', () => {
    expect(isValidRut('123456785')).toBe(true)
  })

  it('acepta RUT con dígito verificador K', () => {
    expect(isValidRut('10.000.013-K')).toBe(true)
  })

  it('rechaza RUT con dígito verificador incorrecto', () => {
    expect(isValidRut('12.345.678-0')).toBe(false)
  })

  it('rechaza RUT vacío', () => {
    expect(isValidRut('')).toBe(false)
  })

  it('rechaza RUT demasiado corto', () => {
    expect(isValidRut('12')).toBe(false)
  })

  it('rechaza RUT con letras inválidas', () => {
    expect(isValidRut('12.345.678-A')).toBe(false)
  })

  it('acepta RUT con K minúscula', () => {
    expect(isValidRut('10.000.013-k')).toBe(true)
  })
})

describe('isValidChilePhone', () => {
  it('acepta teléfono con +56', () => {
    expect(isValidChilePhone('+56912345678')).toBe(true)
  })

  it('acepta teléfono sin +56', () => {
    expect(isValidChilePhone('912345678')).toBe(true)
  })

  it('rechaza teléfono con código de ciudad fijo', () => {
    expect(isValidChilePhone('+56212345678')).toBe(false)
  })

  it('rechaza teléfono muy corto', () => {
    expect(isValidChilePhone('+5691234')).toBe(false)
  })

  it('rechaza teléfono vacío', () => {
    expect(isValidChilePhone('')).toBe(false)
  })
})

describe('isValidEmail', () => {
  it('acepta email válido', () => {
    expect(isValidEmail('test@example.com')).toBe(true)
  })

  it('acepta email con subdominio', () => {
    expect(isValidEmail('user@sub.example.co')).toBe(true)
  })

  it('rechaza email sin @', () => {
    expect(isValidEmail('testexample.com')).toBe(false)
  })

  it('rechaza email sin dominio', () => {
    expect(isValidEmail('test@')).toBe(false)
  })

  it('rechaza email vacío', () => {
    expect(isValidEmail('')).toBe(false)
  })
})
