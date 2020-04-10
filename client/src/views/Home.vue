<template>
  <div>
    <Navigation />

    <b-container>
      <b-row class="justify-content-center">
        <b-col class="mt-3">
          <h2>Home</h2>
        </b-col>
      </b-row>
      <b-row class="justify-content-center">
        <b-col cols="8">
          <ChooseTemplate />
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import ChooseTemplate from '@/components/ChooseTemplate.vue';
import Navigation from '@/components/Navigation.vue';

@Component({
  components: {
    Navigation,
    ChooseTemplate
  }
})
export default class Home extends Vue {}
</script>
