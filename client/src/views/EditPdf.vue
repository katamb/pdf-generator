<template>
  <div>
    <ErrorModal />

    <Navigation />

    <h2 class="pt-2">
      Currently editing:
    </h2>
    <h4 class="pb-3">
      <b-badge variant="info">{{ this.$route.params.template }}</b-badge>
      template in
      <b-badge variant="info">{{ language }}</b-badge>
    </h4>

    <b-modal v-model="showModal" hide-footer hide-header>
      <div class="d-block text-center">
        <h4 class="text-danger">{{ this.errorMessage }}</h4>
      </div>
      <div class="text-center">
        <b-button class="m-3" variant="success" @click="goHome"
          >Go to home page
        </b-button>
      </div>
    </b-modal>

    <b-container fluid>
      <b-row>
        <b-col>
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
import { Component, Vue, Watch } from "vue-property-decorator";
import PdfViewer from "@/components/PdfViewer.vue";
import Navigation from "@/components/Navigation.vue";
import { getRequest } from "@/requests";
import router from "@/router";
import EditingToolbar from "@/components/EditingToolbar.vue";
import ErrorModal from "@/components/ErrorModal.vue";

@Component({
  components: {
    PdfViewer,
    EditingToolbar,
    Navigation,
    ErrorModal
  }
})
export default class EditPdf extends Vue {
  language = "";
  showModal = false;
  errorMessage = null;

  @Watch("$route")
  onPropertyChanged(value: string, oldValue: string) {
    this.getLanguage();
  }

  mounted(): void {
    this.validateFileSelected();
    this.language = this.$route.params.language;
    this.getLanguage();
  }

  goHome(): void {
    router.push({ path: "/home" });
  }

  private getLanguage(): void {
    getRequest(`/api/v1/languages-by-code/${this.$route.params.language}`)
      .then((body: any) => body.json())
      .then(json => (this.language = json.text));
  }

  private validateFileSelected(): void {
    getRequest(`/api/v1/is-sql-file-selected`, false)
      .then((body: any) => body.json())
      .then(json => {
        if (json.status !== undefined && json.status !== 200) {
          this.errorMessage = json.message;
          this.showModal = true;
        }
      });
  }
}
</script>

<style scoped>
.home-button {
  font-size: 26px;
}

.home-button:hover {
  text-decoration: none;
}

.icon-center {
  vertical-align: middle;
}
</style>
