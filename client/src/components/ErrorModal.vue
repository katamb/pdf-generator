<template>
  <div>
    <b-modal v-model="showModal" title="Error" hide-footer>
      <div class="d-block text-center">
        <h4 class="text-danger">{{ this.errorMessage }}</h4>
      </div>
      <div class="text-center">
        <b-button class="m-3" variant="warning" @click="close">Close </b-button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import eventBus from "@/eventBus";

@Component
export default class ErrorModal extends Vue {
  showModal = false;
  errorMessage = "";

  created(): void {
    eventBus.$on("show-error", (message: string) => {
      this.errorMessage = message;
      this.showModal = true;
    });
  }

  close(): void {
    this.showModal = false;
    this.errorMessage = "";
  }
}
</script>

<style scoped></style>
