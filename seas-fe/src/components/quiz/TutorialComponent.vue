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
                <li>퀴즈를 풀때마다 결과가 반영됩니다.</li>
                <li>각 문제당 10점을 획득 할 수 있습니다.</li>
                <li>힌트 사용 후 정답을 맞출 경우 3점만 부여됩니다.</li>
                <li>힌트는 사용을 취소할 수 없습니다.</li>
                <li>
                    취소를 누를 시 현재 문제에서 입력한 정답이 모두 삭제됩니다.
                </li>
                <li>뒤로 돌아갈 수 없습니다.</li>
                <li>제출 시 각 문제의 정답 여부를 알 수 있습니다.</li>
                <li>동일한 문제는 하루에 한 번만 점수가 올라갑니다.</li>
            </ul>
        </div>
    </div>
    <div class="quiz-button-container">
        <button
            v-for="(category, index) in categoryList"
            :key="category"
            class="menu-button"
            @click="startQuiz(index + 1)"
        >
            {{ category.category }}
        </button>
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
    justify-content: center;
    min-height: 400px;
    height: 70vh;
    padding-top: 5vh;
    margin-left: 2vw;

    button {
        height: 12vh;
        margin: 3vh 4vw 3vh 0;
        padding: 2% 0 0 0;
        background-color: rgba($color: #ffffff, $alpha: 0.2);
    }
}
</style>
