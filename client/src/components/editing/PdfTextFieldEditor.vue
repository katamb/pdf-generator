<template>
  <div>
    <b-modal v-model="showModal" hide-footer>
      <div class="d-block text-center">
        <h4 class="text-danger">{{ errorMessage }}</h4>
        <h5>
          Would you like to make that change in all templates where this text is shown or only in
          this template?
        </h5>
      </div>
      <div class="text-center">
        <b-button class="m-3" variant="success" @click="updateText(UPDATE_ONLY_CURRENT)">
          Only in this template
        </b-button>
        <b-button class="m-3" variant="warning" @click="updateText(UPDATE_ALL)">
          In all templates
        </b-button>
      </div>
    </b-modal>

    <b-alert v-model="showDismissibleAlert" variant="danger" dismissible>
      Select text before adding the formatting!
    </b-alert>

    <div v-if="json === null || json === undefined" class="mt-3">
      <p>Click on a text block in the preview window to edit that text!</p>
    </div>

    <div v-if="json !== null && json !== undefined" class="mt-3">
      <label class="mb-0">Alignment controls</label>
      <b-row>
        <b-col>
          <b-button-group class="m-1" size="sm">
            <b-button
              @click="updateVerticalAlignment(verticalAlignTop)"
              v-bind:class="{ 'active-button': isActive(verticalAlignTop) }"
            >
              <VerticalAlignTop />
            </b-button>
            <b-button
              @click="updateVerticalAlignment(verticalAlignCenter)"
              v-bind:class="{ 'active-button': isActive(verticalAlignCenter) }"
            >
              <VerticalAlignCenter />
            </b-button>
            <b-button
              @click="updateVerticalAlignment(verticalAlignBottom)"
              v-bind:class="{ 'active-button': isActive(verticalAlignBottom) }"
            >
              <VerticalAlignBottom />
            </b-button>
          </b-button-group>
          <b-button-group class="m-1" size="sm">
            <b-button
              @click="updateHorizontalAlignment(horizontalAlignLeft)"
              v-bind:class="{
                'active-button': isActive(horizontalAlignLeft) || isActive(horizontalAlignDefault)
              }"
            >
              <FormatAlignLeft />
            </b-button>
            <b-button
              @click="updateHorizontalAlignment(horizontalAlignCenter)"
              v-bind:class="{ 'active-button': isActive(horizontalAlignCenter) }"
            >
              <FormatAlignCenter />
            </b-button>
            <b-button
              @click="updateHorizontalAlignment(horizontalAlignRight)"
              v-bind:class="{ 'active-button': isActive(horizontalAlignRight) }"
            >
              <FormatAlignRight />
            </b-button>
            <b-button
              @click="updateHorizontalAlignment(horizontalAlignJustified)"
              v-bind:class="{ 'active-button': isActive(horizontalAlignJustified) }"
            >
              <FormatAlignJustify />
            </b-button>
          </b-button-group>
        </b-col>
      </b-row>

      <label class="mb-0 mt-3">Text size and style controls</label>
      <b-row>
        <b-col>
          <b-input-group class="mt-1 mb-2 ml-auto limited-width" prepend="Text size">
            <b-form-input type="number" v-model="json.textSize"> </b-form-input>
          </b-input-group>
        </b-col>
        <b-col class="text-left">
          <b-button-group class="my-1" size="sm">
            <b-button @click="applyFormat('BOLD')">
              <FormatBold />
            </b-button>
            <b-button @click="applyFormat('ITALIC')">
              <FormatItalic />
            </b-button>
            <b-button @click="applyFormat('UNDERLINE')">
              <FormatUnderlined />
            </b-button>
            <b-button @click="applyFormat('STRIKETHROUGH')">
              <FormatStrikethrough />
            </b-button>
          </b-button-group>
        </b-col>
      </b-row>

      <label class="mb-0 mt-2">Text padding controls</label>
      <b-row>
        <b-col>
          <b-input-group class="mx-0 px-0 mb-2 limited-width" prepend="Top">
            <b-form-input type="number" v-model="json.paddingTop"> </b-form-input>
          </b-input-group>
        </b-col>
        <b-col>
          <b-input-group class="mx-0 px-0 mb-2 limited-width" prepend="Bottom">
            <b-form-input type="number" v-model="json.paddingBottom"> </b-form-input>
          </b-input-group>
        </b-col>
        <b-col>
          <b-input-group class="mx-0 px-0 mb-2 limited-width" prepend="Left">
            <b-form-input type="number" v-model="json.paddingLeft"> </b-form-input>
          </b-input-group>
        </b-col>
        <b-col>
          <b-input-group class="mx-0 px-0 mb-2 limited-width" prepend="Right">
            <b-form-input type="number" v-model="json.paddingRight"> </b-form-input>
          </b-input-group>
        </b-col>
      </b-row>

      <b-form-textarea
        @select.native="saveCurrentSelection"
        v-model="json.textBlockValue"
        debounce="500"
        rows="4"
        max-rows="25"
      >
      </b-form-textarea>

      <b-button variant="success" class="m-2" @click="updateText(CONFIRM_UPDATE)">
        Update text block
      </b-button>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator';
