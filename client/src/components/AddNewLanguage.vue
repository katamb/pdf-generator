<template>
  <div class="m-3 text-left">
    <p>
      Choose language and click "Create" button to create the same template in another language.
      Once "Create" button is clicked, you'll be redirected to the new document.
    </p>
    <b-form-group class="pt-3" label="Choose language" label-for="language-input">
      <b-form-select id="language-input"
                     v-model="selectedLanguage"
                     :options="languageOptions"></b-form-select>
    </b-form-group>
    <b-button @click="createNewLanguage()"
              :disabled="!isLanguageSelected()"
              variant="success">Create
    </b-button>
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
            this.getLanguages();
        }

        isLanguageSelected(): boolean {
            return this.selectedLanguage !== undefined && this.selectedLanguage !== null;
        }

        private getLanguages(): void {
            getRequest('/api/v1/all-languages')
                .then(response => response.json())
                .then(data => this.languageOptions = data)
                .then(() => this.getAndRemoveUnavailableLanguageOptions());
        }

        private getAndRemoveUnavailableLanguageOptions(): void {
            getRequest(`/api/v1/template-languages/${this.$route.params.template}`)
                .then(response => response.json())
                .then(data => this.unavailableLanguageValues = data.map((el: any) => el.value))
                .then(() => {
                    this.languageOptions = this.languageOptions
                        .filter((element: any) => !this.unavailableLanguageValues.includes(element.value))
                });
        }

        createNewLanguage(): void {
            postRequest(`/api/v1/add-language/${this.$route.params.template}/${this.$route.params.language}/${this.selectedLanguage}`, null)
                .then(() => router.push({path: `/edit-pdf/${this.$route.params.template}/${this.selectedLanguage}/-`}))
                .then(() => {
                    this.getLanguages();
                    this.selectedLanguage = null;
                });
        }

    }
</script>

<style scoped>
</style>
