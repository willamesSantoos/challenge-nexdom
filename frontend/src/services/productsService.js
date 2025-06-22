import api from "../http/axios.config.js";

export const loadProductsTypes = async () => {
  const response = await api.get("/products/types");
  return response.data;
};
