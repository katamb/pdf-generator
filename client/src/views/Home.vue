<template>
  <b-container>
    <b-row class="justify-content-center">
      <b-col class="mt-3">
        <h1>Choose template and language</h1>
      </b-col>
    </b-row>
    <b-row class="justify-content-center">
      <b-col cols="6">
        <ChooseTemplate/>
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import ChooseTemplate from "@/components/ChooseTemplate.vue";

    @Component({
        components: {
            ChooseTemplate
        }
    })
    export default class Home extends Vue {

    };
</script>
