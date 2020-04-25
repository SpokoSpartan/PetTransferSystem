<template>
	<div>
		<div v-if="posts && posts.length">
			<div v-for="post of posts">
				<div style="border-radius: 30px">
					<el-container style="height: 250px; border-radius: 30px">
						<el-aside width="200px">
							<img style="width:150px; height: 150px" :src="post.imageUrl">
						</el-aside>
						<el-container>
							<el-header><p>Id: {{post.id}}, {{post.name}} is currently {{post.status}}</p></el-header>
							<el-main class="text">
								<b>Description:</b> {{post.description}}
								<p class="text">Location: {{post.location}}</p>
								<p class="text">From: {{post.locationType}}</p>
							</el-main>
						</el-container>
					</el-container>
				</div>
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

<style scoped lang="scss">


	.el-main {
		background-color: $color-light;
		text-align: left;
	}

	.el-header, .el-footer {
		background-color: $color-primary;
		color: white;
		text-align: left;
	}

	.el-aside {
		background-color: #def1ff;
		color: white;
		text-align: center;
	}
</style>
