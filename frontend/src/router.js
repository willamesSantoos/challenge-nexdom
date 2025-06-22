import { createRouter, createWebHistory } from "vue-router";
import Dashboard from "./views/Dashboard.vue";
import ProductTable from "./views/ProductTable.vue";

const routes = [
  { path: "/", component: Dashboard },
  { path: "/products", component: ProductTable },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
