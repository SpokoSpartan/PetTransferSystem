<template>
	<div>
		<h1>Edit your pet</h1>
		<el-form class="form-container" ref="form" :model="form" :rules="rules" label-position="left" label-width="100px">
			<br>
			<el-form-item class="form-field" prop="name" label="Name">
				<el-input placeholder="Name"
						  v-model="form.name"
						  maxlength="255"
						  show-word-limit/>
			</el-form-item>
			<el-form-item class="form-field" prop="species" label="Species">
				<el-input placeholder="Species"
						  v-model="form.species"
						  maxlength="255"
						  show-word-limit/>
			</el-form-item>
			<el-form-item class="form-field" prop="race" label="Race">
				<el-input placeholder="Race"
						  v-model="form.race"
						  maxlength="255"
						  show-word-limit/>
			</el-form-item>
			<el-form-item class="form-field" prop="description" label="Description">
				<el-input
					type="textarea"
					:rows="2"
					maxLength="1000"
					placeholder="Description"
					show-word-limit
					v-model="form.description">
				</el-input>
			</el-form-item>
			<el-form-item class="form-field" prop="imageUrl" label="Image URL">
				<div class="upload-fields" style="width: 50%; ">
					<el-input placeholder="Image" v-model="form.imageUrl" style="float: left;"/>
					<el-upload

						style="width: 30%; float: left;"
						action="https://jsonplaceholder.typicode.com/posts/"
						multiple
						:limit="1">
						<!--						:on-preview="handlePreview"-->
						<!--						:on-remove="handleRemove"-->
						<!--						:before-remove="beforeRemove"-->
						<!--						:on-exceed="handleExceed">-->
						<!--						:file-list="fileList"-->

						<el-button size="small" type="primary">Click to upload</el-button>
					</el-upload>
				</div>
				<div style="width: 300px;">jpg/png, max file size allowed 500KB</div>
			</el-form-item>
			<el-form-item class="form-field" label="Date of birth">
				<el-date-picker
					v-model="form.birthDate"
					type="date"
					placeholder="Pick animal's birthdate">
				</el-date-picker>
			</el-form-item>
			<el-form-item class="form-field" label="Sterilized?">
				<el-checkbox v-model="form.sterilized">Sterilized</el-checkbox>
			</el-form-item>
			<el-form-item class="form-field">
				<el-select v-model="form.sex" placeholder="Animal sex">
					<el-option
						v-for="sex in sexes"
						:key="sex"
						:label="sex"
						:value="sex">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button type="success" @click="onSubmit" round>Create</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>

	import axios from "axios";

	export default {
		name: "EditMyPet",
		data() {
			return {
				posts: [],
				errors: [],
				sexes: ['male', 'female', 'unknown'],
				form: {
					name: '', // required
					species: '', // required
					race: '', // required
					description: '',
					birthDate: '', // required
					sex: '',
					sterilized: false,
					imageUrl: ''
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
				},
				animalModel: {
					id: '',
					name: '',
					species: '',
					race: '',
					description: '',
					birthDate: '', // Date
					sex: '',
					sterilized: '', // true false
					shelterJoinDate: '', // Date
					animalLocation: {
						fullName: '',
						phone: '',
						email: '',
						address: '',
						locationType: ''
					},
					imageUrl: '', // link,
					location: '', // AnimalLocationResponse
					treatmentHistories: [] // Set<TreatmentHistoryResponse>
				}
			}
		},
		methods: {
			onSubmit() {
				console.log('submit!');
				console.log(this.form);
				axios.post(this.$APIURL + 'api/animal/create', this.form)
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
			populateForm(model){

				this.form.name = model.name;
				this.form.species = model.species;
				this.form.race = model.race;
				this.form.description = model.descrption;
				this.form.birthDate = "???"
				this.form.sex = model.sex;
				this.form.sterilized = "???"
				this.form.imageUrl = model.imageUrl;
			}
		},
		created() {
			let id = this.$route.params.id;
			axios.get(this.$APIURL + 'api/animal/one/' + id)
				.then(response => {
					this.animalModel = response.data;
					this.populateForm(this.animalModel)
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
