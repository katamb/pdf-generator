<template>
  <div class="m-3 text-left">
    <b-button v-b-toggle="'collapse-new-language'" class="m-1">Show/Close instructions</b-button>

    <b-collapse id="collapse-new-language">
      <b-card title="Overall">
        To make changes, click the text in the PDF preview (on the right), which needs changing.
        This will display that text down below.
      </b-card>
      <b-card title="The value substitutions">
        The text between '${' and '}' (including '${' and '}') will not be shown in the end document.
        This text references the value that will be shown there instead and this value is provided by the service using
        this template. The alignment and size of this text can still be changed.
      </b-card>
      <b-card title="In-text formatting">
        <b-card-text>
          To add bold, italic or underline text, select the text which needs to be formatted and click the
          corresponding button. This adds tags, which are shown in the text field below, but not in the PDF
          document. Adding the tags by hand works as well. To remove the created formatting, delete the tags by hand.
        </b-card-text>
        <b-card-text>
          The text between '&lt;b&gt;' and '&lt;/b&gt;' will be displayed in bold in the document.
          The text between '&lt;i&gt;' and '&lt;/i&gt;' will be displayed in italic in the document.
          The text between '&lt;u&gt;' and '&lt;/u&gt;' will be displayed in underlined in the document.
        </b-card-text>
      </b-card>
    </b-collapse>
  </div>
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";

    @Component
    export default class Explanations extends Vue {
    }
</script>

<style scoped>
</style>
