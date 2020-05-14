<template>
  <div>
    <AddNewTextBlock />

    <hr />

    <DownloadSql />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Info from '@/assets/info.svg';
import Navigation from '@/components/Navigation.vue';
import DownloadSql from '@/components/DownloadSql.vue';
import AddNewTextBlock from '@/components/AddNewTextBlock.vue';

@Component({
  components: {
    Info,
    Navigation,
    DownloadSql,
    AddNewTextBlock
  }
})
export default class NewTemplate extends Vue {}
</script>

<style scoped></style>
