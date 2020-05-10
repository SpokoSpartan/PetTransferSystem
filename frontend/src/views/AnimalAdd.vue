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
					v-model="description">
				</el-input>
			</el-form-item>
			<el-form-item class="form-field" prop="imageUrl" label="Image URL">
				<div class="upload-fields">
					<el-input placeholder="Image" v-model="form.imageUrl"/>
					<el-upload
						class="upload-demo"
						action="https://jsonplaceholder.typicode.com/posts/"
						:on-preview="handlePreview"
						:on-remove="handleRemove"
						:before-remove="beforeRemove"
						multiple
						:limit="1"
						:on-exceed="handleExceed">
<!--						:file-list="fileList"-->

						<el-button size="small" type="primary">Click to upload</el-button>
					</el-upload>
						<div >jpg/png files with a size less than 500kb</div>
				</div>
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
		name: "AnimalAdd",
		data() {
			return {
				posts: [],
				errors: [],
				sexes: ['male', 'female', 'unknown'],
				form: {
					name: '', // required
					species: '', // required
					race: '', // required
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
		float: left;
		display: flex;
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
		margin-botton: 16px;
	}

	.form-container {
		margin: 0 auto;
		width: 50%;
		border-radius: 5px;
		box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
	}
</style>
