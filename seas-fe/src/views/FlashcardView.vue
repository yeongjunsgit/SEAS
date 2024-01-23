<script setup>
import backgroundImage from "@/assets/images/CardPage_BG_HighQuality.png";
import cardImage from "@/assets/images/card_removed.png";
import flashcardImage from "@/assets/images/cardPage.jpg";

import { RouterLink, RouterView } from "vue-router";
import HeaderComponent from "@/components/commons/HeaderComponent.vue";
import { ref, watch } from "vue";

const quizData = ref(null);
</script>

<template>
  <v-app class="bgbg">
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
  </v-app>
</template>

<style scoped lang="scss">
.memo_card {
  position: absolute;
  top: 20%;
  left: 16%;
  transform: rotate(340deg) scale(1, 1.2);
  width: 30vw;
  height: 30vw;

  .card_text {
    transform: scale(1, calc(1 / 1.2)) translate(-50%, -50%);
    position: absolute;
    top: 18%;
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
  top: 25%;
  left: 17.5%;
  transform: rotate(340deg) scale(1, 1.3);
  text-align: center;
  // transition: all 0.3s linear;
  width: 29vw;
  height: 30vw;
  .card_text {
    transform: scale(1, calc(1 / 1.3)) translate(-50%, -50%);
    position: absolute;
    top: 40%;
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
.button_move {
  position: absolute;
  top: 50%;
  left: 70%;
}

.bgbg {
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: -1;
  background-image: url("@/assets/images/CardPage_BG_HighQuality.png");
  background-size: cover;
}
</style>
