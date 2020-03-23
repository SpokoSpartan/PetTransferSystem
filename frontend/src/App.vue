<template>
	<div id="app">
		<el-menu
			:default-active="activeIndex2"
			class="el-menu-demo"
			mode="horizontal"
			@select="handleSelect"
			background-color="#545c64"
			text-color="#fff"
			active-text-color="#ffd04b">
			<el-menu-item index="1">
				<router-link to="/">Home</router-link>
			</el-menu-item>
			<el-menu-item index="2">
				<router-link to="/list">Pets list</router-link>
			</el-menu-item>
			<el-menu-item index="3">
				<router-link to="/animalAdd">Add animal view</router-link>
			</el-menu-item>
			<el-submenu index="4">
				<template slot="title">All views</template>
				<el-menu-item index="4-1">
					<div><router-link to="/register">Register</router-link></div>

				</el-menu-item>
				<el-menu-item index="4-2">
					<router-link to="/login">Login</router-link>
				</el-menu-item>
				<el-menu-item index="4-3">
					<router-link to="/list">Pets list</router-link>
				</el-menu-item>
				<el-menu-item index="4-4">
					<router-link to="/animal">Single animal view</router-link>
				</el-menu-item>
				<el-menu-item index="4-5">
					<div><router-link to="/animalAdd">Add animal view</router-link></div>
				</el-menu-item>
			</el-submenu>
		</el-menu>
		<router-view/>
	</div>
</template>

<style lang="scss">
	@import '../public/index.scss';

	#app {
		font-family: Avenir, Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		text-align: center;
		color: #2c3e50;
	}

	#nav {
		padding: 30px;

		a {
			font-weight: bold;
			color: #2c3e50;

			&.router-link-exact-active {
				color: #42b983;
			}
		}
	}
</style>
<script>
export default {
	data () {
		return {
			activeIndex: '1',
			activeIndex2: '1'
		}
	},
	methods: {
		handleSelect (key, keyPath) {
			console.log(key, keyPath)
		}
	}
}
</script>
