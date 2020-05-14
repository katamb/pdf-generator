import Vue from 'vue';
import VueRouter from 'vue-router';
import jwtDecode from 'jwt-decode';
import Home from '@/views/Home.vue';
import EditPdf from '@/views/EditPdf.vue';
import Login from '@/views/Login.vue';
import { AUTHORIZATION } from '@/scripts/constants';
import { removeCredentialsFromLocalStorage } from '@/scripts/util';
import NewTemplate from '@/views/NewTemplate.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/new-template',
    name: 'NewTemplate',
    component: NewTemplate
  },
  {
    path: '/edit-pdf/:template/:language/:id',
    name: 'EditPdf',
    component: EditPdf
  }
];

const router = new VueRouter({
  routes
});

function isValidAuthorization(jwt: string): boolean {
  const decodedJwt: any = jwtDecode(jwt.substring(7));
  return Date.now() <= decodedJwt.exp * 1000;
}

router.beforeEach(async (to, from, next) => {
  const publicPages = ['/'];
  const authRequired = !publicPages.includes(to.path);
  const jwt: string | null = localStorage.getItem(AUTHORIZATION);

  if (authRequired && jwt === null) {
    return next('/');
  }

  if (authRequired && jwt !== null && !isValidAuthorization(jwt)) {
    removeCredentialsFromLocalStorage();
    return next('/');
  }

  return next();
});

export default router;
