import apiClient from './apiClient.js';

export const fetchClientes = async () => {
  const { data } = await apiClient.get('/clientes');
  return data;
};

export const createCliente = async (payload) => {
  const { data } = await apiClient.post('/clientes', payload);
  return data;
};
