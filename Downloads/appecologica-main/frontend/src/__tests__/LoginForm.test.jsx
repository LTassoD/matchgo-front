import { render, screen, fireEvent } from '@testing-library/react';
import { vi } from 'vitest';
import LoginForm from '../components/LoginForm.jsx';

describe('LoginForm', () => {
  it('ejecuta el callback con las credenciales', () => {
    const handleSubmit = vi.fn();

    render(<LoginForm onSubmit={handleSubmit} />);

    fireEvent.change(screen.getByLabelText(/correo/i), { target: { value: 'admin@demo.com' } });
    fireEvent.change(screen.getByLabelText(/contraseña/i), { target: { value: 'secret' } });
    fireEvent.submit(screen.getByRole('form', { name: /login-form/i }));

    expect(handleSubmit).toHaveBeenCalledWith({
      identifier: 'admin@demo.com',
      password: 'secret'
    });
  });
});
