<template>
	<div style="margin: 0 auto; width: 70%;">
		<h1>Your animals (total amount: {{animals.length}})</h1>

		<div class="filters-container">
			Filter animals by status:
			<el-select v-model="selectedFilters" multiple placeholder="select status">
				<el-option
					v-for="item in filterOptions"
					:key="item.value"
					:label="item.label"
					:value="item.value">
				</el-option>
			</el-select>
			<el-button style="margin-left: 12px;" type="primary" round @click="applyFilters">Apply</el-button>
			<el-button style="margin-left: 12px;" type="info" round @click="clearFilters">Clear</el-button>
		</div>

		<div v-if="animals && animals.length">
			<div v-for="animal of filteredAnimals">
				<el-container class="list-item">
					<el-aside width="200px">
						<img class="img-limit" :src="animal.imageUrl">
					</el-aside>
					<el-container>
						<el-header>
							{{animal.name}} is currently {{animal.status}}
							<el-button v-if="animal.status === 'new in shelter'" @click="putUpForAdoption(animal)" style="margin-left: 12px;" type="success" round>Put up for adoption</el-button>
						</el-header>
						<el-main>
							<b>Description:</b> {{animal.description}}
							<p>Location: {{animal.location}}</p>
							<router-link
								:to="{ path: '/animal/' + animal.id, params: {id: animal.id}}">
								<el-button type="success" round>
									More details
								</el-button>
							</router-link>
							<router-link v-if="!(animal.status === 'adopted' || animal.status === 'dead')" style="padding-left: 12px;"
										 :to="{ path: '/edit/' + animal.id, params: {id: animal.id}}">
								<el-button type="info" round>
									Update info
								</el-button>
							</router-link>
							<router-link v-if="!(animal.status === 'adopted' || animal.status === 'dead')" style="padding-left: 12px;"
										 :to="{ path: '/editTreatment/' + animal.id, params: {id: animal.id}}">
								<el-button type="info" round>
									Update treatment
								</el-button>
							</router-link>
							<router-link style="padding-left: 12px;" :to="{ path: ''}" v-if="!animal.archived && animal.status === 'ready for adoption'">
								<el-button type="primary" style="background: #ffc520; border-color: #ffc520;"
										   @click="archiveAnimal(animal)" round>Hide
								</el-button>
							</router-link>
							<router-link style="padding-left: 12px;" :to="{ path: ''}" v-if="animal.archived && animal.status === 'ready for adoption'">
								<el-button type="info" style="background: #009926; border-color: #009926;"
										   @click="reverseArchiving(animal)" round>Unhide
								</el-button>
							</router-link>
							<router-link v-if="!(animal.status === 'adopted' || animal.status === 'dead')" style="padding-left: 10px;"
										 :to="{ path: '/transfer/' + animal.id,  params: {id: animal.id}}">
								<el-button style="background: #0074D9; border-color: #0074D9;" type="danger" round>
									Transfer
								</el-button>
							</router-link>
							<router-link v-if="animal.status === 'ready for adoption'" style="padding-left: 10px;"
										 :to="{ path: '/adopt/' + animal.id,  params: {id: animal.id}}">
								<el-button type="success" round>
									Hand over for adoption
								</el-button>
							</router-link>
							<router-link v-if="!(animal.status === 'adopted')" style="padding-left: 10px;" :to="{ path: ''}">
								<el-button type="danger" @click="removeAnimal(animal)" round>
									Remove
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
				errors: [],
				selectedFilters: [],
				filterOptions: [{
					value: 'new in shelter',
					label: 'New in shelter'
				}, {
					value: 'ready for adoption',
					label: 'Ready for adoption'
				}, {
					value: 'adopted',
					label: 'Adopted'
				}, {
					value: 'dead',
					label: 'Dead'
				}]
			}
		},
		methods: {
			archiveAnimal(animal) {
				const token = localStorage.getItem('access_token');
				if (token !== null) {
					axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
				}
				axios.post(this.$APIURL + 'animal/archive/' + animal.id)
					.then(() => {
						animal.archived = true;
						this.$message({
							type: 'success',
							message: 'Archive completed'
						});
					})
					.catch((e) => {
						animal.archived = false;
						this.$message({
							type: 'error',
							message: 'An error occurred. Please refresh this page and try again.'
						});
					});
			},
			reverseArchiving(animal) {
				const token = localStorage.getItem('access_token');
				if (token !== null) {
					axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
				}
				axios.post(this.$APIURL + 'animal/reverse-archiving/' + animal.id)
					.then(() => {
						animal.archived = false;
						this.$message({
							type: 'success',
							message: 'Remove archiving completed'
						});
					})
					.catch((e) => {
						animal.archived = true;
						this.$message({
							type: 'error',
							message: 'An error occurred. Please refresh this page and try again.'
						});
					});
			},
			removeAnimal(animal) {
				this.$confirm('This will permanently delete the animal in the system. Continue?', 'Warning', {
					confirmButtonText: 'OK',
					cancelButtonText: 'Cancel',
					type: 'warning'
				}).then(() => {
					const token = localStorage.getItem('access_token');
					if (token !== null) {
						axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
					}
					axios.post(this.$APIURL + 'animal/remove/' + animal.id)
						.then(() => {
							this.$message({
								type: 'success',
								message: 'Delete completed'
							});
							this.animals = this.animals.filter(function (obj) {
								return obj.id !== animal.id;
							});
						})
						.catch((e) =>
							this.$message({
								type: 'error',
								message: 'An error occurred. Please refresh this page and try again.'
							}));
				}).catch(() => {
					this.$message({
						type: 'info',
						message: 'Delete canceled'
					});
				});
			},
			applyFilters() {
				this.filteredAnimals = Object.assign([], this.animals.filter((animal) => {
					const status = animal.status;
					for (let i = 0; i < this.selectedFilters.length; i++) {
						let value = this.selectedFilters[i];
						console.log(value === status)
						if (status === value) {
							return true;
						}
					}
					return false;
				}))
			},
			clearFilters() {
				this.filteredAnimals = Object.assign([], this.animals);
				this.selectedFilters = [];
      },
			putUpForAdoption(animal) {
       const token = localStorage.getItem('access_token');
       if (token !== null) {
         axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
        }
        axios.post(this.$APIURL + 'status/animal/' + animal.id + '/add?status=READY_FOR_ADOPTION')
          .then(() => {
          	this.$message({
            type: 'success',
            message: 'Status changed'
          });
           	animal.status = 'ready for adoption';
          })
          .catch((e) =>
            this.$message({
            type: 'error',
						message: 'An error occurred. Please refresh this page and try again.'
          }));
      }
    },
		created() {
			const token = localStorage.getItem('access_token');
			if (token !== null) {
				axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
			}
			axios.get(this.$APIURL + 'animal/my/all?page=0&size=1000')
				.then(response => {
					this.animals = response.data.content;
					this.filteredAnimals = Object.assign([], this.animals);
					console.log(response.data.content);
				}).catch(e => {
				this.errors.push(e)
			})
		}
	}


</script>

<style scoped lang="scss">
	.filters-container {
		text-align: left;
		margin-bottom: 12px;
	}

	.list-item {
		background-color: $color-light;
		height: fit-content;
	}


	.el-main {
		text-align: left;
	}

	.el-header, .el-footer {
		background-color: $color-secondary;
		color: white;
		text-align: left;
		border-radius: 30px;
	}

	.el-aside {
		margin-top: 12px;
		background-color: $color-light;
		color: white;
		text-align: center;
	}

	.img-limit {
		border-radius: 10%;
		max-width: 180px;
		max-height: 220px;
	}

	.el-header {
		width: fit-content;
		/*max-width: 600px;*/
		margin-top: 12px;
		line-height: 60px;
	}
</style>
