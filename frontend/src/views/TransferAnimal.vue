<template>
	<div>
		<h1>Transfer animal</h1>
		<el-row>
			<el-col :span="11"><div class="grid-content bg-purple">
				<el-form class="form-container" style="width: 70%" ref="form" label-position="left" label-width="90%;">
					<h2 style="margin-top: 5%;">Animal to be transferred:</h2>
					<img width="60%" class="img-limit" :src="this.animalModel.imageUrl">
					<h2>{{this.animalModel.name}}</h2>
				</el-form>
			</div></el-col>
			<el-col :span="2"><div class="grid-content bg-purple-light">
				<img style="margin-top: 60%;" src="../assets/icons-arrow.png">
			</div></el-col>
			<el-col :span="11"><div class="grid-content bg-purple-light">
				<el-form class="form-container" style="width: 90%" ref="form" :model="form" :rules="rules" label-position="left" label-width="90%;">
					<h2>Find user:</h2>
					<el-form-item class="form-field" prop="pattern" label="Find user" id="login-username">

						<el-row>
							<el-col :span="17">
							<el-input placeholder="User name or email"
									  @keydown.enter.native="findUsers"
									  v-model="form.pattern"/>
							</el-col>
							<el-col :span="2">
							<el-button type="primary" @click="findUsers" round>Search</el-button>
							</el-col>
						</el-row>
					</el-form-item>
					<el-table
						:data="users"
						style="width: 100%">
						<el-table-column
							label="Name"
							prop="fullName">
						</el-table-column>
						<el-table-column
							label="Email"
							prop="email">
						</el-table-column>
						<el-table-column
							label="Phone number"
							prop="phoneNumber">
						</el-table-column>
						<el-table-column
							label="Actions">
							<template slot-scope="scope">
								<el-button
									round
									type="primary"
									size="normal"
									@click="transferAnimalTo(scope.$index)">Transfer</el-button>
							</template>
						</el-table-column>
					</el-table>
					<br>
				</el-form>
			</div></el-col>
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
		name: "Transfer pet",
		data() {
			return {
				animalModel: AnimalModel,
				users: [],
				form: {
					pattern: ''
				},
				rules: {
					pattern: [
						{required: true, message: 'Search value can not be empty', trigger: 'blur'}
					]
				}
			}
		},
		methods: {
			findUsers() {
				const token = localStorage.getItem('access_token');
				if (token !== null) {
					axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
				}
				axios.get(this.$APIURL + 'user/find/' + this.form.pattern)
					.then(response => {
						this.users = response.data;
						console.log(response);
					}).catch(e => {
					this.errors.push(e)
				})
			},
			transferAnimalTo(userId) {
				const receivingUser = this.users[userId];
				this.$confirm('This will transfer ownership over this animal to selected user. If this user does not belong to your organization, you will lose access to manage this animal.', 'Warning', {
					confirmButtonText: 'OK',
					cancelButtonText: 'Cancel',
					type: 'warning'
				}).then(() => {
					const token = localStorage.getItem('access_token');
					if (token !== null) {
						axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
					}
					axios.post(this.$APIURL + 'animal/' + this.animalModel.id + '/transfer-to/' + receivingUser.id)
						.then(response => {
							this.$message({
								type: 'success',
								message: 'Moving completed'
							});
							this.redirectToAnimalPage()
						}).catch(e => {
						this.$message({
							type: 'error',
							message: 'An error occurred. Please reload this page'
						});
					})
				}).catch(e => {
					this.$message({
						type: 'info',
						message: 'Moving canceled'
					});
				});
			},
			redirectToAnimalPage() {
				console.log('AFTER TRANSFER');
				this.$router.push({path: '/myPets'});
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
