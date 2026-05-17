<script setup>
import { useAuthStore } from '../stores/authStore'

const authStore = useAuthStore()
</script>

<template>
  <div class="page" style="background: var(--bg);">
    <div class="container-sm" style="padding-top: 60px; text-align: center;">
      <div style="width: 56px; height: 56px; background: var(--surface); border: 1px solid var(--border); border-radius: 16px; display: flex; align-items: center; justify-content: center; margin: 0 auto 20px; box-shadow: var(--shadow);">
        <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="var(--accent)" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M22 10v6M2 10l10-5 10 5-10 5z"/><path d="M6 12v5c3 3 9 3 12 0v-5"/>
        </svg>
      </div>
      <h1 style="font-size: 32px; font-weight: 800; letter-spacing: -.5px; margin: 0 0 12px; color: var(--text);">Testing System</h1>
      <p style="color: var(--text-muted); font-size: 15px; margin: 0 0 36px; max-width: 420px; margin-left: auto; margin-right: auto; line-height: 1.6;">
        Create knowledge tests, manage questions, and let students take tests and see their scores instantly.
      </p>
      
      <div style="display: flex; gap: 10px; justify-content: center; flex-wrap: wrap;">
        <template v-if="!authStore.isAuthenticated">
          <RouterLink to="/auth" class="btn btn-primary btn-lg">Get Started</RouterLink>
        </template>
        <template v-else-if="authStore.isStudent">
          <RouterLink to="/tests" class="btn btn-primary btn-lg">Browse available tests</RouterLink>
        </template>
        <template v-else-if="authStore.isTutor">
          <RouterLink to="/manage" class="btn btn-primary btn-lg">Manage tests</RouterLink>
        </template>
      </div>

      <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 12px; margin-top: 48px; text-align: left;">
        <div class="card card-body" style="gap: 8px;">
          <div style="font-size: 20px; margin-bottom: 4px;">📝</div>
          <div class="font-semibold" style="font-size: 14px;">Create Tests</div>
          <div class="text-sm text-muted">Tutors build tests with single or multiple-choice questions</div>
        </div>
        <div class="card card-body" style="gap: 8px;">
          <div style="font-size: 20px; margin-bottom: 4px;">✍️</div>
          <div class="font-semibold" style="font-size: 14px;">Take Tests</div>
          <div class="text-sm text-muted">Students browse and submit answers for instant scoring</div>
        </div>
        <div class="card card-body" style="gap: 8px;">
          <div style="font-size: 20px; margin-bottom: 4px;">📊</div>
          <div class="font-semibold" style="font-size: 14px;">View Results</div>
          <div class="text-sm text-muted">See all submissions with scores and progress bars</div>
        </div>
      </div>
    </div>
  </div>
</template>
