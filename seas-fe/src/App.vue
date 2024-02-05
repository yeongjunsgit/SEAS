<script setup>
import { onBeforeMount } from "vue";
import { RouterView } from "vue-router";
import HeaderComponent from "./components/commons/HeaderComponent.vue";

var isChecked = false;

onBeforeMount(() => {
  if (document.location.pathname === "/mypage/popup") {
    isChecked = true;
  }
});
</script>

<template>
  <header>
    <HeaderComponent v-if="!isChecked" />
  </header>
  <div class="main_conatiner">
    <RouterView />
  </div>
</template>

<style lang="scss">
* {
  margin: 0px;
  padding: 0px;
}
a {
  text-decoration: none;
}

header {
  position: absolute;
  z-index: 1;
  width: 100%;
}

.main_container {
  position: relative;
  z-index: 2;
}
</style>
