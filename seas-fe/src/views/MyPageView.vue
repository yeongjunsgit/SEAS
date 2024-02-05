<script setup>
import MyPageComponent from "@/components/mypage/MyPageComponent.vue";
import CategoryComponent from "@/components/mypage/CategoryComponent.vue";
import RadarChart from "@/components/mypage/RadarChart.vue";
import LineChartVue from "@/components/mypage/LineChart.vue";
import GrassComponentVue from "@/components/mypage/GrassComponent.vue";

const categories = [
  "자료구조",
  "알고리즘",
  "운영체제",
  "데이터베이스",
  "네트워크",
  "컴퓨터 구조",
];
</script>

<template>
  <div class="mypage-background text-font">
    <div class="container">
      <div class="mypage-component-background userinfo">
        <div>
          <p class="text-center">유저 정보</p>
          <div class="user-container">
            <div class="user-box">
              <div>
                <img src="@/assets/images/Logo.png" alt="" />
              </div>
              <p>김싸피</p>
              <p>현상금액 : $10000</p>
              <p>전체 푼 횟수 : 40</p>
              <p>정답률 : 75%</p>
            </div>
            <div>
              <div class="radar">
                <RadarChart />
              </div>
            </div>
          </div>
        </div>
        <div class="grass">
          <p class="text-center">일일 학습 경과</p>
          <div class="grass-component">
            <GrassComponentVue />
          </div>
        </div>
      </div>
      <div class="mypage-component-background mychart">
        <div v-for="(category, n) in categories" :key="n" cols="4">
          <div class="transparent-card">
            <div class="chart-box">
              <LineChartVue />
            </div>
          </div>
        </div>
      </div>
      <div class="mypage-component-background mycard">
        <div>
          <CategoryComponent type="즐겨찾기" />
        </div>
        <div>
          <CategoryComponent type="오답노트" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
@import url("@/assets/style/main.scss");

.container {
  display: flex;
  flex-direction: column;
}
.user-container {
  display: flex;
  justify-content: space-between;
}

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
.userinfo {
  margin-top: 50px;
  display: grid;
  align-items: center;
  grid-template-columns: 1fr 1fr;
  // margin-bottom: 0;
  // padding-bottom: 0;
  img {
    width: 100%;
  }
}

.rotate {
  rotate: -90deg;
}
.mypage-component-background {
  // width: 80%;
  height: auto;
  overflow: hidden;
  margin-inline: 10%;
  padding: 50px;
  background-image: url("@/assets/images/mypage_paper.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  background-position-x: center;
}
.user-box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
}
.mychart {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  row-gap: 30px;
}

.grass {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  // padding-top: 105px;
  // padding-left: 30px;
}

// .grass-component {
//   padding-left: 50px;
// }
//
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
