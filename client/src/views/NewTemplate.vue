<template>
  <div>
    <b-container>
      <b-row class="justify-content-center">
        <b-col class="mt-3 mb-2">
          <h2 class="mt-2">Add template text</h2>
          <p>
            The template code and language code need to be defined in the system to use this
            interface.
          </p>
        </b-col>
      </b-row>

      <b-row class="justify-content-center">
        <b-col cols="3" class="my-auto">
          <label :for="'template-code'" class="my-auto">Template code (name)</label>
        </b-col>
        <b-col cols="8">
          <b-form-input
            id="template-code"
            type="text"
            maxlength="150"
            v-model="templateCode"
          ></b-form-input>
        </b-col>
      </b-row>
      <b-row class="justify-content-center mt-2">
        <b-col cols="3" class="my-auto">
          <label :for="'language-code'" class="my-auto">Language code</label>
        </b-col>
        <b-col cols="8">
          <b-form-input
            id="language-code"
            type="text"
            maxlength="2"
            v-model="languageCode"
          ></b-form-input>
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
          <b-form-textarea
            id="text-block-value"
            rows="3"
            v-model="textBlockValue"
          ></b-form-textarea>
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
            min="0000"
            max="0015"
            v-model="numberingLevel"
          ></b-form-input>
        </b-col>
      </b-row>

      <b-row class="justify-content-center">
        <b-col class="mt-3">
          <b-button class="m-3" variant="success" @click="todo">
            Add new text-block
          </b-button>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import downloadFile from '@/scripts/downloading';
import Info from '@/assets/info.svg';
import Navigation from '@/components/Navigation.vue';

@Component({
  components: {
    Info,
    Navigation
  },
  methods: {
    downloadFile
  }
})
export default class NewTemplate extends Vue {
  templateCode = '';

  languageCode = '';

  textBlockName = '';

  textGroupCode = '';

  textBlockValue = '';

  ordering = 0;

  numbering = false;

  numberingLevel = 0;
}
</script>

<style scoped></style>
