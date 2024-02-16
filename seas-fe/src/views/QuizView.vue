<script setup>
import { ref, onMounted } from "vue";
import { getInitRank } from "@/api/quiz.js";

import TutorialComponent from "@/components/quiz/TutorialComponent.vue";
import QuizComponent from "@/components/quiz/QuizComponent.vue";
import ResultComponent from "@/components/quiz/ResultComponent.vue";

// 컴포넌트 변수
const currentComponent = ref("tutorial");
// 카테고리 변수
const categoryId = ref(0);

// 컴포넌트 변경 메소드
const changeComponent = (component, index) => {
    console.log(index);
    categoryId.value = index;
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

const userRank = ref();
getInitRank(
    ({ data }) => {
        userRank.value = data.data.tier;
        console.log(userRank.value);
    },
    (error) => {
        console.log(error);
    }
);
</script>

<template>
    <div class="quiz-container text-font">
        <!-- TutorialComponent -->
        <TutorialComponent
            v-if="currentComponent === 'tutorial'"
            @startQuiz="changeComponent"
        />

        <!-- QuizComponent -->
        <QuizComponent
            v-else-if="currentComponent === 'quiz'"
            @showResult="changeComponent('result')"
            :quizCategory="categoryId"
        />

        <!-- ResultComponent -->
        <ResultComponent
            v-else-if="currentComponent === 'result'"
            @showTutorial="changeComponent('tutorial')"
            :currentUserRank="userRank"
        />
    </div>
</template>

<style scoped lang="scss">
@import "@/assets/style/main.scss";

.quiz-container {
    position: absolute;
    background-image: url($url-path + "images/map.jpg");
    background-size: cover;
    min-height: 600px;
    height: 100%;
    width: 100%;
    display: flex;
    justify-content: space-evenly;
    font-size: larger;
    padding-top: 2%;
}
</style>
