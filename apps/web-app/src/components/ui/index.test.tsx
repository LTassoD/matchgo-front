// @vitest-environment jsdom
import { describe, it, expect } from 'vitest'
import { render, screen } from '@testing-library/react'
import { Button, Card, Badge, Spinner, Input, Select, Textarea, Checkbox } from './index'

describe('Button', () => {
  it('renderiza con texto', () => {
    render(<Button>Click</Button>)
    expect(screen.getByText('Click')).toBeInTheDocument()
  })

  it('aplica variante primary por defecto', () => {
    render(<Button>Click</Button>)
    expect(screen.getByRole('button')).toHaveClass('bg-primary-500')
  })

  it('aplica variante outline', () => {
    render(<Button variant="outline">Click</Button>)
    expect(screen.getByRole('button')).toHaveClass('border-2')
  })

  it('deshabilita cuando loading', () => {
    render(<Button loading>Click</Button>)
    expect(screen.getByRole('button')).toBeDisabled()
  })

  it('deshabilita con prop disabled', () => {
    render(<Button disabled>Click</Button>)
    expect(screen.getByRole('button')).toBeDisabled()
  })

  it('aplica fullWidth', () => {
    render(<Button fullWidth>Click</Button>)
    expect(screen.getByRole('button')).toHaveClass('w-full')
  })
})

describe('Card', () => {
  it('renderiza children', () => {
    render(<Card><p>contenido</p></Card>)
    expect(screen.getByText('contenido')).toBeInTheDocument()
  })

  it('tiene clases por defecto', () => {
    render(<Card>card</Card>)
    expect(screen.getByText('card')).toHaveClass('bg-white')
    expect(screen.getByText('card')).toHaveClass('rounded-lg')
  })
})

describe('Badge', () => {
  it('renderiza texto', () => {
    render(<Badge>Activo</Badge>)
    expect(screen.getByText('Activo')).toBeInTheDocument()
  })

  it('aplica variante success', () => {
    render(<Badge variant="success">OK</Badge>)
    expect(screen.getByText('OK')).toHaveClass('bg-green-100')
  })

  it('aplica variante error', () => {
    render(<Badge variant="error">Error</Badge>)
    expect(screen.getByText('Error')).toHaveClass('bg-red-100')
  })
})

describe('Spinner', () => {
  it('renderiza con tamaño md por defecto', () => {
    const { container } = render(<Spinner />)
    expect(container.firstChild).toHaveClass('h-8')
  })

  it('aplica tamaño sm', () => {
    const { container } = render(<Spinner size="sm" />)
    expect(container.firstChild).toHaveClass('h-4')
  })
})

describe('Input', () => {
  it('renderiza input con label', () => {
    render(<Input label="Nombre" placeholder="Ingresa nombre" />)
    expect(screen.getByText('Nombre')).toBeInTheDocument()
    expect(screen.getByPlaceholderText('Ingresa nombre')).toBeInTheDocument()
  })

  it('muestra error', () => {
    render(<Input label="Email" error="Email inválido" />)
    expect(screen.getByText('Email inválido')).toBeInTheDocument()
    expect(screen.getByRole('textbox')).toHaveClass('border-red-500')
  })
})

describe('Select', () => {
  const options = [
    { value: '1', label: 'Opción 1' },
    { value: '2', label: 'Opción 2' },
  ]

  it('renderiza opciones', () => {
    render(<Select label="Elige" options={options} />)
    expect(screen.getByText('Opción 1')).toBeInTheDocument()
    expect(screen.getByText('Opción 2')).toBeInTheDocument()
  })

  it('renderiza label', () => {
    render(<Select label="Categoría" options={options} />)
    expect(screen.getByText('Categoría')).toBeInTheDocument()
  })
})

describe('Textarea', () => {
  it('renderiza textarea con label', () => {
    render(<Textarea label="Descripción" />)
    expect(screen.getByText('Descripción')).toBeInTheDocument()
  })

  it('muestra error', () => {
    render(<Textarea label="Comentario" error="Campo requerido" />)
    expect(screen.getByText('Campo requerido')).toBeInTheDocument()
  })
})

describe('Checkbox', () => {
  it('renderiza con label', () => {
    render(<Checkbox label="Acepto términos" />)
    expect(screen.getByText('Acepto términos')).toBeInTheDocument()
  })

  it('renderiza checkbox input', () => {
    render(<Checkbox label="Opción" />)
    expect(screen.getByRole('checkbox')).toBeInTheDocument()
  })
})
