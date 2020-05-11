<template>
  <div>
    <h2 class="pt-2">
      Currently editing:
    </h2>
    <h4 class="pb-3">
      <b-badge variant="info">{{ $route.params.template }}</b-badge>
      template in
      <b-badge variant="info">{{ language }}</b-badge>
    </h4>

    <b-modal v-model="showModal" hide-footer hide-header>
      <div class="d-block text-center">
        <h4 class="text-danger">{{ errorMessage }}</h4>
      </div>
      <div class="text-center">
        <b-button class="m-3" variant="success" @click="goHome">
          Go to home page
        </b-button>
      </div>
    </b-modal>

    <b-container fluid>
      <b-row>
        <b-col v-if="isRoleEditor">
          <EditingToolbar />
        </b-col>
        <b-col>
          <PdfViewer />
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator';
import PdfViewer from '@/components/PdfViewer.vue';
import Navigation from '@/components/Navigation.vue';
import EditingToolbar from '@/components/editing/EditingToolbar.vue';
import { getRequest } from '@/scripts/requests';
import { isCurrentUserEditor } from '@/scripts/util';

@Component({
  components: {
    PdfViewer,
    EditingToolbar,
    Navigation
  }
})
export default class EditPdf extends Vue {
  language = '';

  showModal = false;

  errorMessage = null;

  isRoleEditor = false;

  @Watch('$route')
  onPropertyChanged() {
    this.getLanguage();
  }

  mounted(): void {
    this.isRoleEditor = isCurrentUserEditor();
    if (this.isRoleEditor) {
      this.validateFileSelected();
    }
    this.language = this.$route.params.language;
    this.getLanguage();
  }

  goHome(): void {
    this.$router.push({ path: '/home' });
  }

  private getLanguage(): void {
    getRequest(`/api/v1/languages-by-code/${this.$route.params.language}`)
      .then((body: any) => body.json())
      .then((json) => {
        this.language = json.text;
      });
  }

  private validateFileSelected(): void {
    getRequest(`/api/v1/is-sql-file-selected`, false)
      .then((body: any) => body.json())
      .then((json) => {
        if (json.status !== undefined && json.status !== 200) {
          this.errorMessage = json.message;
          this.showModal = true;
        }
      });
  }
}
</script>

<style scoped></style>
