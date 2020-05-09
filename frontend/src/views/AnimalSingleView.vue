<template>
	<div>
		<el-container>
			<el-aside width="400px">
				<div width="1000px">
					<img style="width:400px;" :src="animalModel.imageUrl">
				</div>
				<p>Species: {{animalModel.species}}</p>
				<p>Race:{{animalModel.race}}</p>
				<p>Birth date: {{animalModel.birthDate}}</p>
				<p>Sex: {{animalModel.sex}}</p>
				<p>Sterilized: {{animalModel.sterilized}}</p>
				<p>In shelter from: {{animalModel.shelterJoinDate}}</p>


			</el-aside>
			<el-container>
				<el-header>
					<h1>This is {{animalModel.name}}</h1>
				</el-header>
				<el-main style="text-align: left;">
					<p>Description: {{animalModel.description}}</p>
					<div>
						<h3>Contact info:</h3>
						It is currently at {{animalModel.animalLocation.locationType}}: {{animalModel.animalLocation.fullName}}
						<p>Address: {{animalModel.animalLocation.address}}</p>
						<p>Email: {{animalModel.animalLocation.email}}</p>
						<p>Pmail: {{animalModel.animalLocation.phone}}</p>
					</div>
				</el-main>
				<el-footer>Footer</el-footer>
			</el-container>
		</el-container>


	</div>
</template>


<script>
	import axios from "axios";

	export default {
		name: "AnimalSingleView",
		data() {
			return {
				posts: [],
				errors: [],
				animalModel: {
					id: 'x',
					name: 'name',
					species: 'species',
					race: 'race',
					description: 'descr',
					birthDate: 'asdf', // Date
					sex: 'sex',
					sterilized: 's', // true false
					shelterJoinDate: '', // Date
					animalLocation: {
						fullName: '',
						phone: '',
						email: '',
						address: '',
						locationType: ''
					},
					imageUrl: 'aasdfasd', // link,
					location: 'asdfa', // AnimalLocationResponse
					treatmentHistory: 'shit' // Set<TreatmentHistoryResponse>
				}
			}
		},
		computed: {
			getAgeWithBirthDate: function () {
				// calculate age here, reutnr 'unknown' if birthdate is null
				return new Date(this.animalModel.birthDate);
			}
		},
		created() {
			let id = this.$route.params.id;
			axios.get('http://34.207.67.160:80/api/animal/one/' + id)
				.then(response => {
					this.animalModel = response.data;
					console.log(response.data);
				}).catch(e => {
				this.errors.push(e)
			});
		}
	}
</script>

<style scoped>

</style>
