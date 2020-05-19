<template>
	<div>
		<el-container>
			<el-aside width="400px">
				<div width="1000px">
					<img style="width:400px;" :src="animalModel.imageUrl">
				</div>
				<div style="text-align: left;">
					<p>Species: {{animalModel.species}}</p>
					<p>Race: {{animalModel.race}}</p>
					<p>Sex: {{animalModel.sex}}</p>
					<p>Sterilized: {{translateBooleanToHuman(animalModel.sterilized)}}</p>
					<p>Birth date: {{getAgeWithBirthDate(animalModel.birthDate)}} </p>
				</div>
			</el-aside>
			<el-container>
				<el-header>
					<h1>This is {{animalModel.name}}</h1>
				</el-header>
				<el-main style="text-align: left;">
					<p>Description: {{animalModel.description}}</p>
					<p>In shelter from: {{getAgeWithBirthDate(animalModel.shelterJoinDate)}}</p>
					<br>
					<br>
					<br>
					<h3>Treatment history</h3>
					<div class="list-container" v-for="treatment in animalModel.treatmentHistories">
						<p>Treatment status: {{treatment.treatmentStatus}}</p>
						<p><strong>{{treatment.title}}</strong> was done by <strong>{{treatment.doctor}}</strong> </p>
						<p>Description: {{treatment.description}}</p>
						<p>Treatment time from <strong>{{getAgeWithBirthDate(treatment.startDate)}}</strong> to <strong>{{getAgeWithBirthDate(treatment.endDate)}}</strong></p>
						<p>Place: {{treatment.place}}</p>
						<p>Price: {{treatment.price}} złotóweczek</p>
					</div>

					<div>
						<h3>Contact info:</h3>
						It is currently at {{animalModel.animalLocation.locationType}}: {{animalModel.animalLocation.fullName}}
						<p>Address: {{animalModel.animalLocation.address}}</p>
						<p>Email: {{animalModel.animalLocation.email}}</p>
						<p>Pmail: {{animalModel.animalLocation.phone}}</p>
					</div>
				</el-main>
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
			getAgeWithBirthDate: function (value) {
				let date = new Date(value);
				return date.getDay() + "." + date.getMonth() + "." + date.getFullYear();
			},
			translateBooleanToHuman: function (value) {
				if (value) {
					return "yes"
				}
				return "no"
			}
		},
		created() {
			let id = this.$route.params.id;
			axios.get('http://52.91.229.171:80/api/animal/one/' + id)
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

</style>
