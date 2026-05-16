export function isValidRut(rut: string): boolean {
  if (!rut || rut.length < 3) return false
  const rutClean = rut.replace(/[^0-9kK]/g, '').toUpperCase()
  if (rutClean.length < 2) return false
  const cuerpo = rutClean.slice(0, -1)
  const dv = rutClean.slice(-1)
  let suma = 0
  let mul = 2
  for (let i = cuerpo.length - 1; i >= 0; i--) {
    suma += parseInt(cuerpo[i]) * mul
    mul = mul === 7 ? 2 : mul + 1
  }
  const resto = suma % 11
  const dvCalculado = resto === 0 ? '0' : resto === 1 ? 'K' : String(11 - resto)
  return dv === dvCalculado
}

export function isValidChilePhone(phone: string): boolean {
  return /^(\+?56)?9\d{8}$/.test(phone.replace(/\s/g, ''))
}

export function isValidEmail(email: string): boolean {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
}
