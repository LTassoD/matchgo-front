import { useState } from 'react';
import { useNavigate, Navigate } from 'react-router-dom';
import { Card, Form, Button, Alert } from 'react-bootstrap';
import { useAuth } from '../hooks/useAuth.js';

function RegisterPage() {
  const { register, isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({
    nombre: '',
    email: '',
    rut: '',
    password: '',
    role: 'USER'
  });
  const [errorMessage, setErrorMessage] = useState('');

  if (isAuthenticated()) {
    return <Navigate to="/" replace />;
  }

  const handleChange = (event) => {
    const { name, value } = event.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      setErrorMessage('');
      await register(form);
      navigate('/');
    } catch (error) {
      setErrorMessage(error.response?.data?.message ?? 'No fue posible registrar el usuario');
    }
  };

  return (
    <div className="d-flex justify-content-center mt-4">
      <Card style={{ maxWidth: 520 }} className="w-100 shadow-sm">
        <Card.Body>
          <Card.Title>Crear cuenta</Card.Title>
          {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
          <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3">
              <Form.Label>Nombre</Form.Label>
              <Form.Control name="nombre" value={form.nombre} onChange={handleChange} required />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Correo</Form.Label>
              <Form.Control
                type="email"
                name="email"
                value={form.email}
                onChange={handleChange}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>RUT</Form.Label>
              <Form.Control name="rut" value={form.rut} onChange={handleChange} required />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Contraseña</Form.Label>
              <Form.Control
                type="password"
                name="password"
                value={form.password}
                onChange={handleChange}
                required
                minLength={6}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Rol</Form.Label>
              <Form.Select name="role" value={form.role} onChange={handleChange}>
                <option value="USER">Usuario</option>
                <option value="CHOFER">Chofer</option>
                <option value="ADMIN">Administrador</option>
              </Form.Select>
            </Form.Group>
            <Button type="submit" className="w-100">
              Registrar
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </div>
  );
}

export default RegisterPage;
