<template>
  <div id="app">
    <ErrorModal />
    <router-view />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import ErrorModal from '@/components/ErrorModal.vue';
import { CLIENT_ID } from './scripts/constants';

@Component({
  components: {
    ErrorModal
  }
})
export default class Login extends Vue {
  mounted(): void {
    this.$nextTick(() => this.loadLoginButton());
  }

  loadLoginButton(): void {
    window.gapi.load('auth2', () => {
      window.gapi.auth2
        .init({
          client_id: CLIENT_ID,
          cookie_policy: 'single_host_origin'
        })
        .then((auth) => {
          this.$store.dispatch('setGoogleOauth', auth);
        });
    });
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
