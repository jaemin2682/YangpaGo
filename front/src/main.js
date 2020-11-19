import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VueCryptojs from "vue-cryptojs";
import "@babel/polyfill";
import VueSocialSharing from "vue-social-sharing";
import 'chart.js'
import 'hchs-vue-charts'
import Chartkick from 'chartkick'
import VueChartkick from 'vue-chartkick'
import VueMonthlyPicker from 'vue-monthly-picker'

Vue.use(VueSocialSharing);
Vue.use(VueCryptojs);
Vue.use(window.VueCharts)
Vue.use(VueChartkick, { Chartkick })
Vue.use(VueMonthlyPicker)

Vue.config.productionTip = false;
Vue.prototype.$apiServer = "http://j3a404.p.ssafy.io:8081/api";

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
