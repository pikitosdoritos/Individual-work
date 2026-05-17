import { defineStore } from 'pinia'
import { ref } from 'vue'
import * as resultsApi from '../api/results'

export const useResultsStore = defineStore('results', () => {
  const results = ref([])

  async function submitTest(data) {
    const res = await resultsApi.submitTest(data)
    return res.data
  }

  async function fetchResultsByTestId(testId) {
    const res = await resultsApi.getResultsByTest(testId)
    results.value = res.data
  }

  return { results, submitTest, fetchResultsByTestId }
})
