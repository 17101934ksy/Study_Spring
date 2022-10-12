<template>
  <div>
    <div>
      <label for="html" >
        <input type="radio" name="" id="html" value="HTML" v-model="favoriteLang"> HTML
      </label>
  </div>
  <div>
    <label for="css" >
      <input type="radio" name="" id="css" value="CSS" v-model="favoriteLang"> CSS
    </label>
  </div>
  <div>
    <label for="js" >
      <input type="radio" name="" id="js" value="JS" v-model="favoriteLang"> JS
    </label>
  </div>
  <button @click="selectLang">제출</button>
  </div>
  <div>
    선택한 지역: {{ favoriteLang }}
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      favoriteLang: '',
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {
    selectLang() {
      console.log(this.favoriteLang)
    },
  },
}
</script>
