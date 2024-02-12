<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";

const categoryId = ref(0);
const quizId = ref(0);
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
      .get(`https://i10a609.p.ssafy.io/api/quiz/${categoryId.value}`)
      .then(function (response) {
        response.data.data.quizList.forEach((element) => {
          if (element.quizId == quizId.value) {
            quizDes.value = element.quiz;
          }
        });
      });
  } catch (error) {
    console.error(error);
  }
});
</script>

<template>
  <div>
    <h1>퀴즈~~~~</h1>
    <h2>{{ quizDes }}</h2>
  </div>
</template>

<style scoped lang="scss"></style>
