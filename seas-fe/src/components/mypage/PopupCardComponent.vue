<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";

const category = ref("");
const cardNum = ref(0);
const cardKwd = ref("");
const cardDesArr = ref([]);
const user_access_token = ref("");

// URL 파싱 함수
function getQueryParam(name) {
  const urlSearchParams = new URLSearchParams(window.location.search);
  return urlSearchParams.get(name);
}

onMounted(async () => {
  category.value = getQueryParam("category");
  cardNum.value = getQueryParam("cardNum");
  user_access_token.value = getQueryParam("auth");
  try {
    console.log(cardNum.value);
    await axios
      .get(`https://i10a609.p.ssafy.io/api/flashcard/${cardNum.value}`, {
        headers: {
          Authorization: `Bearer ${user_access_token.value}`,
          "Content-Type": "application/json",
        },
      })
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
  <div class="background-img">
    <div class="favorite-popup">
      <h1>나의 CS 카드</h1>
      <h2>{{ cardKwd }}</h2>
      <h3 class="des" v-for="cardDes in cardDesArr">{{ cardDes }}</h3>
    </div>
  </div>
</template>

<style scoped lang="scss">
.background-img {
  background-image: url("@/assets/images/CardPage_BG_HighQuality.png");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  height: 100vh;
}
.favorite-popup {
  text-align: center; /* 가운데 정렬 */
  color: white; /* 텍스트 색상 설정 */
  display: flex;
  flex-direction: column;
  padding-top: 5%;
  padding-inline: 200px;
}
h1,
h2 {
  margin-top: 50px;
  display: inline-block;
  align-self: center;
}
.des {
  margin-top: 20px;
}
</style>
