<template>
  <div>
    <Navigation/>
    <h2 class="pt-2">
      Currently editing:
    </h2>
    <h4 class="pb-3">
      <b-badge variant="info">{{this.$route.params.template}}</b-badge>
      template in
      <b-badge variant="info">{{language}}</b-badge>
    </h4>

    <b-modal v-model="showModal" hide-footer hide-header>
      <div class="d-block text-center">
        <h4 class="text-danger">{{this.errorMessage}}</h4>
      </div>
      <div class="text-center">
        <b-button class="m-3"
                  variant="success"
                  @click="goHome">Go to home page
        </b-button>
      </div>
    </b-modal>

    <b-container fluid>
      <b-row>

        <b-col>
          <PdfTextFieldEditor/>
        </b-col>

        <b-col>
          <PdfViewer/>
        </b-col>

      </b-row>
    </b-container>

  </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import PdfViewer from "@/components/PdfViewer.vue";
    import PdfTextFieldEditor from "@/components/PdfTextFieldEditor.vue";
    import Navigation from "@/components/Navigation.vue";
    import {getRequest} from "@/requests";
    import router from "@/router";

    @Component({
        components: {
            PdfViewer,
            PdfTextFieldEditor,
            Navigation
        }
    })
    export default class EditPdf extends Vue {
        language = "";
        showModal = false;
        errorMessage = null;

        mounted(): void {
            getRequest(`/api/v1/is-sql-file-selected`)
                .then((body: any) => body.json())
                .then((json) => {
                    if (json.status !== undefined && json.status !== 200) {
                        this.errorMessage = json.message;
                        this.showModal = true;
                    }
                });
            this.language = this.$route.params.language;
            this.getLanguage();
        }

        goHome(): void {
            router.push({path: '/home'})
        }

        getLanguage(): void {
            getRequest(`/api/v1/languages-by-code/${this.$route.params.language}`)
                .then((body: any) => body.json())
                .then((json) => this.language = json.text)
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
