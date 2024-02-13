<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";

const category = ref("");
const cardNum = ref(0);
const cardKwd = ref("");
const cardDesArr = ref([]);

// URL 파싱 함수
function getQueryParam(name) {
  const urlSearchParams = new URLSearchParams(window.location.search);
  return urlSearchParams.get(name);
}

onMounted(async () => {
  category.value = getQueryParam("category");
  cardNum.value = getQueryParam("cardNum");
  try {
    await axios
      .get(`https://i10a609.p.ssafy.io/api/flashcard/${cardNum.value}`)
      .then(function (response) {
        cardKwd.value = response.data.data.keyword;
        cardDesArr.value = response.data.data.contents;
      });
  } catch (error) {
    console.error(error);
  }
});
</script>

<template>
  <div class="favorite-popup">
    <h1>나의 CS 카드</h1>
    <br />
    <h2>{{ cardKwd }}</h2>
    <br />
    <h3 v-for="cardDes in cardDesArr">{{ cardDes }}</h3>
  </div>
</template>

<style scoped lang="scss">
.favorite-popup {
  text-align: center;
  background-image: url("@/assets/images/CardPage_BG_HighQuality.png");
  background-size: cover;
  background-repeat: no-repeat;
}
</style>
