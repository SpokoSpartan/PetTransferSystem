<template>
	<div>
		<h1>Login</h1>
		<el-form class="form-container" ref="form" :model="form" :rules="rules" label-position="left" label-width="140px">
			<br>
			<el-form-item class="form-field" prop="username" label="Username" id="login-username">
				<el-input placeholder="Username"
						  v-model="form.username"/>
			</el-form-item>
			<el-form-item class="form-field" prop="password" label="Password">
				<el-input placeholder="Login"
						  v-model="form.password"
							@keydown.enter.native="loginEnterPressed()"
						  show-password/>
			</el-form-item>
			<br>

			<el-button style="margin-bottom: 12px;" type="primary" @click="onSubmit" round>Login</el-button>

			<br>
		</el-form>
	</div>
</template>

<script>
	import axios from "axios";
	import Vue from 'vue'
	import VueRouter from 'vue-router'
	import App from "../App";

	Vue.use(VueRouter)

	export default {
		name: 'Login',
		data() {
			return {
				form: {
					username: '',
					password: ''
				},
				rules: {
					username: [
						{required: true, message: 'Username is required', trigger: 'blur'}
					],
					password: [
						{required: true, message: 'Password is required', trigger: 'blur'}
					]
				}
			}
		},
		methods: {
			onSubmit() {
				this.isError = 'false';
				axios.post(this.$APIBASE + 'auth/login', this.form)
					.then(response => {
						if (response.status === 200) {
							App.methods.afterLogin(response);
							this.$router.push({path: 'animals'});
							location.reload();
						}
					}).catch(e => {
					console.log(e);
					this.form.username = '';
					this.form.password = '';
					this.$message({
						type: 'error',
						message: 'Bad credentials'
					});
				})
			},
			loginEnterPressed() {
				this.onSubmit();
			}
		}

	}
</script>

<style scoped lang="scss">

	>>> input {
		border-radius: 30px;
		text-decoration-color: black;
		color: black;
		border: black 1px solid;
		width: 50%;
		margin: 0 auto;
		float: left;
	}

	>>> textarea {
		border-radius: 30px;
		text-decoration-color: black;
		color: black;
		border: black 1px solid;
		width: 90%;
		margin: 0 auto;
		float: left;
	}

	.el-form-item {
		margin: 0 auto;
		width: 90%;
	}

	>>> ::placeholder {
		color: black;
	}

	.form-field {
		margin-top: 16px;
		margin-botton: 16px;
	}

	.form-container {
		margin: 0 auto;
		width: 30%;
		border-radius: 5px;
		box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
	}
</style>


