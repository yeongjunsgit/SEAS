<script setup>
import backgroundImage from "@/assets/images/CardPage_BG_HighQuality.png";
import cardImage from "@/assets/images/card_removed.png";
import flashcardImage from "@/assets/images/cardPage.jpg";
import buttonBgImage from "@/assets/images/button_background.png";

import { RouterLink, RouterView } from "vue-router";
import HeaderComponent from "@/components/commons/HeaderComponent.vue";
import { ref, watch, computed } from "vue";
import { gsap } from "gsap";
import axios from "axios"
import { useRouter } from "vue-router";
const router = useRouter();

// 카테고리 선택시 관리 할 편수들!
const category = ref(null);
const keywordData = ref(null);
const commentData = ref(null);
const favoriteData = ref(null);
const idx = ref(0);

// 애니메이션 로직을 위한 변수들!
const isReversed = ref(false);
const isUp = ref(false);
const isFront = ref(true);
const isInvisible = ref(false);
const isAnimating = ref(false);

// 카드가 뒤집어지는 효과를 담당하는 함수
const reverseCard = function () {
  if (isUp.value === true && isAnimating.value === false) {
    isFront.value = !isFront.value;
    isReversed.value = !isReversed.value;
    isInvisible.value = !isInvisible.value;

    gsap.to(".flashcard", {
      onStart: () => {
        isAnimating.value = true
      },
      duration: 1,
      rotationY: "+=180",
      onComplete: () => {
        isInvisible.value = !isInvisible.value;
        isAnimating.value = false
      },
    });
  }
};

// 카드 넘길때 사라지고 다시 나타나는 애니메이션 구현
// 다음 버튼 눌렀을 때 기믹
const fadeAwayNext = function () {
  if (isAnimating.value === false) {
    const tl1 = gsap.timeline();
    tl1.to(".flashcard", {
      onStart: () => {
        isAnimating.value = true
      },
      duration: 0.3,
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
        isAnimating.value = false
      },
    });
  }
};

// 이전 버튼 눌렀을때 기믹
const fadeAwayPre = function () {
  if (isAnimating.value === false) {
    const tl1 = gsap.timeline();
    tl1.to(".flashcard", {
      onStart: () => {
        isAnimating.value = true
      },
      duration: 0.3,
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
        isAnimating.value = false
      },
    });
  }
};


// idx개수를 추적하여 THE END를 표시해줄 watch
watch(idx, (newValue, oldValue) => {
  if (newValue === 10) {
    isOver.value = !isOver.value;
    idx.value = 0;
  }
});

// 현재 favorteData의 값이 1인지, 0인지에 따라 T/F값을 바꿔줄 computed
const calculFavorite = computed(() => {
  if (favoriteData.value && favoriteData.value[idx.value] === '1') {
    return true
  } else {
    return false
  }
}) 


// 좋아요 추가 기능 구현하기!
const addLike = function () {
  if (favoriteData.value[idx.value] === "1") {
    // axios를 통해 좋아요 취소 기능

    // 끝난 후 false로 변환
    favoriteData.value[idx.value] = "0"
  } 
  else if (favoriteData.value[idx.value] === "0") {
    // axios를 통해 좋아요 추가 기능

    // 끝난후 true로 변환
    favoriteData.value[idx.value] = "1"
  }
}

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
  if (isAnimating.value === false) {
    isSelected.value = !isSelected.value;
    isUp.value = !isUp.value;
    category.value = ref(cate);
  
    gsap.to(".flashcard", {
      onStart: () => {
        isAnimating.value = true
      },
      duration: 0.3,
      xPercent: 30,
      scale: (1.5, 1.6),
      rotate: 0.2,
      onComplete: () => {
        isAnimating.value = false
      }
    });
  
    // 여기서 받은 카테고리를 axios로 보내자! 일단 임시로 아무거나 만들어서 쓰겠다
    axios({
      method: 'get',
      url: `http://70.12.247.130:8080/api/flashcard?category=${category.value}`,
  
    }) .then((res) => {
      console.log(res.data)
      keywordData.value = response.data.keyword
      commentData.value = response.data.content
    }) .catch((error) => {
      console.log(error)
    })
  
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
      ["1컴퓨터의 뇌로서, 프로그램에서 명령어를 해석하고 실행하는 핵심 부품",
        "제어 유닛은 명령어를 해독하고 실행하는 역할을 하며, 산술 논리 연산 장치(ALU)는 산술 연산과 논리 연산을 수행",
        "레지스터는 매우 빠른 속도로 데이터를 저장하고 처리하는데 사용"],
      ["데이터와 명령을 저장하는 공간",
        "주 기억장치(RAM)는 현재 실행 중인 프로그램과 데이터를 저장하며, 보조 기억장치(하드 디스크, SSD)는 영구적인 데이터 저장을 담당",
        "캐시도 메모리의 일종으로, 빠른 데이터 접근을 위해 사용"],
      ["CPU, 메모리, 그리고 입출력 장치 등 각 구성 요소 간에 데이터, 주소, 제어 신호를 전송하는 통로",
        "주소 버스는 메모리 위치를 지정하고, 데이터 버스는 실제 데이터를 전송하며, 제어 버스는 명령과 상태 정보를 전송"],
      ["CPU 내부에 위치한 소량의 고속 기억장치",
        "현재 수행 중인 명령어나 연산에 필요한 데이터를 일시적으로 저장",
        "레지스터는 ALU에서의 계산에 필수적이며, 레지스터의 크기와 수는 CPU의 성능에 영향을 미침"],
      ["CPU가 이해하고 실행하는 명령어의 집합",
        "각 명령어는 특정한 동작을 수행하며, 이러한 명령어들을 조합하여 프로그램이 동작"],
      ["명령어 처리를 여러 단계로 분할하여 병렬적으로 실행하는 구조",
        "이를 통해 여러 명령어가 동시에 처리되어 CPU의 성능이 향상"],
      ["빠른 속도로 접근 가능한 작은 용량의 메모리",
        "CPU가 자주 사용하는 데이터를 일시적으로 저장하여 메인 메모리에 접근하는 속도를 향상",
        "주로 L1, L2, L3 캐시로 구성"],
      ["전체 시스템의 동기화를 담당하는 신호",
        "CPU와 다른 시스템 구성 요소의 동작 주기를 조절",
        "클럭 주파수가 높을수록 더 빠른 연산이 가능"],
      ["CPU가 현재 수행 중인 작업을 중단하고 외부 이벤트에 대응하는 메커니즘",
        "하드웨어나 소프트웨어에서 발생할 수 있으며, 시스템의 효율적인 동작과 응답성을 보장"],
      ["내장된 소프트웨어로, 하드웨어 초기화, 부팅, 기본 시스템 설정 및 제어를 담당",
        "주로 ROM 또는 플래시 메모리에 저장되며, 시스템의 안정성과 동작을 지원",
        "펌웨어는 업데이트 가능하며, 제조사가 제공하는 업데이트로 기능 확장 가능"],
    ];
    
    favoriteData.value = [
      '1','0','1','0','1','1','0','1','0','1'
    ]
  }
};

