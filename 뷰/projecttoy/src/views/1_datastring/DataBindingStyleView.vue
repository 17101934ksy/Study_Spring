<template>
  <div>
    <div>
      <div style="color: red; font-size: 30px"> 스타일 바인딩: 글씨는 red, 폰트크기 30px</div>
    </div>
    <div>
      <div :style="style1"> 스타일 바인딩: 글씨는 blue, 폰트크기 30px</div>
    </div>
    <button @click="style1.color='green'">색상 바꾸기</button>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      style1: {
        color: 'blue',
        fontSize: '30px',
      },
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {},
}
</script>
