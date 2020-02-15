import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import EditPdf from "@/views/EditPdf.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/edit-pdf/:template/:language",
    name: "EditPdf",
    component: EditPdf
  }
];

const router = new VueRouter({
  routes
});

export default router;
