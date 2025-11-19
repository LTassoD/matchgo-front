import apiClient from './apiClient.js';

export const fetchRutas = async () => {
  const { data } = await apiClient.get('/rutas');
  return data;
};

export const fetchOrdenesPorRuta = async (rutaId) => {
  const { data } = await apiClient.get(`/ordenes/ruta/${rutaId}`);
  return data;
};
