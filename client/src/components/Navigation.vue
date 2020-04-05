<template>
  <div>
    <b-navbar type="dark" variant="dark">
      <b-navbar-nav>
        <b-navbar-brand @click="routeHome" class="link">
          <Home /> Home
        </b-navbar-brand>
      </b-navbar-nav>

      <b-navbar-nav class="ml-auto">
        <b-nav-item>{{ email }}</b-nav-item>
        <b-nav-item @click="logout">Log out</b-nav-item>
      </b-navbar-nav>
    </b-navbar>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import router from "@/router";
import Home from "@/assets/home.svg";
import jwtDecode from "jwt-decode";

@Component({
  components: {
    Home
  }
})
export default class Navigation extends Vue {
  email: string | null = null;

  mounted(): void {
    const jwt: string | null = localStorage.getItem('Authorization');
    if (jwt) {
      const decodedJwt: any = jwtDecode(jwt.substring(7));
      this.email = decodedJwt.email;
    }
  }

  routeHome(): void {
    const routeTo = "/home";
    if (this.$route.path !== routeTo) {
      router.push({ path: routeTo });
    }
  }

  logout(): void {
    this.$store.state.googleAuth2.signOut();
    this.$store.state.googleAuth2.disconnect();
    localStorage.removeItem("Authorization");
    router.push({ path: "/" });
  }
}
</script>

<style scoped>
.link {
  cursor: pointer;
}
</style>
