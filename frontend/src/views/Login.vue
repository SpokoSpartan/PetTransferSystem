<template>
	<div>
		<h1>Login</h1>
		<el-form class="form-container" ref="form" :model="form" :rules="rules" label-position="left" label-width="100px">
			<br>
			<el-form-item class="form-field" prop="login" label="Login">
				<el-input placeholder="Login"
						  v-model="form.login"/>
			</el-form-item>
			<el-form-item class="form-field" prop="password" label="Password">
				<el-input placeholder="Login"
						  v-model="form.password"
						  show-password/>
			</el-form-item>
			<br>
			<el-form-item>
				<el-button type="primary" @click="onSubmit" round>Create</el-button>
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
					login: '',
					password: ''
				},
				rules: {
					name: [
						{required: true, message: 'Please input animal\'s name', trigger: 'blur'}
					],
					species: [
						{required: true, message: 'Please input animal\'s species', trigger: 'blur'}
					],
					race: [
						{required: true, message: 'Please input animal\'s race', trigger: 'blur'}
					],
				}
			}
		},
		methods: {
			onSubmit() {
				console.log('submit!');
				console.log(this.form);
				axios.post('http://34.207.67.160:80/api/animal/create', this.form)
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

	.upload-fields {
		float: left;
		display: flex;
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
