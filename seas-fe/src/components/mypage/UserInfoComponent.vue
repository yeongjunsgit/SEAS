<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import RadarChart from "./RadarChart.vue";
import TagComponent from "@/components/ranking/TagComponent.vue";
import {
  getMyInfo,
  getUserInfo,
  getMyBadge,
  getUserBadge,
} from "@/api/mypage.js";
import { useauthControllerStore } from "@/stores/authController";

const userStore = useauthControllerStore();

const user_access_token = userStore.myAccessToken;

const userinfo = ref({});

const props = defineProps(["nickname"]);

const badgeList = ref([
  { id: 1, name: "알고리즘" },
  { id: 2, name: "네트워크" },
  { id: 3, name: "자료구조" },
  { id: 4, name: "데이터베이스" },
  { id: 5, name: "컴퓨터구조" },
  { id: 6, name: "운영체제" },
]);

// 전역 Axios 사용
const getInitUserInfo = async () => {
  try {
    if (props.nickname) {
      // axios함수를 통해 데이터를 불러온다.
      await axios
        .get(
          `https://i10a609.p.ssafy.io/api/mypage/my-info?nickname=${props.nickname}`,
          {
            headers: {
              Authorization: `Bearer ${user_access_token}`,
              "Content-Type": "application/json",
            },
          }
        )
        .then(function (response) {
          userinfo.value = response.data.data;
          console.log(111);
        })
        .catch((error) => console.log(error));

      await axios
        .get(
          `https://i10a609.p.ssafy.io/api/mypage/badge?nickname=${props.nickname}`,
          {
            headers: {
              Authorization: `Bearer ${user_access_token}`,
              "Content-Type": "application/json",
            },
          }
        )
        .then(function (response) {
          const idArray = [1, 2, 3, 4, 5, 6];
          const tmpArray = idArray.filter(
            (item) => !response.data.data.map((e) => e.id).includes(item)
          );

          for (var i = 0; i < badgeList.value.length; i++) {
            if (tmpArray.some((e) => e === badgeList.value[i].id)) {
              badgeList.value.splice(i, 1);
              i--;
            }
          }
          console.log(111111);
        })
        .catch((error) => console.log(error));
    } else {
      await axios
        .get("https://i10a609.p.ssafy.io/api/mypage/my-info", {
          headers: {
            Authorization: `Bearer ${user_access_token}`,
            "Content-Type": "application/json",
          },
        })
        .then(function (response) {
          userinfo.value = response.data.data;
          console.log(111);
        })
        .catch((error) => console.log(error));

      await axios
        .get("https://i10a609.p.ssafy.io/api/mypage/badge", {
          headers: {
            Authorization: `Bearer ${user_access_token}`,
            "Content-Type": "application/json",
          },
        })
        .then(function (response) {
          const idArray = [1, 2, 3, 4, 5, 6];
          const tmpArray = idArray.filter(
            (item) => !response.data.data.map((e) => e.id).includes(item)
          );

          for (var i = 0; i < badgeList.value.length; i++) {
            if (tmpArray.some((e) => e === badgeList.value[i].id)) {
              badgeList.value.splice(i, 1);
              i--;
            }
          }
          console.log(111111);
        })
        .catch((error) => console.log(error));
    }

    console.log(222);
  } catch (error) {
    console.log(error);
  }
};
onMounted(async () => {
  await getInitUserInfo();
  console.log(333);
});
</script>

<template>
  <div class="info-container">
    <div>
      <h2 class="text-center">유저 정보</h2>
    </div>
    <div class="user-container">
      <div class="user-box">
        <div>
          <img src="@/assets/images/Logo.png" alt="" />
        </div>
        <p>{{ userinfo.nickname }}</p>
        <div>
          <TagComponent :level="userinfo.tier" :tagList="badgeList" />
        </div>
        <p>현상금액 : ${{ userinfo.point }}</p>
        <p>전체 푼 횟수 : {{ userinfo.solvedCount }}</p>
        <p>정답률 : {{ userinfo.correctRate }}%</p>
      </div>
      <div class="radar">
        <div v-if="props.nickname">
          <RadarChart :nickname="props.nickname" />
        </div>
        <div v-else>
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
  justify-content: space-between;
}
.user-box {
  padding-left: 80px;
  width: 230px;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
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
