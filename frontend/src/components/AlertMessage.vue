<script setup>
import { ref, onMounted } from 'vue'
const props = defineProps({ type: { type: String, default: 'danger' }, message: String })
const visible = ref(true)
onMounted(() => setTimeout(() => visible.value = false, 5000))
</script>

<template>
  <div v-if="visible" :class="`alert alert-${type} animate-in`" role="alert">
    <span>{{ message }}</span>
    <button class="alert-close" @click="visible = false">×</button>
  </div>
</template>
