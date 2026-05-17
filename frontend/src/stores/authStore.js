import { defineStore } from 'pinia'
import axios from 'axios'

const API = 'http://localhost:8080/api/auth'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: JSON.parse(localStorage.getItem('user')) || null,
    loading: false,
    error: null
  }),
  getters: {
    isAuthenticated: (state) => !!state.user,
    isStudent: (state) => state.user?.role === 'STUDENT',
    isTutor: (state) => state.user?.role === 'TUTOR',
  },
  actions: {
    async login(username, password) {
      this.loading = true
      this.error = null
      try {
        const res = await axios.post(`${API}/login`, { username, password })
        this.user = res.data
        localStorage.setItem('user', JSON.stringify(res.data))
      } catch (err) {
        this.error = err.response?.data || 'Login failed'
        throw err
      } finally {
        this.loading = false
      }
    },
    async register(username, password, role) {
      this.loading = true
      this.error = null
      try {
        const res = await axios.post(`${API}/register`, { username, password, role })
        this.user = res.data
        localStorage.setItem('user', JSON.stringify(res.data))
      } catch (err) {
        this.error = err.response?.data || 'Registration failed'
        throw err
      } finally {
        this.loading = false
      }
    },
    logout() {
      this.user = null
      localStorage.removeItem('user')
    }
  }
})
