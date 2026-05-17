<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getTestById } from '../api/tests'
import { useResultsStore } from '../stores/resultsStore'
import { useAuthStore } from '../stores/authStore'
import AlertMessage from '../components/AlertMessage.vue'
import LoadingSpinner from '../components/LoadingSpinner.vue'

const route = useRoute()
const router = useRouter()
const resultsStore = useResultsStore()
const authStore = useAuthStore()

const test = ref(null)
const loading = ref(true)
const fetchError = ref(null)
const studentName = ref(authStore.user?.username || '')
const answers = ref({})
const submitting = ref(false)
const submitResult = ref(null)
const submitError = ref(null)

onMounted(async () => {
  try {
    const res = await getTestById(route.params.id)
    test.value = res.data
    res.data.questions.forEach(q => { answers.value[q.id] = [] })
  } catch {
    fetchError.value = 'Could not load this test. It may have been deleted.'
  } finally {
    loading.value = false
  }
})

const answered = computed(() => {
  if (!test.value) return 0
  return test.value.questions.filter(q => answers.value[q.id]?.length > 0).length
})

const total = computed(() => test.value?.questions?.length ?? 0)

const canSubmit = computed(() => studentName.value.trim() && answered.value === total.value && !submitting.value)

function toggleAnswer(qId, idx, type) {
  if (type === 'SINGLE') {
    answers.value[qId] = [idx]
  } else {
    const arr = answers.value[qId]
    const pos = arr.indexOf(idx)
    if (pos === -1) arr.push(idx)
    else arr.splice(pos, 1)
  }
}

async function handleSubmit() {
  submitting.value = true
  submitError.value = null
  try {
    const payload = {
      studentName: studentName.value.trim(),
      testId: test.value.id,
      answers: Object.fromEntries(Object.entries(answers.value))
    }
    submitResult.value = await resultsStore.submitTest(payload)
  } catch {
    submitError.value = 'Failed to submit. Please try again.'
  } finally {
    submitting.value = false
  }
}

const pct = computed(() => {
  if (!submitResult.value) return 0
  return Math.round(submitResult.value.score / submitResult.value.totalQuestions * 100)
})

const letters = ['A', 'B', 'C', 'D', 'E', 'F']
</script>

