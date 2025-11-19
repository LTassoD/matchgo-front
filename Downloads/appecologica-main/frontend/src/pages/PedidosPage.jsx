import { useEffect, useState } from 'react';
import { Alert, Button, Card, Form } from 'react-bootstrap';
import { fetchRutas, fetchOrdenesPorRuta } from '../services/rutaService.js';
import { updateOrden } from '../services/ordenService.js';
import { useAuth } from '../hooks/useAuth.js';

function PedidosPage() {
  const { hasRole } = useAuth();
  const [rutas, setRutas] = useState([]);
  const [selectedRuta, setSelectedRuta] = useState('');
  const [ordenes, setOrdenes] = useState([]);
  const [message, setMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    const load = async () => {
      try {
        const data = await fetchRutas();
        setRutas(data);
        if (data.length > 0) {
          setSelectedRuta(String(data[0].id));
        }
      } catch (error) {
        setErrorMessage('No fue posible cargar las rutas');
      }
    };
    load();
  }, []);

  useEffect(() => {
    if (!selectedRuta) return;
    const loadOrdenes = async () => {
      try {
        const data = await fetchOrdenesPorRuta(selectedRuta);
        setOrdenes(data);
      } catch (error) {
        setErrorMessage('No fue posible cargar las órdenes');
      }
    };
    loadOrdenes();
  }, [selectedRuta]);

  const handleEstado = async (ordenId, estado) => {
    try {
      await updateOrden(ordenId, { estado });
      setMessage('Estado actualizado correctamente');
      setErrorMessage('');
      const data = await fetchOrdenesPorRuta(selectedRuta);
      setOrdenes(data);
    } catch (error) {
      setErrorMessage('No se pudo actualizar la orden');
    }
  };

  return (
    <section>
      <h2 className="page-title">Órdenes de servicio</h2>
      <p className="text-muted">Consulta y actualiza el estado de las órdenes asignadas.</p>

      {message && <Alert variant="success">{message}</Alert>}
      {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}

      <Card className="mb-3">
        <Card.Body>
          <Form.Group className="mb-3">
            <Form.Label>Selecciona una ruta</Form.Label>
            <Form.Select value={selectedRuta} onChange={(e) => setSelectedRuta(e.target.value)}>
              <option value="">-- Selecciona --</option>
              {rutas.map((ruta) => (
                <option key={ruta.id} value={ruta.id}>
                  Ruta #{ruta.id} - {ruta.fecha}
                </option>
              ))}
            </Form.Select>
          </Form.Group>
          {ordenes.map((orden) => (
            <div key={orden.id} className="border rounded p-3 mb-2">
              <h5 className="mb-1">Orden #{orden.id}</h5>
              <p className="mb-1">Punto: {orden.punto?.direccion}</p>
              <p className="mb-1">Material: {orden.punto?.material}</p>
              <p className="mb-3">
                Estado: <strong>{orden.estado}</strong>
              </p>
              {hasRole(['ADMIN', 'CHOFER']) && (
                <div className="d-flex gap-2">
                  <Button variant="outline-primary" onClick={() => handleEstado(orden.id, 'EN_PROCESO')}>
                    Marcar en proceso
                  </Button>
                  <Button variant="outline-success" onClick={() => handleEstado(orden.id, 'COMPLETADA')}>
                    Finalizar
                  </Button>
                </div>
              )}
            </div>
          ))}
        </Card.Body>
      </Card>
    </section>
  );
}

export default PedidosPage;
