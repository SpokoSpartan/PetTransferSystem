<template>
	<div>
		<div class="input-label">Email address</div>
		<el-input class="input-field" placeholder="Email" v-model="input"></el-input>
		<div class="input-label">Password</div>
		<el-input class="input-field" placeholder="Password" v-model="input"></el-input>
		<div class="input-label">Confirm password</div>
		<el-input class="input-field" placeholder="Confirm password" v-model="input"></el-input>
		<div class="input-label">Phone number</div>
		<el-input class="input-field" placeholder="Phone number" v-model="input"></el-input>
		<el-row>
			<el-button type="success" round>Register</el-button>
		</el-row>
	</div>
</template>

<script>export default {
	name: 'Register'
}
</script>

<style scoped lang="scss">
	.input-field {
		width: 50%;
	}

	.input-label {
	}
</style>
