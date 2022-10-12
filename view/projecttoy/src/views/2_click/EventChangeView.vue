<template>
  <div>
    <div>
      <select name="" id="" aria-label="select" @change="changeCity" v-model="selectedCity">
        <option value="">==도시선택==</option>
        <option
          :value="city.cityCode"
          :key="city.cityCode"
          v-for="city in cityList">
          {{ city.title }}
        </option>
      </select>
      <select name="" id="" aria-label="select2">
        <option
        :value="dong.dongCode"
        :key="dong.dongCode"
        v-for="dong in selectedDongList">
        {{ dong.dongTitle }}
      </option>
      </select>
    </div>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      selectedCity: '',
      cityList: [
        { cityCode: '02', title: '서울' },
        { cityCode: '051', title: '부산' },
        { cityCode: '064', title: '제주' },
      ],
      dongList: [
        { cityCode: '02', dongCode: '1', dongTitle: '서울1동' },
        { cityCode: '051', dongCode: '1', dongTitle: '부산1동' },
        { cityCode: '051', dongCode: '2', dongTitle: '부산2동' },
        { cityCode: '051', dongCode: '3', dongTitle: '부산3동' },
        { cityCode: '051', dongCode: '4', dongTitle: '부산4동' },
        { cityCode: '064', dongCode: '1', dongTitle: '제주1동' },
        { cityCode: '064', dongCode: '2', dongTitle: '제주2동' },
        { cityCode: '064', dongCode: '3', dongTitle: '제주3동' },
        { cityCode: '064', dongCode: '4', dongTitle: '제주4동' },
        { cityCode: '021', dongCode: '2', dongTitle: '서울3동' },
      ],
      selectedDongList: [],
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {
    changeCity() {
      this.selectedDongList = this.dongList.filter((dong) => dong.cityCode === this.selectedCity)
    },
  },
}
</script>
