<template>
  <div class="about">
    <router-link to="/">Home</router-link>

    <h1>Page for editing PDF's</h1>

    <b-container fluid>
      <b-row>

        <b-col>
          <PdfTextFieldEditor />
        </b-col>

        <b-col>
          <PdfViewer />
        </b-col>

      </b-row>
    </b-container>

  </div>
</template>


<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import PdfViewer from "@/components/PdfViewer.vue";
    import PdfTextFieldEditor from "@/components/PdfTextFieldEditor.vue";

    @Component({
        components: {
            PdfViewer,
            PdfTextFieldEditor
        }
    })
    export default class EditPdf extends Vue {}
</script>

<style scoped></style>
