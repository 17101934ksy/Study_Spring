import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue'),
  },
  {
    path: '/datastring',
    name: 'DataBindingHtmlView',
    component: () => import(/* webpackChunkName: "datastring" */ '../views/1_datastring/DataBindingHtmlView.vue'),
  },
  {
    path: '/datastring/input',
    name: 'DataBindingInputHtmlView',
    component: () => import(/* webpackChunkName: "datastring" */ '../views/1_datastring/DataBindingInputHtmlView.vue'),
  },
  {
    path: '/datastring/select',
    name: 'DataBindingSelectView',
    component: () => import(/* webpackChunkName: "datastring" */ '../views/1_datastring/DataBindingSelectView.vue'),
  },
  {
    path: '/datastring/checkbox',
    name: 'DataBindingCheckboxView',
    component: () => import(/* webpackChunkName: "datastring" */ '../views/1_datastring/DataBindingCheckboxView.vue'),
  },
  {
    path: '/datastring/radio',
    name: 'DataBindingRadioView',
    component: () => import(/* webpackChunkName: "datastring" */ '../views/1_datastring/DataBindingRadioView.vue'),
  },
  {
    path: '/datastring/attribute',
    name: 'DataBindingAttributeView',
    component: () => import(/* webpackChunkName: "datastring" */ '../views/1_datastring/DataBindingAttributeView.vue'),
  },
  {
    path: '/datastring/list',
    name: 'DataBindingListView',
    component: () => import(/* webpackChunkName: "datastring" */ '../views/1_datastring/DataBindingListView.vue'),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
