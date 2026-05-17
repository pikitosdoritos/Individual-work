<script setup>
import { onMounted } from 'vue'
import { useTestsStore } from '../stores/testsStore'
import LoadingSpinner from '../components/LoadingSpinner.vue'
import AlertMessage from '../components/AlertMessage.vue'

const store = useTestsStore()
onMounted(() => store.fetchTests())

function fmt(date) {
  return new Date(date).toLocaleDateString('uk-UA', { day: '2-digit', month: '2-digit', year: 'numeric' })
}
</script>

<template>
  <div class="page">
    <div class="container">
      <div class="flex items-center justify-between mb-6">
        <div>
          <h1 class="page-title">Available Tests</h1>
          <p class="page-subtitle">Choose a test and start right away</p>
        </div>
        <RouterLink to="/manage" class="btn btn-outline">+ New test</RouterLink>
      </div>

      <AlertMessage v-if="store.error" type="danger" :message="store.error" :key="store.error" />
      <LoadingSpinner v-if="store.loading" />

      <div v-else-if="store.tests.length === 0" class="empty-state card">
        <span class="empty-icon">📋</span>
        <span class="empty-text">No tests created yet.</span>
        <RouterLink to="/manage" class="btn btn-outline mt-3">Create your first test</RouterLink>
      </div>

      <div v-else class="grid-3">
        <div v-for="test in store.tests" :key="test.id" class="test-card animate-in">
          <div>
            <div class="flex items-center justify-between mb-1">
              <span class="badge badge-gray">{{ test.subject }}</span>
              <span class="text-sm text-muted">{{ fmt(test.createdAt) }}</span>
            </div>
            <h3 style="font-size: 15px; font-weight: 600; margin: 8px 0 4px; line-height: 1.3;">{{ test.title }}</h3>
            <p class="text-sm text-muted" style="margin: 0;">{{ test.questions?.length ?? 0 }} question{{ test.questions?.length !== 1 ? 's' : '' }}</p>
          </div>
          <div class="divider" style="margin: 12px 0 8px;"></div>
          <div class="flex gap-2">
            <RouterLink :to="`/tests/${test.id}`" class="btn btn-primary flex-1" style="justify-content:center;">Take test</RouterLink>
            <RouterLink :to="`/results/${test.id}`" class="btn btn-ghost">Results</RouterLink>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
