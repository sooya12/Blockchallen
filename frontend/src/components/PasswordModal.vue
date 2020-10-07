<template>
  <div>
    <v-text-field
        :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[rules.required, rules.min]"
        :type="show ? 'text' : 'password'"
        name="input-10-2"
        label="비밀번호"
        hint="입력"
        :value="password"
        class="input-group--focused"
        @click:append="show = !show"
        style="width:50%; "
    ></v-text-field>
  </div>
</template>

<script>
export default {
  name: "PasswordModal.vue",
  data() {
    return {
      show: false,
      password: '',
      rules: {
        required: value => !!value || '입력해주세요.',
        min: v => v.length == 4 || '비밀번호는 4글자 입니다.',
      },
    }
  },
  methods: {}
}

</script>

<style scoped>

</style>