<template>
  <div class="app">
    <div v-if="state.account.mid"> 안녕하세요. {{ state.account.memberName }} 님</div>
    <div v-else>
      <label for="loginId">
        <span>아이디</span>
        <input type="text" name="" id="loginId" v-model="state.form.loginId" />
      </label>
      <label for="loginPw">
        <span> 패스워드 </span>
        <input type="password" id="loginPw" v-model="state.form.loginPw" />
      </label>
      <hr />
      <button @click="summit">로그인</button>
    </div>
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
    axios.get('/api/account').then((res) => {
      state.account = res.data;
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
      axios.post('api/account', args).then((res) => {
        console.log(res);
      })
    },
  },
}
</script>
