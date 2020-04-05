<template>
  <div class="background">
    <b-container>
      <b-row class="justify-content-center">
        <b-col cols="12" md="auto">
          <b-card
            bg-variant="light"
            title="PDF generator"
            class="text-center card-margin"
          >
            <b-card-text>
              Click the button below to log in via Google and continue to the
              application.
            </b-card-text>
            <b-button
              ref="signInBtn"
              @click="logIn"
              variant="outline-primary"
              class="my-3"
            >
              <GoogleLogInLogo />
              Sign in with Google
            </b-button>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import GoogleLogInLogo from "@/assets/google_log_in_logo.svg";
import router from "@/router";
import { getRequest } from "@/requests";

@Component({
  components: {
    GoogleLogInLogo
  }
})
export default class Login extends Vue {
  logIn(): void {
    this.$store.state.googleAuth2
      .signIn()
      .then((token: any) =>
        localStorage.setItem("Authorization", "Bearer " + token.uc.id_token)
      )
      .then(() =>
        getRequest("/api/v1/user/roles")
          .then(response => response.json())
          .then((roles: any) => localStorage.setItem("Roles", roles))
      )
      .then(() => router.push({ path: "/home" }));
  }
}
</script>

<style scoped>
.background {
  min-height: 100vh;
  min-width: 100vw;
  background-image: linear-gradient(
    to bottom right,
    rgb(97, 98, 98) 24%,
    rgb(31, 30, 31) 90%
  );
}

.card-margin {
  margin-top: 20vh;
}
</style>
