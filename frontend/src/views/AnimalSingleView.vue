<template>
	<div style="margin: 0 auto; width: 70%;">
		<el-container>
			<el-header>
				<h1>This is {{animalModel.name}}</h1>
			</el-header>

			<el-container>
				<el-aside width="400px;">
					<div width="1000px;">
						<img class="img-limit" :src="animalModel.imageUrl">
					</div>
					<div style="text-align: left;">
						<p>Species: {{animalModel.species}}</p>
						<p>Race: {{animalModel.race}}</p>
						<p>Sex: {{animalModel.sex}}</p>
						<p>Sterilized: {{translateBooleanToHuman(animalModel.sterilized)}}</p>
						<p>Birth date: {{getAgeWithBirthDate(animalModel.birthDate)}} </p>
					</div>
				</el-aside>
				<el-main style="text-align: left;">
					<el-container>
						<el-main>
							<p><strong>Description:</strong> {{animalModel.description}}</p>
							<p><strong>In shelter from:</strong> {{getAgeWithBirthDate(animalModel.shelterJoinDate)}}</p>
						</el-main>
						<el-main style="min-width: 300px;">
							<div>
								<h3>Contact info:</h3>
								It is currently at {{animalModel.animalLocation.locationType}}: {{animalModel.animalLocation.fullName}}
								<p>Address: {{animalModel.animalLocation.address}}</p>
								<p>Email: {{animalModel.animalLocation.email}}</p>
								<p>Phone: {{animalModel.animalLocation.phone}}</p>
							</div>
						</el-main>
					</el-container>
					<br>
					<br>
				</el-main>
			</el-container>
		</el-container>


	</div>
</template>


<script>
	import axios from "axios";
	import {AnimalModel} from "../models/AnimalModel";
	import {utils} from "../utils";


	export default {
		mixins: [utils],
		name: "AnimalSingleView",
		data() {
			return {
				posts: [],
				errors: [],
				animalModel: new AnimalModel()
			}
		},
		methods: {

		},
		created() {
			let id = this.$route.params.id;
			axios.get(this.$APIURL + 'animal/one/' + id)
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
	.img-limit {
		margin-top: 60px;
		max-width: 400px;
		/*max-height: 400px;*/
	}
</style>
