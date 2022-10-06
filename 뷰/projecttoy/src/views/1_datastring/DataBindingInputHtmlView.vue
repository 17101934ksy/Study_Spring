<template>
  <div>
    <div>
        <input type="text" v-model="userId" aria-label="userInput"/>
        <button @click="login">클릭</button>
        <button @click="changeName">변경</button>
        <br>
        <input type="text" v-model.number="num1" aria-label="userInput"/>
        <input type="text" v-model.number="num2" aria-label="userInput"/>
        <span>{{ num1 + num2 }}</span>
    </div>
</div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      userId: '',
      num1: '',
      num2: '',
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {
    login() {
      console.log(this.userId)
    },
    changeName() {
      this.userId = 'seyun'
    },
  },
}
</script>
