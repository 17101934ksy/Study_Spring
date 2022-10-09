<template>
  <div class="app">
    <section class="test">
    <div v-on:click="kakaoLoginBtn">카카오 연동</div>
  </section>
  </div>
</template>
<script>

import axios from 'axios'
import { reactive } from 'vue';

export default {
  components: {},
  data() {
    return {
    }
  },
  setup() {
    const state = reactive({
      account: {
        mid: null,
        memberName: '',
      },
      form: {
        loginId: '',
        loginPw: '',
      },
    });
    axios.get('/api/login').then((res) => {
      console.log(res.data);
    });
    return { state };
  },
  created() { },
  mounted() { },
  unmounted() { },
  methods: {
    summit() {
      const args = {
        loginId: this.state.form.loginId,
        loginPw: this.state.form.loginPw,
      };
      axios.post('api/login', args).then((res) => {
        console.log(res);
      })
    },
  },
}
</script>
