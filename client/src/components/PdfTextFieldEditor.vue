<template>
  <div>

    <b-modal v-model="showModal" hide-footer>
      <div class="d-block text-center">
        <h4 class="text-danger">{{this.errorMessage}}</h4>
        <h5>Would you like to make that change in all templates where this text is shown or only in this template?</h5>
      </div>
      <div class="text-center">
        <b-button class="m-3"
                  variant="success"
                  @click="updateText(updateOnlyCurrent)">Only in this template
        </b-button>
        <b-button class="m-3"
                  variant="warning"
                  @click="updateText(updateAll)">In all templates
        </b-button>
      </div>
    </b-modal>

<!--    <b-row class="justify-content-center">-->
<!--      <b-col xs="12" sm="10" md="8" lg="6" xl="5">-->
<!--        <b-input-group class="mt-1 mb-2 mx-auto"-->
<!--                       prepend="Text block ID">-->
<!--          <b-form-input type="number"-->
<!--                        v-model="id">-->
<!--          </b-form-input>-->
<!--          <b-input-group-append>-->
<!--            <b-button size="sm" @click="getText" variant="dark">Find</b-button>-->
<!--          </b-input-group-append>-->
<!--        </b-input-group>-->
<!--      </b-col>-->
<!--    </b-row>-->

    <div v-if="this.json !== null && this.json !== undefined" class="mt-3">

      <b-button-group class="m-1" size="sm">
        <b-button @click="updateVerticalAlignment(verticalAlignTop)"
                  v-bind:class="{active: isActive(verticalAlignTop)}">
          <i class="material-icons icon-center">vertical_align_top</i>
        </b-button>
        <b-button @click="updateVerticalAlignment(verticalAlignCenter)"
                  v-bind:class="{active: isActive(verticalAlignCenter)}">
          <i class="material-icons icon-center">vertical_align_center</i>
        </b-button>
        <b-button @click="updateVerticalAlignment(verticalAlignBottom)"
                  v-bind:class="{active: isActive(verticalAlignBottom)}">
          <i class="material-icons icon-center">vertical_align_bottom</i>
        </b-button>
      </b-button-group>
      <b-button-group class="m-1" size="sm">
        <b-button @click="updateHorizontalAlignment(horizontalAlignLeft)"
                  v-bind:class="{active: (isActive(horizontalAlignLeft) || isActive(horizontalAlignDefault))}">
          <i class="material-icons icon-center">format_align_left</i>
        </b-button>
        <b-button @click="updateHorizontalAlignment(horizontalAlignCenter)"
                  v-bind:class="{active: isActive(horizontalAlignCenter)}">
          <i class="material-icons icon-center">format_align_center</i>
        </b-button>
        <b-button @click="updateHorizontalAlignment(horizontalAlignRight)"
                  v-bind:class="{active: isActive(horizontalAlignRight)}">
          <i class="material-icons icon-center">format_align_right</i>
        </b-button>
        <b-button @click="updateHorizontalAlignment(horizontalAlignJustified)"
                  v-bind:class="{active: isActive(horizontalAlignJustified)}">
          <i class="material-icons icon-center">format_align_justify</i>
        </b-button>
      </b-button-group>

      <b-row class="justify-content-center">
        <b-col xs="10" sm="8" md="6" lg="5" xl="4">
          <b-input-group class="mt-1 mb-2 mx-auto"
                         prepend="Text size">
            <b-form-input type="number"
                          v-model="json.textSize">
            </b-form-input>
          </b-input-group>
        </b-col>
      </b-row>

      <b-form-textarea v-model="json.textBlockValue"
                       debounce="500"
                       rows="4"
                       max-rows="25"
      ></b-form-textarea>
      <b-button variant="dark"
                class="m-1"
                @click="updateText(confirmUpdate)">
        Update text block
      </b-button>

    </div>
  </div>
</template>


<script lang="ts">
    import {Component, Prop, Vue, Watch} from "vue-property-decorator";
    import {getRequest, putRequest} from "@/requests";
    import {
        CONFIRM_UPDATE, HORIZONTAL_ALIGN_CENTER, HORIZONTAL_ALIGN_DEFAULT, HORIZONTAL_ALIGN_JUSTIFIED,
        HORIZONTAL_ALIGN_LEFT, HORIZONTAL_ALIGN_RIGHT, UPDATE_ALL, UPDATE_ONLY_CURRENT, VERTICAL_ALIGN_BOTTOM,
        VERTICAL_ALIGN_CENTER, VERTICAL_ALIGN_TOP
    } from "@/constants";
    import eventBus from "@/eventBus";

    @Component
    export default class PdfTextFieldEditor extends Vue {
        // @Prop() private msg!: string;
        private verticalAlignTop = VERTICAL_ALIGN_TOP;
        private verticalAlignCenter = VERTICAL_ALIGN_CENTER;
        private verticalAlignBottom = VERTICAL_ALIGN_BOTTOM;
        private horizontalAlignLeft = HORIZONTAL_ALIGN_LEFT;
        private horizontalAlignDefault = HORIZONTAL_ALIGN_DEFAULT;
        private horizontalAlignCenter = HORIZONTAL_ALIGN_CENTER;
        private horizontalAlignRight = HORIZONTAL_ALIGN_RIGHT;
        private horizontalAlignJustified = HORIZONTAL_ALIGN_JUSTIFIED;
        private updateAll = UPDATE_ALL;
        private updateOnlyCurrent = UPDATE_ONLY_CURRENT;
        private confirmUpdate = CONFIRM_UPDATE;
        // private id = 0;
        private json: any = null;
        private showModal = false;
        private errorMessage = '';

        private mounted(): void {
            this.getText();
        }

        @Watch('$route')
        onPropertyChanged(value: string, oldValue: string) {
            this.getText();
        }

        private getText(): void {
            getRequest(`/api/v1/text-by-id/${this.$route.params.template}/${this.$route.params.language}/${this.$route.params.id}`)
                .then(response => response.json())
                .then(jsons => this.json = jsons)
            ;
        }

        private updateText(updateType: string): void {
            putRequest(`/api/v1/update-text/${updateType}`, this.json)
                .then((response) => response.json())
                .then(jsons => {
                    if (jsons.status === 300) {
                        this.errorMessage = jsons.message;
                        this.showModal = true;
                    } else {
                        this.showModal = false;
                        eventBus.$emit('rerender-pdf');
                    }
                });
        }

        private isActive(data: number): boolean {
            return data == this.json.horizontalAlignment || data == this.json.verticalAlignment;
        }

        private updateHorizontalAlignment(newAlignment: number): void {
            if ([this.horizontalAlignLeft, this.horizontalAlignDefault, this.horizontalAlignCenter,
                this.horizontalAlignRight, this.horizontalAlignJustified].includes(newAlignment)) {
                this.json.horizontalAlignment = newAlignment;
            }
        }

        private updateVerticalAlignment(newAlignment: number): void {
            if ([this.verticalAlignTop, this.verticalAlignCenter, this.verticalAlignBottom].includes(newAlignment)) {
                this.json.verticalAlignment = newAlignment;
            }
        }

    }
</script>


<style scoped>
  .icon-center {
    vertical-align: middle;
  }

  .active {
    background-color: black;
  }
</style>
