import api from "../http/axios.config.js";

export const loadMovementsRecent = async () => {
  const response = await api.get("/movements/recent");
  return response.data;
};

export const loadMovementsChart = async (days) => {
  const response = await api.get(`/movements/chart/${days}`);
  return response.data;
};