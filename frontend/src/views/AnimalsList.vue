<template>
	<div>
		<ul v-if="posts && posts.length">
			<li v-for="post of posts">
				<div style="border-radius: 30px">
					<el-container style="height: 250px; border-radius: 30px">
						<el-aside width="200px">
							<img style="width:150px; height: 150px" src="https://pbs.twimg.com/media/DOINwa5VQAUtkfh.jpg">
						</el-aside>
						<el-container>
							<el-header><p>Id: {{post.id}}, {{post.name}} is currently {{post.status}}</p></el-header>
							<el-main class="text">
								<b>Description:</b> Morus jest ok. 1,5-rocznym pieskiem średnich rozmiarów. Trafił do schroniska jako oddany przez opiekuna, który sobie z nim nie radził. Piesek jest bardzo żywiołowy i aktywny. Równocześnie potrafi zachować się nieprzewidywalnie wobec obcych ludzi i
								psów. Wymaga wiele pracy z nowym opiekunem. Wierzymy, że przy mądrym opiekunie Morus stanie się przyjaznym i wesołym psiakiem.
								<p class="text">Location: {{post.location}}</p>
								<p class="text">From: {{post.locationType}}</p>
							</el-main>
						</el-container>
					</el-container>
				</div>
			</li>
		</ul>
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

<style scoped>


	.el-main {
		background-color: #def1ff;
		text-align: left;
	}

	.el-header, .el-footer {
		background-color: #517cb6;
		color: white;
		text-align: left;
	}

	.el-aside {
		background-color: #def1ff;
		color: #333;
		text-align: center;
	}
</style>
