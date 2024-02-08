<script setup>
import { ref, watch } from "vue";
import { getQuizList, getHint, sendAnswer } from "@/api/quiz.js";

// Props와 Emits를 정의합니다.
const props = defineProps(["showResult", "quizCategory"]);
const emit = defineEmits(["showResult"]);

// 퀴즈가 끝날 시 실행되는 함수 ==================================================
const showResult = () => {
    // @showResult 이벤트를 발생시킨다.
    emit("showResult", "result");
};

// 전체 퀴즈 리스트 받는 함수 ===================================================
const getInitQuiz = () => {
    // axios함수를 통해 데이터를 불러온다.
    getQuizList(
        props.quizCategory,
        ({ data }) => {
            content.value = data.data.quizList; // 퀴즈 문제를 저장
            console.log(content.value);
        },
        (error) => {
            console.log(error);
        }
    );
};

// 문제 가져오기
getInitQuiz();

// 문제 정보
const content = ref();

// 데이터를 받았는지 여부를 판단할 변수 선언
const isQuizData = ref(false);

// watch를 이용해서 데이터가 들어오면 위의 변수를 true 값으로 바꿈
// 따라서 v-if에서 위의 변수를 토대로 판단하여 데이터가 들어와야만 변경됨
const watchRankData = watch(content, () => {
    if (content.value.length > 0) {
        isQuizData.value = true;
        currentQuestion.value = content.value[currentIndex.value];
        console.log(isQuizData.value);
    }
});

// 문제 인덱스 및 문제 표시 =======================================================
// 현재 표시되는 문제의 인덱스를 추적
const currentIndex = ref(0);

// 현재 인덱스의 문제에 접근하는 computed 속성
const currentQuestion = ref({
    quizId: 0,
    quiz: "",
});

// 정답 체크 ====================================================================
// 정답 여부 변수
const isCorrect = ref(false);
const answerShown = ref(false);

// 다음 문제를 표시하는 메소드
const submitAnswer = () => {
    // 정답 제출
    checkAnswer();
    // 정답인지 보여준 후 1초 후 다음 문제 넘어감
    delayResult();
};

const checkAnswer = () => {
    sendAnswer(
        currentQuestion.value.quizId,
        answerInput.value,
        ({ data }) => {
            isCorrect.value = data.data.result; // 정답 여부를 저장
            console.log(isCorrect.value);
        },
        (error) => {
            console.log(error);
        }
    );

    // isCorrect.value = answerInput.value != null ? true : false; // 임시 정답 처리 되는지 확인 코드
    // 얻어낸 bool값으로 해당 문제 맞았는지 틀렸는지 알려주기
    answerShown.value = true;
};

// 정답 여부 시간차 표시 및 넘기기 ==============================================
const delayResult = () => {
    setTimeout(() => {
        // 제출 후 인풋 제거
        clearInput();

        // 인덱스를 증가시켜 다음 문제로 이동
        currentIndex.value += 1;

        // 인덱스가 배열의 범위를 초과하면 결과 처리 후 결과 페이지로 이동
        if (currentIndex.value >= content.value.length) {
            showResult();
        }

        // 현재 인덱스의 문제 업데이트
        hintShown.value = false;
        currentQuestion.value = content.value[currentIndex.value];
        answerShown.value = null;
    }, 1000); // Convert seconds to milliseconds
};

// 힌트 표시 부분 ================================================================
const hintShown = ref(false);
const hint = ref("1");
const showHint = () => {
    hintShown.value = true;
    getHint(
        currentQuestion.value.quizId,
        ({ data }) => {
            hint.value = data.data.hint; // 정답 여부를 저장
            console.log(hint.value);
        },
        (error) => {
            console.log(error);
        }
    );
};

// 정답 인풋 =====================================================================
const answerInput = ref();

// 인풋 초기화
const clearInput = () => {
    answerInput.value = null;
};
</script>

<template>
    <div class="quiz-content" v-if="isQuizData">
        <div class="question-container">
            <h2>Q. {{ currentQuestion.quiz }}</h2>
        </div>
        <div class="hint-container">
            <!-- 힌트 및 정답 여부 출력 -->
            <h3 v-if="hintShown">
                {{ hint ? `힌트: ${hint}` : "존재하는 힌트가 없습니다." }}
            </h3>
        </div>
        <div>
            <input
                v-model="answerInput"
                type="text"
                id="answer"
                autocomplete="off"
                placeholder="정답을 입력하시오."
            />
        </div>
        <div class="result-container">
            <h3
                v-if="answerShown"
                class="input-result"
                :class="{ correct: isCorrect, wrong: !isCorrect }"
            >
                {{ isCorrect ? "맞았습니다" : "틀렸습니다" }}
            </h3>
        </div>
    </div>
    <div class="quiz-button-container" v-if="isQuizData">
        <div class="quiz-number text-font">
            <p>{{ currentIndex + 1 }} / {{ content.length }}</p>
        </div>
        <button
            class="menu-button"
            @click="showHint"
            :class="{ isClicked: hintShown }"
        >
            힌 트
        </button>
        <button class="menu-button" @click="clearInput">취 소</button>
        <button class="menu-button" @click="submitAnswer">제 출</button>
    </div>
</template>

<style scoped lang="scss">
@import "@/assets/style/main.scss";
@import "@/assets/style/quiz.scss";

.question-container {
    width: 100%;
    padding-top: 5%;
    height: 50%;
}

.hint-container {
    height: 10%;
}
.result-container {
    height: 20%;
    display: flex;
    justify-content: center;
    text-align: center;
    font-size: 140%;

    .input-result {
        border: double;
        border-width: 6px;
        padding-top: 1%;
        width: 60%;
        display: flex;
        flex-direction: column;
        justify-content: center;

        transform: rotate(-10deg); /* 45도로 요소를 회전 */
    }

    .correct {
        color: rgb(0, 147, 2);
    }
    .wrong {
        color: red;
    }
}

input {
    width: 100%;
    height: 30%;
    padding: 7% 10% 6% 10%;
    border: 4px double $primary-color;
    font-size: larger;

    &:focus {
        outline: none;
    }
}

.quiz-number {
    background-image: url($url-path + "images/button_background.png");
    background-size: 100% 12vh;
    background-position: center;
    min-width: 12vw;
    min-height: 12vh;
    text-align: center;
    font-size: 4vw;
    display: flex;
    flex-direction: column;
    justify-content: center;

    p {
        margin: 0;
        padding-top: 2vh;
    }
}
.isClicked {
    transition-duration: 0.5s;
    background-color: $selected-color;
    color: white;
}
</style>
