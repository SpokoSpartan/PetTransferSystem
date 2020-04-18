import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
Vue.use(VueRouter)

const routes = [
	{
		path: '/home',
		name: 'Home',
		component: Home
	},
	{
		path: '/login',
		name: 'Login',
		component: Login
	},
	{
		path: '/register',
		name: 'Register',
		component: () => import('../views/Register.vue')
	},
	{
		path: '/animals',
		name: 'Animals',
		component: () => import('../views/AnimalsList.vue')
	}, {
		path: '/animal/add',
		name: 'Add animal',
		component: () => import('../views/AnimalAdd.vue')
	}
]

const router = new VueRouter({
	routes
})

export default router
