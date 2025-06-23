<template>
  <div class="p-6 overflow-auto">
    <div class="flex justify-between items-center mb-6">
      <HeaderSession
        title="Movimentação de Estoque"
        description="Controle as movimentações de entrada e saída dos produtos"
      />
      <div class="flex justify-end mt-4 pt-4">
        <button
          class="cursor-pointer h-10 px-4 py-2 bg-main text-white rounded-lg transition-colors flex items-center justify-center gap-2"
          @click="openModalCreate()"
        >
          <i class="fas fa-plus"></i>
          <span>Nova Movimentação</span>
        </button>
      </div>
    </div>

    <div
      class="bg-white dark:bg-gray-800 rounded-xl shadow-sm border border-gray-200 dark:border-gray-700 overflow-hidden"
    >
      <div
        class="flex flex-col md:flex-row md:items-center md:justify-between p-4 border-b border-gray-200 dark:border-gray-700"
      >
        <div class="mb-3 md:mb-0">
          <h3 class="text-lg font-medium text-gray-800 dark:text-white">
            Movimentações registradas
          </h3>
          <p class="text-sm text-gray-500 dark:text-gray-400">
            Exibindo {{ movementsPageable.numberOfElements }} de
            {{ movementsPageable.totalElements }} movimentações
          </p>
        </div>

        <div class="flex items-center gap-3">
          <div class="relative">
            <select
              v-model="itemsPerPage"
              class="pl-10 pr-8 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:text-white appearance-none"
            >
              <option value="5">5 por página</option>
              <option value="10">10 por página</option>
              <option value="20">20 por página</option>
              <option value="50">50 por página</option>
            </select>
            <div
              class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
            >
              <i class="fas fa-list-ol text-gray-400"></i>
            </div>
          </div>
        </div>
      </div>

      <div class="overflow-x-auto">
        <table class="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
          <thead class="bg-gray-50 dark:bg-gray-700">
            <tr>
              <th
                scope="col"
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
              >
                Data
              </th>
              <th
                scope="col"
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
              >
                Produto
              </th>
              <th
                scope="col"
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
              >
                Código
              </th>
              <th
                scope="col"
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
              >
                Tipo
              </th>
              <th
                scope="col"
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
              >
                Quantidade
              </th>
              <th
                scope="col"
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
              >
                Preço Unitário
              </th>
              <th
                scope="col"
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
              >
                Valor Total
              </th>
              <th
                scope="col"
                class="px-6 py-3 text-right text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
              >
                Ações
              </th>
            </tr>
          </thead>
          <tbody
            class="bg-white dark:bg-gray-800 divide-y divide-gray-200 dark:divide-gray-700"
          >
            <tr
              v-for="movement in movementsPageable.content"
              :key="movement.id"
              class="hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors"
            >
              <td
                class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 dark:text-white"
              >
                {{ formatDate(movement.movementDate) }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                <div class="text-sm font-medium text-gray-900 dark:text-white">
                  {{ movement.product.description }}
                </div>
              </td>
              <td
                class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
              >
                {{ movement.product.code }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                <span
                  :class="`px-2 py-1 text-xs rounded-full ${
                    movement.movementType === 'IN'
                      ? 'bg-green-100 text-green-800'
                      : 'bg-blue-100 text-blue-800'
                  }`"
                >
                  {{ movement.movementType === "IN" ? "Entrada" : "Saída" }}
                </span>
              </td>
              <td
                class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 dark:text-white"
              >
                {{ movement.quantity }}
              </td>
              <td
                class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 dark:text-white"
              >
                {{ formatCurrency(movement.salePrice) }}
              </td>
              <td
                class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 dark:text-white"
              >
                {{ formatCurrency(movement.salePrice * movement.quantity) }}
              </td>
              <td
                class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
              >
                <div class="flex justify-end gap-2">
                  <BtnEdit @click="openModalEdit(movement.id)" />
                  <BtnDelete @click="confirmDelete(movement.id)" />
                </div>
              </td>
            </tr>
            <tr v-if="movementsPageable.totalElements === 0">
              <td colspan="8" class="px-6 py-12 text-center">
                <div
                  class="flex flex-col items-center justify-center text-gray-400 dark:text-gray-500"
                >
                  <i class="fas fa-box-open text-4xl mb-3"></i>
                  <p class="text-lg font-medium">
                    Nenhuma movimentação encontrada
                  </p>
                  <p class="text-sm">Tente ajustar os filtros de busca</p>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <div
        class="px-4 py-3 flex items-center justify-between border-t border-gray-200 dark:border-gray-700 sm:px-6"
      >
        <div class="hidden sm:flex-1 sm:flex sm:items-center sm:justify-center">
          <div>
            <nav
              class="relative z-0 inline-flex rounded-md shadow-sm -space-x-px"
              aria-label="Pagination"
            >
              <button
                @click="prevPage"
                :disabled="currentPage === 0"
                :class="{ 'opacity-50 cursor-not-allowed': currentPage === 0 }"
                class="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-sm font-medium text-gray-500 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-600"
              >
                <span class="sr-only">Anterior</span>
                <i class="fas fa-chevron-left"></i>
              </button>

              <template
                v-for="page in movementsPageable.totalPages"
                :key="page"
              >
                <button
                  @click="currentPage = page"
                  :class="{
                    'z-10 bg-primary-50 dark:bg-gray-600 border-primary-500 dark:border-primary-400 text-primary-600 dark:text-primary-300':
                      currentPage === page,
                  }"
                  class="relative inline-flex items-center px-4 py-2 border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-sm font-medium text-gray-700 dark:text-gray-200 hover:bg-gray-50 dark:hover:bg-gray-600"
                >
                  {{ page }}
                </button>
              </template>

              <button
                @click="nextPage"
                :disabled="currentPage === movementsPageable.totalPages"
                :class="{
                  'opacity-50 cursor-not-allowed':
                    currentPage === movementsPageable.totalPages,
                }"
                class="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-sm font-medium text-gray-500 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-600"
              >
                <span class="sr-only">Próxima</span>
                <i class="fas fa-chevron-right"></i>
              </button>
            </nav>
          </div>
        </div>
      </div>
    </div>

    <div v-if="showModal" class="modal">
      <MovementModal
        v-model="showModal"
        @submit="handleSubmit"
        @close="showModal = false"
        :showModal="showModal"
        :products="products.content"
        v-model:formData="formData"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import {
  formatCurrency,
  formatDate,
  translateMovementType,
} from "../utils/utils.js";
import {
  loadMovements,
  deleteMovement,
  loadMovement,
  newMovement,
  editMovement,
} from "../services/movementsService.js";
import { loadProducts } from "../services/productsService.js";
import HeaderSession from "../components/HeaderSession.vue";
import BtnDelete from "../components/BtnDelete.vue";
import BtnEdit from "../components/BtnEdit.vue";
import MovementModal from "../components/MovementModal.vue";
import axios from 'axios';

const defaultData = {
  productId: 0,
  movementType: "",
  salePrice: null,
  movementDate: "",
  quantity: null,
};

const currentPage = ref(1);
const itemsPerPage = ref(5);
const movementsPageable = ref({});
const products = ref([]);
const showModal = ref(false);
const formData = ref(defaultData);
const filters = ref({
  productName: "",
  movementType: "",
  startDate: "",
  endDate: "",
});

async function handleSubmit() {
  try {
    const movementData = (({ id, ...rest }) => rest)(formData.value);

    if (formData.value.id) {
      await editMovement(formData.value.id, movementData);
      showSuccessMessage("Movimentação atualizada com sucesso!");
    } else {
      await newMovement(movementData);
      showSuccessMessage("Movimentação cadastrada com sucesso!");
    }

    showModal.value = false;
    await refreshMovements();
  } catch (error) {
    if (axios.isAxiosError(error)) {
      if (error.response) {
        const status = error.response.status;
        const message = error.response.data;

        if (status === 422) {
          alert(message);
        } else {
          handleSubmissionError("Erro ao excluir o produto!");
        }
      }
    }
  }
}

function showSuccessMessage(message) {
  alert(message);
}

function handleSubmissionError(error) {
  console.error("Erro na submissão:", error);
  alert("Erro: Ocorreu um erro inesperado. Tente novamente.");
}

async function refreshMovements() {
  movementsPageable.value = await loadMovements(
    currentPage.value - 1,
    itemsPerPage.value
  );
}

function openModalCreate() {
  formData.value = { ...defaultData };
  showModal.value = true;
}

async function openModalEdit(id) {
  const fullMovement = await loadMovement(id);

  formData.value = {
    id: fullMovement.id,
    productId: fullMovement.product.id,
    movementType: fullMovement.movementType,
    salePrice: fullMovement.salePrice,
    movementDate: fullMovement.movementDate,
    quantity: fullMovement.quantity,
  };

  showModal.value = true;
}

async function confirmDelete(id) {
  if (
    confirm(
      "Você tem certeza que deseja excluir esta movimentação? Esta ação não pode ser desfeita."
    )
  ) {
    await deleteMovement(id);
    refreshMovements();
  }
}

function applyFilters() {
  currentPage.value = 1;
  refreshMovements();
}

function resetFilters() {
  filters.value = {
    productName: "",
    movementType: "",
    startDate: "",
    endDate: "",
  };

  applyFilters();
}


watch(itemsPerPage, async (newData) => {
  if (newData) {
    movementsPageable.value = await loadMovements(0, newData, filters.value);
  }
});

watch(currentPage, async (newData) => {
  if (newData) {
    movementsPageable.value = await loadMovements(--newData, itemsPerPage.value, filters.value);
  }
});

onMounted(async () => {
  refreshMovements();
  products.value = await loadProducts(0, 10000);
});
</script>
