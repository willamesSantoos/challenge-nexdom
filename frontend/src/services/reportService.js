import api from "../http/axios.config.js";

export const loadProfitReport = async () => {
  const response = await api.get("/movements/profit-by-product");

  console.log("Dados do relatório de lucro:", response.data);
  return response.data;
};