<template>
  <div>
    <div>
      <button @click="increaseCounter"> add1 </button>
      <p> {{ counter }} </p>
    </div>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      counter: 0,
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {
    increaseCounter() {
      this.counter += 1
    },
  },
}
</script>
