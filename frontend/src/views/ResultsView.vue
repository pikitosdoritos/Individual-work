<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useResultsStore } from '../stores/resultsStore'
import { getTestById } from '../api/tests'
import LoadingSpinner from '../components/LoadingSpinner.vue'
import AlertMessage from '../components/AlertMessage.vue'

const route = useRoute()
const router = useRouter()
const store = useResultsStore()

const test = ref(null)
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    const [testRes] = await Promise.all([
      getTestById(route.params.testId),
      store.fetchResultsByTestId(route.params.testId)
    ])
    test.value = testRes.data
  } catch {
    error.value = 'Failed to load results.'
  } finally {
    loading.value = false
  }
})

function pct(score, total) {
  return total > 0 ? Math.round(score / total * 100) : 0
}

function barColor(p) {
  if (p >= 70) return 'var(--success)'
  if (p >= 50) return 'var(--warning)'
  return 'var(--danger)'
}

function fmt(date) {
  return new Date(date).toLocaleString('uk-UA', { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit' })
}
</script>

<template>
  <div class="page">
    <div class="container">
      <div class="flex items-center gap-3 mb-6">
        <button class="btn btn-ghost btn-sm btn-icon" @click="router.push('/tests')">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M19 12H5m7-7-7 7 7 7"/></svg>
        </button>
        <div>
          <h1 class="page-title">Results{{ test ? ': ' + test.title : '' }}</h1>
          <p class="page-subtitle" style="margin-bottom:0">All student submissions for this test</p>
        </div>
      </div>

      <LoadingSpinner v-if="loading" />
      <AlertMessage v-else-if="error" type="danger" :message="error" />

      <div v-else-if="store.results.length === 0" class="empty-state card">
        <span class="empty-icon">📊</span>
        <span class="empty-text">No submissions yet for this test.</span>
        <RouterLink :to="`/tests/${route.params.testId}`" class="btn btn-outline mt-3">Take this test</RouterLink>
      </div>

      <div v-else>
        <!-- Summary -->
        <div class="grid-3 mb-4">
          <div class="card card-body">
            <span class="text-sm text-muted mb-1">Total submissions</span>
            <span style="font-size: 24px; font-weight: 700;">{{ store.results.length }}</span>
          </div>
          <div class="card card-body">
            <span class="text-sm text-muted mb-1">Average score</span>
            <span style="font-size: 24px; font-weight: 700;">
              {{ (store.results.reduce((s, r) => s + r.score, 0) / store.results.length).toFixed(1) }}
              / {{ store.results[0]?.totalQuestions }}
            </span>
          </div>
          <div class="card card-body">
            <span class="text-sm text-muted mb-1">Pass rate (≥50%)</span>
            <span style="font-size: 24px; font-weight: 700;">
              {{ Math.round(store.results.filter(r => pct(r.score, r.totalQuestions) >= 50).length / store.results.length * 100) }}%
            </span>
          </div>
        </div>

        <!-- Table -->
        <div class="table-wrap">
          <table>
            <thead>
              <tr>
                <th>#</th>
                <th>Student</th>
                <th>Score</th>
                <th style="min-width: 180px;">Progress</th>
                <th>Date</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(r, idx) in store.results" :key="r.id">
                <td class="text-muted" style="width:36px">{{ idx + 1 }}</td>
                <td class="font-medium">{{ r.studentName }}</td>
                <td>
                  <span class="font-semibold">{{ r.score }}</span>
                  <span class="text-muted"> / {{ r.totalQuestions }}</span>
                </td>
                <td>
                  <div class="flex items-center gap-2">
                    <div class="progress-bar-wrap flex-1">
                      <div class="progress-bar-fill" :style="`width:${pct(r.score,r.totalQuestions)}%; background:${barColor(pct(r.score,r.totalQuestions))}`"></div>
                    </div>
                    <span class="text-sm text-muted" style="width:34px;text-align:right">{{ pct(r.score,r.totalQuestions) }}%</span>
                  </div>
                </td>
                <td class="text-muted text-sm">{{ fmt(r.submittedAt) }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>
