import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import EditPdf from '@/views/EditPdf.vue';
import Login from '@/views/Login.vue';
import {getRequest} from "@/requests";

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

router.beforeEach(async (to, from, next) => {
    // redirect to login page if not logged in and trying to access a restricted page
    const publicPages = ['/'];
    const authRequired = !publicPages.includes(to.path);

    if (authRequired) {
        const response = await getRequest('/api/v1/user/email');
        if (response.status === 401) {
            return next('/');
        }
    }
    return next();
});

export default router;