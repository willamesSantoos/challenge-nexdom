import api from "../http/axios.config.js";

export const loadMovementsRecent = async () => {
  const response = await api.get("/movements/recent");
  return response.data;
};

export const loadMovementsChart = async (days) => {
  const response = await api.get(`/movements/chart/${days}`);
  return response.data;
};

export const loadCategories = async () => {
  const response = await api.get("/products/types");
  return response.data;
};

export const loadMovements = async (page, size) => {
  const response = await api.get(`/movements?page=${page}&size=${size}`);
  return response.data;
};

export const loadMovement = async (id) => {
  const response = await api.get(`/movements/${id}`);
  return response.data;
};

export const newMovement = async (data) => {
  console.log(data)
  
  const response = await api.post("/movements/new", data);
  return response.status;
};

export const editMovement = async (id, data) => {
  const response = await api.put(`/movements/edit/${id}`, data);
  return response.status;
};

export const deleteMovement = async (id) => {
  try {
    await api.delete(`/movements/delete/${id}`);
    alert("Movimentação excluída com sucesso!");
  } catch (error) {
    if (axios.isAxiosError(error)) {
      if (error.response) {
        const status = error.response.status;
        const message = error.response.data;

        if (status === 409 || status === 404) {
          alert(message);
        } else {
          alert("Erro ao excluir o produto!");
        }
      }
    }
  }
};