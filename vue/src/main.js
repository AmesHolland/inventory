import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
import router from './router'
import store from './store'
import request from "@/utils/request";
import './assets/gloable.css'

// main.js全局注册
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// use
Vue.use(mavonEditor)

Vue.use(ElementUI, {size:"mini"});
Vue.config.productionTip = false

Vue.prototype.$request = request

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
