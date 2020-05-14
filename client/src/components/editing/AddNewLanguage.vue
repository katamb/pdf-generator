<template>
  <div>
    <b-modal v-model="showModal" title="New version created" hide-footer>
      <div class="d-block text-center">
        <p>
          New version of {{ this.$route.params.template }} template was created. The new version is
          in {{ currentLanguageName }} and it's based on the version written in
          {{ previousLanguageName }}. Would you like to start editing the new version in
          {{ currentLanguageName }} or keep editing the version in {{ previousLanguageName }}?
        </p>
      </div>
      <div class="text-center">
        <b-button class="m-3" variant="primary" @click="continueToNewLanguage()">
          Continue to edit the new version in {{ currentLanguageName }}
        </b-button>
        <b-button class="m-3" variant="secondary" @click="keepEditingCurrent()">
          Keep editing the version in {{ previousLanguageName }}
        </b-button>
      </div>
    </b-modal>

    <div class="m-3 text-left">
      <p>
        Choose language and click "Create" button to create the same template in another language.
        Once "Create" button is clicked, you'll be redirected to the new document.
      </p>
      <b-form-group class="pt-3" label="Choose language" label-for="language-input">
        <b-form-select
          id="language-input"
          v-model="selectedLanguage"
          :options="languageOptions"
        ></b-form-select>
      </b-form-group>
      <b-button @click="createNewLanguage()" :disabled="!isLanguageSelected()" variant="success">
        Create
      </b-button>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { getRequest, postRequest } from '@/scripts/requests';
import { hasNoContent } from '@/scripts/util';
import eventBus from '@/scripts/eventBus';
import { RERENDER_PDF_EVENT } from '@/scripts/constants';

@Component
export default class AddNewLanguage extends Vue {
  private selectedLanguage = '';

  private languageOptions: Array<string> = [];

  private unavailableLanguageValues: Array<string> = [];

  showModal = false;

  currentLanguageName = '';

  previousLanguageName = '';

  mounted(): void {
    this.getLanguages();
  }

  isLanguageSelected(): boolean {
    return !hasNoContent(this.selectedLanguage);
  }

  private getLanguages(): void {
    getRequest('/api/v1/all-languages')
      .then((response) => response.json())
      .then((data) => {
        this.languageOptions = data;
      })
      .then(() => this.getAndRemoveUnavailableLanguageOptions());
  }

  private getAndRemoveUnavailableLanguageOptions(): void {
    getRequest(`/api/v1/template-languages/${this.$route.params.template}`)
      .then((response) => response.json())
      .then((data) => {
        this.unavailableLanguageValues = data.map((el: any) => el.value);
      })
      .then(() => {
        this.languageOptions = this.languageOptions.filter(
          (element: any) => !this.unavailableLanguageValues.includes(element.value)
        );
      });
  }

  createNewLanguage(): void {
    postRequest(
      `/api/v1/add-language/${this.$route.params.template}/${this.$route.params.language}/${this.selectedLanguage}`
    ).then(() => {
      this.getLanguageFromCode();
      this.showModal = true;
    });
  }

  private getLanguageFromCode(): void {
    getRequest(`/api/v1/languages-by-code/${this.selectedLanguage}`)
      .then((body: any) => body.json())
      .then((json) => {
        this.currentLanguageName = json.text;
      });
    getRequest(`/api/v1/languages-by-code/${this.$route.params.language}`)
      .then((body: any) => body.json())
      .then((json) => {
        this.previousLanguageName = json.text;
      });
  }

  continueToNewLanguage(): void {
    this.$router.push({
      path: `/edit-pdf/${this.$route.params.template}/${this.selectedLanguage}/-`
    });
    this.getLanguages();
    this.selectedLanguage = '';
    eventBus.$emit(RERENDER_PDF_EVENT);
    this.showModal = false;
  }

  keepEditingCurrent(): void {
    this.getLanguages();
    this.selectedLanguage = '';
    this.showModal = false;
  }
}
</script>

<style scoped></style>
