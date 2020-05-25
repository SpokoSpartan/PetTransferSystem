<template>
	<div style="margin: 0 auto; width: 70%;">
		<h1>Animals in our shelter (total amount: {{animals.length}})</h1>
		<div class="list-container" v-if="animals && animals.length">
			<div v-for="animal of animals">
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
								<el-button type="success" round>More details</el-button>
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
			const token = localStorage.getItem('access_token');
			if (token !== null) {
				axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
			}
			axios.get( this.$APIURL + 'animal/all?page=0&size=1000')
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
		height: fit-content;
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
