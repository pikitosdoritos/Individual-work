<script setup>
import { reactive, ref, watch } from 'vue'

const props = defineProps({ testId: { type: Number, required: true } })
const emit = defineEmits(['submit', 'cancel'])

const form = reactive({
  text: '',
  type: 'SINGLE',
  options: ['', '', '', ''],
  correctAnswers: []
})
const errors = reactive({ text: '', options: '', correct: '' })
const submitting = ref(false)

watch(() => form.type, () => { form.correctAnswers = [] })

function toggleCorrect(idx) {
  if (form.type === 'SINGLE') {
    form.correctAnswers = [idx]
  } else {
    const pos = form.correctAnswers.indexOf(idx)
    if (pos === -1) form.correctAnswers.push(idx)
    else form.correctAnswers.splice(pos, 1)
  }
}

function isSelected(idx) { return form.correctAnswers.includes(idx) }

function validate() {
  errors.text = form.text.trim() ? '' : 'Question text is required'
  errors.options = form.options.every(o => o.trim()) ? '' : 'All 4 options must be filled'
  errors.correct = form.correctAnswers.length > 0 ? '' : 'Mark at least one correct answer'
  return !errors.text && !errors.options && !errors.correct
}

async function handleSubmit() {
  if (!validate()) return
  submitting.value = true
  emit('submit', {
    testId: props.testId,
    text: form.text.trim(),
    type: form.type,
    options: form.options.map(o => o.trim()),
    correctAnswers: [...form.correctAnswers].sort((a, b) => a - b)
  })
  form.text = ''
  form.type = 'SINGLE'
  form.options = ['', '', '', '']
  form.correctAnswers = []
  errors.text = ''
  errors.options = ''
  errors.correct = ''
  submitting.value = false
}

const letters = ['A', 'B', 'C', 'D']
</script>

<template>
  <div class="animate-in">
    <div class="form-group">
      <label class="form-label">Question text</label>
      <textarea v-model="form.text" class="form-textarea" :class="{ error: errors.text }" placeholder="Enter your question..." rows="2"></textarea>
      <p v-if="errors.text" class="form-error">{{ errors.text }}</p>
    </div>

    <div class="form-group">
      <label class="form-label">Answer type</label>
      <div class="flex gap-2">
        <label
          v-for="t in ['SINGLE', 'MULTIPLE']" :key="t"
          class="flex items-center gap-2 btn btn-outline"
          style="cursor:pointer; font-weight:400"
          :style="form.type === t ? 'border-color: var(--accent); background: var(--accent-light); font-weight:500' : ''"
        >
          <input type="radio" :value="t" v-model="form.type" style="display:none" />
          <span>{{ t === 'SINGLE' ? '◉ Single choice' : '☑ Multiple choice' }}</span>
        </label>
      </div>
      <p class="form-hint">{{ form.type === 'SINGLE' ? 'Student selects one correct answer' : 'Student can select multiple answers' }}</p>
    </div>

    <div class="form-group">
      <label class="form-label">Options — check the correct answer(s)</label>
      <p v-if="errors.options" class="form-error mb-2">{{ errors.options }}</p>
      <p v-if="errors.correct" class="form-error mb-2">{{ errors.correct }}</p>
      <div v-for="(_, i) in form.options" :key="i" style="display:flex; align-items:center; gap:8px; margin-bottom:8px">
        <div
          :class="['option-radio', form.type === 'MULTIPLE' ? 'option-checkbox' : 'option-radio', isSelected(i) ? 'selected' : '']"
          style="cursor:pointer; flex-shrink:0; width:16px; height:16px; border: 2px solid var(--border-dark); border-radius: 3px; display:flex; align-items:center; justify-content:center; transition: all .15s"
          :style="isSelected(i) ? 'border-color: var(--accent); background: var(--accent)' : ''"
          @click="toggleCorrect(i)"
        >
          <span v-if="isSelected(i)" style="color:white; font-size:10px; line-height:1">✓</span>
        </div>
        <span style="font-size:12px; font-weight:600; color:var(--text-muted); width:16px; flex-shrink:0">{{ letters[i] }}</span>
        <input
          v-model="form.options[i]"
          type="text"
          class="form-input"
          :style="isSelected(i) ? 'border-color: var(--accent)' : ''"
          :placeholder="`Option ${letters[i]}`"
        />
      </div>
      <p class="form-hint">Click the checkbox to mark as correct</p>
    </div>

    <div class="flex gap-2 justify-end">
      <button type="button" class="btn btn-ghost" @click="emit('cancel')">Cancel</button>
      <button type="button" class="btn btn-success" @click="handleSubmit">Add question</button>
    </div>
  </div>
</template>
