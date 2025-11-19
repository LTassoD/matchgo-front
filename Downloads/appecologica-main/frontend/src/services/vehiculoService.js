import apiClient from './apiClient.js';

export const fetchVehiculos = async () => {
  const { data } = await apiClient.get('/vehiculos');
  return data;
};
