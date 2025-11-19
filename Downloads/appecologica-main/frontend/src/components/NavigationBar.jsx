import { Navbar, Container, Nav, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth.js';

function NavigationBar() {
  const { isAuthenticated, logout, user } = useAuth();

  return (
    <Navbar bg="dark" data-bs-theme="dark" expand="lg">
      <Container>
        <Navbar.Brand as={Link} to="/">
          App Ecológica
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="main-nav" />
        <Navbar.Collapse id="main-nav">
          <Nav className="me-auto">
            {isAuthenticated() && (
              <>
                <Nav.Link as={Link} to="/">
                  Inicio
                </Nav.Link>
                <Nav.Link as={Link} to="/productos">
                  Materiales
                </Nav.Link>
                <Nav.Link as={Link} to="/pedidos">
                  Órdenes
                </Nav.Link>
                <Nav.Link as={Link} to="/clientes">
                  Clientes
                </Nav.Link>
                <Nav.Link as={Link} to="/vehiculos">
                  Vehículos
                </Nav.Link>
              </>
            )}
          </Nav>
          <Nav>
            {isAuthenticated() ? (
              <>
                <Nav.Link as={Link} to="/perfil">
                  {user?.nombre ?? 'Perfil'}
                </Nav.Link>
                <Button variant="outline-light" className="ms-2" onClick={logout}>
                  Cerrar sesión
                </Button>
              </>
            ) : (
              <>
                <Nav.Link as={Link} to="/login">
                  Login
                </Nav.Link>
                <Nav.Link as={Link} to="/register">
                  Registro
                </Nav.Link>
              </>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavigationBar;
