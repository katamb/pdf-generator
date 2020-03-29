<template>
  <div class="background">
    <b-container>
      <b-row class="justify-content-center">

        <b-col cols="12" md="auto">
          <b-card bg-variant="light" title="PDF generator" class="text-center card-margin">
            <b-card-text>Click the button below to log in via Google and continue to the application.</b-card-text>
            <b-button ref="signInBtn" variant="outline-primary" class="my-3">
              <GoogleLogInLogo/>
              Sign in with Google
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
    import GoogleLogInLogo from '@/assets/google_log_in_logo.svg';
    import {getRequest, postRequest} from "@/requests";
    import router from "@/router";

    @Component({
        components: {
            GoogleLogInLogo
        }
    })
    export default class Login extends Vue {
        loginRedirect() {
            window.location.href = BACKEND_URL + "/api/v1/oauth-login";
            // getRequest("/api/v1/oauth-login-redirect")
            //     .then((res) => console.log(res))
        }

        mounted(): void {
            this.$nextTick(() => this.loadLoginButton());
        }

        loadLoginButton(): void {
            window.gapi.load("auth2", () => {

                const auth2 = window.gapi.auth2.init({
                    // eslint-disable-next-line @typescript-eslint/camelcase
                    client_id: '831887232071-k6dmabuu48v05rn4h5h12evh70r1m5tj.apps.googleusercontent.com',
                    // eslint-disable-next-line @typescript-eslint/camelcase
                    cookie_policy: 'single_host_origin'
                });

                auth2.attachClickHandler(
                    this.$refs.signInBtn,
                    {},
                    googleUser => {
                        postRequest("/api/v1/oauth-login", {"jwt": googleUser.uc.id_token})
                            .then(res => res.json())
                            .then(json => localStorage.setItem('Authorization', 'Bearer ' + json.jwt))
                            .then(() => router.push({path: '/home'}))
                    },
                    error => {
                        alert(error);
                    }
                );

            });
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
