import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
	{
		path: '/',
		redirect: '/animals'
	},
	{
		path: '/login',
		name: 'Login',
		component: () => import('../views/Login.vue')
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
		path: '/myPets',
		name: 'My pets',
		component: () => import('../views/MyPets.vue')
	},
	{
		path: '/animal/add',
		name: 'Add animal',
		component: () => import('../views/AnimalAdd.vue')
	},
	{
		path: '/animal/:id',
		name: 'Single animal view',
		component: () => import('../views/AnimalSingleView.vue')
	},
	{
		path: '/edit/:id',
		name: 'Edit pet',
		component: () => import('../views/EditMyPet.vue')
	},
	{
		path: '/editTreatment/:id',
		name: 'Edit treatment history for pet',
		component: () => import('../views/EditTreatmentHistory.vue')
	},
	{
		path: '/transfer/:id',
		name: 'Transfer pet',
		component: () => import('../views/TransferAnimal.vue')
	}
]

const router = new VueRouter({
	routes
})

export default router
