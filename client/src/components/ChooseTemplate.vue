<template>
  <div>
    <b-form-group class="pt-3" label="Choose template" label-for="template-input">
      <b-form-select id="template-input"
                     v-model="selectedTemplate"
                     @change="getLanguages"
                     :options="templateOptions"></b-form-select>
    </b-form-group>

    <b-form-group class="pt-3" label="Choose language" label-for="language-input">
      <b-form-select id="language-input"
                     v-if="languageSelectionAllowed"
                     v-model="selectedLanguage"
                     :options="languageOptions"></b-form-select>
    </b-form-group>

    <b-button v-if="navigationAllowed" @click="navigateToEditPage">Edit</b-button>
  </div>
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import {getRequest} from "@/requests";
    import router from "@/router";

    @Component
    export default class HelloWorld extends Vue {
        private selectedTemplate: any = null;
        private selectedLanguage: any = null;
        private templateOptions: any = [];
        private languageOptions: any = [];

        mounted(): void {
            getRequest('/api/v1/all-templates')
                .then(response => response.json())
                .then(data => this.templateOptions = data)
            ;
        }

        getLanguages(): void {
            getRequest(`/api/v1/template-languages/${this.selectedTemplate}`)
                .then(response => response.json())
                .then(data => this.languageOptions = data)
            ;
        }

        languageSelectionAllowed(): boolean {
            return this.elementSelectionAllowed(this.selectedTemplate);
        }

        navigationAllowed(): boolean {
            return this.elementSelectionAllowed(this.selectedTemplate) && this.elementSelectionAllowed(this.selectedLanguage);
        }

        private elementSelectionAllowed(element: any): boolean {
            return element !== undefined && element !== null && element !== '';
        }

        navigateToEditPage(): void {
            router.push({path: `edit-pdf/${this.selectedTemplate}/${this.selectedLanguage}/-`});
        }

    }
</script>

<style scoped>
</style>
