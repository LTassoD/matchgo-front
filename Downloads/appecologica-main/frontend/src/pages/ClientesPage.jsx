import { useEffect, useState } from 'react';
import { Alert, Card, Form, Button } from 'react-bootstrap';
import ResourceTable from '../components/ResourceTable.jsx';
import { fetchClientes, createCliente } from '../services/clienteService.js';

const columns = [
  { key: 'nombre', label: 'Nombre' },
  { key: 'direccion', label: 'Dirección' },
  { key: 'contactoEmail', label: 'Email' },
  { key: 'contactoTelefono', label: 'Teléfono' }
];

function ClientesPage() {
  const [clientes, setClientes] = useState([]);
  const [form, setForm] = useState({
    nombre: '',
    direccion: '',
    contactoEmail: '',
    contactoTelefono: ''
  });
  const [message, setMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const loadClientes = async () => {
    try {
      const data = await fetchClientes();
      setClientes(data);
    } catch (error) {
      setErrorMessage('No fue posible cargar los clientes');
    }
  };

  useEffect(() => {
    loadClientes();
  }, []);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      await createCliente(form);
      setForm({ nombre: '', direccion: '', contactoEmail: '', contactoTelefono: '' });
      setMessage('Cliente creado correctamente');
      await loadClientes();
    } catch (error) {
      setErrorMessage(error.response?.data?.message ?? 'No se pudo crear el cliente');
    }
  };

  return (
    <section>
      <h2 className="page-title">Gestión de clientes</h2>
      <p className="text-muted">Registra empresas o instituciones que reciben el servicio.</p>
      {message && <Alert variant="success">{message}</Alert>}
      {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
      <Card className="mb-3">
        <Card.Body>
          <ResourceTable columns={columns} data={clientes} />
        </Card.Body>
      </Card>
      <Card>
        <Card.Body>
          <h5>Nuevo cliente</h5>
          <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3">
              <Form.Label>Nombre</Form.Label>
              <Form.Control name="nombre" value={form.nombre} onChange={handleChange} required />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Dirección</Form.Label>
              <Form.Control
                name="direccion"
                value={form.direccion}
                onChange={handleChange}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                name="contactoEmail"
                value={form.contactoEmail}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Teléfono</Form.Label>
              <Form.Control
                name="contactoTelefono"
                value={form.contactoTelefono}
                onChange={handleChange}
              />
            </Form.Group>
            <Button type="submit">Guardar</Button>
          </Form>
        </Card.Body>
      </Card>
    </section>
  );
}

export default ClientesPage;
