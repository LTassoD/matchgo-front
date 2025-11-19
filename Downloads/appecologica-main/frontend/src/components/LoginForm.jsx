import { useState } from 'react';
import { Form, Button, Alert } from 'react-bootstrap';

function LoginForm({ onSubmit, errorMessage }) {
  const [identifier, setIdentifier] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    onSubmit({ identifier, password });
  };

  return (
    <Form onSubmit={handleSubmit} aria-label="login-form">
      {errorMessage && (
        <Alert variant="danger" data-testid="login-error">
          {errorMessage}
        </Alert>
      )}
      <Form.Group className="mb-3">
        <Form.Label>Correo o RUT</Form.Label>
        <Form.Control
          type="text"
          value={identifier}
          onChange={(event) => setIdentifier(event.target.value)}
          placeholder="usuario@correo.com o 11111111-1"
          required
        />
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>Contraseña</Form.Label>
        <Form.Control
          type="password"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
          required
        />
      </Form.Group>
      <Button type="submit" variant="success" className="w-100">
        Ingresar
      </Button>
    </Form>
  );
}

export default LoginForm;
