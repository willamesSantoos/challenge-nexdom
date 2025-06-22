<template>
  <div class="p-6 overflow-auto">
    <div class="flex justify-between items-center mb-6">
      <HeaderSession
        title="Produtos"
        description="Gerencie seu catálogo de produtos"
      />
      <div class="flex justify-end mt-4 pt-4">
        <button
          class="cursor-pointer h-10 px-4 py-2 bg-main text-white rounded-lg transition-colors flex items-center justify-center gap-2"
          @click="openModalCreate()"
        >
          <i class="fas fa-file-invoice"></i>
          <span>Cadastrar Produto</span>
        </button>
      </div>
    </div>

    <div
      class="bg-white dark:bg-gray-800 rounded-xl shadow-sm border border-gray-200 dark:border-gray-700 mb-6"
    >
      <div class="p-5">
        <h2 class="text-lg font-medium text-gray-800 dark:text-white mb-4">
          Filtrar produtos
        </h2>

        <div class="grid grid-cols-1 md:grid-cols-12 gap-4">
          <div class="md:col-span-4">
            <label
              class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1"
              >Descrição</label
            >
            <div class="relative">
              <input
                type="text"
                placeholder="Buscar por nome"
                class="w-full pl-10 pr-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:text-white"
              />
              <div
                class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
              >
                <i class="fas fa-search text-gray-400"></i>
              </div>
            </div>
          </div>

          <div class="md:col-span-3">
            <label
              class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1"
              >Categoria</label
            >
            <div class="relative">
              <select
                class="w-full pl-10 pr-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:text-white appearance-none"
              >
                <option value="">Todas</option>
                <option
                  v-for="category in categories"
                  :key="category"
                  :value="category"
                >
                  {{ translateCategories(category) }}
                </option>
              </select>
              <div
                class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
              >
                <i class="fas fa-tag text-gray-400"></i>
              </div>
            </div>
          </div>

          <div class="md:col-span-5">
            <label
              class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1"
              >Faixa de Preço</label
            >
            <div class="flex items-center gap-2">
              <div class="relative flex-1">
                <input
                  type="number"
                  min="0"
                  step="0.01"
                  placeholder="Mínimo"
                  class="w-full pl-8 pr-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:text-white"
                />
                <div
                  class="absolute inset-y-0 left-0 pl-2 flex items-center pointer-events-none"
                >
                  <span class="text-gray-400">R$</span>
                </div>
              </div>
              <span class="text-gray-400">a</span>
              <div class="relative flex-1">
                <input
                  type="number"
                  min="0"
                  step="0.01"
                  placeholder="Máximo"
                  class="w-full pl-8 pr-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:text-white"
                />
                <div
                  class="absolute inset-y-0 left-0 pl-2 flex items-center pointer-events-none"
                >
                  <span class="text-gray-400">R$</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div
          class="flex justify-end gap-3 mt-6 pt-4 border-t border-gray-200 dark:border-gray-700"
        >
          <button
            @click="resetFilters"
            type="button"
            class="px-4 py-2 text-sm font-medium text-gray-700 dark:text-gray-300 bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 rounded-lg transition-colors"
          >
            Limpar
          </button>
          <button
            @click="applyFilters"
            type="button"
            class="px-4 py-2 text-sm font-medium text-white bg-primary-600 hover:bg-primary-700 rounded-lg transition-colors flex items-center gap-2"
          >
            <i class="fas fa-filter"></i>
            Aplicar Filtros
          </button>
        </div>
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
            Produtos cadastrados
          </h3>
          <p class="text-sm text-gray-500 dark:text-gray-400">
            Exibindo {{ productsPageable.numberOfElements }} de
            {{ productsPageable.totalElements }} produtos
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
                Código
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
                Categoria
              </th>
              <th
                scope="col"
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
              >
                Preço
              </th>
              <th
                scope="col"
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
              >
                Estoque
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
              v-for="product in productsPageable.content"
              :key="product.id"
              class="hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors"
            >
              <td
                class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 dark:text-white"
              >
                {{ product.code }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                <div class="text-sm font-medium text-gray-900 dark:text-white">
                  {{ product.description }}
                </div>
              </td>
              <td
                class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
              >
                {{ translateCategories(product.productType) }}
              </td>
              <td
                class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 dark:text-white"
              >
                {{ formatCurrency(product.supplierPrice) }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                <div class="text-sm text-gray-900 dark:text-white">
                  {{ product.stockQuantity }}
                </div>
              </td>
              <td
                class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
              >
                <div class="flex justify-end gap-2">
                  <BtnEdit @click="openModalEdit(product.id)" />
                  <BtnDelete @click="confirmDelete(product.id)" />
                </div>
              </td>
            </tr>
            <tr v-if="productsPageable.totalElements === 0">
              <td colspan="7" class="px-6 py-12 text-center">
                <div
                  class="flex flex-col items-center justify-center text-gray-400 dark:text-gray-500"
                >
                  <i class="fas fa-box-open text-4xl mb-3"></i>
                  <p class="text-lg font-medium">Nenhum produto encontrado</p>
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

              <template v-for="page in productsPageable.totalPages" :key="page">
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
                :disabled="currentPage === productsPageable.totalPages"
                :class="{
                  'opacity-50 cursor-not-allowed':
                    currentPage === productsPageable.totalPages,
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
      <ProductModal
        v-model="showModal"
        @submit="handleSubmit"
        @close="showModal = false"
        :showModal="showModal"
        :categories="categories"
        :formData="formData"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { formatCurrency, translateCategories } from "../utils/utils.js";
import {
  loadProducts,
  deleteProduct,
  loadCategories,
  loadProduct,
  newProduct,
  editProduct,
} from "../services/productsService.js";
import HeaderSession from "../components/HeaderSession.vue";
import BtnDelete from "../components/BtnDelete.vue";
import BtnEdit from "../components/BtnEdit.vue";
import ProductModal from "../components/ProductModal.vue";

const defaultData = {
  id: 0,
  code: "",
  description: "",
  productType: "",
  supplierPrice: 0,
  stockQuantity: 0,
};

const currentPage = ref(1);
const itemsPerPage = ref(5);
const productsPageable = ref({});
const categories = ref([]);
const showModal = ref(false);
const formData = ref(defaultData);

async function handleSubmit() {
  try {
    const productData = (({ id, ...rest }) => rest)(formData.value);

    if (formData.value.id) {
      await editProduct(formData.value.id, productData);
      showSuccessMessage("Produto atualizado com sucesso!");
    } else {
      await newProduct(productData);
      showSuccessMessage("Produto cadastrado com sucesso!");
    }

    showModal.value = false;
    await refreshProducts();
  } catch (error) {
    handleSubmissionError(error);
  }
}

function showSuccessMessage(message) {
  alert(message);
}

function handleSubmissionError(error) {
  console.error("Erro na submissão:", error);
  alert("Erro: Ocorreu um erro inesperado. Tente novamente.");
}

async function refreshProducts() {
  productsPageable.value = await loadProducts(
    currentPage.value - 1,
    itemsPerPage.value
  );
}

function openModalCreate() {
  formData.value = { ...defaultData };
  showModal.value = true;
}

async function openModalEdit(id) {
  formData.value = await loadProduct(id);
  showModal.value = true;
}

async function confirmDelete(id) {
  if (
    confirm(
      "Você tem certeza que deseja excluir este produto? Esta ação não pode ser desfeita."
    )
  ) {
    await deleteProduct(id);
    refreshProducts();
  }
}

watch([currentPage, itemsPerPage], async (newData) => {
  if (newData) {
    let [page, items] = newData;

    productsPageable.value = await loadProducts(--page, items);
  }
});

onMounted(async () => {
  refreshProducts();

  categories.value = await loadCategories();
});
</script>
