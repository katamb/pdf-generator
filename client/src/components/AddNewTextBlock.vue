<template>
  <b-container>
    <b-row class="justify-content-center">
      <b-col class="mt-3 mb-2">
        <h2 class="mt-2">Add template text</h2>
      </b-col>
    </b-row>

    <b-row class="justify-content-center">
      <b-col cols="3" class="my-auto">
        <label :for="'template-code'" class="my-auto">Template code (name)</label>
      </b-col>
      <b-col cols="8">
        <b-form-select
          id="template-input"
          v-model="templateCode"
          :options="templateOptions"
        ></b-form-select>
      </b-col>
    </b-row>

    <b-row class="justify-content-center mt-2">
      <b-col cols="3" class="my-auto">
        <label :for="'language-code'" class="my-auto">Language code</label>
      </b-col>
      <b-col cols="8">
        <b-form-select
          id="language-input"
          v-model="languageCode"
          :options="languageOptions"
        ></b-form-select>
      </b-col>
    </b-row>

    <b-row class="justify-content-center mt-2">
      <b-col cols="3" class="my-auto">
        <label :for="'text-block-name'" class="my-auto">Text block name</label>
      </b-col>
      <b-col cols="8">
        <b-form-input
          id="text-block-name"
          type="text"
          maxlength="255"
          v-model="textBlockName"
        ></b-form-input>
      </b-col>
    </b-row>

    <b-row class="justify-content-center mt-2">
      <b-col cols="3" class="my-auto">
        <label :for="'text-group-code'" class="my-auto">Text group code</label>
      </b-col>
      <b-col cols="8">
        <b-form-input
          id="text-group-code"
          type="text"
          maxlength="255"
          v-model="textGroupCode"
        ></b-form-input>
      </b-col>
    </b-row>

    <b-row class="justify-content-center mt-2">
      <b-col cols="3" class="my-auto">
        <label :for="'text-block-value'" class="my-auto">Text</label>
      </b-col>
      <b-col cols="8">
        <b-form-textarea id="text-block-value" rows="3" v-model="textBlockValue"></b-form-textarea>
      </b-col>
    </b-row>

    <b-row class="justify-content-center mt-2">
      <b-col cols="3" class="my-auto">
        <label :for="'ordering'" class="my-auto">Order number</label>
      </b-col>
      <b-col cols="8">
        <b-form-input
          id="ordering"
          type="number"
          min="0000"
          max="9999"
          v-model="ordering"
        ></b-form-input>
      </b-col>
    </b-row>

    <b-row class="justify-content-center mt-2">
      <b-col cols="3" class="my-auto">
        <label :for="'numbering'" class="my-auto">Numbering</label>
      </b-col>
      <b-col cols="8">
        <b-form-checkbox
          id="numbering"
          name="numbering-checkbox"
          v-model="numbering"
        ></b-form-checkbox>
      </b-col>
    </b-row>

    <b-row class="justify-content-center mt-2">
      <b-col cols="3" class="my-auto">
        <label :for="'numbering-level'" class="my-auto">Numbering level</label>
      </b-col>
      <b-col cols="8">
        <b-form-input
          id="numbering-level"
          type="number"
          min="0001"
          max="0025"
          v-model="numberingLevel"
        ></b-form-input>
      </b-col>
    </b-row>

    <b-row class="justify-content-center">
      <b-col class="mt-3">
        <b-button
          class="m-3"
          :disabled="hasNoContent(templateCode) || hasNoContent(languageCode)"
          variant="success"
          @click="addNewText"
        >
          Add new text-block
        </b-button>
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import downloadFile from '@/scripts/downloading';
import Info from '@/assets/info.svg';
import Navigation from '@/components/Navigation.vue';
import DownloadSql from '@/components/DownloadSql.vue';
import { hasNoContent } from '@/scripts/util';
import { getRequest, postRequest } from '@/scripts/requests';

@Component({
  components: {
    Info,
    Navigation,
    DownloadSql
  },
  methods: {
    downloadFile,
    hasNoContent
  }
})
export default class AddNewTextBlock extends Vue {
  templateCode = '';

  templateOptions: Array<string> = [];

  languageCode = '';

  languageOptions: Array<string> = [];

  textBlockName = '';

  textGroupCode = '';

  textBlockValue = '';

  ordering = 0;

  numbering = false;

  numberingLevel = 1;

  mounted(): void {
    this.getTemplates();
    this.getLanguages();
  }

  getTemplates(): void {
    getRequest('/api/v1/all-enum-templates')
      .then((response) => response.json())
      .then((data) => {
        this.templateOptions = data;
      });
  }

  getLanguages(): void {
    getRequest(`/api/v1/all-languages`)
      .then((response) => response.json())
      .then((data) => {
        this.languageOptions = data;
      });
  }

  addNewText(): void {
    postRequest(`/api/v1/add-new-text-block`, {
      templateCode: this.templateCode.toUpperCase(),
      languageCode: this.languageCode.toLowerCase(),
      textBlockName: this.textBlockName,
      textGroupCode: this.textGroupCode,
      textBlockValue: this.textBlockValue,
      ordering: this.ordering,
      numbering: this.numbering,
      numberingLevel: this.numberingLevel
    }).then((response) => {
      if (response.status === 200) {
        this.makeToast();
      }
    });
  }

  makeToast() {
    this.$bvToast.toast(`Text-block added successfully!`, {
      title: 'BootstrapVue Toast',
      autoHideDelay: 2000,
      appendToast: true
    });
  }
}
</script>

<style scoped></style>
