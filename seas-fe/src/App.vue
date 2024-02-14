<script setup>
import { onBeforeMount } from "vue";
import { RouterView } from "vue-router";
import HeaderComponent from "./components/commons/HeaderComponent.vue";

var isChecked = false;

onBeforeMount(() => {
  if (
    (document.location.pathname === "/mypage/popupcard") |
    (document.location.pathname === "/mypage/popupquiz")
  ) {
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

/* 수평 스크롤 바 감추기 */
body::-webkit-scrollbar {
  display: none;
}

/* 수직 스크롤 바 감추기 */
body::-webkit-scrollbar-thumb {
  display: none;
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
