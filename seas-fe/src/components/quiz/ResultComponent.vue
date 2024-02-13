<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { getResult, getResultRank } from "@/api/quiz.js";

const router = useRouter();

// 퀴즈 결과 불러오기 ===========================================
// 결과 저장 변수
const resultInfo = ref({
    answerCount: 0,
    wrongCount: 0,
    usedHintCount: 0,
    earnedScore: 0,
});

getResult(
    ({ data }) => {
        resultInfo.value = data.data;
        console.log(resultInfo.value);
    },
    (error) => {
        console.log(error);
    }
);

// Props와 Emits를 정의합니다.
const props = defineProps(["currentUserRank"]);
const emit = defineEmits(["showTutorial"]);

// 이전 티어와 퀴즈 후 티어 결과 비교 ===========================
const isRankChanged = ref();
getResultRank(
    props.currentUserRank,
    ({ data }) => {
        isRankChanged.value = data.data;
        console.log(isRankChanged.value);
    },
    (error) => {
        console.log(error);
    }
);

// 단순 라우팅 =================================================
// showTutorial 버튼 클릭 시 실행되는 함수
const goTutorial = () => {
    // @showTutorial 이벤트를 발생시킵니다.
    emit("showTutorial", "tutorial");
};

const goHome = () => {
    router.push({ path: "/" });
};
const goMypage = () => {
    router.push({ path: "/mypage" });
};
</script>

<template>
    <div class="quiz-content">
        <div>
            <h1>결과</h1>
        </div>
        <div>
            <h2>맞춘 문제 수 : {{ resultInfo.answerCount }}</h2>
            <h2>틀린 문제 수 : {{ resultInfo.wrongCount }}</h2>
            <h2>힌트 사용 횟수 : {{ resultInfo.usedHintCount }}</h2>
            <h2>획득한 골드 : {{ resultInfo.earnedScore }}</h2>
        </div>
        <div>
            <h2 v-if="resultInfo.earnedScore >= 80">
                당신은 훌륭한 해적이군요!
            </h2>
            <h2 v-else-if="resultInfo.earnedScore >= 50">
                조금은 해적 같군요!
            </h2>
            <h2 v-else>이제 막 해적질을 시작한 초보인가요?</h2>
        </div>
    </div>
    <div class="quiz-button-container">
        <button class="menu-button" @click="goTutorial">한번 더</button>
        <button class="menu-button" @click="goHome">메인으로</button>
        <button class="menu-button" @click="goMypage">마이페이지로</button>
    </div>
</template>

<style scoped lang="scss">
// url이 아닌 바로 이렇게 직접적으로 써야 변수를 끌어다 쓸 수 있다.
@import "@/assets/style/main.scss";
@import "@/assets/style/quiz.scss";

.quiz-content {
    text-align: center;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
}
</style>
