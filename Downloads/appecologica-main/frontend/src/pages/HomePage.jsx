import { useEffect, useState } from 'react';
import { Card, Col, Row, Spinner, Alert } from 'react-bootstrap';
import { fetchRutas } from '../services/rutaService.js';
import { useAuth } from '../hooks/useAuth.js';

function HomePage() {
  const { user } = useAuth();
  const [rutas, setRutas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        const data = await fetchRutas();
        setRutas(data);
      } catch (error) {
        setErrorMessage('No fue posible cargar las rutas');
      } finally {
        setLoading(false);
      }
    };
    load();
  }, []);

  return (
    <section>
      <div className="mb-4">
        <h1 className="page-title">Hola, {user?.nombre ?? 'usuario'} 👋</h1>
        <p className="text-muted">
          Desde aquí puedes controlar las rutas, materiales y clientes del servicio de reciclaje.
        </p>
      </div>

      {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}

      {loading ? (
        <div className="text-center">
          <Spinner animation="border" />
        </div>
      ) : (
        <Row xs={1} md={2} lg={3} className="g-3">
          {rutas.map((ruta) => (
            <Col key={ruta.id}>
              <Card>
                <Card.Body>
                  <Card.Title>Ruta #{ruta.id}</Card.Title>
                  <p className="mb-1">
                    Fecha: <strong>{ruta.fecha}</strong>
                  </p>
                  <p className="mb-1">Chofer: {ruta.chofer?.nombre ?? 'Por asignar'}</p>
                  <p className="mb-0">Órdenes: {ruta.ordenes?.length ?? 0}</p>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      )}
    </section>
  );
}

export default HomePage;
