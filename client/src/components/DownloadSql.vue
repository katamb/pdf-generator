<template>
  <div>
    <div class="text-left">
      Click the button below to download the SQL file chosen earlier.
    </div>
    <div class="text-center pt-3">
      <b-button @click="download" variant="success">
        Download SQL
      </b-button>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { getRequest } from "@/requests";
import { Base64 } from "js-base64";

@Component
export default class DownloadSql extends Vue {
  download(): void {
    getRequest("/api/v1/download-sql/selected")
      .then(response => response.json())
      .then(file => {
        const element = document.createElement("a");
        element.setAttribute(
          "href",
          "data:text/plain;charset=utf-8," + Base64.decode(file.file)
        );
        element.setAttribute("download", file.fileName);
        element.style.display = "none";
        document.body.appendChild(element);
        element.click();
        document.body.removeChild(element);
      });
  }
}
</script>

<style scoped></style>
