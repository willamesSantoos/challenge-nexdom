import { createRouter, createWebHistory } from "vue-router";
import Dashboard from "./views/Dashboard.vue";
import ProductTable from "./views/ProductTable.vue";
import MovementTable from "./views/MovementTable.vue";
import ProfitbyProduct from "./views/ProfitbyProduct.vue";

const routes = [
  { path: "/", component: Dashboard },
  { path: "/products", component: ProductTable },
  { path: "/movements", component: MovementTable },
  { path: "/profit-by-product", component: ProfitbyProduct },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
