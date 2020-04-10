import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    googleAuth2: null
  },
  getters: {
    getGoogleOauth: (state) => {
      return state.googleAuth2;
    }
  },
  mutations: {
    setGoogleOauth: (state, newValue) => {
      state.googleAuth2 = newValue;
    }
  },
  actions: {
    setGoogleOauth: (context, newValue) => {
      context.commit('setGoogleOauth', newValue);
    }
  },
  modules: {}
});
