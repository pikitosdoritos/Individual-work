import api from './axios'

export const submitTest = (data) => api.post('/api/results/submit', data)
export const getResultsByTest = (testId) => api.get(`/api/results/test/${testId}`)
