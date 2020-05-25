<template>
	<div id="app">
		<el-menu
			:default-active="activeIndex"
			class="menu"
			mode="horizontal"
			text-color="#fff"
			active-text-color="#dba11c"
			background-color="#0F4C81">

			<div style="float: left; padding-left: 16px;">
				<img alt="Vue logo" src="../src/assets/logo-blue.png">
			</div>
			<el-menu-item index="1">
				<router-link to="/animals">Pets list</router-link>
			</el-menu-item>
			<el-menu-item v-if="this.$isLogged" index="2">
				<router-link to="/myPets">My pets</router-link>
			</el-menu-item>
			<el-menu-item v-if="this.$isLogged" index="3">
				<router-link to="/animal/add">Add animal</router-link>
			</el-menu-item>
      <el-menu-item v-if="this.$isLogged" index="4">
        <router-link to="/organization">Organization</router-link>
      </el-menu-item>
			<el-menu-item v-if="!this.$isLogged" style="float: right;" index="5">
				<router-link to="/register">Register</router-link>
			</el-menu-item>
			<el-menu-item v-if="!this.$isLogged" style="float: right;" index="6">
				<router-link to="/login">Login</router-link>
			</el-menu-item>
			<el-menu-item v-if="this.$isLogged" @click="afterLogout" style="float: right;" index="6">
				<router-link to="/animals">Logout</router-link>
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
				activeIndex: '1'
			};
		},
		methods: {
			afterLogout() {
				localStorage.removeItem('username');
				localStorage.removeItem('access_token');
				localStorage.removeItem('refresh_token');
				this.$isLogged = false;
				this.$forceUpdate();
			},
			afterLogin(response) {
				localStorage.setItem('username', response.data.username);
				localStorage.setItem('access_token', response.data.access_token);
				localStorage.setItem('refresh_token', response.data.refresh_token);
				this.$isLogged = true;
			}
		}
	}

</script>
