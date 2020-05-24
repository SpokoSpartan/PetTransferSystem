<template>
	<div style="margin-bottom: 4%;">
		<h1>Adopt animal</h1>
		<el-row>
			<el-col :span="11">
				<div class="grid-content bg-purple">
					<el-form class="form-container" style="width: 70%" ref="form" label-position="left"
							 label-width="90%;">
						<h2 style="margin-top: 5%;">Animal to be adopted:</h2>
						<img width="60%" class="img-limit" :src="this.animalModel.imageUrl">
						<h2>{{this.animalModel.name}}</h2>
					</el-form>
				</div>
			</el-col>

			<el-col :span="2">
				<div class="grid-content bg-purple-light">
					<img style="margin-top: 60%;" src="../assets/icons-arrow.png">
				</div>
			</el-col>

			<el-col :span="11">
				<div class="grid-content bg-purple-light">
					<el-form style="width: 90%;" class="form-container" ref="form" :model="form" :rules="rules" label-position="left" label-width="140px">
						<h2 style="margin-bottom: -1%;">Adopting person's data:</h2>
						<br>
						<el-form-item class="form-field" prop="fullName" label="Full name" :error="getErrorForFullName()">
							<el-input placeholder="Full name"
									  v-model="form.fullName"/>
						</el-form-item>
						<el-form-item class="form-field" prop="address" label="Address" :error="getErrorForAddress()">
							<el-input placeholder="Address"
									  v-model="form.address"/>
						</el-form-item>
						<el-form-item class="form-field" prop="phoneNumber" label="Phone number" :error="getErrorForPhoneNumber()">
							<el-input placeholder="Phone number"
									  v-model="form.phoneNumber"/>
						</el-form-item>
							<el-button style="margin-bottom: 5%;" type="primary" @click="adoptAnimal()" round>Proceed adoption</el-button>
						<br>
					</el-form>
				</div>
			</el-col>
		</el-row>
	</div>
</template>

<script>
	import axios from "axios";
	import {AnimalModel} from "../models/AnimalModel";
	import VueRouter from 'vue-router'
	import Vue from "vue";

	Vue.use(VueRouter)

	export default {
		name: "Adopt pet",
		data() {
			return {
				animalModel: AnimalModel,
				users: [],
				form: {
					phoneNumber: '',
					address: '',
					fullName: ''
				},
				rules: {
					phoneNumber: [
						{required: true, message: 'Phone number is required', trigger: 'blur'}
					],
					address: [
						{required: true, message: 'Address is required', trigger: 'blur'}
					],
					fullName: [
						{required: true, message: 'Full name is required', trigger: 'blur'}
					]
				}
			}
		},
		methods: {
			getErrorForFullName() {
				if (this.form.fullName === '') {
					return;
				}
				if (this.form.fullName.length > 5) {
					return;
				}
				return 'Full name must have at least 6 chars';
			},
			getErrorForPhoneNumber() {
				if (this.form.phoneNumber === '') {
					return;
				}
				if (this.form.phoneNumber.length > 5) {
					return;
				}
				return 'Phone number must have at least 6 chars';
			},
			getErrorForAddress() {
				if (this.form.address === '') {
					return;
				}
				if (this.form.address.length > 5) {
					return;
				}
				return 'Address must have at least 6 chars';
			},
			adoptAnimal(userId) {
				const moveTo = this.users[userId];
				this.$confirm('Are you sure you want proceed adoption of this animal?', 'Warning', {
					confirmButtonText: 'OK',
					cancelButtonText: 'Cancel',
					type: 'warning'
				}).then(() => {
					const token = localStorage.getItem('access_token');
					if (token !== null) {
						axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
					}
					axios.post(this.$APIURL + 'animal/' + this.animalModel.id + '/adopt', this.form)
						.then(response => {
							this.$message({
								type: 'success',
								message: 'Adopting completed'
							});
							this.redirectToAnimalPage(this.animalModel.id)
						}).catch(e => {
						this.$message({
							type: 'error',
							message: 'An error occurred. Please reload this page'
						});
					})
				}).catch(e => {
					this.$message({
						type: 'info',
						message: 'Adopting canceled'
					});
				});
			},
			redirectToAnimalPage(id) {
				console.log('AFTER ADOPTING');
				this.$router.push({path: '/animal/' + id});
			}
		},
		created() {
			let id = this.$route.params.id;
			const token = localStorage.getItem('access_token');
			if (token !== null) {
				axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
			}
			axios.get(this.$APIURL + 'animal/one/' + id)
				.then(response => {
					this.animalModel = response.data;
					console.log(response.data);
				}).catch(e => {
				this.errors.push(e)
			});
		},
	}
</script>

<style scoped>
	>>> input {
		border-radius: 30px;
		text-decoration-color: black;
		color: black;
		border: black 1px solid;
		width: 90%;
		margin: 0 auto;
		float: left;
	}

	.upload-fields {
		/*float: left;*/
		/*display: flex;*/
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
		margin-bottom: 16px;
	}

	.form-container {
		margin: 0 auto;
		width: 50%;
		border-radius: 5px;
		box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
	}
</style>
