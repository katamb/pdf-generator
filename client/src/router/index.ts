import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '@/views/Home.vue';
import EditPdf from '@/views/EditPdf.vue';
import Login from '@/views/Login.vue';
import {isUndefinedOrNull} from '@/util';
import jwtDecode from 'jwt-decode';

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
        path: '/edit-pdf/:template/:language/:id',
        name: 'EditPdf',
        component: EditPdf
    }
];

const router = new VueRouter({
    routes
});


function isValidAuthorization(jwt: any): boolean {
    const decodedJwt: any = jwtDecode(jwt);
    return jwt && (Date.now() < decodedJwt.exp * 1000);
}

function removeJwtIfExists(jwt: any): void {
    if (jwt) {
        localStorage.removeItem('Authorization');
    }
}
router.beforeEach(async (to, from, next) => {
    const publicPages = ['/'];
    const authRequired = !publicPages.includes(to.path);
    const jwt = localStorage.getItem('Authorization');
    if ((authRequired && isUndefinedOrNull(jwt)) || (authRequired && !isValidAuthorization(jwt))) {
        removeJwtIfExists(jwt);
        return next('/');
    }

    return next();
});

export default router;
