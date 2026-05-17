<script setup>
import { reactive, watch } from 'vue'

const props = defineProps({ modelValue: { type: Object, default: null } })
const emit = defineEmits(['submit', 'cancel'])

const form = reactive({ title: '', subject: '' })
const errors = reactive({ title: '', subject: '' })

watch(() => props.modelValue, (val) => {
  form.title = val?.title ?? ''
  form.subject = val?.subject ?? ''
  errors.title = ''
  errors.subject = ''
}, { immediate: true })

function validate() {
  errors.title = form.title.trim() ? '' : 'Required'
  errors.subject = form.subject.trim() ? '' : 'Required'
  return !errors.title && !errors.subject
}

function handleSubmit() {
  if (validate()) emit('submit', { title: form.title.trim(), subject: form.subject.trim() })
}
</script>

<template>
  <form @submit.prevent="handleSubmit" novalidate>
    <div class="form-group">
      <label class="form-label">Title</label>
      <input v-model="form.title" type="text" class="form-input" :class="{ error: errors.title }" placeholder="e.g. Java Programming Basics" />
      <p v-if="errors.title" class="form-error">{{ errors.title }}</p>
    </div>
    <div class="form-group">
      <label class="form-label">Subject</label>
      <input v-model="form.subject" type="text" class="form-input" :class="{ error: errors.subject }" placeholder="e.g. Java" />
      <p v-if="errors.subject" class="form-error">{{ errors.subject }}</p>
    </div>
    <div class="flex gap-2 justify-end">
      <button type="button" class="btn btn-outline" @click="emit('cancel')">Cancel</button>
      <button type="submit" class="btn btn-primary">{{ modelValue ? 'Save changes' : 'Create test' }}</button>
    </div>
  </form>
</template>
