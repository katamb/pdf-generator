<template>
  <div>
    <div class="text-left">
      Click the button below to download the SQL file chosen earlier.
    </div>
    <div class="text-center pt-3">
      <b-button @click="downloadFile(null)" variant="success">
        Download SQL
      </b-button>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import downloadFile from '@/scripts/downloading';
import Info from '@/assets/info.svg';

@Component({
  components: {
    Info
  },
  methods: {
    downloadFile
  }
})
export default class DownloadSql extends Vue {}
</script>

<style scoped></style>
