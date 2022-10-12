<template>
  <div>
    <div>
      <select name="" id="" aria-label="city">
          <option value="" :key="city.code" v-for="city in cities">
            {{ city.title }}
          </option>
      </select>
    </div>
    <table>
      <thead>
        <tr>
          <th> 제품번호 </th>
          <th> 가격 </th>
          <th> 주문수량 </th>
          <th> 합계 </th>
        </tr>
      </thead>
      <tbody>
        <tr :key="drink.drinkId" v-for="drink in drinkList">
          <td> {{ drink.drinkId }} </td>
          <td> {{ drink.drinkName }} </td>
          <td> {{ drink.price }} </td>
          <td><input type="number" name="" id="" aria-label="orderList" v-model="drink.qty"></td>
          <td> {{ drink.price * drink.qty }} </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      drinkList: [
        {
          drinkId: '1',
          drinkName: '코카콜라',
          price: 700,
          qty: 1,
        },
      ],
      cities: [
        { title: '서울', code: '02' },
        { title: '부산', code: '051' },
        { title: '제주', code: '064' },
      ],
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {},
}
</script>
