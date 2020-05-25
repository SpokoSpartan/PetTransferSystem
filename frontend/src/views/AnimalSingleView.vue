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
						<p><b>Species: </b> {{animalModel.species}}</p>
						<p><b>Race: </b>{{animalModel.race}}</p>
						<p><b>Sex: </b>{{animalModel.sex}}</p>
						<p><b>Sterilized: </b>{{translateBooleanToHuman(animalModel.sterilized)}}</p>
						<p><b>Birth date: </b>{{getAgeWithBirthDate(animalModel.birthDate)}} </p>
					</div>
				</el-aside>
				<el-main style="text-align: left;">
					<el-container>
						<el-main>
							<p><strong>Description:</strong> {{animalModel.description}}</p>
							<p><strong>In shelter from:</strong> {{getAgeWithBirthDate(animalModel.shelterJoinDate)}}</p>
						</el-main>
						<el-main style="min-width: 300px;">
							<div style="border: 1px solid black; padding-left: 12px; padding-right: 12px;">
								<h3>Contact info:</h3>
								It is currently at {{animalModel.animalLocation.locationType}}: {{animalModel.animalLocation.fullName}}
								<p><b>Address:</b> {{animalModel.animalLocation.address}}</p>
								<p><b>Email:</b> {{animalModel.animalLocation.email}}</p>
								<p><b>Phone:</b> {{animalModel.animalLocation.phone}}</p>
							</div>
              <div style="border: 1px solid black; padding-left: 12px; padding-right: 12px; margin-top: 30px;">
                <h3>Adopter info:</h3>
                Was adopted by: {{this.animalModel.adoption.fullName}}
                <p><b>Address:</b>  {{this.animalModel.adoption.address}} </p>
                <p><b>Phone:</b> {{this.animalModel.adoption.phoneNumber}}</p>
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
		methods: {},
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
		border-radius: 10%;
		margin-top: 60px;
		max-width: 400px;
		/*max-height: 400px;*/
	}
</style>
