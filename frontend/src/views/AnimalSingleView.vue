<template>
	<div>
		<h1>Name: {{animalModel.name}}</h1>
		<p>Species: {{animalModel.species}}</p>
		<p>Race:{{animalModel.race}}</p>
		<p>Description:{{description}}</p>
		<p>:{{}}</p>
		<p>:{{}}</p>
		<p>:{{}}</p>
		<p>:{{}}</p>
		<p>:{{}}</p>
		<p>:{{}}</p>
		<p>:{{}}</p>
		<p>:{{}}</p>
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
					shelterJoinDate: 'now', // Date
					imageUrl: 'aasdfasd', // link,
					location: 'asdfa', // AnimalLocationResponse
					treatmentHistory: 'shit' // Set<TreatmentHistoryResponse>
				}
			}
		},
		computed: {
			getAgeWithBirthDate: function () {
				// calculate age here, reutnr 'unknown' if birthdate is null
				return this.animalModel.birthDate;
			}
		},
		created() {
			axios.get('http://34.207.67.160:80/api/animal/all?page=0&size=1000')
				.then(response => {
					this.posts = response.data.content;
					console.log(response.data.content);
				}).catch(e => {
				this.errors.push(e)
			})
		}
	}
</script>

<style scoped>

</style>
