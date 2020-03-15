<template>
  <div>
    <div class="text-left">
      Click the button below to download the SQL file chosen earlier.
    </div>
    <div class="text-center pt-3">
      <b-button variant="success">
        <a class="download-link"
           :href="backendUrl + '/api/v1/download-sql/selected'">
          Download SQL
        </a>
      </b-button>
    </div>
  </div>
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import {BACKEND_URL} from '@/constants';

    @Component
    export default class DownloadSql extends Vue {
        backendUrl: string = BACKEND_URL;
    }
</script>

<style scoped>
  .download-link {
    color: white;
    text-decoration: none;
  }
</style>
