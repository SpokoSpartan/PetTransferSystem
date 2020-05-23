<template>
	<div>
		<h1>Transfer animal</h1>
		<el-row>
			<el-col :span="11"><div class="grid-content bg-purple">
				<p>{{animalModel.name}}</p>
				<img width="40%" class="img-limit" :src="animalModel.imageUrl">
			</div></el-col>
			<el-col :span="2"><div class="grid-content bg-purple-light">
				<img style="margin-top: 60%;" src="../assets/icons-arrow.png">
			</div></el-col>
			<el-col :span="11"><div class="grid-content bg-purple-light">
				<el-form class="form-container" style="width: 90%" ref="form" :model="form" :rules="rules" label-position="left" label-width="90%;">
					<h2>Find user:</h2>
<!--					<br>-->
					<el-form-item class="form-field" prop="pattern" label="Pattern" id="login-username">
						<el-input placeholder="Phone number, email, or name"
								  v-model="form.pattern"/>
						<el-row>
							<el-col :span="20">
							</el-col>
							<el-col :span="4">
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
									type="primary"
									size="normal"
									@click="moveAnimalTo(scope.$index)">Move</el-button>
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
						{required: true, message: 'Pattern is required', trigger: 'blur'}
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
			moveAnimalTo(userId) {
				const moveTo = this.users[userId];
				this.$confirm('This will move this animal to new user. If new user not belong to your organization ' +
					'you will not have access to manage this entity anymore?', 'Warning', {
					confirmButtonText: 'OK',
					cancelButtonText: 'Cancel',
					type: 'warning'
				}).then(() => {
					const token = localStorage.getItem('access_token');
					if (token !== null) {
						axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
					}
					axios.get(this.$APIURL + '/animal/' + this.animalModel.id + '/transfer-to/1' + moveTo.id)
						.then(response => {
							this.$message({
								type: 'success',
								message: 'Moving completed'
							});
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
			}
		},
		created() {
			let id = this.$route.params.id;
			const token = localStorage.getItem('access_token');
			if (token !== null) {
				axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
			}
			axios.get(this.$APIURL + 'api/animal/one/' + id)
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