import { getRequest, putRequest } from '@/scripts/requests';
import { RERENDER_PDF_EVENT } from '@/scripts/constants';
import eventBus from '@/scripts/eventBus';
import Explanations from '@/components/Explanations.vue';
import AddNewLanguage from '@/components/editing/AddNewLanguage.vue';
import DownloadSql from '@/components/DownloadSql.vue';
import FormatStrikethrough from '@/assets/format_strikethrough.svg';
import FormatUnderlined from '@/assets/format_underlined.svg';
import FormatItalic from '@/assets/format_italic.svg';
import FormatBold from '@/assets/format_bold.svg';
import FormatAlignCenter from '@/assets/format_align_center.svg';
import FormatAlignJustify from '@/assets/format_align_justify.svg';
import FormatAlignLeft from '@/assets/format_align_left.svg';
import FormatAlignRight from '@/assets/format_align_right.svg';
import VerticalAlignCenter from '@/assets/vertical_align_center.svg';
import VerticalAlignBottom from '@/assets/vertical_align_bottom.svg';
import VerticalAlignTop from '@/assets/vertical_align_top.svg';
import { isUndefinedOrNull, getTag } from '@/scripts/util';

@Component({
  components: {
    Explanations,
    AddNewLanguage,
    DownloadSql,
    FormatStrikethrough,
    FormatUnderlined,
    FormatItalic,
    FormatBold,
    FormatAlignCenter,
    FormatAlignJustify,
    FormatAlignLeft,
    FormatAlignRight,
    VerticalAlignCenter,
    VerticalAlignBottom,
    VerticalAlignTop
  }
})
export default class PdfTextFieldEditor extends Vue {
  horizontalAlignLeft = -1;

  horizontalAlignDefault = 0;

  horizontalAlignCenter = 1;

  horizontalAlignRight = 2;

  horizontalAlignJustified = 3;

  verticalAlignTop = 4;

  verticalAlignCenter = 5;

  verticalAlignBottom = 6;

  UPDATE_ALL = 'UPDATE_ALL';

  UPDATE_ONLY_CURRENT = 'UPDATE_ONLY_CURRENT';

  CONFIRM_UPDATE = 'CONFIRM_UPDATE';

  showDismissibleAlert = false;

  showModal = false;

  errorMessage = '';

  json: any = null;

  private selectionStart: any = null;

  private selectionEnd: any = null;

  mounted(): void {
    this.getText();
  }

  @Watch('$route')
  onPropertyChanged() {
    this.getText();
  }

  private getText(): void {
    if (this.$route.params.id === '-') {
      this.json = null;
      return;
    }
    getRequest(
      `/api/v1/text-by-id/${this.$route.params.template}/${this.$route.params.language}/${this.$route.params.id}`
    )
      .then((response) => response.json())
      .then((response) => {
        if (response.status !== undefined && response.status !== null && response.status !== 200) {
          this.json = null;
        } else {
          this.json = response;
        }
      });
  }

  updateText(updateType: string): void {
    putRequest(`/api/v1/update-text/${updateType}`, this.json)
      .then((response) => response.json())
      .then((jsonResponse) => {
        if (jsonResponse.statusCode === 300) {
          this.errorMessage = jsonResponse.message;
          this.showModal = true;
        } else if (jsonResponse.statusCode === 200) {
          this.showModal = false;
          eventBus.$emit(RERENDER_PDF_EVENT);
          this.getText();
        }
      });
  }

  isActive(data: number): boolean {
    return data === this.json.horizontalAlignment || data === this.json.verticalAlignment;
  }

  updateHorizontalAlignment(newAlignment: number): void {
    this.json.horizontalAlignment = newAlignment;
  }

  updateVerticalAlignment(newAlignment: number): void {
    this.json.verticalAlignment = newAlignment;
  }

  nullCurrentSelection(): void {
    this.selectionStart = null;
    this.selectionEnd = null;
  }

  saveCurrentSelection(event: any): void {
    this.selectionStart = event.currentTarget.selectionStart;
    this.selectionEnd = event.currentTarget.selectionEnd;
  }

  applyFormat(format: string): void {
    if (!isUndefinedOrNull(this.selectionStart) && !isUndefinedOrNull(this.selectionEnd)) {
      const stringBeforeSelection = this.json.textBlockValue.substring(0, this.selectionStart);
      const selection = this.json.textBlockValue.substring(this.selectionStart, this.selectionEnd);
      const stringAfterSelection = this.json.textBlockValue.substring(
        this.selectionEnd,
        this.json.textBlockValue.length
      );
      this.json.textBlockValue =
        stringBeforeSelection +
        getTag('START', format) +
        selection +
        getTag('END', format) +
        stringAfterSelection;
      this.nullCurrentSelection();
    } else {
      this.showDismissibleAlert = true;
    }
  }
}
</script>

<style scoped>
.active-button {
  background-color: #191919;
}

.limited-width {
  max-width: 170px;
}
</style>
