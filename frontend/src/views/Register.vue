<template>
	<div>
		<h1>Register</h1>
		<el-form class="form-container" ref="form" :model="form" :rules="rules" label-position="left" label-width="140px">
			<br>
			<el-form-item class="form-field" prop="username" label="Username">
            	<el-input placeholder="Username"
            			  v-model="form.fullName"/>
            </el-form-item>
			<el-form-item class="form-field" prop="email" label="Email">
				<el-input placeholder="Email"
						  v-model="form.email"/>
			</el-form-item>
			<el-form-item class="form-field" prop="password" label="Password">
				<el-input placeholder="Password"
						  v-model="form.password"
						  show-password/>
			</el-form-item>
			<el-form-item class="form-field" prop="passwordConfirm" label="Confirm password">
				<el-input placeholder="Confirm password"
						  v-model="form.passwordConfirm"
						  show-password/>
			</el-form-item>
			<el-form-item class="form-field" prop="address" label="Address">
				<el-input placeholder="Address"
						  v-model="form.address"/>
			</el-form-item>
			<el-form-item class="form-field" prop="phone" label="Phone number">
				<el-input placeholder="Phone number"
						  v-model="form.phoneNumber"/>
			</el-form-item>
			<br>
			<el-form-item>
				<el-button type="primary" @click="onSubmit" round>Register</el-button>
			</el-form-item>
			<br>
		</el-form>
	</div>
</template>

<script>
	import axios from "axios";
	import * as router from "vue-router";

	export default {
		data() {
			return {
				form: {
					fullName: '',
					email: '',
					password: '',
					passwordConfirm: '',
					phoneNumber: '',
					address: ''
				},
				rules: {
					fullName: [
						{required: true, message: 'Username is required.', trigger: 'blur'}
					],
					email: [
						{required: true, message: 'Email is required.', trigger: 'blur'}
					],
					password: [
						{required: true, message: 'Password is required', trigger: 'blur'}
					],
					passwordConfirm: [
						{required: true, message: 'Both passwords must match', trigger: 'blur'}
					],
					address: [
						{required: true, message: 'Address is required', trigger: 'blur'}
					],
					phoneNumber: [
						{required: true, message: 'Phone number is required', trigger: 'blur'}
					]
				}
			}
		},
		methods: {
			onSubmit() {
				axios.post('http://52.91.229.171:80/auth/register', this.form)
					.then(response => {
						if (response.status === 200) {
							router.push("/login")
						}
					}).catch(e => {
						router.push('login')
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
		width: 80%;
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
		width: 40%;
		border-radius: 5px;
		box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
	}
</style>