// 다시하기 눌렀을 때, 맨 처음으로 되돌아가는 함수 replayFlashcard
const replayFlashcard = function () {
  if (isAnimating.value === false) {
    isSelected.value = !isSelected.value;
    isOver.value = !isOver.value;
    isUp.value = !isUp.value;
    keywordData.value = null;
    category.value = null;
  
    gsap.fromTo(
      ".flashcard",
      {
        onStart: () => {
          isAnimating.value = true
        },
        xPercent: 30,
        scale: (1.5, 1.6),
        rotate: 0.2,
      },
      {
        duration: 0.5,
        xPercent: 0,
        scaleX: 1,
        scaleY: 1.3,
        rotation: -20,
        onComplete: () => {
          isAnimating.value = false
        }
      }
    );
  }
};

// 각각 메인과 퀴즈로 보내는 router 함수
const goToMain = function () {
  router.push({ path: "/" });
};
const goToQuiz = function () {
  router.push({ path: "/quiz" });
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
        <div class="category_border">
          <p class="category_text"> {{ category }}</p>
        </div>
        <br />
        {{ keywordData[idx] }}
      </p>
      <p
        :class="{ reverse_text: true, invisible: isInvisible }"
        v-else-if="keywordData && isReversed"
      >
      <br>
        <li v-for="data in commentData[idx]">
          {{ data }}
        </li>
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
          <div :class="{'complete_like': favoriteData[idx] === '1', 'yet_like': favoriteData[idx] === '0', 'menu-button': true }" @click="addLike">
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
@import "@/assets/style/main.scss";
@import "@/assets/style/quiz.scss";

// 카드 판에서 쓰이는 CSS - memo_card
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

// 움직이는 메모장에서 쓰일 flashcard
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
// flashcard에 들어가는 text로 처음 시작할때 출력되는 텍스트 css
.start_text {
  transform: scale(1, calc(1 / 1.3)) translate(-50%, -50%);
  position: absolute;
  top: 41%;
  left: 50%;
  font-size: 3vw;
  width: 20vw;
}

// 카드가 띄워진 후 출력되는 텍스트 css
.card_text {
  transform: scale(1) translate(-50%, -50%);
  position: absolute;
  top: 45%;
  left: 50%;
  font-size: 2vw;
  width: 20vw;
}

// 플래시 카드에 선택한 카테고리를 띄우는데 쓰이는 텍스트의 css
.category_text {
  transform: scale(1) translate(-50%, -50%);
  position: absolute;
  top: -20%;
  left: 50%;
  font-size: 1vw;
  width: 15vw;
  border-bottom: 1px solid black;
}

// 카드 뒷면에 존재하는 내용에 쓰이는 CSS
.reverse_text {
  transform: scale(1) translate(-50%, -50%) rotateY(180deg);
  position: absolute;
  text-align: left;
  top: 44%;
  left: 50%;
  font-size: 1vw;
  width: 25vw;
}


// 버튼들을 다루는 flex box와 그안에서 쓰이는 세부 css
.button_menu {
  position: absolute;
  margin-top: 10vw;
  margin-left: 62vw;
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

  // 베이직 한 기본 버튼들에 쓰임
  .menu-button {
    min-width: 40%; 
    width: 12vw;
    height: 5vw;
    margin-left: 8vw;
    border: 3px solid rgba(81, 60, 58, 1);
    color: 000000;
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
    // 메인으로 가기 텍스트가 너무길어서 따로 조절이 필요해 추가로 css 작성
    .gotomain_text {
      width: 8vw;
      font-size: 1.6vw;
      transform: translate(20%, -20%);
      font-weight: bold;
    }
  }
}

// 뒷배경에 쓰이는 css
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

// 애니메이션 로직중에 텍스트를 안보이게 하기위한 css 
.invisible {
  display: none;
}

.complete_like {
      background-color: #7c5c3f;
      color: white;
    }
  
.yet_like {
  transition-duration: 0.5s;
  background-color: none;
  color: 000000;
}

</style>
