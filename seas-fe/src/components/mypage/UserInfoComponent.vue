<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import RadarChart from "./RadarChart.vue";
import TagComponent from "@/components/ranking/TagComponent.vue";

const userinfo = ref(Object);

const badgeList = ref([
  { id: 1, name: "알고리즘" },
  { id: 2, name: "네트워크" },
  { id: 3, name: "자료구조" },
  { id: 4, name: "운영체제" },
  { id: 5, name: "컴퓨터구조" },
  { id: 6, name: "데이터베이스" },
]);

onMounted(async () => {
  try {
    const infoResponse = await axios.get(
      "https://i10a609.p.ssafy.io/api/mypage/my-info"
    );
    userinfo.value = infoResponse.data.data;

    const badgeResponse = await axios.get(
      "https://i10a609.p.ssafy.io/api/mypage/badge"
    );
    const idArray = [1, 2, 3, 4, 5, 6];
    const tmpArray = idArray.filter(
      (item) => !badgeResponse.data.data.map((e) => e.id).includes(item)
    );

    for (var i = 0; i < badgeList.value.length; i++) {
      if (tmpArray.some((e) => e === badgeList.value[i].id)) {
        badgeList.value.splice(i, 1);
        i--;
      }
    }
  } catch (error) {
    console.error(error);
  }
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
        <TagComponent :level="userinfo.tier" :tagList="badgeList" />
        <p>현상금액 : ${{ userinfo.point }}</p>
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

.my-tier {
  display: flex;
  justify-content: center;

  img {
    margin-left: 5px;
    margin-bottom: 2px;
    width: 15px;
    height: 15px;
    align-self: center;
  }
}
</style>
