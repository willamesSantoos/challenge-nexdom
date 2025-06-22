<template>
  <div class="p-6 space-y-8 overflow-auto">
    <HeaderSession
      title="Controle de Estoque"
      description="Monitoramento de entradas e saídas de produtos"
    />

    <div class="w-full">
      <div class="bg-white p-6 rounded-xl shadow border border-gray-100">
        <div class="flex justify-between items-center mb-4">
          <h2 class="text-lg font-semibold text-gray-900">
            Movimentação de Estoque
          </h2>
          <div class="flex space-x-2">
            <button
              @click="setChartRange(7)"
              class="px-3 py-1 text-xs rounded transition cursor-pointer"
              :class="
                chartRange === 7
                  ? 'bg-heightlight'
                  : 'bg-gray-100 hover:bg-gray-200'
              "
            >
              7 dias
            </button>
            <button
              @click="setChartRange(30)"
              class="px-3 py-1 text-xs rounded transition cursor-pointer"
              :class="
                chartRange === 30
                  ? 'bg-heightlight'
                  : 'bg-gray-100 hover:bg-gray-200'
              "
            >
              30 dias
            </button>
          </div>
        </div>
        <div class="h-64 w-4/5 m-auto">
          <canvas ref="movementChart"></canvas>
        </div>
      </div>
    </div>

    <div class="bg-white p-6 rounded-xl shadow border border-gray-100">
      <div class="flex justify-between items-center mb-4">
        <h2 class="text-lg font-semibold text-gray-900">
          Últimas Movimentações
        </h2>
        <button
          @click="$router.push('/movements')"
          class="cursor-pointer bg-main text-white px-3 py-1 rounded text-sm flex items-center space-x-1 transition"
        >
          <span>Ver todas</span>
          <i class="fas fa-chevron-right text-xs"></i>
        </button>
      </div>
      <div class="overflow-x-auto">
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
            <tr>
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
              >
                Data
              </th>
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
              >
                Produto
              </th>
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
              >
                Tipo
              </th>
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
              >
                Quantidade
              </th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="(movement, index) in recentMovements" :key="index">
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                {{ formatarData(movement.movementDate) }}
              </td>
              <td
                class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900"
              >
                {{ movement.product.description }}
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
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                {{ movement.quantity }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { Chart, registerables } from "chart.js";
import { formatarData } from "../utils/utils.js";
import {
  loadMovementsRecent,
  loadMovementsChart,
} from "../services/movementsService.js";
import HeaderSession from "../components/HeaderSession.vue";

Chart.register(...registerables);

const chartRange = ref(7);
const movementChart = ref(null);

var recentMovements = ref([]);
var chartData = ref([]);

const setChartRange = (range) => {
  chartRange.value = range;
};

function createMovementChart() {
  if (!movementChart.value) return;

  if (movementChart.value.chart) {
    movementChart.value.chart.destroy();
  }

  movementChart.value.chart = new Chart(movementChart.value, {
    type: "bar",
    data: chartData.value,
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        y: {
          beginAtZero: true,
          title: {
            display: true,
            text: "Quantidade",
          },
        },
        x: {
          title: {
            display: true,
            text: "Data",
          },
        },
      },
      plugins: {
        tooltip: {
          callbacks: {
            label: function (context) {
              return `${context.dataset.label}: ${context.raw} unidades`;
            },
          },
        },
      },
    },
  });
}

async function loadDataAndCreateCharts() {
  try {
    recentMovements.value = await loadMovementsRecent();
    chartData.value = await loadMovementsChart(chartRange.value);

    createMovementChart();
  } catch (error) {
    console.error("Erro ao carregar dados do Dashboard:", error);
    alert("Ops! Ocorreu um erro ao carregar os dados do Dashboard.");
  }
}

watch(chartRange, async (newData) => {
  if (newData) {
    chartData.value = await loadMovementsChart(chartRange.value);

    createMovementChart();
  }
});

onMounted(() => {
  loadDataAndCreateCharts();
});
</script>

<style>
canvas {
  width: 100% !important;
  height: 100% !important;
}
</style>
