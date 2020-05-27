<template>
	<div style="margin: 0 auto; width: 70%;">
		<h1>Animals in our shelter (total: {{animals.length}})</h1>
		<div class="filters-container">
			Filter animals by species:
			<el-select v-model="selectedSpecies" multiple placeholder="select species">
				<el-option
					v-for="item in optionSpecies"
					:key="item"
					:label="item"
					:value="item">
				</el-option>
			</el-select>
			or by race:
			<el-select v-model="selectedRaces" multiple placeholder="select race">
				<el-option
					v-for="item in optionsRaces"
					:key="item"
					:label="item"
					:value="item">
				</el-option>
			</el-select>

			<el-button style="margin-left: 12px;" type="primary" round @click="applyFilters">Apply</el-button>
			<el-button style="margin-left: 12px;" type="info" round @click="clearFilters">Clear</el-button>
		</div>

		<div class="list-container" v-if="filteredAnimals && filteredAnimals.length">
			<div v-for="animal of filteredAnimals">
				<el-container class="list-item">
					<el-aside width="200px">
						<img class="img-limit" :src="animal.imageUrl">
					</el-aside>
					<el-container>
						<el-header>
							<strong>{{animal.name}}</strong> is currently {{animal.status}}
						</el-header>
						<el-main>
							<b>Description:</b> {{animal.description}}
							<p>Location: {{animal.location}}</p>
							<p>From: {{animal.locationType}}</p>
							<router-link
								:to="{ path: '/animal/' + animal.id, params: {id: animal.id}}">
								<el-button type="success" round  style="background: #0074D9; border-color: #0074D9;">
									More details
								</el-button>
							</router-link>
						</el-main>
					</el-container>
				</el-container>

				<br>
			</div>
		</div>
	</div>
</template>

<script>
	import axios from "axios";

	export default {
		name: "AnimalsList",
		data() {
			return {
				animals: [],
				filteredAnimals: [],
				races: [],
				species: [],
				selectedRaces: [],
				selectedSpecies: [],
				errors: [],
				optionsRaces: [],
				optionSpecies: []
			}
		},
		created() {
			const token = localStorage.getItem('access_token');
			if (token !== null) {
				axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
			}
			axios.get(this.$APIURL + 'animal/all?page=0&size=1000')
				.then(response => {
					this.animals = response.data.content;
					this.filteredAnimals = Object.assign([], this.animals);
					this.getFilterOptions();
					console.log(this.animals);
				}).catch(e => {
				this.errors.push(e)
			})
		},
		methods: {
			applyFilters() {
				if (this.selectedRaces.length === 0 && this.selectedSpecies.length === 0) {
					this.filteredAnimals = Object.assign([], this.animals);
				} else {
					this.filteredAnimals = Object.assign([], this.animals.filter((animal) => {
						const race = animal.race;
						const species = animal.species;

						for (let i = 0; i < this.selectedRaces.length; i++) {
							if (race === this.selectedRaces[i]) {
								return true;
							}
						}
						for (let i = 0; i < this.selectedSpecies.length; i++) {
							if (species === this.selectedSpecies[i]) {
								return true;
							}
						}

						return false;
					}))
				}

			},
			clearFilters() {
				this.filteredAnimals = Object.assign([], this.animals);
				this.selectedRaces = [];
				this.selectedSpecies = [];
			},
			getFilterOptions() {
				this.animals.forEach((a) => {
					if (!!a.race && !this.optionsRaces.includes(a.race)) {
						this.optionsRaces.push(a.race);
					}
					if (!!a.species && !this.optionSpecies.includes(a.species)) {
						this.optionSpecies.push(a.species);
					}
				})
				this.optionsRaces = this.optionsRaces.sort();
				this.optionSpecies.sort();
			}
		}
	}


</script>

<style scoped lang="scss">

	.filters-container {
		text-align: left;
		margin-bottom: 12px;
	}

	.list-container {

	}

	.list-item {
		background-color: $color-light;
		height: fit-content;
		max-height: 360px;
	}

	.el-main {
		text-align: left;
	}

	.el-aside {
		background-color: $color-light;
		color: white;
		text-align: center;
	}

	.img-limit {
		border-radius: 10%;
		margin-top: 12px;
		max-width: 180px;
		max-height: 230px;
		box-shadow: 0 10px 10px 0 rgba(0, 0, 0, 0.2);
	}

	.el-header {
		background-color: $color-secondary;
		color: white;
		width: fit-content;
		/*max-width: 600px;*/
		margin-top: 12px;
		line-height: 60px;
		text-align: left;
		border-radius: 30px;
	}
</style>
