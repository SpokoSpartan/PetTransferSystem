<template>
	<div>
		<el-input placeholder="Email" v-model="input"></el-input>
		<el-input placeholder="Password" v-model="input"></el-input>
		<el-input placeholder="Confirm password" v-model="input"></el-input>
		<el-input placeholder="Phone number" v-model="input"></el-input>
		<el-row>
			<el-button type="success" round>Register</el-button>
		</el-row>
	</div>
</template>

<script>export default {
	name: 'Register'
}
</script>

<style scoped>

</style>
