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

			<el-form-item class="form-field" label="From ... to">
				<div class="block">
					<el-date-picker
						v-model="startEndDates"
						type="daterange"
						range-separator="To"
						start-placeholder="Start date"
						end-placeholder="End date">
					</el-date-picker>
				</div>
			</el-form-item>

			<el-form-item class="form-field" prop="place" label="Place">
				<el-input placeholder="Place"
						  v-model="form.place"/>
			</el-form-item>

			<el-form-item class="form-field" prop="price" label="Price">
				<el-input placeholder="Price"
						  v-model="form.price"/>
			</el-form-item>
			<div>
				<el-button v-if="!isEditing" style="margin-bottom: 12px;" type="success" round @click="addTreatmentHistory">Add</el-button>
				<el-button v-if="isEditing" style="margin-bottom: 12px;" type="success" round @click="updateTreatmentHistory">Update</el-button>
				<el-button v-if="formVisible" style="margin-bottom: 12px;" type="danger" round @click="formVisible = false; isEditing = false">Cancel</el-button>
			</div>

		</el-form>
		<br>
		<el-table
			:data="tableData"
			:default-sort = "{prop: 'startDate', order: 'ascending'}"
			stripe
			border
			style="width: 100%; padding-top: 12px;">
			<el-table-column prop="treatmentStatus" label="Status" width="180"></el-table-column>
			<el-table-column prop="title" label="Name" width="180"></el-table-column>
			<el-table-column prop="doctor" label="Doctor"></el-table-column>
			<el-table-column prop="description" label="Description"></el-table-column>
			<el-table-column prop="startDate" label="Start" sortable></el-table-column>
			<el-table-column prop="endDate" label="End"></el-table-column>
			<el-table-column prop="place" label="Place"></el-table-column>
			<el-table-column prop="price" label="Price"></el-table-column>
			<el-table-column label="Update" width="100">
				<el-button type="info" slot-scope="scope" @click="openEditForm(scope.$index)" v-if="scope.row.treatmentStatus !== 'canceled'"> Update</el-button>
			</el-table-column>
			<el-table-column label="Cancel" width="100">
				<el-button type="danger" slot-scope="scope" @click="cancelTreatment(scope.$index)" v-if="scope.row.treatmentStatus !== 'canceled'">Cancel</el-button>
			</el-table-column>
		</el-table>
	</div>
</template>

<script>

	import axios from "axios";
	import {TreatmentRequestDTO} from "../models/dto.request/TreatmentRequestDTO";
	import {utils} from "../utils";

	export default {
		mixins: [utils],
		name: "EditTreatmentHisotory",
		data() {
			return {
				isEditing: false,
				editId: -1,
				posts: [],
				errors: [],
				sexes: ['male', 'female', 'unknown'],
				form: new TreatmentRequestDTO(),
				rules: {},
				tableData: [],
				formVisible: false,
				startEndDates: []
			}
		},
		methods: {
			addTreatmentHistory() {
				const token = localStorage.getItem('access_token');
				if (token !== null) {
					axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
				}
				this.form.startDate = this.startEndDates[0];
				this.form.endDate = this.startEndDates[1];
				axios.post(this.$APIURL + 'treatment/animal/' + this.animalModel.id + '/add', this.form)
					.then(() => {
						this.formVisible = false;
						this.getHistories();
					})
			},
			updateTreatmentHistory() {
				const token = localStorage.getItem('access_token');
				if (token !== null) {
					axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
				}
				this.form.startDate = this.startEndDates[0];
				this.form.endDate = this.startEndDates[1];
				axios.post(this.$APIURL + 'treatment/update/' + this.editId, this.form)
					.then(() => {
						this.formVisible = false;
						this.getHistories();
					});
			},
			openEditForm(index) {
				this.populateForm(this.tableData[index])
				this.formVisible = true;
				this.isEditing = true;
				this.editId = this.tableData[index].id;
			},
			cancelTreatment(index) {
				const token = localStorage.getItem('access_token');
				if (token !== null) {
					axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
				}
				axios.post(this.$APIURL + 'treatment/cancel/' + this.tableData[index].id)
					.then(() => {
						this.formVisible = false;
						this.getHistories();
					});
			},
			getHistories() {
				let id = this.$route.params.id;
				const token = localStorage.getItem('access_token');
				if (token !== null) {
					axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
				}
				axios.get(this.$APIURL + 'animal/one/' + id)
					.then(response => {
						this.animalModel = response.data;
						this.tableData = this.animalModel.treatmentHistories;
						this.tableData.forEach((row) => {
							row.startDate = utils.methods.getAgeWithBirthDate(row.startDate)
							row.endDate = utils.methods.getAgeWithBirthDate(row.endDate)
						})
						console.log(this.tableData)
					}).catch(e => {
					this.errors.push(e)
				});
			},
			populateForm(model) {
				this.form.title = model.title;
				this.form.doctorName = model.doctorName;
				this.form.description = model.description;
				this.form.place = model.place;
				this.form.price = model.price;

				this.startEndDates[0] = model.startDate;
				this.startEndDates[1] = model.endDate;
			}
		},
		created() {
			this.getHistories();
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
