<script setup>
import { ref } from "vue";
// Props와 Emits를 정의합니다.
const props = defineProps(["startQuiz"]);
const emit = defineEmits(["startQuiz"]);

// Start Quiz 버튼 클릭 시 실행되는 함수
const startQuiz = (categoryId) => {
  // @startQuiz 이벤트를 발생시키면서 인자 두개를 넘긴다.
  emit("startQuiz", "quiz", categoryId);
};

const categoryList = ref([
  { category: "데이터베이스" },
  { category: "네트워크" },
  { category: "자료구조" },
  { category: "알고리즘" },
  { category: "컴퓨터구조" },
  { category: "운영체제" },
]);
</script>

<template>
  <div class="quiz-content">
    <div>
      <h1>퀴즈 주의사항</h1>
    </div>
    <div class="tutorial-content">
      <ul>
        <li>퀴즈를 마지막까지 풀지 않고 종료할 경우 결과가 반영되지 않습니다.</li>
        <li>각 문제당 10점을 획득 할 수 있습니다.</li>
        <li>힌트 사용 후 정답을 맞출 경우 3점만 부여됩니다.</li>
        <li>힌트는 사용을 취소할 수 없습니다.</li>
        <li>취소를 누를 시 현재 문제에서 입력한 정답이 모두 삭제됩니다.</li>
        <li>뒤로 돌아갈 수 없습니다.</li>
        <li>제출 시 각 문제의 정답 여부를 알 수 있습니다.</li>
      </ul>
    </div>
  </div>
  <div class="quiz-button-container">
    <div v-for="(category, index) in categoryList" :key="category">
      <button class="menu-button" @click="startQuiz(index + 1)">
        {{ category.category }}
      </button>
    </div>
  </div>
</template>

<style scoped lang="scss">
// url이 아닌 바로 이렇게 직접적으로 써야 변수를 끌어다 쓸 수 있다.
@import "@/assets/style/main.scss";
@import "@/assets/style/quiz.scss";

h1 {
  text-align: center;
}

li {
  margin-top: 4%;
}

.quiz-button-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  height: 45vh;
  margin-top: 32vh;

  button {
    height: 90px;
    margin: 30px 0 30px 0;
  }
}
</style>
