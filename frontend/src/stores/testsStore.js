import { defineStore } from 'pinia'
import { ref } from 'vue'
import * as testsApi from '../api/tests'

export const useTestsStore = defineStore('tests', () => {
  const tests = ref([])
  const loading = ref(false)
  const error = ref(null)

  async function fetchTests() {
    loading.value = true
    error.value = null
    try {
      const res = await testsApi.getAllTests()
      tests.value = res.data
    } catch (e) {
      error.value = e.response?.data?.error || 'Failed to load tests'
    } finally {
      loading.value = false
    }
  }

  async function createTest(data) {
    const res = await testsApi.createTest(data)
    tests.value.push(res.data)
    return res.data
  }

  async function updateTest(id, data) {
    const res = await testsApi.updateTest(id, data)
    const idx = tests.value.findIndex(t => t.id === id)
    if (idx !== -1) tests.value[idx] = res.data
    return res.data
  }

  async function deleteTest(id) {
    await testsApi.deleteTest(id)
    tests.value = tests.value.filter(t => t.id !== id)
  }

  return { tests, loading, error, fetchTests, createTest, updateTest, deleteTest }
})
