<script setup>
import { ref, onMounted } from 'vue'
import { useTestsStore } from '../stores/testsStore'
import { createQuestion, deleteQuestion } from '../api/questions'
import TestForm from '../components/TestForm.vue'
import QuestionForm from '../components/QuestionForm.vue'
import AlertMessage from '../components/AlertMessage.vue'
import LoadingSpinner from '../components/LoadingSpinner.vue'

const store = useTestsStore()

const mode = ref('list') // 'list' or 'form'
const editingTest = ref(null)
const addingQuestion = ref(false)

const notification = ref(null)
const notifKey = ref(0)

function notify(type, message) {
  notification.value = { type, message }
  notifKey.value++
}

onMounted(() => store.fetchTests())

function startCreate() {
  editingTest.value = null
  addingQuestion.value = false
  mode.value = 'form'
}

function startEdit(test) {
  editingTest.value = JSON.parse(JSON.stringify(test)) // deep copy
  addingQuestion.value = false
  mode.value = 'form'
}

function cancelForm() {
  mode.value = 'list'
  editingTest.value = null
  addingQuestion.value = false
}

async function handleSaveTest(data) {
  try {
    if (editingTest.value?.id) {
      const res = await store.updateTest(editingTest.value.id, data)
      editingTest.value = res.data // update with fresh data
      notify('success', 'Test details updated')
    } else {
      const res = await store.createTest(data)
      editingTest.value = res.data // now it has an id
      notify('success', 'Test created! Now you can add questions.')
    }
  } catch (e) {
    notify('danger', 'Failed to save test')
  }
}

async function handleDeleteTest(id) {
  if (!confirm('Delete this test and all its questions? This cannot be undone.')) return
  try {
    await store.deleteTest(id)
    notify('success', 'Test deleted')
  } catch (e) {
    notify('danger', 'Failed to delete: ' + (e.response?.data?.error || e.message))
  }
}

async function handleAddQuestion(data) {
  try {
    const res = await createQuestion(data)
    // Update local editingTest
    if (!editingTest.value.questions) editingTest.value.questions = []
    editingTest.value.questions.push(res.data)
    
    // Also update in store
    const testInStore = store.tests.find(t => t.id === data.testId)
    if (testInStore) {
      if (!testInStore.questions) testInStore.questions = []
      testInStore.questions.push(res.data)
    }
    
    addingQuestion.value = false
    notify('success', 'Question added')
  } catch {
    notify('danger', 'Failed to add question')
  }
}

async function handleDeleteQuestion(questionId) {
  if (!confirm('Delete this question?')) return
  try {
    await deleteQuestion(questionId)
    
    // Update local editingTest
    editingTest.value.questions = editingTest.value.questions.filter(q => q.id !== questionId)
    
    // Also update in store
    const testInStore = store.tests.find(t => t.id === editingTest.value.id)
    if (testInStore) {
      testInStore.questions = testInStore.questions.filter(q => q.id !== questionId)
    }
    
    notify('success', 'Question deleted')
  } catch {
    notify('danger', 'Failed to delete question')
  }
}

const letters = ['A', 'B', 'C', 'D', 'E', 'F']
</script>

