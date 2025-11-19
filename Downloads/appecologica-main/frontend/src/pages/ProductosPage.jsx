import { useEffect, useState } from 'react';
import { Alert, Button, Card, Form, Modal } from 'react-bootstrap';
import ResourceTable from '../components/ResourceTable.jsx';
import { fetchMateriales, createMaterial } from '../services/materialService.js';
import { useAuth } from '../hooks/useAuth.js';

const columns = [
  { key: 'nombre', label: 'Nombre' },
  { key: 'descripcion', label: 'Descripción' },
  { key: 'unidadMedida', label: 'Unidad' },
  { key: 'precioReferencia', label: 'Precio' }
];

function ProductosPage() {
  const { hasRole } = useAuth();
  const [materiales, setMateriales] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [form, setForm] = useState({
    nombre: '',
    descripcion: '',
    unidadMedida: '',
    precioReferencia: 0
  });

  const loadMateriales = async () => {
    try {
      const data = await fetchMateriales();
      setMateriales(data);
    } catch (error) {
      setErrorMessage('No fue posible cargar los materiales');
    }
  };

  useEffect(() => {
    loadMateriales();
  }, []);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      await createMaterial({ ...form, precioReferencia: Number(form.precioReferencia) });
      setShowModal(false);
      setForm({ nombre: '', descripcion: '', unidadMedida: '', precioReferencia: 0 });
      await loadMateriales();
    } catch (error) {
      setErrorMessage(error.response?.data?.message ?? 'No se pudo crear el material');
    }
  };

  return (
    <section>
      <div className="d-flex justify-content-between align-items-center mb-3">
        <div>
          <h2 className="page-title">Materiales</h2>
          <p className="text-muted">Listado de materiales con los que trabaja la empresa.</p>
        </div>
        {hasRole(['ADMIN']) && (
          <Button onClick={() => setShowModal(true)}>Nuevo material</Button>
        )}
      </div>
      {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
      <Card>
        <Card.Body>
          <ResourceTable columns={columns} data={materiales} />
        </Card.Body>
      </Card>

      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Form onSubmit={handleSubmit}>
          <Modal.Header closeButton>
            <Modal.Title>Registrar material</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form.Group className="mb-3">
              <Form.Label>Nombre</Form.Label>
              <Form.Control name="nombre" value={form.nombre} onChange={handleChange} required />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Descripción</Form.Label>
              <Form.Control
                name="descripcion"
                value={form.descripcion}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Unidad de medida</Form.Label>
              <Form.Control
                name="unidadMedida"
                value={form.unidadMedida}
                onChange={handleChange}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Precio referencia</Form.Label>
              <Form.Control
                type="number"
                name="precioReferencia"
                value={form.precioReferencia}
                onChange={handleChange}
                min={0}
                required
              />
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={() => setShowModal(false)}>
              Cancelar
            </Button>
            <Button type="submit">Guardar</Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </section>
  );
}

export default ProductosPage;
