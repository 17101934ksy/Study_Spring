<template>
  <div class="test">
    <h1>This is a Test page. </h1>
    <div id="demo">
      <button @click="submitBtn()">post하기</button>
    </div>
  </div>
</template>
<script>

import axios from 'axios'

export default {
  data() { },
  mounted() {
    this.load();
  },
  methods: {
    load() {
      // this.axios.get('http://localhost:3000/api/example-v1').then((res) => {
      //   console.log(res.data);
      // });
      axios.get('/api/example-v1', {
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Content-Type': 'application/json; charset = utf-8',
        },
      }).then((response) => {
        console.log(response.data)
      }).catch((e) => {
        console.log('error : ', e)
      })
    },
    submitBtn() {
      axios.get('h/api/example-v1/post', 'this is string', {
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Content-Type': 'application/json; charset = utf-8',
        },
      }).then((res) => {
        console.log(res)
      }).catch((err) => {
        console.log(err)
      });
    },
  },
}

</script>
