import { useState } from 'react';
import { useNavigate, Navigate } from 'react-router-dom';
import Card from 'react-bootstrap/Card';
import LoginForm from '../components/LoginForm.jsx';
import { useAuth } from '../hooks/useAuth.js';
import styles from '../styles/LoginPage.module.css';

function LoginPage() {
  const { login, isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const [errorMessage, setErrorMessage] = useState('');

  if (isAuthenticated()) {
    return <Navigate to="/" replace />;
  }

  const handleSubmit = async (credentials) => {
    try {
      setErrorMessage('');
      await login(credentials);
      navigate('/');
    } catch (error) {
      setErrorMessage(error.response?.data?.message ?? 'No fue posible iniciar sesión');
    }
  };

  return (
    <div className={styles.wrapper}>
      <Card className="shadow-sm">
        <Card.Body>
          <Card.Title className="text-center mb-3">Bienvenido</Card.Title>
          <p className="text-muted text-center">
            Ingresa tus credenciales para acceder al panel administrativo.
          </p>
          <LoginForm onSubmit={handleSubmit} errorMessage={errorMessage} />
        </Card.Body>
      </Card>
    </div>
  );
}

export default LoginPage;
