import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth.js';

function RoleGuard({ roles }) {
  const { hasRole } = useAuth();

  if (!hasRole(roles)) {
    return <Navigate to="/" replace />;
  }

  return <Outlet />;
}

export default RoleGuard;
