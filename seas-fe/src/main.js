import { createApp } from "vue";
import { createPinia } from "pinia";
import { localAxios } from "@/util/axios.js";

import App from "./App.vue";
import router from "./router";

// Vuetify
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import { useauthControllerStore } from "./stores/authController";

const vuetify = createVuetify({
  components,
  directives,
});

const pinia = createPinia();

const app = createApp(App);

app.use(pinia);
app.use(router);
app.use(vuetify);

const authControllerStore = useauthControllerStore();

app.provide("store", authControllerStore);

app.mount("#app");
