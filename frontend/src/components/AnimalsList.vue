<template>
  <div class="hello">
dsfasdf
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import axios from 'axios';

@Component
export default class Login extends Vue {
  @Prop() private msg!: string;

}
</script>

<style scoped lang="scss">

</style>
