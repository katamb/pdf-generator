<template>
  <div class="background">
    <b-container>
      <b-row class="justify-content-center">

        <b-col cols="12" md="auto">
          <b-card bg-variant="light" title="PDF generator" class="text-center card-margin">
            <b-card-text>Click the button below to log in via Google and continue to the application.</b-card-text>
            <b-button @click="loginRedirect" variant="outline-primary" class="my-3">
              <img alt="G" src="./../assets/btn_google.svg"/> Log in with Google
            </b-button>
          </b-card>
        </b-col>

      </b-row>
    </b-container>
  </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import {BACKEND_URL} from '@/constants';

    @Component
    export default class Login extends Vue {
        loginRedirect() {
            window.location.href = BACKEND_URL + "/api/v1/oauth-login";
        }
    };
</script>

<style scoped>
  .background {
    min-height: 100vh;
    min-width: 100vw;
    background-image: linear-gradient(to bottom right, rgb(97, 98, 98) 24%, rgb(31, 30, 31) 90%);
  }

  .card-margin {
    margin-top: 20vh;
  }
</style>
