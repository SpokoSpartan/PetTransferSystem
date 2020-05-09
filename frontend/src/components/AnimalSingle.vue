<template>
	<div>
		<h1>Name: {{animalModel.name}}</h1>
		<p>Species: {{animalModel.species}}</p>
		<p>Race:{{animalModel.race}}</p>
		<p>Description:{{description}}</p>
		<p>:{{$route}}</p>
		<p>:{{}}</p>
		<p>:{{}}</p>
		<p>:{{}}</p>
		<p>:{{}}</p>
		<p>:{{}}</p>
		<p>:{{}}</p>
		<p>:{{}}</p>
	</div>
</template>


<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import axios from 'axios';


@Component
export default class AnimalSingle extends Vue {


}



</script>

<style scoped lang="scss">

</style>
