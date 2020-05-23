<template>
	<div>
		<h1>Your animals (total amount: {{animals.length}})</h1>
		<div class="list-container" v-if="animals && animals.length">
			<div v-for="animal of animals">
				<el-container class="list-item">
					<el-aside width="200px">
						<img class="img-limit" :src="animal.imageUrl">
					</el-aside>
					<el-container>
						<el-header>
							<p>{{animal.name}} is currently {{animal.status}}</p>
						</el-header>
						<el-main>
							<b>Description:</b> {{animal.description}}
							<p>Location: {{animal.location}}</p>
							<p>From: {{animal.locationType}}</p>
							<router-link
								:to="{ path: '/animal/' + animal.id, params: {id: animal.id}}">
								<el-button type="primary" round>
									Show full details
								</el-button>
							</router-link>
							<router-link style="padding-left: 12px;"
										 :to="{ path: '/edit/' + animal.id, params: {id: animal.id}}">
								<el-button type="info" round>
									Update pet info
								</el-button>
							</router-link>
							<router-link style="padding-left: 12px;"
										 :to="{ path: '/editTreatment/' + animal.id, params: {id: animal.id}}">
								<el-button type="info" round>
									Update treatment history
								</el-button>
							</router-link>
							<router-link style="padding-left: 12px;" :to="{ path: ''}" v-if="!animal.archived">
								<el-button type="primary" style="background: #ffc520; border-color: #ffc520;" @click="archiveAnimal(animal)" round>Archive</el-button>
							</router-link>
							<router-link style="padding-left: 12px;" :to="{ path: ''}" v-if="animal.archived">
								<el-button type="info" style="background: #009926; border-color: #009926;" @click="reverseArchiving(animal)" round>Reverse archiving</el-button>
							</router-link>
							<router-link style="padding-left: 10px;"
										 :to="{ path: '/remove/' + animal.id, params: {id: animal.id}}">
								<el-button type="danger" round>
									Remove animal
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
				errors: []
			}
		},
		methods: {
			archiveAnimal(animal) {
				const token = localStorage.getItem('access_token');
				if (token !== null) {
					axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
				}
				axios.post(this.$APIURL + '/api/animal/archive/' + animal.id)
					.then(() => animal.archived = true)
					.catch((e) => animal.archived = false);
			},
			reverseArchiving(animal) {
				const token = localStorage.getItem('access_token');
				if (token !== null) {
					axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
				}
				axios.post(this.$APIURL + '/api/animal/reverse-archiving/' + animal.id)
					.then(() => animal.archived = false)
					.catch((e) => animal.archived = true);
			}
		},
		created() {
			const token = localStorage.getItem('access_token');
			if (token !== null) {
				axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
			}
			axios.get(this.$APIURL + 'api/animal/my/all?page=0&size=1000')
				.then(response => {
					this.animals = response.data.content;
					console.log(response.data.content);
				}).catch(e => {
				this.errors.push(e)
			})
		}
	}


</script>

<style scoped lang="scss">
	.list-container {

	}

	.list-item {
		background-color: $color-light;
		height: 250px;
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
		background-color: $color-light;
		color: white;
		text-align: center;
	}

	.img-limit {
		max-width: 180px;
		max-height: 240px;
	}
</style>
