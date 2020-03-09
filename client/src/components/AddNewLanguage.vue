<template>
  <div class="m-3 text-left">
    <b-button v-b-toggle="'collapse-explanations'" class="m-1">Add new language</b-button>

    <b-collapse id="collapse-explanations">
      <b-card title="Add new language">
        <b-card-text>
          Write language code according to ISO 639-1 standard below and click "Create" button to create the same
          template in another language.
        </b-card-text>
        <b-form-group class="pt-3" label="Choose language" label-for="language-input">
          <b-form-select id="language-input"
                         v-model="selectedLanguage"
                         :options="languageOptions"></b-form-select>
        </b-form-group>
        <b-button @click="createNewLanguage()">Create</b-button>
      </b-card>
    </b-collapse>
  </div>
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import {getRequest, postRequest} from "@/requests";
    import router from "@/router";

    @Component
    export default class AddNewLanguage extends Vue {
        private selectedLanguage: any = null;
        private languageOptions: any = [];
        private unavailableLanguageValues: any = [];

        mounted(): void {
            getRequest('/api/v1/all-languages')
                .then(response => response.json())
                .then(data => this.languageOptions = data)
                .then(() => this.getUnavailableLanguageOptions())
            ;
        }

        private getUnavailableLanguageOptions(): void {
            getRequest(`/api/v1/template-languages/${this.$route.params.template}`)
                .then(response => response.json())
                .then(data => this.unavailableLanguageValues = data.map((el: any) => el.value))
                .then(() => {
                    this.languageOptions = this.languageOptions
                        .filter((element: any) => !this.unavailableLanguageValues.includes(element.value))
                })
            ;
        }

        createNewLanguage(): void {
            postRequest(`/api/v1/add-language/${this.$route.params.template}/${this.$route.params.language}/${this.selectedLanguage}`, null)
                .then(() => router.push({path: `/edit-pdf/${this.$route.params.template}/${this.selectedLanguage}/-`}));
        }

    }
</script>

<style scoped>
</style>
