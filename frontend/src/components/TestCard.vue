<script setup>
defineProps({ test: Object })
</script>

<template>
  <div class="card h-100 shadow-sm border-0">
    <div class="card-body d-flex flex-column">
      <div class="d-flex justify-content-between align-items-start mb-2">
        <h5 class="card-title mb-0 fw-bold">{{ test.title }}</h5>
        <span class="badge bg-primary rounded-pill">{{ test.subject }}</span>
      </div>
      <p class="text-muted small mb-1">
        <i class="bi bi-question-circle"></i> {{ test.questions?.length ?? 0 }} questions
      </p>
      <p class="text-muted small mb-3">
        <i class="bi bi-calendar"></i> {{ new Date(test.createdAt).toLocaleDateString() }}
      </p>
      <div class="mt-auto d-flex gap-2">
        <RouterLink :to="`/tests/${test.id}`" class="btn btn-primary btn-sm flex-grow-1">Take Test</RouterLink>
        <RouterLink :to="`/results/${test.id}`" class="btn btn-outline-secondary btn-sm">Results</RouterLink>
      </div>
    </div>
  </div>
</template>
