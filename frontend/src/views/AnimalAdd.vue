<template>
	<div>
		<h1>Add animal</h1>
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
				<el-row :gutter="10">
					<el-col :span="4" style="margin-right: 12px;">
						<el-upload
							action=""
							:http-request="uploadFileAndGetLink"
							:show-file-list="false"
						>
							<el-button size="small" type="primary">Click to upload</el-button>
						</el-upload>
					</el-col>
					<el-col :span="8">
						<el-input placeholder="Image URL" v-model="form.imageUrl"/>
					</el-col>

					<el-col :span="6">
						<div style="width: 250px;">jpg/png, max file size allowed 500KB</div>
					</el-col>
				</el-row>
			</el-form-item>

			<el-form-item class="form-field" label="Sex">
				<el-select v-model="form.sex" placeholder="Animal sex" style="width: 100%;">
					<el-option
						v-for="sex in sexes"
						:key="sex"
						:label="sex"
						:value="sex">
					</el-option>
				</el-select>
			</el-form-item>

			<el-form-item class="form-field" label="Date of birth">
				<el-date-picker
					v-model="form.birthDate"
					type="date"
					placeholder="Pick animal's birthdate"
					style="width: 100%;">
				</el-date-picker>
			</el-form-item>

			<el-form-item class="form-field" label="Sterilized?">
				<el-checkbox v-model="form.sterilized"  style="float: left;">
					Sterilized
				</el-checkbox>
			</el-form-item>

			<el-button style="margin-bottom: 12px;" type="success" @click="onSubmit" round>Create</el-button>

		</el-form>
	</div>
</template>

<script>

	import axios from "axios";
	import Vue from "vue";
	import VueRouter from "vue-router";

	Vue.use(VueRouter);

	export default {
		name: "AnimalAdd",
		data() {
			return {
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
				}
			}
		},
		methods: {
			onSubmit() {
				console.log(this.form);
				const token = localStorage.getItem('access_token');
				if (token !== null) {
					axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
				}
				axios.post(this.$APIURL + 'animal/create', this.form)
					.then(response => {
						this.$router.push({path: '/myPets'});
						console.log(response);
					}).catch(e => {
					this.errors.push(e)
				})
			},
			uploadFileAndGetLink(file, fileList) {
				console.log('upload function')

				const fd = new FormData();
				fd.append("image", file.file);

				const token = localStorage.getItem('access_token');
				if (token !== null) {
					axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
				}
				axios.post(this.$APIURL + 'file/image/upload', fd)
					.then(response => {
						this.uploadedImageURL = response.data.url;
						this.form.imageUrl = this.uploadedImageURL;
					}).catch(e => {
					this.errors.push(e)

				})
			}
		}
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
