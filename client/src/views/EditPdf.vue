<template>
  <div>
    <Navigation/>
    <div class="about">
      <router-link class="home-button" to="/">
        <i class="material-icons md-48 icon-center">home</i> Home
      </router-link>

      <h1>Edit PDF templates</h1>

      <b-container fluid>
        <b-row>

          <b-col>
            <PdfTextFieldEditor/>
          </b-col>

          <b-col>
            <PdfViewer/>
          </b-col>

        </b-row>
      </b-container>

    </div>
  </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import PdfViewer from "@/components/PdfViewer.vue";
    import PdfTextFieldEditor from "@/components/PdfTextFieldEditor.vue";
    import Navigation from "@/components/Navigation.vue";

    @Component({
        components: {
            PdfViewer,
            PdfTextFieldEditor,
            Navigation
        }
    })
    export default class EditPdf extends Vue {
    }
</script>

<style scoped>
  .home-button {
    font-size: 26px;
  }

  .home-button:hover {
    text-decoration: none;
  }

  .icon-center {
    vertical-align: middle;
  }
</style>
