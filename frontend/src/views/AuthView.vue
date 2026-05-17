<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import AlertMessage from '../components/AlertMessage.vue'
import LoadingSpinner from '../components/LoadingSpinner.vue'

const router = useRouter()
const authStore = useAuthStore()

const isLogin = ref(true)
const form = ref({ username: '', password: '', role: 'STUDENT' })

async function handleSubmit() {
  if (!form.value.username || !form.value.password) return
  
  try {
    if (isLogin.value) {
      await authStore.login(form.value.username, form.value.password)
    } else {
      await authStore.register(form.value.username, form.value.password, form.value.role)
    }
    router.push('/')
  } catch (e) {
    // Error is handled by store and displayed via AlertMessage
  }
}
</script>

<template>
  <div class="page" style="display: flex; align-items: center; justify-content: center; padding-top: 100px;">
    <div class="card" style="width: 100%; max-width: 400px;">
      <div class="card-header" style="justify-content: center;">
        <h2 style="margin:0; font-size: 18px;">{{ isLogin ? 'Sign In' : 'Create Account' }}</h2>
      </div>
      <div class="card-body">
        <AlertMessage v-if="authStore.error" type="danger" :message="authStore.error" />
        
        <form @submit.prevent="handleSubmit">
          <div class="form-group">
            <label class="form-label">Username</label>
            <input v-model="form.username" type="text" class="form-input" placeholder="Enter username" required />
          </div>
          <div class="form-group">
            <label class="form-label">Password</label>
            <input v-model="form.password" type="password" class="form-input" placeholder="Enter password" required />
          </div>
          
          <div v-if="!isLogin" class="form-group">
            <label class="form-label">I am a...</label>
            <div class="flex gap-2">
              <label class="flex items-center gap-2 btn btn-outline flex-1" :style="form.role === 'STUDENT' ? 'border-color: var(--accent); background: var(--accent-light); font-weight:500' : ''" style="cursor:pointer;">
                <input type="radio" value="STUDENT" v-model="form.role" style="display:none" />
                <span>Student</span>
              </label>
              <label class="flex items-center gap-2 btn btn-outline flex-1" :style="form.role === 'TUTOR' ? 'border-color: var(--accent); background: var(--accent-light); font-weight:500' : ''" style="cursor:pointer;">
                <input type="radio" value="TUTOR" v-model="form.role" style="display:none" />
                <span>Tutor</span>
              </label>
            </div>
          </div>
          
          <button type="submit" class="btn btn-primary" style="width: 100%; justify-content: center; margin-top: 12px;" :disabled="authStore.loading">
            <span v-if="authStore.loading" class="spinner" style="width:14px;height:14px;border-width:2px;margin-right:6px;"></span>
            {{ isLogin ? 'Sign In' : 'Sign Up' }}
          </button>
        </form>
        
        <div class="divider"></div>
        
        <div style="text-align: center;">
          <span class="text-sm text-muted">{{ isLogin ? "Don't have an account?" : "Already have an account?" }}</span>
          <button class="btn btn-ghost btn-sm" @click="isLogin = !isLogin; authStore.error = null">
            {{ isLogin ? 'Create one' : 'Sign in here' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
