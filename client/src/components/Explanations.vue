<template>
  <div class="m-3 text-left">
    <p>
      To make changes, click the text in the PDF preview, which needs changing. This will display that text down below.
    </p>
    <p>
      The text between '${' and '}' (including '${' and '}') will not be shown in the document.
      This text references the value that will be shown there instead and this value is provided by the service using
      this template. The alignment and size of this text can still be changed.
    </p>
    <p>
      The text between '**' and '**' will be displayed in bold in the document.
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
