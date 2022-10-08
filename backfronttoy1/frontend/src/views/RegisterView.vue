<template>
  <div class="test">
    <h1>This is a Test page. </h1>
    <div id="demo">
      <button @click="submitBtn()">post하기</button>
    </div>
  </div>
</template>

<script>
export default {
  data() { },
  mounted() {
    this.load();
  },
  methods: {
    load() {
      this.axios.get('/api/twitter/').then((res) => {
        console.log(res.data);
      });
    },
    submitBtn() {
      this.$axios.post('/api/twitter/post', 'this is string').then((res) => {
        console.log(res)
      }).catch((err) => {
        console.log(err)
      });
    },
  },
}

</script>
