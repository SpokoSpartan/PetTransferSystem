<template>
	<div>
		<h1>Login</h1>
		<el-form class="form-container" ref="form" :model="form" :rules="rules" label-position="top" label-width="100px">
			<br>
			<el-form-item class="form-field" prop="email" label="Email">
				<el-input placeholder="Email"
						  v-model="form.email"/>
			</el-form-item>
			<el-form-item class="form-field" prop="password" label="Password">
				<el-input placeholder="Login"
						  v-model="form.password"
						  show-password/>
			</el-form-item>
			<br>
			<el-form-item>
				<el-button type="primary" @click="onSubmit" round>Login</el-button>
			</el-form-item>
			<br>
		</el-form>

	</div>
</template>

<script>
	import axios from "axios";

	export default {
		name: 'Login',
		data() {
			return {
				form: {
					email: '',
					password: ''
				},
				rules: {
					email: [
						{required: true, message: 'Email is required', trigger: 'blur'}
					],
					password: [
						{required: true, message: 'Password is required', trigger: 'blur'}
					]
				}
			}
		},
		methods: {
			onSubmit() {
				console.log(this.form.login);
				console.log(this.form.password);
				axios.post('http://52.91.229.171:80/api/animal/create', this.form)
					.then(response => {
						this.posts = response;
						console.log(response);
					}).catch(e => {
					this.errors.push(e)
				})
			}
		}

	}
</script>

<style scoped lang="scss">

	> > > input {
		border-radius: 30px;
		text-decoration-color: black;
		color: black;
		border: black 1px solid;
		width: 50%;
		margin: 0 auto;
		float: left;
	}

	> > > textarea {
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

	> > > ::placeholder {
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
