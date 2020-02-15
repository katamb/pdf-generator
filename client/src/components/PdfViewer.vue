<template>
  <div>
    <div v-if="this.pdf !== null">
      <b-embed
          type="iframe"
          aspect="4by3"
          :src="this.pdf"
      >
      </b-embed>
    </div>
    <div v-if="this.pdf === null">
      <p>Loading or unable to display file!</p>
    </div>
  </div>
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import {getRequest} from '@/requests';
    import eventBus from "@/eventBus";

    @Component
    export default class PdfViewer extends Vue {
        private pdf: any = null;

        public created(): void {
            eventBus.$on('rerender-pdf', () => this.getPdf());
        }

        private mounted(): void {
            this.getPdf();
        }

        private getPdf(): void {
            getRequest(`/api/v1/file-generator/edit/pdf/${this.$route.params.template}/${this.$route.params.language}`)
                .then(response => response.blob())
                .then(blob => this.pdf = URL.createObjectURL(blob))
            ;
        }
    }
</script>

<style scoped>
</style>
