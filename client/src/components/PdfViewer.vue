<template>
  <div>
    <div v-if="pdf !== null">
      <b-embed type="iframe" aspect="4by3" :src="pdf"> </b-embed>
    </div>
    <div v-if="pdf === null">
      <p>Loading or unable to display file!</p>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { getRequest } from "@/requests";
import eventBus from "@/eventBus";

@Component
export default class PdfViewer extends Vue {
  pdf: any = null;

  created(): void {
    eventBus.$on("rerender-pdf", () => this.getPdf());
  }

  mounted(): void {
    this.getPdf();
  }

  private getPdf(): void {
    getRequest(
      `/api/v1/file-generator/edit/pdf/${this.$route.params.template}/${this.$route.params.language}`
    )
      .then(response => response.blob())
      .then(blob => (this.pdf = URL.createObjectURL(blob)));
  }
}
</script>

<style scoped></style>
