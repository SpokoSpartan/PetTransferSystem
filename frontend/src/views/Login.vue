<template>
	<div>
		<el-input placeholder="Email" v-model="input"></el-input>
		<el-input placeholder="Password" v-model="input"></el-input>
		<el-row>
			<el-button type="primary" round>Login</el-button>
		</el-row>
	</div>
</template>

<script>export default {
	name: 'Login'
}
</script>

<style scoped>

</style>
