<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import RadarChart from "./RadarChart.vue";

const userinfo = ref(Object);
onMounted(() => {
  axios
    .get("https://i10a609.p.ssafy.io/api/mypage/my-info")
    .then((response) => {
      userinfo.value = response.data.data;
      // console.log(userinfo);
    });
});
</script>

<template>
  <div class="info-container">
    <h2 class="text-center">유저 정보</h2>
    <div class="user-container">
      <div class="user-box">
        <div>
          <img src="@/assets/images/Logo.png" alt="" />
        </div>
        <p>{{ userinfo.nickname }}</p>
        <p>현상금액 : $1{{ userinfo.point }}</p>
        <p>전체 푼 횟수 : {{ userinfo.solvedCount }}</p>
        <p>정답률 : {{ userinfo.correctRate }}%</p>
      </div>
      <div class="radar">
        <div>
          <RadarChart />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.info-container {
  padding-top: 20px;
}
.text-center {
  text-align: center;
}
.user-container {
  display: flex;
  justify-content: space-around;
}
.user-box {
  padding-left: 80px;
  width: 230px;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  img {
    width: 100%;
  }
}
</style>
