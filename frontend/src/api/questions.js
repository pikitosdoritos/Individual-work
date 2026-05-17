import api from './axios'

export const getQuestionsByTest = (testId) => api.get(`/api/questions/test/${testId}`)
export const createQuestion = (data) => api.post('/api/questions', data)
export const updateQuestion = (id, data) => api.put(`/api/questions/${id}`, data)
export const deleteQuestion = (id) => api.delete(`/api/questions/${id}`)
