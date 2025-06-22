<template>
  <li @click="$router.push(path)">
    <a
      class="flex items-center p-2 rounded-lg session-sidebar transition-colors duration-200"
      aria-current="page"
    >
      <span class="ml-3">{{ title }}</span>
    </a>
  </li>
</template>

<script setup>
const props = defineProps({
  title: String,
  path: String,
});
</script>

<style scoped>
.session-sidebar {
  font-weight: bolder;
}
.session-sidebar:hover {
  background-color: var(--color-secondary);
}
</style>
