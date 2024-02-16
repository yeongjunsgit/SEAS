<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";

const categoryId = ref(0);
const quizId = ref(0);
const quizAns = ref("");
const quizDes = ref("");
const user_access_token = ref("");

// URL 파싱 함수
function getQueryParam(name) {
  const urlSearchParams = new URLSearchParams(window.location.search);
  return urlSearchParams.get(name);
}

onMounted(async () => {
  categoryId.value = getQueryParam("categoryId");
  quizId.value = getQueryParam("quizId");
  user_access_token.value = getQueryParam("auth");
  try {
    await axios
      .get(
        `https://i10a609.p.ssafy.io/api/mypage/incorrect-note/info/${quizId.value}`,
        {
          headers: {
            Authorization: `Bearer ${user_access_token.value}`,
            "Content-Type": "application/json",
          },
        }
      )
      .then(function (response) {
        console.log(response.data.data);
        quizAns.value = response.data.data.answer;
        quizDes.value = response.data.data.quiz;
      });
  } catch (error) {
    console.error(error);
  }
});
</script>

<template>
  <div class="background-img">
    <div class="incorrect-popup">
      <h1>오답노트</h1>

      <h2>{{ quizDes }}</h2>

      <h2>{{ quizAns }}</h2>
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
.incorrect-popup {
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
