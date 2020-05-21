import Vue from 'vue'
// @ts-ignore
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'

Vue.use(ElementUI)
Vue.config.productionTip = false

Vue.prototype.$APIURL = 'http://52.91.229.171:80/';
new Vue({
	router,
	store,
	render: h => h(App)
}).$mount('#app')
