<template>
  <div
    v-if="showModal"
    class="fixed inset-0 z-50 flex items-center justify-center overflow-y-auto bg-modal"
  >
    <div class="relative w-full max-w-3/5 p-4">
      <div class="rounded-lg bg-white shadow">
        <div class="flex items-center justify-between border-b p-4">
          <h3 class="text-xl font-semibold text-gray-900">
            Movimentação de Estoque
          </h3>
          <button
            @click="closeModal"
            class="h-8 w-8 text-gray-400 hover:text-gray-900"
          >
            <i class="fa-solid fa-xmark"></i>
          </button>
        </div>

        <form @submit.prevent="submitForm" class="space-y-4 p-5">
          <div>
            <label class="block text-sm font-medium text-gray-900"
              >Produto</label
            >
            <select v-model="formData.productId" required class="input">
              <option value="" disabled>Selecione um produto</option>
              <option
                v-for="product in products"
                :key="product.id"
                :value="product.id"
              >
                {{ product.description }}
              </option>
            </select>
            <p v-if="errors.productId" class="text-sm text-red-600">
              {{ errors.productId }}
            </p>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-900"
              >Tipo de Movimentação</label
            >
            <select v-model="formData.movementType" required class="input">
              <option value="" disabled selected>Selecione um tipo</option>
              <option value="IN">Entrada</option>
              <option value="OUT">Saída</option>
            </select>
            <p v-if="errors.movementType" class="text-sm text-red-600">
              {{ errors.movementType }}
            </p>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-900"
              >Preço
              {{
                formData.movementType == "IN" ? "do Fornecedor" : "de  Venda"
              }}</label
            >
            <input
              :readonly="formData.movementType == 'IN'"
              type="number"
              v-model="formData.salePrice"
              class="input"
              min="0.01"
              step="0.01"
              required
            />
            <p v-if="errors.salePrice" class="text-sm text-red-600">
              {{ errors.salePrice }}
            </p>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-900"
              >Data da Movimentação</label
            >
            <input
              type="datetime-local"
              v-model="formData.movementDate"
              class="input"
              required
            />
            <p v-if="errors.movementDate" class="text-sm text-red-600">
              {{ errors.movementDate }}
            </p>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-900"
              >Quantidade</label
            >
            <input
              type="number"
              v-model="formData.quantity"
              class="input"
              min="1"
              required
            />
            <p v-if="errors.quantity" class="text-sm text-red-600">
              {{ errors.quantity }}
            </p>
          </div>

          <div class="flex justify-end space-x-2 pt-2">
            <button
              type="button"
              @click="closeModal"
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
</template>

<script setup>
import { reactive, computed, watch, defineModel, toRefs } from "vue";

const props = defineProps({
  showModal: Boolean,
  products: Array,
});
const emit = defineEmits(["close", "submit"]);

const formData = defineModel("formData"); // Isso é um ref()

const { products: productsRef } = toRefs(props);

const errors = reactive({
  productId: "",
  movementType: "",
  salePrice: "",
  movementDate: "",
  quantity: "",
});

function closeModal() {
  emit("close");
}

function validateForm() {
  let valid = true;
  Object.keys(errors).forEach((k) => (errors[k] = ""));

  const data = formData.value;

  if (!data.productId) {
    errors.productId = "O produto é obrigatório.";
    valid = false;
  }
  if (!data.movementType) {
    errors.movementType = "O tipo de movimentação é obrigatório.";
    valid = false;
  }
  if (!data.salePrice || data.salePrice <= 0) {
    errors.salePrice = "O preço de venda deve ser maior que zero.";
    valid = false;
  }
  if (!data.movementDate) {
    errors.movementDate = "A data da movimentação é obrigatória.";
    valid = false;
  }
  if (!data.quantity || data.quantity <= 0) {
    errors.quantity = "A quantidade deve ser maior que zero.";
    valid = false;
  }

  return valid;
}

function submitForm() {
  if (validateForm()) {
    emit("submit");
  }
}

const supplierPrice = computed(() => {
  const product = productsRef.value.find(
    (p) => p.id === formData.value.productId
  );

  return product?.supplierPrice || 0;
});

watch(
  () => formData.value.movementType,
  (newType) => {
    if (newType === "IN") {
      formData.value.salePrice = supplierPrice.value;
    } else {
      formData.value.salePrice = 0;
    }
  },
  { immediate: true }
);

watch(
  () => formData.value.productId,
  () => {
    if (formData.value.movementType === "IN") {
      formData.value.salePrice = supplierPrice.value;
    }
  }
);
</script>

<style scoped>
.bg-modal {
  background-color: rgba(0, 0, 0, 0.7);
}
.input {
  width: 100%;
  padding: 0.5rem;
  border-radius: 0.375rem;
  border: 1px solid #d1d5db;
  background-color: #f9fafb;
  font-size: 0.875rem;
}

.btn-secondary {
  background-color: white;
  color: #6b7280;
  border: 1px solid #d1d5db;
  padding: 0.5rem 1rem;
  border-radius: 0.375rem;
}
</style>
