<template>
  <div>
    <b-card no-body>
      <b-tabs v-model="tabIndex" card>
        <b-tab title="Instructions">
          <Explanations />
        </b-tab>
        <b-tab title="Add language">
          <AddNewLanguage />
        </b-tab>
        <b-tab title="Edit">
          <PdfTextFieldEditor />
        </b-tab>
        <b-tab title="Download SQL">
          <DownloadSql />
        </b-tab>
      </b-tabs>
    </b-card>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator';
import Explanations from '@/components/Explanations.vue';
import AddNewLanguage from '@/components/editing/AddNewLanguage.vue';
import DownloadSql from '@/components/DownloadSql.vue';
import PdfTextFieldEditor from '@/components/editing/PdfTextFieldEditor.vue';

@Component({
  components: {
    Explanations,
    AddNewLanguage,
    DownloadSql,
    PdfTextFieldEditor
  }
})
export default class EditingToolbar extends Vue {
  tabIndex = 0;

  @Watch('$route')
  onPropertyChanged() {
    if (this.$route.params.id === '-') {
      this.tabIndex = 0;
    } else {
      this.tabIndex = 2;
    }
  }
}
</script>

<style scoped></style>
