<template>
  <div class="p-6 overflow-auto">
    <HeaderSession
      title="Relatório de Lucros por Produto"
      description="Visualize o desempenho financeiro detalhado de cada produto, com receitas, custos e lucros calculados para auxiliar na tomada de decisões."
    />

    <div
      class="bg-white dark:bg-gray-800 rounded-xl shadow-sm border border-gray-200 dark:border-gray-700 overflow-hidden mt-6"
    >
      <div class="p-4 border-b border-gray-200 dark:border-gray-700">
        <h3 class="text-lg font-medium text-gray-800 dark:text-white">
          Produtos
        </h3>
        <p class="text-sm text-gray-500 dark:text-gray-400">
          Exibindo {{ productsPageable.numberOfElements }} de
          {{ productsPageable.totalElements }} produtos
        </p>
      </div>
    </div>

    <div class="overflow-x-auto">
      <table class="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
        <thead class="bg-gray-50 dark:bg-gray-700">
          <tr>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
            >
              Produto
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
            >
              Quantidade Vendida
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
            >
              Receita Total
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
            >
              Custo Total
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
            >
              Lucro
            </th>
          </tr>
        </thead>
        <tbody
          class="bg-white dark:bg-gray-800 divide-y divide-gray-200 dark:divide-gray-700"
        >
          <tr
            v-for="(product, index) in productsPageable.content"
            :key="index"
            class="hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors"
          >
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm font-medium text-gray-900 dark:text-white">
                {{ product.description }}
              </div>
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
            >
              {{ product.totalQuantity }}
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
            >
              {{
                product.totalRevenue
                  ? formatCurrency(product.totalRevenue)
                  : "-"
              }}
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
            >
              {{ product.totalCost ? formatCurrency(product.totalCost) : "-" }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm text-gray-900 dark:text-white font-semibold">
                {{
                  product.totalProfit
                    ? formatCurrency(product.totalProfit)
                    : "-"
                }}
              </div>
            </td>
          </tr>

          <tr v-if="productsPageable.totalElements === 0">
            <td colspan="4" class="px-6 py-12 text-center">
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

        <button
          v-for="page in productsPageable.totalPages"
          :key="page"
          @click="currentPage = page - 1"
          :class="{
            'z-10 bg-primary-50 dark:bg-gray-600 border-primary-500 dark:border-primary-400 text-primary-600 dark:text-primary-300':
              currentPage === page - 1,
          }"
          class="relative inline-flex items-center px-4 py-2 border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-sm font-medium text-gray-700 dark:text-gray-200 hover:bg-gray-50 dark:hover:bg-gray-600"
        >
          {{ page }}
        </button>

        <button
          @click="nextPage"
          :disabled="currentPage === productsPageable.totalPages - 1"
          :class="{
            'opacity-50 cursor-not-allowed':
              currentPage === productsPageable.totalPages - 1,
          }"
          class="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-sm font-medium text-gray-500 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-600"
        >
          <span class="sr-only">Próxima</span>
          <i class="fas fa-chevron-right"></i>
        </button>
      </nav>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { formatCurrency } from "../utils/utils.js";
import { loadProfitReport } from "../services/reportService.js";
import HeaderSession from "../components/HeaderSession.vue";

const currentPage = ref(0);
const itemsPerPage = ref(5);
const productsPageable = ref({
  content: [],
  totalElements: 0,
  numberOfElements: 0,
  totalPages: 0,
});

async function loadData() {
  productsPageable.value = await loadProfitReport(
    currentPage.value,
    itemsPerPage.value
  );
}

function prevPage() {
  if (currentPage.value > 0) {
    currentPage.value--;
  }
}

function nextPage() {
  if (currentPage.value < productsPageable.value.totalPages - 1) {
    currentPage.value++;
  }
}

watch([currentPage, itemsPerPage], loadData);

onMounted(loadData);
</script>
