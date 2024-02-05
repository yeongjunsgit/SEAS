<script setup>
import { ref, onMounted } from "vue";

const category = ref("");
const cardNum = ref(0);

// URL 파싱 함수
function getQueryParam(name) {
  const urlSearchParams = new URLSearchParams(window.location.search);
  return urlSearchParams.get(name);
}

onMounted(() => {
  category.value = getQueryParam("category");
  cardNum.value = getQueryParam("cardNum");
});
</script>

<template>
  <div>
    <h1>날좀보소!!!!!!!!!</h1>
    <h2>{{ category }} : {{ cardNum }}</h2>
    <h3>이제 위 cardNum 갖고 axios 열심히하면댐</h3>
  </div>
</template>

<style scoped lang="scss"></style>
