<style scoped>
.session-sidebar {
    font-weight: bolder;
}

.session-sidebar:hover {
  background-color: var(--color-secondary);
}
</style>

<template>
  <li>
    <a
      href="#"
      class="flex items-center p-2 rounded-lg session-sidebar transition-colors duration-200"
      aria-current="page"
    >
      <span class="ml-3">
        {{ session }}
      </span>
    </a>
  </li>
</template>

<script setup>
import { defineProps } from "vue";

const { session } = defineProps({
  session: {
    type: String,
    required: true,
  },
});
</script>
