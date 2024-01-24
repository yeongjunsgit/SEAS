<script setup>
import MyPageComponent from "@/components/mypage/MyPageComponent.vue";
import CategoryComponent from "@/components/mypage/CategoryComponent.vue";
import RadarChart from "@/components/mypage/RadarChart.vue";
import LineChartVue from "@/components/mypage/LineChart.vue";

const categories = [
  "자료구조",
  "알고리즘",
  "운영체제",
  "데이터베이스",
  "네트워크",
  "컴퓨터 구조",
];

const weeks = Array.from({ length: 52 }, (_, index) => index + 1);
const days = Array.from({ length: 7 }, (_, index) => index + 1);
const filledDays = [7, 14, 21, 28, 35, 42, 49];

const isFilled = function (day) {
  return filledDays.includes(day);
};
</script>

<template>
  <div class="mypage-background text-font">
    <div class="container">
      <div class="mypage-component-background userinfo">
        <div>
          <div class="user-box">
            <div class="text-center">
              <div>
                <img src="@/assets/images/Logo.png" alt="" />
              </div>
              <p>김싸피</p>
              <p>현상금액 : $10000</p>
              <p>전체 푼 횟수 : 40</p>
              <p>정답률 : 75%</p>
            </div>
            <div>
              <p>유저 정보</p>
              <div>
                <RadarChart />
              </div>
            </div>
          </div>
        </div>
        <div class="grass">
          <div class="text-center">일일 학습 경과</div>
          <div class="grass-container">
            <div v-for="week in weeks" :key="week" class="week">
              <div
                v-for="day in days"
                :key="day"
                class="day"
                :class="{ filled: isFilled(day) }"
              ></div>
            </div>
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

img {
  max-width: 100%;
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
  grid-template-columns: 1fr 1fr;
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
  margin-left: 50px;
  border: 2px brown;
  border-style: none;
  border-radius: 0.5cap;
  display: grid;
  grid-template-columns: 5fr 7fr;
  gap: 10px;
}
.mychart {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  // grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  // grid-auto-rows: minmax(100px, auto);
}
.grass-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: start;
  padding-left: 20px;
}
.week {
  display: flex;
}

.day {
  width: 16px;
  height: 16px;
  margin: 1px;
  border: 1px solid black;
  background-color: #ebedf0;
  transition: background-color 0.3s ease;
}
.day.filled {
  background-color: green;
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
</style>
