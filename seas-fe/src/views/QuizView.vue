<script setup>
import { ref } from "vue";

// 문제 정보
const content = ref([
    {
        question: "프로그램에서 명령어를 해석하고 실행하는 핵심 부품은?",
        hint: "컴퓨터의 뇌",
    },
    {
        question: "컴퓨터에서 데이터를 일시적으로 저장하는 기능을 하는 부품은?",
        hint: "",
    },
    {
        question:
            "컴퓨터에서 데이터를 일시적으로 저장하는 기능을 하는 부품은? 그냥 길게 늘여봐아아아ㅏㅏㅏㅏㅏㅏㅏㅏㅏ",
        hint: "",
    },
]);

// 할 일
// invisible 말고 아얘 f12로 안보이게 하기
// 정답인지를 보여주고 1초 뒤에 다음 문제로 넘어가게 하기

// 문제 인덱스 및 문제 표시 =======================================================
// 현재 표시되는 문제의 인덱스를 추적
const currentIndex = ref(0);

// 현재 인덱스의 문제에 접근하는 computed 속성
const currentQuestion = ref(content.value[currentIndex.value]);

// 다음 문제를 표시하는 메소드
const submitAnswer = () => {
    // 정답 제출
    checkAnswer();
    // 정답인지 보여준 후 1초 후 다음 문제 넘어감
    delayResult();
};

// 정답 여부 시간차 표시 및 넘기기
const delayResult = () => {
    setTimeout(() => {
        // 제출 후 인풋 제거
        clearInput();

        // 인덱스를 증가시켜 다음 문제로 이동
        currentIndex.value += 1;

        // 인덱스가 배열의 범위를 초과하면 결과 페이지를 보여줌 (현재는 처음으로 돌아감)
        if (currentIndex.value >= content.value.length) {
            currentIndex.value = 0;
        }

        // 현재 인덱스의 문제 업데이트
        hintShown.value = false;
        currentQuestion.value = content.value[currentIndex.value];
        answerShown.value = null;
    }, 1000); // Convert seconds to milliseconds
};

// 정답 체크 ====================================================================
// 정답 여부 변수
const isCorrect = ref(false);
const correctMessage = ref(null);
const answerShown = ref(true);
const answerColor = ref();

const checkAnswer = () => {
    // 백으로 input 값 보내주기 (힌트 사용 여부 포함)
    // isCorrect = sendAnswer();
    isCorrect.value = answerInput.value != null ? true : false; // 임시 정답 처리 되는지 확인 코드
    // 얻어낸 bool값으로 해당 문제 맞았는지 틀렸는지 알려주기
    answerShown.value = true;
    correctMessage.value = isCorrect.value ? "맞았습니다!" : "틀렸습니다!";
    answerColor.value = isCorrect.value ? "green" : "red";
};

// 백으로 입력된 정답 전송
const sendAnswer = () => {};
// 힌트 표시 부분 ================================================================
const hintShown = ref(false);
const showHint = () => {
    hintShown.value = true;
    console.log(hintShown.value);
};

// 정답 인풋 =====================================================================
const answerInput = ref();

// 인풋 초기화
const clearInput = () => {
    answerInput.value = null;
};
</script>

<template>
    <div class="quiz-container text-font">
        <div class="quiz-content">
            <div class="question-container">
                <h2>Q. {{ currentQuestion.question }}</h2>
            </div>
            <div class="hint-container">
                <!-- 힌트 및 정답 여부 출력 -->
                <h3 v-if="hintShown">
                    {{
                        currentQuestion.hint
                            ? `힌트: ${currentQuestion.hint}`
                            : "존재하는 힌트가 없습니다."
                    }}
                </h3>
            </div>
            <div>
                <input
                    v-model="answerInput"
                    type="text"
                    id="answer"
                    placeholder="정답을 입력하시오."
                />
            </div>
            <div class="result-container">
                <h3
                    :style="{
                        visibility: answerShown ? 'visible' : 'hidden',
                        color: answerColor,
                    }"
                >
                    {{ correctMessage }}
                </h3>
            </div>
        </div>
        <div class="quiz-button-container">
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
