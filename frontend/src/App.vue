<template>
	<div id="app">
		<el-menu
			:default-active="activeIndex2"
			class="el-menu-demo"
			mode="horizontal"
			@select="handleSelect"
			background-color="#0F4C81"
			text-color="#fff"
			active-text-color="#ffd04b">
			<el-menu-item>
				<img alt="Vue logo" src="../src/assets/logo-blue.png">
			</el-menu-item>
			<el-menu-item index="1">
				<router-link to="/home">Home</router-link>
			</el-menu-item>
			<el-menu-item index="2">
				<router-link to="/animals">Pets list</router-link>
			</el-menu-item>
			<el-menu-item index="3">
				<router-link to="/animalAdd">Add animal view</router-link>
			</el-menu-item>
			<el-menu-item>
				<router-link to="/login">Login</router-link>
			</el-menu-item>
			<el-menu-item>
				<router-link to="/register">Register</router-link>
			</el-menu-item>
			<el-menu-item>
				<router-link class="link" to="/animal">Single animal view</router-link>
			</el-menu-item>
		</el-menu>
		<div>
			<router-view></router-view>
		</div>
	</div>
</template>

<style lang="scss">
	@import '../public/index.scss';

	a {
		text-decoration: none;
	}

	.link {
		text-decoration: none;
		color: white;
	}

	#app {
		font-family: Avenir, Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		text-align: center;

	}

	.white {
		color: white;
		background: pink;
	}

</style>
<script>

</script>
