import { render, screen, waitFor } from '@testing-library/react';
import { vi } from 'vitest';
import ProductosPage from '../pages/ProductosPage.jsx';

vi.mock('../services/materialService.js', () => ({
  fetchMateriales: vi.fn().mockResolvedValue([
    { id: 1, nombre: 'Cartón', descripcion: 'Fardos', unidadMedida: 'Kg', precioReferencia: 120 }
  ]),
  createMaterial: vi.fn()
}));

vi.mock('../hooks/useAuth.js', () => ({
  useAuth: () => ({ hasRole: () => true })
}));

describe('ProductosPage', () => {
  it('renderiza la tabla de materiales con datos de la API', async () => {
    render(<ProductosPage />);

    await waitFor(() => {
      expect(screen.getByText('Cartón')).toBeInTheDocument();
    });
    expect(screen.getByText('Fardos')).toBeInTheDocument();
  });
});