<template>
  <div class="page">
    <div class="container">

      <!-- Notification -->
      <AlertMessage v-if="notification" :type="notification.type" :message="notification.message" :key="notifKey" />

      <!-- Header for List mode -->
      <div v-if="mode === 'list'" class="flex items-center justify-between mb-6">
        <div>
          <h1 class="page-title">Manage Tests</h1>
          <p class="page-subtitle">Create, edit, and organize tests and questions</p>
        </div>
        <button class="btn btn-primary" @click="startCreate">+ New test</button>
      </div>

      <!-- Header for Form mode -->
      <div v-if="mode === 'form'" class="flex items-center justify-between mb-6">
        <div class="flex items-center gap-3">
          <button class="btn btn-ghost btn-sm btn-icon" @click="cancelForm">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M19 12H5m7-7-7 7 7 7"/></svg>
          </button>
          <div>
            <h1 class="page-title">{{ editingTest?.id ? 'Edit Test' : 'Create New Test' }}</h1>
            <p class="page-subtitle" style="margin-bottom:0">{{ editingTest?.title || 'Enter details below' }}</p>
          </div>
        </div>
      </div>

      <!-- Editor Mode -->
      <div v-if="mode === 'form'" class="animate-in grid" style="gap: 24px;">
        <!-- Test Details -->
        <div class="card" style="max-width: 600px;">
          <div class="card-header">
            <span class="font-semibold text-sm">Test Details</span>
          </div>
          <div class="card-body">
            <TestForm :modelValue="editingTest" @submit="handleSaveTest" @cancel="cancelForm" />
          </div>
        </div>

        <!-- Questions Section (only visible if test is saved) -->
        <div v-if="editingTest?.id" class="card" style="max-width: 800px;">
          <div class="card-header flex items-center justify-between">
            <span class="font-semibold text-sm">Questions ({{ editingTest.questions?.length || 0 }})</span>
            <button v-if="!addingQuestion" class="btn btn-outline btn-sm" @click="addingQuestion = true">+ Add Question</button>
          </div>
          <div class="card-body">
            
            <div v-if="!editingTest.questions?.length && !addingQuestion" class="empty-state" style="padding: 24px;">
              <span class="empty-icon" style="font-size:24px;">❓</span>
              <span class="empty-text">No questions yet.</span>
              <button class="btn btn-success btn-sm mt-3" @click="addingQuestion = true">+ Add your first question</button>
            </div>

            <!-- List of existing questions in the editor -->
            <div v-if="editingTest.questions?.length" class="mb-4">
              <div v-for="(q, qi) in editingTest.questions" :key="q.id" class="card mb-3" style="border-color: var(--border);">
                <div class="card-header" style="background: var(--surface-2); padding: 10px 16px;">
                  <div class="flex items-center gap-3 w-full">
                    <span style="font-size:12px;font-weight:600;color:var(--text-muted);flex-shrink:0">Q{{ qi + 1 }}</span>
                    <span style="flex:1;font-size:14px;font-weight:500;">{{ q.text }}</span>
                    <span :class="q.type === 'SINGLE' ? 'badge badge-blue' : 'badge badge-amber'" style="flex-shrink:0">
                      {{ q.type === 'SINGLE' ? 'Single Choice' : 'Multiple Choice' }}
                    </span>
                    <button class="btn btn-danger btn-sm btn-icon ml-2" @click="handleDeleteQuestion(q.id)" title="Delete question">
                      <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M18 6 6 18M6 6l12 12"/></svg>
                    </button>
                  </div>
                </div>
                <div class="card-body" style="padding: 12px 16px;">
                  <div class="grid-2">
                    <div v-for="(opt, oi) in q.options" :key="oi" class="flex items-center gap-2 mb-1">
                      <span style="font-size:12px;font-weight:600;color:var(--text-muted);width:16px;">{{ letters[oi] }}</span>
                      <span :style="q.correctAnswers?.includes(oi) ? 'font-weight:600;color:var(--success);' : 'color:var(--text);'">{{ opt }}</span>
                      <span v-if="q.correctAnswers?.includes(oi)" style="color:var(--success);font-size:12px;">✓ Correct</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Add Question Form -->
            <div v-if="addingQuestion" class="card animate-in" style="border: 1px solid var(--accent); box-shadow: var(--shadow-sm);">
              <div class="card-header" style="background: var(--accent-light);">
                <span class="font-medium text-sm">New Question</span>
              </div>
              <div class="card-body">
                <QuestionForm :testId="editingTest.id" @submit="handleAddQuestion" @cancel="addingQuestion = false" />
              </div>
            </div>

          </div>
        </div>
        
        <div v-else-if="!editingTest?.id" class="alert alert-info" style="max-width: 600px;">
          Save the test details above to unlock the question editor.
        </div>
      </div>

      <!-- Tests list -->
      <div v-if="mode === 'list'">
        <LoadingSpinner v-if="store.loading" />
        <AlertMessage v-else-if="store.error" type="danger" :message="store.error" />

        <div v-else-if="store.tests.length === 0" class="empty-state card">
          <span class="empty-icon">📋</span>
          <span class="empty-text">No tests yet. Create your first one.</span>
          <button class="btn btn-outline mt-3" @click="startCreate">+ New test</button>
        </div>

        <div v-else class="grid-3">
          <div v-for="test in store.tests" :key="test.id" class="card mb-3 animate-in flex flex-col justify-between">
            <div class="card-body">
              <div class="flex items-center gap-2 mb-2">
                <span class="badge badge-gray">{{ test.subject }}</span>
                <span class="text-sm text-muted">{{ test.questions?.length ?? 0 }} questions</span>
              </div>
              <div class="font-semibold" style="font-size:16px; margin-bottom: 8px;">{{ test.title }}</div>
            </div>
            <div class="card-footer flex gap-2 justify-end bg-surface-2" style="background: var(--surface-2);">
              <button class="btn btn-outline btn-sm flex-1" style="justify-content:center" @click="startEdit(test)">✎ Edit / Questions</button>
              <button class="btn btn-danger btn-sm btn-icon" @click="handleDeleteTest(test.id)">
                <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M3 6h18M19 6v14a2 2 0 01-2 2H7a2 2 0 01-2-2V6m3 0V4a2 2 0 012-2h4a2 2 0 012 2v2"/></svg>
              </button>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>
