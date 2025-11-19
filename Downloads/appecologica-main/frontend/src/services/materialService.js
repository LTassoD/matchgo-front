import apiClient from './apiClient.js';

export const fetchMateriales = async () => {
  const { data } = await apiClient.get('/materiales');
  return data;
};

export const createMaterial = async (payload) => {
  const { data } = await apiClient.post('/materiales', payload);
  return data;
};
