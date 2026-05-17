<script setup>
import { RouterLink, useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'

const authStore = useAuthStore()
const router = useRouter()

function logout() {
  authStore.logout()
  router.push('/auth')
}
</script>

<template>
  <header class="nav-bar">
    <RouterLink to="/" class="nav-brand">
      <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round">
        <path d="M22 10v6M2 10l10-5 10 5-10 5z"/><path d="M6 12v5c3 3 9 3 12 0v-5"/>
      </svg>
      Testing System
    </RouterLink>
    
    <nav class="nav-links" style="flex: 1;">
      <RouterLink to="/" class="nav-link" active-class="active" exact>Home</RouterLink>
      
      <template v-if="authStore.isAuthenticated">
        <RouterLink v-if="authStore.isStudent" to="/tests" class="nav-link" active-class="active">Tests</RouterLink>
        <RouterLink v-if="authStore.isTutor" to="/manage" class="nav-link" active-class="active">Manage Tests</RouterLink>
      </template>
    </nav>
    
    <div class="nav-links">
      <template v-if="authStore.isAuthenticated">
        <div class="flex items-center gap-3">
          <span class="text-sm font-medium">
            {{ authStore.user.username }} 
            <span class="text-muted" style="font-weight:400">({{ authStore.isTutor ? 'Tutor' : 'Student' }})</span>
          </span>
          <button @click="logout" class="btn btn-ghost btn-sm">Sign out</button>
        </div>
      </template>
      <template v-else>
        <RouterLink to="/auth" class="btn btn-primary btn-sm">Sign In / Register</RouterLink>
      </template>
    </div>
  </header>
</template>
