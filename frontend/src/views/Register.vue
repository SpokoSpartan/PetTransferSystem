<template>
	<div>
		<h1>Register</h1>
		<el-form class="form-container" ref="form" :model="form" :rules="rules" label-position="left" label-width="140px">
			<br>
			<el-form-item class="form-field" prop="fullName" label="Username" :error="getErrorForUsername()">
            	<el-input placeholder="Username"
            			  v-model="form.fullName"/>
            </el-form-item>
			<el-form-item class="form-field" prop="email" label="Email" :error=getErrorForEmail()>
				<el-input placeholder="Email"
						  v-model="form.email"/>
			</el-form-item>
			<el-form-item class="form-field" prop="password" label="Password" :error="getErrorForPassword()">
				<el-input placeholder="Password"
						  v-model="form.password"
						  show-password/>
			</el-form-item>
			<el-form-item class="form-field" prop="passwordConfirm" label="Confirm password" :error="getErrorForPasswordConfirm()">
				<el-input placeholder="Confirm password"
						  v-model="form.passwordConfirm"
						  show-password/>
			</el-form-item>
			<el-form-item class="form-field" prop="address" label="Address" :error="getErrorForAddress()">
				<el-input placeholder="Address"
						  v-model="form.address"/>
			</el-form-item>
			<el-form-item class="form-field" prop="phoneNumber" label="Phone number" :error="getErrorForPhoneNumber()">
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
	import Vue from 'vue'
	import VueRouter from 'vue-router'

	Vue.use(VueRouter)

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
						{required: true, message: 'Username is required.', trigger: 'blur'},
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
				axios.post(this.$APIURL + 'auth/register', this.form)
					.then(response => {
						if (response.status === 200) {
							this.$router.push({path: 'login'});
						}
					}).catch(e => {
						this.form.fullName = '';
						this.$message({
							type: 'error',
							message: 'User with given name already exists in the system'
						});
				})
			},
			getErrorForUsername() {
				if (this.form.fullName === '') {
					return;
				}
				if (this.form.fullName.length > 5) {
					return;
				}
				return 'Username must have at least 6 chars';
			},
			getErrorForEmail() {
				if (this.form.email === '') {
					return;
				}
				let regex = new RegExp('(?:[a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&\'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\\])');
				if (this.form.email.match(regex) !== null) {
					return;
				}
				return 'Email format is required';
			},
			getErrorForPassword() {
				if (this.form.password === '') {
					return;
				}
				let regex = new RegExp('^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$');
				if (this.form.password.match(regex) !== null) {
					return;
				}
				return 'Eight chars, one letter and one number';
			},
			getErrorForPasswordConfirm() {
				if (this.form.passwordConfirm === '') {
					return;
				}
				if (this.form.password === this.form.passwordConfirm) {
					return;
				}
				return 'Passwords must be the same';
			},
			getErrorForAddress() {
				if (this.form.address === '') {
					return;
				}
				if (this.form.address.length > 5) {
					return;
				}
				return 'Username must have at least 6 chars';
			},
			getErrorForPhoneNumber() {
				if (this.form.phoneNumber === '') {
					return;
				}
				if (this.form.phoneNumber.length > 8) {
					return;
				}
				return 'Phone number must have at least 9 chars';
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
