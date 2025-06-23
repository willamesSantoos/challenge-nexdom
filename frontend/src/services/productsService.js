import api from "../http/axios.config.js";
import axios from "axios";

export const loadCategories = async () => {
  const response = await api.get("/products/types");
  return response.data;
};

export const loadProducts = async (page, size, filters = {}) => {
  const params = new URLSearchParams();

  params.set("page", page);
  params.set("size", size);

  const { description, category, stockQuantity } = filters;

  if (description?.trim()) {
    params.set("description", description.trim());
  }

  if (category?.trim()) {
    params.set("productType", category.trim());
  }

  if (stockQuantity !== null && stockQuantity !== undefined && stockQuantity > 0) {
    params.set("stockQuantity", stockQuantity);
  }

  const response = await api.get(`/products?${params.toString()}`);
  return response.data;
};


export const loadProduct = async (id) => {
  const response = await api.get(`/products/${id}`);
  return response.data;
};

export const newProduct = async (data) => {
  const response = await api.post("/products/new", data);
  return response.status;
};

export const editProduct = async (id, data) => {
  const response = await api.put(`/products/edit/${id}`, data);
  return response.status;
};

export const deleteProduct = async (id) => {
  try {
    await api.delete(`/products/delete/${id}`);
    alert("Produto excluído com sucesso!");
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
