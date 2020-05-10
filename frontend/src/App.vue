<template>
	<div id="app">
		<el-menu
			:default-active="activeIndex"
			class="menu"
			mode="horizontal"
			@select="handleSelect()"
			text-color="#fff"
			active-text-color="#dba11c"
			background-color="#0F4C81"
		>
			<el-menu-item index="1">
				<img alt="Vue logo" src="../src/assets/logo-blue.png">
			</el-menu-item>
			<el-menu-item index="2">
				<router-link to="/animals">Pets list</router-link>
			</el-menu-item>
			<el-menu-item index="3">
				<router-link to="/myPets">My pets</router-link>
			</el-menu-item>
			<el-menu-item index="4">
				<router-link to="/animal/add">Add animal</router-link>
			</el-menu-item>
			<el-menu-item style="float: right;" index="5">
				<router-link to="/register">Register</router-link>
			</el-menu-item>
			<el-menu-item style="float: right;" index="6">
				<router-link to="/login">Login</router-link>
			</el-menu-item>
		</el-menu>
		<div>
			<router-view></router-view>
		</div>
	</div>
</template>

<style lang="scss">
	@import '../public/index.scss';

	.menu {
		border-radius: 2px;
		background-color: $color-primary;
	}

	a {
		text-decoration: none;
	}

	#app {
		font-family: Avenir, Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		text-align: center;
	}


</style>
<script>
	export default {
		data() {
			return {
				activeIndex: '2'

			};
		}, methods: {
			handleSelect(key, keyPath) {
				console.log(key, keyPath);
			}
		}
	}

</script>
