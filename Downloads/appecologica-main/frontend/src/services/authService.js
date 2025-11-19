import apiClient from './apiClient.js';

export const loginRequest = async (credentials) => {
  const { data } = await apiClient.post('/auth/login', credentials);
  return data;
};

export const registerRequest = async (payload) => {
  const { data } = await apiClient.post('/auth/register', payload);
  return data;
};

export const fetchProfile = async () => {
  const { data } = await apiClient.get('/usuarios/me');
  return data;
};
