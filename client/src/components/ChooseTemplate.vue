<template>
  <div class="text-left">
    <b-form-group class="pt-2" label="Choose template" label-for="template-input">
      <b-form-select
        id="template-input"
        v-model="selectedTemplate"
        @change="getLanguages"
        :options="templateOptions"
      ></b-form-select>
    </b-form-group>

    <b-form-group class="pt-2" label="Choose language" label-for="language-input">
      <b-form-select
        id="language-input"
        v-if="isLanguageSelectionAllowed"
        v-model="selectedLanguage"
        :options="languageOptions"
      ></b-form-select>
    </b-form-group>

    <div v-if="isCurrentUserEditor() || isCurrentUserDeveloper()">
      <b-row class="">
        <b-col class="">
          <label class="pt-2">
            Choose file&nbsp;
            <Info
              v-b-popover.hover.top="
                'To start creating new changes click \'New File\'. ' +
                'This will create a new file on top of the list. Select the new file and proceed. ' +
                'To continue working on the changes started earlier, choose the correct file from the list below. ' +
                'At least one of the files below needs to be selected to continue. ' +
                'To download one of the files, click on the file name.'
              "
              title="Instructions"
            >
            </Info>
          </label>
        </b-col>
        <b-col class="text-right">
          <b-button class="ml-auto" variant="primary" size="sm" @click="createNewFile">
            New file
          </b-button>
        </b-col>
      </b-row>

      <div class="limited-height">
        <b-table striped hover :items="sqlFileOptions" :fields="fields">
          <template v-slot:cell(createdAt)="data">
            {{ getDateForTable(data.item.createdAt) }}
          </template>
          <template v-slot:cell(sqlFileName)="data">
            <a class="normal-link" v-on:click.prevent="downloadFile(data)">
              {{ data.item.sqlFileName }}
            </a>
          </template>
          <template v-slot:cell(selected)="data">
            <b-button
              size="sm"
              :variant="getButtonStyle(data.item.selected)"
              @click="select(data.item.id)"
            >
              {{ getButtonText(data.item.selected) }}
            </b-button>
          </template>
        </b-table>
      </div>
    </div>

    <div class="text-center">
      <b-button
        :disabled="!isNavigationAllowed()"
        class="mt-3"
        variant="success"
        @click="navigateToEditPage"
      >
        {{ isCurrentUserEditor() ? 'Proceed to editing template' : 'Proceed to view template' }}
      </b-button>

      <b-button
        v-if="isCurrentUserDeveloper()"
        :disabled="!isCreatingNewTemplateAllowed()"
        class="ml-3 mt-3"
        variant="secondary"
        @click="addTemplate"
      >
        Add new template
      </b-button>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { getRequest, postRequest, putRequest } from '@/scripts/requests';
import Info from '@/assets/info.svg';
import {
  isCurrentUserEditor,
  isCurrentUserDeveloper,
  hasNoContent,
  getDateForTable,
  getButtonText,
  getButtonStyle
} from '@/scripts/util';
import downloadFile from '@/scripts/downloading';

@Component({
  components: {
    Info
  },
  methods: {
    isCurrentUserEditor,
    isCurrentUserDeveloper,
    downloadFile,
    getDateForTable,
    getButtonText,
    getButtonStyle
  }
})
export default class ChooseTemplate extends Vue {
  selectedTemplate = '';

  selectedLanguage = '';

  templateOptions: Array<string> = [];

  languageOptions: Array<string> = [];

  fields: Array<string> = ['createdAt', 'sqlFileName', 'selected'];

  sqlFileOptions: Array<any> = [];

  mounted(): void {
    this.getTemplates();
    if (isCurrentUserEditor() || isCurrentUserDeveloper()) {
      this.getFiles();
    }
  }

  getTemplates(): void {
    getRequest('/api/v1/all-templates')
      .then((response) => response.json())
      .then((data) => {
        this.templateOptions = data;
      });
  }

  getFiles(): void {
    getRequest('/api/v1/sql-files')
      .then((response) => response.json())
      .then((data) => {
        this.sqlFileOptions = data;
      });
  }

  getLanguages(): void {
    getRequest(`/api/v1/template-languages/${this.selectedTemplate}`)
      .then((response) => response.json())
      .then((data) => {
        this.languageOptions = data;
      });
  }

  createNewFile(): void {
    postRequest(`/api/v1/add-sql`, null).then(() => this.getFiles());
  }

  isLanguageSelectionAllowed(): boolean {
    return !hasNoContent(this.selectedTemplate);
  }

  isNavigationAllowed(): boolean {
    if (isCurrentUserEditor()) {
      return (
        !hasNoContent(this.selectedTemplate) &&
        !hasNoContent(this.selectedLanguage) &&
        !hasNoContent(this.sqlFileOptions) &&
        this.sqlFileOptions.filter((el: any) => el.selected).length === 1
      );
    }
    return !hasNoContent(this.selectedTemplate) && !hasNoContent(this.selectedLanguage);
  }

  isCreatingNewTemplateAllowed(): boolean {
    return (
      isCurrentUserDeveloper() &&
      !hasNoContent(this.sqlFileOptions) &&
      this.sqlFileOptions.filter((el: any) => el.selected).length === 1
    );
  }

  navigateToEditPage(): void {
    this.$router.push({
      path: `edit-pdf/${this.selectedTemplate}/${this.selectedLanguage}/-`
    });
  }

  addTemplate(): void {
    const routeTo = '/new-template';
    this.$router.push({ path: routeTo });
  }

  select(id: number) {
    putRequest(`/api/v1/select-sql/${id}`, null).then(() => this.getFiles());
  }
}
</script>

<style scoped>
.limited-height {
  max-height: 55vh;
  overflow: scroll;
}

.normal-link {
  cursor: pointer;
  color: blue;
  text-decoration: underline;
}

.normal-link:hover {
  text-decoration: none;
  color: #0000c1;
}
</style>
