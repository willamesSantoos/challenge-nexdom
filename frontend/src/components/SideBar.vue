<template>
  <aside
    class="side-bar w-60 h-screen bg-main text-white flex flex-col shadow-xl"
    aria-label="Menu principal"
  >
    <div class="pt-20 flex-1 flex flex-col">
      <nav class="flex-1 px-3 py-4 overflow-y-auto">
        <ul class="space-y-2">  
          <SessionSideBar
            v-for="session in ['Dashboard', 'Produtos', 'Movimentações']"
            :key="session"
            :session="session"
          />
        </ul>
      </nav>

      <footer
        class="px-4 py-3 border-t border-green-100 text-xs text-green-100"
      >
        <div class="text-center">&copy; 2025 NEXDOM HealthTech</div>
      </footer>
    </div>
  </aside>
</template>

<script setup>
import SessionSideBar from "./SessionSideBar.vue";
</script>
