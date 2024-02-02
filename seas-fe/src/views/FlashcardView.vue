<script setup>
import backgroundImage from "@/assets/images/CardPage_BG_HighQuality.png";
import cardImage from "@/assets/images/card_removed.png";
import flashcardImage from "@/assets/images/cardPage.jpg";
import buttonBgImage from "@/assets/images/button_background.png";

import { RouterLink, RouterView } from "vue-router";
import HeaderComponent from "@/components/commons/HeaderComponent.vue";
import { ref, watch, computed } from "vue";
import { gsap } from "gsap";

import { useRouter } from "vue-router";
const router = useRouter();

const category = ref(null);
const keywordData = ref(null);
const commentData = ref(null);
const idx = ref(0);
const isReversed = ref(false);
const isUp = ref(false);
const isFront = ref(true);
const isInvisible = ref(false);

// 카드가 뒤집어지는 효과를 담당하는 함수
const reverseCard = function () {
  if (isUp.value === true) {
    isFront.value = !isFront.value;
    isReversed.value = !isReversed.value;
    isInvisible.value = !isInvisible.value;

    gsap.to(".flashcard", {
      duration: 1,
      rotationY: "+=180",
      onComplete: () => {
        isInvisible.value = !isInvisible.value;
      },
    });
  }
};

// 카드 넘길때 사라지고 다시 나타나는 애니메이션 구현
const fadeAwayNext = function () {
  const tl1 = gsap.timeline();
  tl1.to(".flashcard", {
    duration: 1,
    xPercent: -40,
    opacity: 0,
    onComplete: () => {
      isInvisible.value = !isInvisible.value;
      if (isReversed.value === true) {
        gsap.to(".flashcard", {
          delay: 1,
          duration: 0,
          rotationY: "-=180",
        });
        isReversed.value = false;
      }
    },
  });

  tl1.to(".flashcard", {
    onStart: () => {
      idx.value = idx.value += 1;
    },
    opacity: 100,
    xPercent: 25,
    duration: 0.5,
    delay: 1,
    onComplete: () => {
      isInvisible.value = !isInvisible.value;
    },
  });
};
// const fadeAwayNext = function () {
//   gsap.to(".flashcard", {
//     duration: 1,
//     xPercent: -40,
//     opacity: 0,
//     onComplete: () => {
//       if (isReversed.value === true) {
//         gsap.to(".flashcard", {
//           duration: 0.5,
//           rotationY: "+=180",
//         });
//         isReversed.value = false;
//       }
//     },
//   });

//   gsap.to(
//     ".flashcard",
//     {
//       opacity: 100,
//       xPercent: 25,
//       duration: 1,
//       onComplete: () => {
//         idx.value = idx.value += 1;
//       },
//     },
//     "+=1"
//   );
// };

const fadeAwayPre = function () {
  const tl1 = gsap.timeline();
  tl1.to(".flashcard", {
    duration: 1,
    xPercent: -40,
    opacity: 0,
    onComplete: () => {
      isInvisible.value = !isInvisible.value;
      if (isReversed.value === true) {
        gsap.to(".flashcard", {
          delay: 1,
          duration: 0,
          rotationY: "-=180",
        });
        isReversed.value = false;
      }
    },
  });

  tl1.to(".flashcard", {
    onStart: () => {
      if (idx.value > 0) {
        idx.value = idx.value -= 1;
      }
    },
    opacity: 100,
    xPercent: 25,
    duration: 0.5,
    delay: 1,
    onComplete: () => {
      isInvisible.value = !isInvisible.value;
    },
  });
};

// 좋아요 추가 기능 구현하기!
const addLike = function () {
  console.log("구현해주세요");
};

watch(idx, (newValue, oldValue) => {
  if (newValue === 10) {
    isOver.value = !isOver.value;
    idx.value = 0;
  }
});

// 버튼 출력을 다룰 변수 2개 생성
const isSelected = ref(false);
const isOver = ref(false);

// 버튼들에 출력할 내용들 생성
const buttonCategory = [
  "네트워크",
  "자료구조",
  "컴퓨터 구조",
  "운영체제",
  "알고리즘",
  "데이터베이스",
];

