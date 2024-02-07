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
const flashcardData = ref(null);
const keywordData = ref(false);
const favoriteData = ref(null);
const idx = ref(0);


const isHaveFlashcardData = ref(false)

const watchFlashcardData = watch (flashcardData, () => {
  if (flashcardData.value.length > 0) {
    isHaveFlashcardData.value = true
  }
})

// 애니메이션 로직을 위한 변수들!
const isReversed = ref(false);
const isUp = ref(false);
const isFront = ref(true);
const isInvisible = ref(false);
const isAnimating = ref(false);

// 버튼 출력을 다룰 변수 2개 생성
const isSelected = ref(false);
const isOver = ref(false);

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
  if (flashcardData.value[idx.value].isFavorite === true) {
    // axios를 통해 좋아요 취소 기능
    axios({
      method: "delete",
      url: `https://i10a609.p.ssafy.io/api/flashcard/${flashcardData.value[idx.value].id}/favorite`

    })
      .then((res) => {
        console.log("Delete complete")
      })
      .catch((err) => {
        console.log(err)
      })

    // 끝난 후 false로 변환
    flashcardData.value[idx.value].isFavorite = false
  } 
  else if (flashcardData.value[idx.value].isFavorite === false) {
    // axios를 통해 좋아요 추가 기능
    axios({
      method: "post",
      url: `https://i10a609.p.ssafy.io/api/flashcard/${flashcardData.value[idx.value].id}/favorite`

    })
      .then((res) => {
        console.log("favorite complete")
      })
      .catch((err) => {
        console.log(err)
      })
      
    // 끝난후 true로 변환
    flashcardData.value[idx.value].isFavorite = true
  }
}


// 버튼들에 출력할 내용들 생성
const buttonCategory = [
  "네트워크",
  "자료구조",
  "컴퓨터구조",
  "운영체제",
  "알고리즘",
  "데이터베이스",
];

// 버튼 눌렀을 때 호출되는 함수들
const selectCategory = async function (cate) {
  if (isAnimating.value === false) {
    try {
      isSelected.value = !isSelected.value;
      isUp.value = !isUp.value;
      category.value = ref(cate);
      // keywordData.value = true

      const response = await axios({
        method: 'get',
        url: `https://i10a609.p.ssafy.io/api/flashcard?category=${category.value.value}`,
      });

      flashcardData.value = response.data.data;
      keywordData.value = true

    } catch (error) {
      console.log(error);
    }
    gsap.to(".flashcard", {
      onStart: () => {
        isAnimating.value = true;
      },
      duration: 0.3,
      xPercent: 30,
      scale: (1.5, 1.6),
      rotate: 0.2,
      onComplete: () => {
        isAnimating.value = false;
      }
    });
  }
};

// 다시하기 눌렀을 때, 맨 처음으로 되돌아가는 함수 replayFlashcard
const replayFlashcard = function () {
  if (isAnimating.value === false) {
    isSelected.value = !isSelected.value;
    isOver.value = !isOver.value;
    isUp.value = !isUp.value;
    keywordData.value = false;
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
          if (isReversed.value === true) {
            isReversed.value = !isReversed.value
            gsap.to(".flashcard", {
              onStart: () => {
                isAnimating.value = true
                isInvisible.value = !isInvisible.value;
              },
              duration: 0.5,
              rotationY: "+=180",
              onComplete: () => {
                isInvisible.value = !isInvisible.value;
                isAnimating.value = false
              },
            });
          }
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
      <!-- 모든 카드를 다 봤을 때 앞면-->
      <p :class="{ card_text: true, invisible: isInvisible }" v-if="isOver && keywordData && !isReversed">THE END</p>

      <!-- 모든 카드를 다 봤을 때 뒷면-->
      <p :class="{ reverse_over_text: true, invisible: isInvisible }" v-else-if="isOver && keywordData && isReversed">수고하셨습니다</p>

      <!-- 카드 앞면 -->
      <p
        :class="{ card_text: true, invisible: isInvisible }"
        v-else-if="keywordData && !isReversed"
      >
        <div class="category_border">
          <p class="category_text"> {{ category }}</p>
        </div>
        <br />
        {{ flashcardData[idx].keyword }}
      </p>
      <!-- 카드 뒷면 -->
      <p
        :class="{ reverse_text: true, invisible: isInvisible }"
        v-else-if="keywordData && isReversed"
      >
      <br>
        <li v-for="data in flashcardData[idx].contents">
          {{ data }}
        </li>
      </p>

      <!-- 카테고리 선택 전 -->
      <p :class="{ start_text: true, invisible: isInvisible }" v-else-if="!keywordData">
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
    <v-container class="button_menu" v-else-if="isSelected && !isOver && isHaveFlashcardData ">
      <v-row align="start" no-gutters>
        <v-col cols="12">
          <div class="menu-button">
            <!-- 현재는 idx/10으로 적어 두었지만 axios로 데이터를 받아오면 각 문제의 index 값을 출력해야함! -->
            <p class="button_text">{{ idx + 1 }} / 10</p>
          </div>
        </v-col>
        <v-col cols="12">
          <div :class="{'complete_like': flashcardData[idx].isFavorite === true, 'yet_like': flashcardData[idx].isFavorite === false, 'menu-button': true }" @click="addLike">
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
    <v-container class="button_menu" v-else-if="isSelected && isOver && isHaveFlashcardData">
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

.reverse_over_text {
  transform: scale(1) translate(-50%, -50%) rotateY(180deg);
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