<template>
  <div class="page">
    <div class="container-sm">
      <LoadingSpinner v-if="loading" />
      <AlertMessage v-else-if="fetchError" type="danger" :message="fetchError" />

      <div v-else-if="test && !submitResult">
        <!-- Header -->
        <div class="flex items-center gap-3 mb-6">
          <button class="btn btn-ghost btn-sm btn-icon" @click="router.push('/tests')">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M19 12H5m7-7-7 7 7 7"/></svg>
          </button>
          <div class="flex-1">
            <div class="flex items-center gap-2 mb-1">
              <span class="badge badge-gray">{{ test.subject }}</span>
              <span class="text-sm text-muted">{{ test.questions.length }} questions</span>
            </div>
            <h1 class="page-title">{{ test.title }}</h1>
          </div>
        </div>

        <!-- Progress bar -->
        <div v-if="total > 0" class="card card-body mb-4" style="padding: 12px 16px;">
          <div class="flex items-center justify-between mb-2">
            <span class="text-sm font-medium">Progress</span>
            <span class="text-sm text-muted">{{ answered }} / {{ total }} answered</span>
          </div>
          <div class="progress-bar-wrap">
            <div class="progress-bar-fill" style="background: var(--accent);" :style="`width: ${total ? (answered/total*100) : 0}%`"></div>
          </div>
        </div>

        <div v-if="total === 0" class="empty-state card mb-4">
           <span class="empty-icon">⚠️</span>
           <span class="empty-text">This test has no questions yet. It cannot be taken.</span>
           <button class="btn btn-outline mt-3" @click="router.push('/tests')">Go back</button>
        </div>

        <!-- Questions -->
        <template v-if="total > 0">
          <div v-for="(q, qi) in test.questions" :key="q.id" class="card mb-3 animate-in">
            <div class="card-header">
              <div class="flex items-center gap-3">
                <span style="width:24px;height:24px;background:var(--bg);border:1px solid var(--border);border-radius:50%;display:flex;align-items:center;justify-content:center;font-size:12px;font-weight:600;flex-shrink:0;">{{ qi + 1 }}</span>
                <span style="font-weight: 500; font-size: 14px; line-height: 1.4;">{{ q.text }}</span>
              </div>
              <span :class="q.type === 'SINGLE' ? 'badge badge-blue' : 'badge badge-amber'" style="flex-shrink:0; margin-left:12px;">
                {{ q.type === 'SINGLE' ? 'Single' : 'Multiple' }}
              </span>
            </div>
            <div class="card-body">
              <p class="text-sm text-muted mb-3">{{ q.type === 'SINGLE' ? 'Select one answer' : 'Select all correct answers' }}</p>
              <div
                v-for="(opt, oi) in q.options"
                :key="oi"
                class="option-item"
                :class="{ selected: answers[q.id]?.includes(oi) }"
                @click="toggleAnswer(q.id, oi, q.type)"
              >
                <div :class="[q.type === 'SINGLE' ? 'option-radio' : 'option-checkbox', { selected: answers[q.id]?.includes(oi) }]">
                  <span v-if="answers[q.id]?.includes(oi)" style="color:white;font-size:10px;line-height:1">{{ q.type === 'SINGLE' ? '●' : '✓' }}</span>
                </div>
                <span style="font-size:12px;font-weight:600;color:var(--text-muted);margin-right:4px">{{ letters[oi] }}</span>
                <span style="font-size: 13.5px;">{{ opt }}</span>
              </div>
            </div>
          </div>

          <AlertMessage v-if="submitError" type="danger" :message="submitError" />

          <div class="card card-body flex items-center justify-between" style="margin-top: 24px;">
            <span class="text-sm text-muted">{{ canSubmit ? 'Ready to submit!' : `Answer all ${total} questions to submit` }}</span>
            <button class="btn btn-primary btn-lg" :disabled="!canSubmit" @click="handleSubmit">
              <span v-if="submitting" class="spinner" style="width:14px;height:14px;border-width:2px;"></span>
              Submit test
            </button>
          </div>
        </template>
      </div>

      <!-- Result screen -->
      <div v-else-if="submitResult" class="animate-in" style="text-align: center; padding: 48px 24px;">
        <div style="font-size: 48px; margin-bottom: 16px;">{{ pct === 100 ? '🏆' : pct >= 70 ? '✅' : pct >= 50 ? '👍' : '📝' }}</div>
        <h2 style="font-size: 24px; font-weight: 700; margin-bottom: 4px;">{{ submitResult.studentName }}</h2>
        <p class="text-muted mb-6">{{ test?.title }}</p>

        <div class="card card-body" style="max-width: 320px; margin: 0 auto 24px; text-align: left;">
          <div class="flex items-center justify-between mb-3">
            <span class="font-medium">Score</span>
            <span style="font-size: 22px; font-weight: 700;">{{ submitResult.score }} / {{ submitResult.totalQuestions }}</span>
          </div>
          <div class="progress-bar-wrap" style="height: 8px; margin-bottom: 8px;">
            <div class="progress-bar-fill"
              :style="`width:${pct}%; background: ${pct>=70 ? 'var(--success)' : pct>=50 ? 'var(--warning)' : 'var(--danger)'}`"
            ></div>
          </div>
          <p class="text-sm text-muted" style="text-align: center; margin: 0;">{{ pct }}% correct</p>
        </div>

        <div :class="`alert alert-${pct >= 50 ? 'success' : 'warning'}`" style="max-width: 320px; margin: 0 auto 24px; justify-content: center;">
          {{ pct === 100 ? 'Perfect score!' : pct >= 70 ? 'Great job!' : pct >= 50 ? 'Passed!' : 'Better luck next time' }}
        </div>

        <div class="flex gap-2 justify-center">
          <button class="btn btn-outline" @click="router.push('/tests')">← Back to tests</button>
          <RouterLink :to="`/results/${test?.id}`" class="btn btn-ghost">View all results</RouterLink>
        </div>
      </div>
    </div>
  </div>
</template>
