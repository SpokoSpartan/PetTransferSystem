<template>
	<div>
		<div>Login</div>
		<el-input  class="input-field" placeholder="Email" v-model="input"></el-input>
		<div>Password</div>
		<el-input  class="input-field" placeholder="Password" v-model="input"></el-input>
		<el-row>
			<el-button type="primary" round>Login</el-button>
		</el-row>
	</div>
</template>

<script>export default {
	name: 'Login'
}
</script>

<style scoped lang="scss">

	.input-field {
		width: 50%;
	}

	.input-label {
	}
</style>
