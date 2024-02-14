<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";

const categoryId = ref(0);
const quizId = ref(0);
const quizAns = ref("");
const quizDes = ref("");

// URL 파싱 함수
function getQueryParam(name) {
  const urlSearchParams = new URLSearchParams(window.location.search);
  return urlSearchParams.get(name);
}

onMounted(async () => {
  categoryId.value = getQueryParam("categoryId");
  quizId.value = getQueryParam("quizId");
  try {
    await axios
      .get(
        `https://i10a609.p.ssafy.io/api/mypage/incorrect-note/info/${quizId.value}`
      )
      .then(function (response) {
        quizAns.value = response.data.data.answer;
        quizDes.value = response.data.data.quiz;
      });
  } catch (error) {
    console.error(error);
  }
});
</script>

<template>
  <div class="incorrect-popup">
    <h1>오답노트</h1>
    <br />
    <h2>{{ quizDes }}</h2>
    <br />
    <h2>{{ quizAns }}</h2>
  </div>
</template>

<style scoped lang="scss">
.incorrect-popup {
  text-align: center;
}
</style>
