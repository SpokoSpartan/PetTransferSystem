<template>
	<div>
		<h1>Add animal</h1>
		<el-form class="form-container" ref="form" :model="form">
			<br>
			<el-form-item l class="form-field">
				<el-input placeholder="Name" v-model="form.name"/>
			</el-form-item>
			<div class="form-field">
				<el-input placeholder="Race" v-model="form.race"/>
			</div>
			<el-form-item class="form-field">
				<el-input placeholder="Species" v-model="form.species"/>
			</el-form-item>
			<el-form-item class="form-field">
				<el-input
					type="textarea"
					:rows="2"
					placeholder="Description"
					v-model="description">
				</el-input>
			</el-form-item>
			<el-form-item class="form-field">
				<el-input placeholder="Image" v-model="form.imageUrl"/>
				<el-upload
					class="upload-demo"
					action="https://jsonplaceholder.typicode.com/posts/"
					:on-preview="handlePreview"
					:on-remove="handleRemove"
					:before-remove="beforeRemove"
					multiple
					:limit="1"
					:on-exceed="handleExceed"
					:file-list="fileList">
					<el-button size="small" type="primary">Click to upload</el-button>
					<div slot="tip" class="el-upload__tip">jpg/png files with a size less than 500kb</div>
				</el-upload>
			</el-form-item>
			<el-form-item class="form-field">
				<div class="block">
					<span>Default</span>
					<el-date-picker
						v-model="form.birthDate"
						type="date"
						placeholder="Pick a day of animal birth">
					</el-date-picker>
				</div>
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
					sterilised: true,
					imageUrl: ''
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
	}

	>>> textarea {
		border-radius: 30px;
		text-decoration-color: black;
		color: black;
		border: black 1px solid;
		width: 90%;
		margin: 0 auto;
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
