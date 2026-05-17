import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import { useAuthStore } from '../stores/authStore'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/auth',
      name: 'auth',
      component: () => import('../views/AuthView.vue')
    },
    {
      path: '/tests',
      name: 'tests',
      component: () => import('../views/TestsView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/tests/:id',
      name: 'take-test',
      component: () => import('../views/TakeTestView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/manage',
      name: 'manage',
      component: () => import('../views/ManageView.vue'),
      meta: { requiresAuth: true, requiresTutor: true }
    },
    {
      path: '/results/:testId',
      name: 'results',
      component: () => import('../views/ResultsView.vue'),
      meta: { requiresAuth: true, requiresTutor: true }
    }
  ]
})

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next('/auth')
  } else if (to.meta.requiresTutor && !authStore.isTutor) {
    next('/')
  } else {
    next()
  }
})

export default router
