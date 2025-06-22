<template>
  <div class="flex justify-between items-center">
    <div>
      <h1 class="text-2xl font-bold text-gray-900">{{ title }}</h1>
      <p class="text-gray-500">
        {{ description }}
      </p>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const { title, description } = defineProps({
  title: String,
  description: String,
});
</script>
