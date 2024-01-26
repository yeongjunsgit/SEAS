<script setup>
import backgroundImage from "@/assets/images/CardPage_BG_HighQuality.png";
import cardImage from "@/assets/images/card_removed.png";
import flashcardImage from "@/assets/images/cardPage.jpg";
import buttonBgImage from "@/assets/images/button_background.png";

import { RouterLink, RouterView } from "vue-router";
import HeaderComponent from "@/components/commons/HeaderComponent.vue";
import { ref, watch, computed } from "vue";

import { useRouter } from "vue-router";
const router = useRouter();

const category = ref(null);
const keywordData = ref(null);
const commentData = ref(null);
const idx = ref(0);
const isReversed = ref(false);
const isFront = ref(true);

const reverseCard = function () {
  isReversed.value = !isReversed.value;
  isFront.value = !isFront.value;
};

const gotoNextCard = function () {
  idx.value = idx.value += 1;
};

const gotoPreviousCard = function () {
  idx.value = idx.value -= 1;
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
  category.value = ref(cate);
  console.log(category);
  // 여기서 받은 카테고리를 axios로 보내자! 일단 임시로 아무거나 만들어서 쓰겠다
  keywordData.value = [
    "키워드 1",
    "키워드 2",
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
    "내용 1",
    "내용 2",
    "내용 3",
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
    <!-- <img :src="backgroundImage" class="bg_position" /> -->
    <!-- <v-img contain cover :src="backgroundImage" class="bg_position"> </v-img> -->
    <!-- 할 것을 정리하자!

    흐름:
    카테고리를 누르면 이미지가 떠오른다 (완료)

    떠오른 이미지를 누르면 뒤집어지며 키워드와, 해당 관련 내용이 나온다
    - 어떻게 해야할까?
    1. 카테고리를 눌렀을때 해당 이미지에 클릭 버튼시 스타일이 변화하는 함수를 넣어야한다
    2. 함수에서 스타일을 바꿔 적용할 수 있어야한다
    2-1. 어떻게? boolean 값을 가진 변수를 이용해서 on off 한다!
    - 현재 출력해야 하는 앞뒷면과, 클릭시 스타일을 추가하는 식으로 진행하자!
    2-2 함수 내에서 스타일을 수정해줄 방법이 있을까?
    - 챗지피티가 이런거 없다고 한다.
  - 우선 클릭 이벤트는 이미지에서 이루어져야 한다는 점
  - 즉, 화면이 띄워 올라가면 바로 이미지 클릭시 스타일에 저장해둔 회전이 작동해야한다는 점
  - 그 이전에는 회전을 안해야 한다는 점을 구현해야하는 상황이다.
  - 한번 잘 찾아봐야할듯.... 
  
  -->
    <v-img :src="cardImage" class="memo_card">
      <!-- text 박스의 크기를 늘려서 길이가 늘어나도 문제없게 수정하자!! -->
      <div class="card_text card_box">
        <p>{{ category || "암기하기" }}</p>
      </div>
    </v-img>
    <v-img
      :src="flashcardImage"
      class="flashcard"
      :class="{ flashcard_effect: isSelected }"
    >
      <!---->
      <!-- 클릭했을때 내용이 바뀌게 만들자! -->
      <p class="card_text" v-if="isOver && keywordData">THE END</p>
      <p
        class="card_text"
        v-else-if="keywordData && !isReversed"
        @click="reverseCard"
        :style="{ reverse_effect: !isFront }"
      >
        {{ keywordData[idx] }}
      </p>
      <p
        class="card_text"
        v-else-if="keywordData && isReversed"
        @click="reverseCard"
        :style="{ reverse_effect: isFront }"
      >
        {{ commentdData[idx] }}
      </p>
      <p class="card_text" v-else-if="!keywordData">
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
            <p class="gotomain_text">좋아요</p>
          </div>
        </v-col>
        <v-col cols="12">
          <div class="menu-button" @click="gotoNextCard">
            <p class="gotomain_text">다음</p>
          </div>
        </v-col>
        <v-col cols="12">
          <div
            class="menu-button"
            @click="gotoPreviousCard"
            :class="{ disabled: idx === 0 }"
          >
            <p class="gotomain_text">이전</p>
          </div>
        </v-col>
        <v-col
          v-for="(func, index) in buttonFunc"
          :key="index"
          cols="12"
          :disabled="buttonValue === 'hihi' && idx === 0"
        >
          <div class="menu-button">
            <p class="button_text">{{ func }}</p>
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

  .card_text {
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

  // 회전을 위해 3D 로 선언
  transform-style: preserve-3d;
  .card_text {
    transform: scale(1, calc(1 / 1.3)) translate(-50%, -50%);
    position: absolute;
    top: 42%;
    left: 50%;
    font-size: 3vw;
    width: 20vw;
  }
}
.flashcard_effect {
  /* translate(px - +  = 오른쪽, - = 왼쪽 , % - + = 아래, - = 위 ) 
            scale(x, y) 1개만 넣으면 x만큼 커짐, x, y를 넣으면 가로 세로 비율 따로 지정 가능  
        */
  transition: all 0.3s linear;
  transform: translate(20px, -15%) scale(1.5, 1.8);
  .card_text {
    transform: scale(calc(1 / 1.5), calc(1 / 1.8)) translate(-50%, -50%);
    font-size: 3vw;
    left: 40%;
    top: 45%;
  }
}

.reverse_effect {
  transform: rotateY(180deg);
}

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
    border: 3px solid rgb(124, 92, 63);
    color: rgb(124, 92, 63);
    padding: 2%;

    .button_text {
      width: 10vw;
      font-size: 1.8vw;
      transform: translate(8%, 11%);
      font-weight: bold;
      // position: absolute;
    }
    border-radius: 10px;

    &:hover {
      transition-duration: 0.5s;
      background-color: rgb(124, 92, 63);
      color: white;
    }

    .gotomain_text {
      width: 8vw;
      font-size: 1.6vw;
      transform: translate(20%, -13%);
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
</style>
