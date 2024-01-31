<script setup>
import { ref } from "vue";

import TutorialComponent from "@/components/quiz/TutorialComponent.vue";
import QuizComponent from "@/components/quiz/QuizComponent.vue";
import ResultComponent from "@/components/quiz/ResultComponent.vue";

// 컴포넌트 변수
const currentComponent = ref("tutorial");
// 컴포넌트 변경 메소드
const changeComponent = (component) => {
    currentComponent.value = component;
};

// 이미지와 비디오의 경로 ================================================
const imageUrl = "https://d2qkxc1ity7pm2.cloudfront.net/images/quiz_bg.png";

// 이미지와 비디오를 사전 로딩하는 함수
onMounted(() => {
    preloadResource(imageUrl, "image");
});

function preloadResource(url, type) {
    const link = document.createElement("link");
    link.rel = "preload";
    link.as = type;
    link.href = url;
    document.head.appendChild(link);
}
</script>

<template>
    <div class="quiz-container text-font">
        <!-- TutorialComponent -->
        <TutorialComponent
            v-if="currentComponent === 'tutorial'"
            @startQuiz="changeComponent('quiz')"
        />

        <!-- QuizComponent -->
        <QuizComponent
            v-else-if="currentComponent === 'quiz'"
            @showResult="changeComponent('result')"
        />

        <!-- ResultComponent -->
        <ResultComponent
            v-else-if="currentComponent === 'result'"
            @showTutorial="changeComponent('tutorial')"
        />
    </div>
</template>

<style scoped lang="scss">
@import "@/assets/style/main.scss";

.quiz-container {
    position: absolute;
    background-image: url($url-path + "images/map.jpg");
    background-size: cover;
    height: 100%;
    width: 100%;
    display: flex;
    justify-content: space-evenly;
    font-size: larger;

    .quiz-content {
        width: 50%;
        height: 80%;
        margin: 11vh 0 0 10%;
        background-image: url($url-path + "images/quiz_bg.png");
        background-size: 100% 100%;
        background-position: center;
        padding: 5% 7% 7% 7%;

        .question-container {
            width: 100%;
            min-height: 200px;
            padding-top: 5%;
        }

        .hint-container {
            height: 50px;
        }

        .result-container {
            padding-top: 3%;
            height: 50px;
            text-align: center;
        }

        input {
            width: 100%;
            height: 100%;
            padding: 7% 10% 7% 10%;
            border: 4px double $primary-color;
        }
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

        .quiz-number {
            background-image: url($url-path + "images/button_background.png");
            background-size: 100% 12vh;
            background-position: center;
            min-width: 12vw;
            min-height: 12vh;
            text-align: center;

            p {
                margin: 0;
                padding-top: 2vh;
            }
        }

        button {
            width: 12vw;
            padding: 3vh 2vh 2vh 2vh;
            font-size: xx-large;
        }
    }
    .isClicked {
        transition-duration: 0.5s;
        background-color: $selected-color;
        color: white;
    }
}
</style>
