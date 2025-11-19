import { Routes, Route } from 'react-router-dom';
import LoginPage from '../pages/LoginPage.jsx';
import RegisterPage from '../pages/RegisterPage.jsx';
import HomePage from '../pages/HomePage.jsx';
import ProductosPage from '../pages/ProductosPage.jsx';
import PedidosPage from '../pages/PedidosPage.jsx';
import ClientesPage from '../pages/ClientesPage.jsx';
import VehiculosPage from '../pages/VehiculosPage.jsx';
import PerfilPage from '../pages/PerfilPage.jsx';
import NotFoundPage from '../pages/NotFoundPage.jsx';
import ProtectedRoute from '../components/ProtectedRoute.jsx';
import RoleGuard from '../components/RoleGuard.jsx';

function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />

      <Route element={<ProtectedRoute />}>
        <Route path="/" element={<HomePage />} />
        <Route path="/productos" element={<ProductosPage />} />
        <Route path="/pedidos" element={<PedidosPage />} />
        <Route path="/perfil" element={<PerfilPage />} />

        <Route element={<RoleGuard roles={['ADMIN']} />}>
          <Route path="/clientes" element={<ClientesPage />} />
          <Route path="/vehiculos" element={<VehiculosPage />} />
        </Route>
      </Route>

      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  );
}

export default AppRoutes;
