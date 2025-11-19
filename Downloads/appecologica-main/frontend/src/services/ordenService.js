import apiClient from './apiClient.js';

export const updateOrden = async (ordenId, payload) => {
  const { data } = await apiClient.put(`/ordenes/${ordenId}`, payload);
  return data;
};
