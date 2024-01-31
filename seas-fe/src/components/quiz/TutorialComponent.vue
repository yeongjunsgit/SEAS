<script setup>
// Props와 Emits를 정의합니다.
const props = defineProps(["startQuiz"]);
const emit = defineEmits(["startQuiz"]);

// Start Quiz 버튼 클릭 시 실행되는 함수
const startQuiz = () => {
    // @startQuiz 이벤트를 발생시킵니다.
    emit("startQuiz", "quiz");
};
</script>

<template>
    <div class="quiz-content">
        <div>
            <p>튜토리얼 내용</p>
        </div>
    </div>
    <div class="quiz-button-container">
        <button class="menu-button" @click="startQuiz">시 작</button>
    </div>
</template>

<style scoped lang="scss">
// url이 아닌 바로 이렇게 직접적으로 써야 변수를 끌어다 쓸 수 있다.
@import "@/assets/style/main.scss";

.quiz-content {
    width: 50%;
    height: 80%;
    margin: 11vh 0 0 10%;
    background-image: url($url-path + "images/quiz_bg.png");
    background-size: 100% 100%;
    background-position: center;
    padding: 5% 7% 7% 7%;
}
.quiz-button-container {
    width: 27%;
    height: 80%;
    margin: 11vh 10% 0 0;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-size: xxx-large;

    p {
        margin: 0;
        padding-top: 2vh;
    }

    button {
        width: 12vw;
        padding: 3vh 2vh 2vh 2vh;
        font-size: xx-large;
    }
}
</style>
