<script setup>
import Header from "./components/Header.vue";
import SideBar from "./components/SideBar.vue";
</script>

<template>
  <div>
    <Header />
    <div class="h-screen">
      <SideBar />
      <router-view class="pt-20 bg-white" />
    </div>
  </div>
</template>
