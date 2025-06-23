<style scoped>
.bg-modal {
  background-color: rgba(0, 0, 0, 0.7);
}
</style>

<template>
  <div
    v-if="showModal"
    class="fixed inset-0 z-50 flex items-center justify-center overflow-y-auto overflow-x-hidden bg-modal"
  >
    <div class="relative w-full max-w-3/5 p-4">
      <div class="relative rounded-lg bg-white shadow">
        <div
          class="flex items-center justify-between rounded-t border-b p-4 md:p-5"
        >
          <h3 class="text-xl font-semibold text-gray-900">
            Cadastro de Produto
          </h3>
          <button
            @click="closeModal"
            type="button"
            class="ms-auto inline-flex h-8 w-8 items-center justify-center rounded-lg bg-transparent text-sm text-gray-400 hover:bg-gray-200 hover:text-gray-900"
          >
            <i class="fa-solid fa-xmark"></i>
          </button>
        </div>

        <div class="space-y-4 p-4 md:p-5">
          <form @submit.prevent="submitForm">
            <div class="mb-4">
              <label
                for="code"
                class="mb-2 block text-sm font-medium text-gray-900"
                >Código do Produto <span class="text-red-500">*</span></label
              >
              <input
                type="text"
                id="code"
                v-model="formData.code"
                class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
                placeholder="Digite o código do produto"
                required
              />
              <p v-if="errors.code" class="mt-1 text-sm text-red-600">
                {{ errors.code }}
              </p>
            </div>

            <div class="mb-4">
              <label
                for="description"
                class="mb-2 block text-sm font-medium text-gray-900"
                >Descrição <span class="text-red-500">*</span></label
              >
              <input
                type="text"
                id="description"
                v-model="formData.description"
                class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
                placeholder="Digite a descrição do produto"
                maxlength="255"
                required
              />
              <p v-if="errors.description" class="mt-1 text-sm text-red-600">
                {{ errors.description }}
              </p>
            </div>

            <div class="mb-4">
              <label
                for="productType"
                class="mb-2 block text-sm font-medium text-gray-900"
                >Tipo de Produto <span class="text-red-500">*</span></label
              >
              <select
                id="productType"
                v-model="formData.productType"
                class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
                required
              >
                <option value="" disabled selected>Selecione um tipo</option>
                <option
                  v-for="category in categories"
                  :key="category"
                  :value="category"
                >
                  {{ translateCategories(category) }}
                </option>
              </select>
              <p v-if="errors.productType" class="mt-1 text-sm text-red-600">
                {{ errors.productType }}
              </p>
            </div>

            <div class="mb-4">
              <label
                for="supplierPrice"
                class="mb-2 block text-sm font-medium text-gray-900"
                >Preço do Fornecedor <span class="text-red-500">*</span></label
              >
              <input
                type="number"
                id="supplierPrice"
                v-model="formData.supplierPrice"
                class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
                placeholder="0.00"
                min="0.01"
                step="0.01"
                required
              />
              <p v-if="errors.supplierPrice" class="mt-1 text-sm text-red-600">
                {{ errors.supplierPrice }}
              </p>
            </div>

            <div class="flex items-center justify-end space-x-3 pt-2">
              <button
                @click="closeModal"
                type="button"
                class="cursor-pointer rounded-lg border border-gray-200 bg-white px-5 py-2.5 text-sm font-medium text-gray-500 hover:bg-gray-100 hover:text-gray-900 focus:z-10 focus:outline-none focus:ring-4 focus:ring-gray-200"
              >
                Cancelar
              </button>
              <button
                type="submit"
                class="cursor-pointer rounded-lg px-5 py-2.5 text-center text-sm font-medium text-white bg-main focus:outline-none focus:ring-4 focus:ring-blue-300"
              >
                Salvar
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { translateCategories } from "../utils/utils.js";

const { showModal, categories, formData } = defineProps({
  showModal: {
    type: Boolean,
    required: true,
  },
  categories: {
    type: Array,
    default: () => [],
  },
  formData: {
    code: "",
    description: "",
    productType: "",
    supplierPrice: null,
    stockQuantity: null,
  },
});

const emit = defineEmits(["close", "submit"]);

const errors = reactive({
  code: "",
  description: "",
  productType: "",
  supplierPrice: "",
  stockQuantity: "",
});

function closeModal() {
  emit("close");
}

function submitForm() {
  if (validateForm()) {
    emit("submit", { ...formData });
  }
}

function validateForm() {
  let isValid = true;

  Object.keys(errors).forEach((key) => (errors[key] = ""));

  if (!formData.code.trim()) {
    errors.code = "O código do produto é obrigatório.";
    isValid = false;
  }

  if (!formData.description.trim()) {
    errors.description = "A descrição é obrigatória.";
    isValid = false;
  } else if (formData.description.length > 255) {
    errors.description = "A descrição deve ter no máximo 255 caracteres.";
    isValid = false;
  }

  if (!formData.productType) {
    errors.productType = "O tipo de produto é obrigatório.";
    isValid = false;
  }

  if (formData.supplierPrice == null) {
    errors.supplierPrice = "O preço do fornecedor é obrigatório.";
    isValid = false;
  } else if (formData.supplierPrice <= 0) {
    errors.supplierPrice = "O preço do fornecedor deve ser maior que zero.";
    isValid = false;
  }

  if (formData.stockQuantity == null) {
    errors.stockQuantity = "A quantidade em estoque é obrigatória.";
    isValid = false;
  } else if (formData.stockQuantity < 0) {
    errors.stockQuantity = "A quantidade em estoque não pode ser negativa.";
    isValid = false;
  }

  return isValid;
}
</script>
