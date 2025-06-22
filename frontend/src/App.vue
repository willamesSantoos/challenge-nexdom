<script setup>
import Header from "./components/Header.vue";
import Sidebar from "./components/Sidebar.vue";
</script>

<template>
  <div>
    <Header />
    <div class="h-screen flex">
      <Sidebar />
      <router-view class="pt-20 bg-white w-full" />
    </div>
  </div>
</template>
