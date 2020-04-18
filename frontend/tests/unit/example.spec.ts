import {shallowMount} from '@vue/test-utils'
// @ts-ignore
import AnimalsList from '@/components/AnimalsList.vue'

describe('AnimalsList.vue', () => {
  it('renders props.msg when passed', () => {
   shallowMount(AnimalsList);
  })
});
