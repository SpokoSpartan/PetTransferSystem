<template>
	<div>
		<h1>Your animals</h1>
		<div class="list-container" v-if="posts && posts.length">
			<div v-for="post of posts">
				<el-container class="list-item">
					<el-aside width="200px">
						<img style="width:190px;" :src="post.imageUrl">
					</el-aside>
					<el-container>
						<el-header>
							<p>{{post.name}} is currently {{post.status}}</p>
						</el-header>
						<el-main>
							<b>Description:</b> {{post.description}}
							<p>Location: {{post.location}}</p>
							<p>From: {{post.locationType}}</p>
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
				posts: [],
				errors: []
			}
		},
		created() {
			axios.get('http://34.207.67.160:80/api/animal/my/all?page=0&size=1000')
				.then(response => {
					this.posts = response.data.content;
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
