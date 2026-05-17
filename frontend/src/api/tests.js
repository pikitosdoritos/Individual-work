import api from './axios'

export const getAllTests = () => api.get('/api/tests')
export const getTestById = (id) => api.get(`/api/tests/${id}`)
export const createTest = (data) => api.post('/api/tests', data)
export const updateTest = (id, data) => api.put(`/api/tests/${id}`, data)
export const deleteTest = (id) => api.delete(`/api/tests/${id}`)
