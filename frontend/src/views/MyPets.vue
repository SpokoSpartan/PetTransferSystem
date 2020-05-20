<template>
	<div>
		<h1>Your animals</h1>
		<div class="list-container" v-if="animals && animals.length">
			<div v-for="animal of animals">
				<el-container class="list-item">
					<el-aside width="200px">
						<img style="width:190px;" :src="animal.imageUrl">
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
								:to="{ path: '/edit/' + animal.id, params: {id: animal.id}}"
							><el-button type="primary" round>
								Update pet info
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
		created() {
			axios.get('http://52.91.229.171:80/api/animal/my/all?page=0&size=1000')
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
</style>
