import Vue from 'vue'
// @ts-ignore
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import { utils } from '@/utils';

Vue.use(ElementUI);
Vue.config.productionTip = false;
const apiBase = 'http://localhost:8081/';
Vue.prototype.$APIBASE = apiBase;
Vue.prototype.$APIURL = apiBase + 'api/';
Vue.prototype.$isLogged = true;

new Vue({
	router,
	store,
	render: h => h(App),
	methods: {

	},
	mixins: [utils]
}).$mount('#app');
