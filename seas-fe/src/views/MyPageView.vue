<script setup>
import { ref, onMounted } from "vue";
import UserInfoComponent from "@/components/mypage/UserInfoComponent.vue";
import CategoryComponent from "@/components/mypage/CategoryComponent.vue";
import LineChartVue from "@/components/mypage/LineChart.vue";
import GrassComponentVue from "@/components/mypage/GrassComponent.vue";
import axios from "axios";

const categories = [
  "데이터베이스",
  "네트워크",
  "자료구조",
  "알고리즘",
  "컴퓨터구조",
  "운영체제",
];
const loaded = ref(false);
const categoryObj = ref();

const favoriteApi = "https://i10a609.p.ssafy.io/api/mypage/flashcard/favorite";
const incorrectApi = "https://i10a609.p.ssafy.io/api/mypage/incorrect";

onMounted(async () => {
  loaded.value = false;
  try {
    const response = await fetch("https://i10a609.p.ssafy.io/api/mypage/graph");

    const categoryData = await response.json();
    categoryObj.value = await categoryData.data;

    loaded.value = true;
  } catch (error) {
    console.error(error);
  }
});
</script>

<template>
  <div class="mypage-background text-font">
    <div class="container">
      <div class="mypage-component-background userinfo">
        <UserInfoComponent />
        <GrassComponentVue />
      </div>
      <div class="mypage-component-background mychart">
        <div v-for="(category, n) in categoryObj" :key="n">
          <div class="transparent-card">
            <div class="chart-box">
              <LineChartVue v-if="loaded" :category="category" />
            </div>
          </div>
        </div>
      </div>
      <div class="mypage-component-background mycard">
        <div>
          <CategoryComponent type="즐겨찾기" :apiUrl="favoriteApi" />
        </div>
        <div>
          <CategoryComponent type="오답노트" :apiUrl="incorrectApi" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
@import url("@/assets/style/main.scss");
.text-center {
  text-align: center;
}
.mypage-background {
  overflow: hidden;
  height: auto;
  background-image: url("@/assets/images/mypage_background.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}
.container {
  display: flex;
  flex-direction: column;
}

.userinfo {
  margin-top: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.rotate {
  rotate: -90deg;
}
.mypage-component-background {
  height: auto;
  overflow: hidden;
  margin-inline: 10%;
  padding: 50px;
  background-image: url("@/assets/images/mypage_paper.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  background-position-x: center;
}

.mychart {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
}

.chart-box {
  padding-left: 40px;
}
.transparent-card {
  background: transparent;
  padding-left: 30px;
  width: 350px;
}
.mycard {
  display: grid;
  grid-template-columns: 1fr 1fr;
}

@media screen and (max-width: 1350px) {
  .mychart {
    grid-template-columns: repeat(2, 1fr);
    row-gap: 30px;
  }
}
@media screen and (max-width: 1150px) {
  .userinfo {
    display: flex;
    flex-direction: column;
  }

  .mycard {
    padding-top: 90px;
    align-self: center;
    display: flex;
    flex-direction: column;
  }
}
@media screen and (max-width: 960px) {
  .mychart {
    grid-template-columns: repeat(1, 1fr);
    row-gap: 30px;
  }
}
@media screen and (max-width: 600px) {
  body {
    overflow: auto; /* 모든 방향으로 스크롤 생성 */
    width: 600px; /* 또는 원하는 크기로 설정 */
    margin: 0 auto; /* 가운데 정렬을 위해 사용 */
    /* 추가로 필요한 스타일 설정 */
  }
}
</style>
