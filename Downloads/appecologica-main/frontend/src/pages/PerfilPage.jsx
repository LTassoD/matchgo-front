import { Card, Badge } from 'react-bootstrap';
import { useAuth } from '../hooks/useAuth.js';

function PerfilPage() {
  const { user } = useAuth();

  return (
    <section>
      <h2 className="page-title">Mi perfil</h2>
      <p className="text-muted">Información básica del usuario autenticado.</p>

      <Card>
        <Card.Body>
          <h5 className="mb-3">{user?.nombre}</h5>
          <p className="mb-1">
            Rol: <Badge bg="info">{user?.role}</Badge>
          </p>
          <p className="text-muted">El rol determina qué opciones verás en el menú.</p>
        </Card.Body>
      </Card>
    </section>
  );
}

export default PerfilPage;
