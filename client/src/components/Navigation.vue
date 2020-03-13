<template>
  <div>
    <b-navbar type="dark" variant="dark">

      <b-navbar-nav>
        <b-navbar-brand @click="routeHome" class="link">
          <i class="material-icons md-48 icon-center">home</i> Home
        </b-navbar-brand>
      </b-navbar-nav>

      <b-navbar-nav class="ml-auto">
        <b-nav-item href="#">email</b-nav-item>
        <b-nav-item @click="logout">Log out</b-nav-item>
      </b-navbar-nav>

    </b-navbar>
  </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import router from "@/router";
    import {getRequest} from "@/requests";

    @Component
    export default class Navigation extends Vue {
        routeHome(): void {
            router.push({path: `/home`});
        }
        logout(): void {
            getRequest("/logout")
                .then(() => router.push({path: `/`}));
        }
    };
</script>

<style scoped>
  .icon-center {
    vertical-align: middle;
  }

  .link {
    cursor: pointer;
  }
</style>