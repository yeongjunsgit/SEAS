<script setup>
import backgroundImage from "@/assets/images/CardPage_BG_HighQuality.png";
import cardImage from "@/assets/images/card_removed.png";
import flashcardImage from "@/assets/images/cardPage.jpg";
import buttonBgImage from "@/assets/images/button_background.png";

import { RouterLink, RouterView } from "vue-router";
import HeaderComponent from "@/components/commons/HeaderComponent.vue";
import { ref, watch } from "vue";

const quizData = ref(null);

const button_category = [
  "네트워크",
  "자료구조",
  "컴퓨터 구조",
  "운영체제",
  "알고리즘",
  "데이터베이스",
];
</script>

<template>
  <v-app class="bgbg text-font">
    <!-- <img :src="backgroundImage" class="bg_position" /> -->
    <!-- <v-img contain cover :src="backgroundImage" class="bg_position"> </v-img> -->
    <v-img :src="cardImage" class="memo_card">
      <p class="card_text">암기하기</p>
    </v-img>
    <v-img :src="flashcardImage" class="flashcard">
      <p class="card_text" v-if="quizData">{{ quizData }}</p>
      <p class="card_text" v-else>
        카테고리를<br />
        선택하세요<br />
      </p>
    </v-img>
    <v-container class="button_menu">
      <v-row align="start" no-gutters>
        <v-col v-for="(button, index) in button_category" :key="index" cols="6">
          <v-img :src="buttonBgImage" class="menu_img">
            <p class="button_text">{{ button }}</p>
          </v-img>
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
  margin-top: 14vw;
  margin-left: 18vw;

  .card_text {
    transform: scale(1, calc(1 / 1.2)) translate(-50%, -50%);
    position: absolute;
    top: 20%;
    left: 50%;
    font-size: 3vw;
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
  margin-top: 16vw;
  margin-left: 19.2vw;
  // transition: all 0.3s linear;
  width: 29vw;
  height: 30vw;
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
  transform: translate(10px, 20%) scale(2.2);
  .card_text {
    transform: scale(calc(1 / 2.2)) translate(-50%, -50%);
  }
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
