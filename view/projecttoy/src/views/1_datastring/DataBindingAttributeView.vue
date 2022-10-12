<template>
  <div>
      <input type="text" name="" id="" aria-label="attributeSelect" v-bind:value="userId" readonly/>
      <input type="text" name="" id="" aria-label="attributeSelect" :value="userId" readonly/>
      <br>
      <!-- <img :src="imgSrc" alt="" style="width:5px, height:5px;"> -->
      <br>
      <input type="search" name="" id="" aria-label="search" v-model="text1">
      <button :disabled="text1 === ''">조회</button>
      <br>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      userId: 'seyun',
      imgSrc: 'https://t1.kakaocdn.net/kakaocorp/corp_thumbnail/Kakao.png',
      text1: '',
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {},
}
</script>
