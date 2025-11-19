import { createContext, useCallback, useEffect, useMemo, useState } from 'react';
import PropTypes from 'prop-types';
import { fetchProfile, loginRequest, registerRequest } from '../services/authService.js';

export const AuthContext = createContext(null);

const TOKEN_KEY = 'app-token';
const USER_KEY = 'app-user';

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem(TOKEN_KEY));
  const [user, setUser] = useState(() => {
    const stored = localStorage.getItem(USER_KEY);
    return stored ? JSON.parse(stored) : null;
  });
  const [loadingProfile, setLoadingProfile] = useState(false);

  const logout = useCallback(() => {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    setToken(null);
    setUser(null);
  }, []);

  useEffect(() => {
    if (!token || user) {
      return;
    }
    const loadProfile = async () => {
      try {
        setLoadingProfile(true);
        const profile = await fetchProfile();
        setUser((prev) => {
          const next = { ...prev, ...profile };
          localStorage.setItem(USER_KEY, JSON.stringify(next));
          return next;
        });
      } catch (error) {
        console.error('Error cargando perfil', error);
        logout();
      } finally {
        setLoadingProfile(false);
      }
    };
    loadProfile();
  }, [token, user, logout]);

  const saveSession = (authResponse) => {
    setToken(authResponse.token);
    const nextUser = { nombre: authResponse.nombre, role: authResponse.role };
    setUser(nextUser);
    localStorage.setItem(TOKEN_KEY, authResponse.token);
    localStorage.setItem(USER_KEY, JSON.stringify(nextUser));
  };

  const login = async (credentials) => {
    const data = await loginRequest(credentials);
    saveSession(data);
  };

  const register = async (payload) => {
    const data = await registerRequest(payload);
    saveSession(data);
  };

  const value = useMemo(
    () => ({
      user,
      token,
      loadingProfile,
      login,
      register,
      logout,
      isAuthenticated: () => Boolean(token),
      hasRole: (roles) => {
        if (!roles || roles.length === 0) return true;
        return roles.includes(user?.role);
      }
    }),
    [user, token, loadingProfile, logout]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

AuthProvider.propTypes = {
  children: PropTypes.node
};
