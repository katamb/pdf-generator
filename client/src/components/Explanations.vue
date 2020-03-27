<template>
  <div class="m-3 text-left">

    <h5>Overall</h5>
    <p>
      To make changes, click the text in the PDF preview (on the right), which needs changing.
      This will display that text down below.
    </p>
    <h5>The value substitutions</h5>
    <p>
      The text between <code>${</code> and <code>}</code> (including <code>${</code> and <code>}</code>) will not be shown in the end document.
      This text references the value that will be shown there instead and this value is provided by the service using
      this template. The alignment and size of this text can still be changed.
    </p>
    <h5>In-text formatting</h5>
    <p>
      To add bold, italic or underline text, select the text which needs to be formatted and click the
      corresponding button. This adds tags, which are shown in the text field below, but not in the PDF
      document. Adding the tags by hand works as well. To remove the created formatting, delete the tags by hand.
    </p>
    <p>
      The text between <code>&lt;b&gt;</code> and <code>&lt;/b&gt;</code> will be displayed in bold in the document.
      The text between <code>&lt;i&gt;</code> and <code>&lt;/i&gt;</code> will be displayed in italic in the document.
      The text between <code>&lt;u&gt;</code> and <code>&lt;/u&gt;</code> will be displayed underlined in the document.
      The text between <code>&lt;s&gt;</code> and <code>&lt;/s&gt;</code> will be displayed striked through in the document.
    </p>

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