// 버튼 눌렀을 때 호출되는 함수들
const selectCategory = function (cate) {
  isSelected.value = !isSelected.value;
  isUp.value = !isUp.value;
  category.value = ref(cate);

  gsap.to(".flashcard", {
    duration: 1,
    xPercent: 30,
    scale: (1.5, 1.6),
    rotate: 0.2,
  });

  // 여기서 받은 카테고리를 axios로 보내자! 일단 임시로 아무거나 만들어서 쓰겠다
  keywordData.value = [
    "Primary Key",
    "Foreign Key",
    "키워드 3",
    "키워드 4",
    "키워드 5",
    "키워드 6",
    "키워드 7",
    "키워드 8",
    "키워드 9",
    "키워드 10",
  ];
  commentData.value = [
    "관계형 데이터베이스에서 조(레코드)의 식별자로 이용하기에 가장 적합한 것을 관계 (테이블)마다 단 한 설계자에 의해 선택, 정의된 후보 키",
    "다른 테이블의 기본 키를 참조하여 두 테이블을 연결하는 테이블의 속성 집합",
    "- 컴퓨터의 뇌로서, 프로그램에서 명령어를 해석하고 실행하는 핵심 부품 제어 유닛은 명령어를 해독하고 실행하는 역할을 하며, 산술 논리 연산 장치(ALU)는 산술 연산과 논리 연산을 수행 레지스터는 매우 빠른 속도로 데이터를 저장하고 처리하는데 사용",
    "내용 4",
    "내용 5",
    "내용 6",
    "내용 7",
    "내용 8",
    "내용 9",
    "내용 10",
  ];
};

const goToMain = function () {
  router.push({ path: "/" });
};
const goToQuiz = function () {
  router.push({ path: "/quiz" });
};
const replayFlashcard = function () {
  isSelected.value = !isSelected.value;
  isOver.value = !isOver.value;
  keywordData.value = null;
  category.value = null;
};
</script>

<template>
  <v-app class="bgbg text-font">
    <v-img :src="cardImage" class="memo_card">
      <!-- text 박스의 크기를 늘려서 길이가 늘어나도 문제없게 수정하자!! -->
      <div class="memo_card_text card_box">
        <p>{{ category || "암기하기" }}</p>
      </div>
    </v-img>
    <v-img :src="flashcardImage" class="flashcard" @click="reverseCard">
      <!-- 클릭했을때 내용이 바뀌게 만들자! -->
      <p class="card_text" v-if="isOver && keywordData">THE END</p>
      <p
        :class="{ card_text: true, invisible: isInvisible }"
        v-else-if="keywordData && !isReversed"
      >
        {{ keywordData[idx] }}
      </p>
      <p
        :class="{ reverse_text: true, invisible: isInvisible }"
        v-else-if="keywordData && isReversed"
      >
        {{ commentData[idx] }}
      </p>
      <p class="start_text" v-else-if="!keywordData">
        카테고리를<br />
        선택하세요<br />
      </p>
    </v-img>

    <!-- 카테고리 선택 전 출력할 버튼 목록 -->
    <v-container class="button_menu" v-if="!isSelected">
      <v-row align="start" no-gutters>
        <v-col
          v-for="(button, index) in buttonCategory"
          :key="index"
          cols="6"
          @click="selectCategory(button)"
        >
          <v-img :src="buttonBgImage" class="menu_img">
            <p class="button_text">{{ button }}</p>
          </v-img>
        </v-col>
      </v-row>
    </v-container>

    <!-- 카테고리 선택 후 진행상황, 좋아요, 이전, 다음 기능 버튼 출력 -->
    <v-container class="button_menu" v-else-if="isSelected && !isOver">
      <v-row align="start" no-gutters>
        <v-col cols="12">
          <div class="menu-button">
            <!-- 현재는 idx/10으로 적어 두었지만 axios로 데이터를 받아오면 각 문제의 index 값을 출력해야함! -->
            <p class="button_text">{{ idx + 1 }} / 10</p>
          </div>
        </v-col>
        <v-col cols="12">
          <div class="menu-button" @click="addLike">
            <p class="button_text">좋아요</p>
          </div>
        </v-col>
        <v-col cols="12">
          <div class="menu-button next_btn" @click="fadeAwayNext">
            <p class="button_text">다음</p>
          </div>
        </v-col>
        <v-col cols="12">
          <div class="menu-button previous_btn" @click="fadeAwayPre">
            <p class="button_text">이전</p>
          </div>
        </v-col>
      </v-row>
    </v-container>

    <!-- 모든 암기를 본 후에 이동할 페이지를 보여주는 버튼 출력 -->
    <v-container class="button_menu" v-else-if="isSelected && isOver">
      <v-row align="start" no-gutters>
        <v-col cols="12">
          <div class="menu-button" @click="goToMain">
            <p class="gotomain_text">메인 메뉴로 돌아가기</p>
          </div>
        </v-col>
        <v-col cols="12">
          <div class="menu-button" @click="replayFlashcard">
            <p class="button_text">다시하기</p>
          </div>
        </v-col>
        <v-col cols="12">
          <div class="menu-button" @click="goToQuiz">
            <p class="button_text">퀴즈로 가기</p>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<style scoped lang="scss">
