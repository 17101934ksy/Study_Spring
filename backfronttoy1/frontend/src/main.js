import { createApp } from 'vue';
import axios from 'axios';
import App from './App.vue';
import router from './router';
import store from './store';

createApp(App).config.productionTip = false;
createApp(App).use(store).use(router).mount('#app');

const BACKEND_PORT = process.env.BACKEND_PORT === null ? '' : `:${process.env.BACKEND_PORT}`
const BACKEND_DOMAIN = process.env.BACKEND_DOMAIN === null ? `${location.protocol}//${location.hostname}` : process.env.BACKEND_DOMAIN
axios.defaults.baseURL = `${BACKEND_DOMAIN}${BACKEND_PORT}`

new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
