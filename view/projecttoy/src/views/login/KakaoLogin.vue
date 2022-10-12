<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<template>
  <div>
    <h1>{{ user.email ? "로그인 성공!" : "로그인 후 사용하세요." }}</h1>
    <a v-show="!user.email" id="custom-login-btn" aria-label="kakaoLogin" @click="kakaoLogin">
      <img
        src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg" key=""
        alt=""
        width="222"
        />
      </a>
    <button v-show="user.email" @click="kakaoLogout">카카오 로그아웃</button>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      sampleData: '',
    };
  },
  computed: {
    user() {
      return this.$store.state.user;
    },
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {
    kakaoLogin() {
      window.Kakao.Auth.login({
        scope: 'profile_nickname,account_email',
        success: this.getKakaoAccount,
        fail: (error) => {
          console.log(error)
        },
      });
    },
    getKakaoAccount() {
      window.Kakao.API.request({
        url: '/v2/user/me',
        success: (res) => {
          const kakaoAccount = res.kakao_account;
          console.log(kakaoAccount);
          this.$store.commit('user', kakaoAccount);
        },
      });
    },
    kakaoLogout() {
      if (!window.Kakao.Auth.getAccessToken()) {
        console.log('Not logged in.');
        return;
      }
      window.Kakao.Auth.logout((res) => {
        console.log(window.Kakao.Auth.getAccessToken());
        console.log(res);
        this.$store.commit('user', {});
      });
    },
  },
};
</script>