@import url("@/assets/style/main.scss");

.memo_card {
  position: absolute;
  // top: 20%;
  // left: 16%;
  transform: rotate(340deg) scale(1, 1.2);
  width: 30vw;
  height: 30vw;
  margin-top: 11vw;
  margin-left: 18vw;

  .memo_card_text {
    transform: scale(1, calc(1 / 1.2)) translate(-50%, -50%);
    position: absolute;
    top: 20%;
    left: 50%;
    font-size: 3vw;
    width: 18vw;
  }

  .card_box {
    width: 50vw;
    text-align: center;
  }
}
.bg_position {
  position: relative;
  width: 100%;
  height: 100%;
}
.flashcard {
  position: absolute;
  // top: 25%;
  // left: 17.5%;
  transform: rotate(340deg) scale(1, 1.3);
  text-align: center;
  margin-top: 13vw;
  margin-left: 19.2vw;
  width: 29vw;
  height: 30vw;
  transition: all 0.3s linear;

  // 회전을 위해 3D 로 선언
  transform-style: preserve-3d;
}
.start_text {
  transform: scale(1, calc(1 / 1.3)) translate(-50%, -50%);
  position: absolute;
  top: 41%;
  left: 50%;
  font-size: 3vw;
  width: 20vw;
}

.card_text {
  transform: scale(1) translate(-50%, -50%);
  position: absolute;
  top: 45%;
  left: 50%;
  font-size: 2vw;
  width: 20vw;
}
.reverse_text {
  transform: scale(1) translate(-50%, -50%) rotateY(180deg);
  position: absolute;
  top: 44%;
  left: 50%;
  font-size: 1.2vw;
  width: 25vw;
}
// .flashcard_effect {
//   /* translate(px - +  = 오른쪽, - = 왼쪽 , % - + = 아래, - = 위 )
//             scale(x, y) 1개만 넣으면 x만큼 커짐, x, y를 넣으면 가로 세로 비율 따로 지정 가능
//         */
//   transition: all 0.3s linear;
//   transform: translate(20px, -15%) scale(1.5, 1.8);
//   .card_text {
//     transform: scale(calc(1 / 1.5), calc(1 / 1.8)) translate(-50%, -50%);
//     font-size: 3vw;
//     left: 40%;
//     top: 45%;
//   }
// }

.button_menu {
  position: absolute;
  margin-top: 10vw;
  margin-left: 58vw;
  text-align: center;
  display: flex;
  width: 35vw;
  height: 35vw;

  .menu_img {
    width: 14vw;
    height: 9vw;
    position: absolute;
    transform: scale(1, 0.7);
    filter: drop-shadow(5px 5px 5px #000);
    cursor: pointer;

    .button_text {
      width: 10vw;
      font-size: 1.8vw;
      transform: scale(1, calc(1 / 0.7)) translate(20%, 75%);
      // position: absolute;
    }
  }

  .menu-button {
    width: 12vw;
    height: 5vw;
    margin-left: 8vw;
    border: 3px solid rgba(81, 60, 58, 1);
    color: rgb(124, 92, 63);
    padding: 2%;
    cursor: pointer;

    &:hover {
      transition-duration: 0.5s;
      background-color: #7c5c3f;
      color: white;
    }
    .button_text {
      width: 10vw;
      font-size: 1.8vw;
      transform: translate(8%, 11%);
      font-weight: bold;
      // position: absolute;
    }

    .gotomain_text {
      width: 8vw;
      font-size: 1.6vw;
      transform: translate(20%, -20%);
      font-weight: bold;
    }
  }
}
.bgbg {
  position: fixed;
  min-width: 100%;
  min-height: 100%;
  width: auto;
  height: auto;
  z-index: -1;
  background-image: url("@/assets/images/CardPage_BG_HighQuality.png");
  background-position: center;
  background-size: cover;
  background-repeat: no-repeat;
}

.invisible {
  display: none;
}
</style>
