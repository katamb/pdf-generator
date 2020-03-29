<template>
  <div class="text-left">

    <b-form-group class="pt-2" label="Choose template" label-for="template-input">
      <b-form-select id="template-input"
                     v-model="selectedTemplate"
                     @change="getLanguages"
                     :options="templateOptions"></b-form-select>
    </b-form-group>

    <b-form-group class="pt-2" label="Choose language" label-for="language-input">
      <b-form-select id="language-input"
                     v-if="languageSelectionAllowed"
                     v-model="selectedLanguage"
                     :options="languageOptions"></b-form-select>
    </b-form-group>

    <b-row class="">
      <b-col class="">
        <label class="pt-2">
          Choose file&nbsp;
          <Info
              v-b-popover.hover.top="'To start creating new changes click \'New File\'. ' +
              'This will create a new file on top of the list. Select the new file and proceed. ' +
              'To continue working on the changes started earlier, choose the correct file from the list below. ' +
              'At least one of the files below needs to be selected to continue. ' +
              'To download one of the files, click on the file name.'"
              title="Instructions">
          </Info>
        </label>
      </b-col>
      <b-col class="text-right">
        <b-button class="ml-auto" variant="primary" size="sm" @click="createNewFile">New file</b-button>
      </b-col>
    </b-row>

    <div class="limited-height">
      <b-table striped hover :items="sqlFileOptions" :fields="fields">
        <template v-slot:cell(createdAt)="data">
          {{getDateForTable(data.item.createdAt)}}
        </template>
        <template v-slot:cell(sqlFileName)="data">
          <a :href="`${backendUrl}/api/v1/download-sql/${data.item.sqlFileName}`">{{data.item.sqlFileName}}</a>
        </template>
        <template v-slot:cell(selected)="data">
          <b-button size="sm"
                    :variant="getButtonStyle(data.item.selected)"
                    @click="select(data.item.id)">
            {{getButtonText(data.item.selected)}}
          </b-button>
        </template>
      </b-table>
    </div>

    <div class="text-center">
      <b-button :disabled="!navigationAllowed()"
                class="mt-3"
                variant="success"
                @click="navigateToEditPage">Proceed to editing page
      </b-button>
    </div>

  </div>
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import {getRequest, postRequest, putRequest} from "@/requests";
    import router from "@/router";
    import {BACKEND_URL} from '@/constants';
    import Info from "@/assets/info.svg";

    @Component({
        components: {
            Info
        }
    })
    export default class ChooseTemplate extends Vue {
        selectedTemplate: any = null;
        selectedLanguage: any = null;
        templateOptions: any = [];
        languageOptions: any = [];
        backendUrl: string = BACKEND_URL;
        fields: any = ['createdAt', 'sqlFileName', 'selected'];
        sqlFileOptions: any = null;

        mounted(): void {
            this.getTemplates();
            this.getFiles();
        }

        getTemplates(): void {
            getRequest('/api/v1/all-templates')
                .then(response => response.json())
                .then(data => this.templateOptions = data)
            ;
        }

        getFiles(): void {
            getRequest('/api/v1/sql-files')
                .then(response => response.json())
                .then(data => this.sqlFileOptions = data)
            ;
        }

        getLanguages(): void {
            getRequest(`/api/v1/template-languages/${this.selectedTemplate}`)
                .then(response => response.json())
                .then(data => this.languageOptions = data)
            ;
        }

        createNewFile(): void {
            postRequest(`/api/v1/add-sql`, null)
                .then(() => this.getFiles())
            ;
        }

        languageSelectionAllowed(): boolean {
            return this.elementSelectionAllowed(this.selectedTemplate);
        }

        navigationAllowed(): boolean {
            return this.elementSelectionAllowed(this.selectedTemplate)
                && this.elementSelectionAllowed(this.selectedLanguage)
                && this.elementSelectionAllowed(this.sqlFileOptions)
                && (this.sqlFileOptions.filter((el: any) => el.selected).length === 1);
        }

        private elementSelectionAllowed(element: any): boolean {
            return element !== undefined && element !== null && element !== '';
        }

        navigateToEditPage(): void {
            router.push({path: `edit-pdf/${this.selectedTemplate}/${this.selectedLanguage}/-`});
        }

        getDateForTable(dateTime: string) {
            const date = dateTime.split('T')[0];
            const time = dateTime.split('T')[1].split('.')[0];
            return date + " " + time;
        }

        getButtonText(selected: boolean) {
            return selected ? 'Selected' : 'Select';
        }

        getButtonStyle(selected: boolean) {
            return selected ? 'primary' : 'outline-primary';
        }

        select(id: number) {
            putRequest(`/api/v1/select-sql/${id}`, null)
                .then(() => this.getFiles());
        }
    }
</script>

<style scoped>
  .limited-height {
    max-height: 55vh;
    overflow: scroll;
  }
</style>
