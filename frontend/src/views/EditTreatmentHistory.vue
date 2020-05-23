<template>
	<div>
		<el-button v-if="!formVisible" type="primary" @click="formVisible = true">Add treatment history</el-button>
		<h1 v-if="formVisible">Add treatment</h1>
		<el-form v-if="formVisible" class="form-container" ref="form" :model="form" :rules="rules" label-position="left" label-width="100px">
			<br>
			<el-form-item class="form-field" prop="title" label="Title">
				<el-input placeholder="Title"
						  v-model="form.title"/>
			</el-form-item>
			<el-form-item class="form-field" prop="name" label="Doctor name">
				<el-input placeholder="Doctor name"
						  v-model="form.doctorName"/>
			</el-form-item>
			<el-form-item class="form-field" prop="description" label="Description">
				<el-input
					type="textarea"
					:rows="1"
					maxLength="1000"
					placeholder="Description"
					show-word-limit
					v-model="form.description">
				</el-input>
			</el-form-item>

			<el-form-item class="form-field" label="Start date">
				<el-date-picker
					v-model="form.startDate"
					type="date"
					placeholder="Beginning of treatment">
				</el-date-picker>
			</el-form-item>
			<el-form-item class="form-field" label="End date">
				<el-date-picker
					v-model="form.endDate"
					type="date"
					placeholder="End of treatment">
				</el-date-picker>
			</el-form-item>
			<el-form-item class="form-field" prop="place" label="Place">
				<el-input placeholder="Place"
						  v-model="form.place"/>
			</el-form-item>
			<el-form-item class="form-field" prop="price" label="Price">
				<el-input placeholder="Price"
						  v-model="form.price"/>
			</el-form-item>

			<el-form-item>
				<el-button type="success" @click="addTreatmentHistory" round>Create</el-button>
			</el-form-item>
		</el-form>

		<el-table
			:data="tableData"
			stripe
			style="width: 100%; padding-top: 12px;">
			<el-table-column prop="treatmentStatus" label="Status" width="180"></el-table-column>
			<el-table-column prop="title" label="Name" width="180"></el-table-column>
			<el-table-column prop="doctor" label="Doctor"></el-table-column>
			<el-table-column prop="description" label="Description"></el-table-column>
			<el-table-column prop="startDate" label="Start"></el-table-column>
			<el-table-column prop="endDate" label="End"></el-table-column>
			<el-table-column prop="place" label="Place"></el-table-column>
			<el-table-column prop="price" label="Price"></el-table-column>
			<el-table-column label="Actions">
				<el-button type="danger">Remove</el-button>
				<el-button type="info">Edit treatment</el-button>
			</el-table-column>
		</el-table>

	</div>
</template>

<script>

	import axios from "axios";
	import {TreatmentRequestDTO} from "../models/dto.request/TreatmentRequestDTO";

	export default {
		name: "EditTreatmentHisotory",
		data() {
			return {
				posts: [],
				errors: [],
				sexes: ['male', 'female', 'unknown'],
				form: new TreatmentRequestDTO(),
				rules: {},
				tableData: [],
				formVisible: false
			}
			// {
			// 	title: '',
			// 		description: '',
			// 	doctorName: '',
			// 	place: '',
			// 	price: 0,
			// 	startDate: '',
			// 	endDate: ''
			// }
		},
		methods: {
			addTreatmentHistory() {
				console.log(this.form);
				console.log(this.$APIURL + 'api/treatment/animal/' + this.animalModel.id + '/add')
				axios.post(this.$APIURL + 'api/treatment/animal/' + this.animalModel.id + '/add', this.form)
				const token = localStorage.getItem('access_token');
				if (token !== null) {
					axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
				}
				axios.post('http://52.91.229.171:80/api/animal/create', this.form)
					.then(response => {
						this.posts = response;
						console.log(response);
					}).catch(e => {
					this.errors.push(e)
				})
			},
			populateForm(model) {
				this.form.title = model.title;
				this.form.doctorName = model.doctorName;
				this.form.description = model.description;
				this.form.startDate = model.startDate;
				this.form.endDate = model.endDate;
				this.form.place = model.place;
				this.form.price = model.price;
			}
		},
		created() {
			let id = this.$route.params.id;
			axios.get(this.$APIURL + 'api/animal/one/' + id)
				.then(response => {
					this.animalModel = response.data;
					this.tableData = this.animalModel.treatmentHistories;
					console.log(this.tableData);
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
