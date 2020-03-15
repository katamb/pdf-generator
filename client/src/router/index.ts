import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import EditPdf from '@/views/EditPdf.vue';
import Login from '@/views/Login.vue';

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
    if (from === to) {
        return;
    }

    return next();
});

export default router;
