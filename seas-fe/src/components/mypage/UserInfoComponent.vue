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

const userinfo = ref(Object);

const props = defineProps(["nickname"]);
console.log(props.nickname);

const badgeList = ref([
  { id: 1, name: "알고리즘" },
  { id: 2, name: "네트워크" },
  { id: 3, name: "자료구조" },
  { id: 4, name: "운영체제" },
  { id: 5, name: "컴퓨터구조" },
  { id: 6, name: "데이터베이스" },
]);

// 전역 Axios 사용
const getInitUserInfo = async () => {
  try {
    if (props.nickname) {
      // axios함수를 통해 데이터를 불러온다.
      getUserInfo(
        props.nickname,
        ({ data }) => {
          userinfo.value = data.data;
        },
        (error) => {
          console.log(error);
        }
      );
      getUserBadge(
        props.nickname,
        ({ data }) => {
          userinfo.value = data.data;

          const idArray = [1, 2, 3, 4, 5, 6];
          const tmpArray = idArray.filter(
            (item) => !data.data.map((e) => e.id).includes(item)
          );

          for (var i = 0; i < badgeList.value.length; i++) {
            if (tmpArray.some((e) => e === badgeList.value[i].id)) {
              badgeList.value.splice(i, 1);
              i--;
            }
          }
        },
        (error) => {
          console.log(error);
        }
      );
    } else {
      getMyInfo(
        ({ data }) => {
          userinfo.value = data.data;
        },
        (error) => {
          console.log(error);
        }
      );
      getMyBadge(
        ({ data }) => {
          userinfo.value = data.data;

          const idArray = [1, 2, 3, 4, 5, 6];
          const tmpArray = idArray.filter(
            (item) => !data.data.map((e) => e.id).includes(item)
          );

          for (var i = 0; i < badgeList.value.length; i++) {
            if (tmpArray.some((e) => e === badgeList.value[i].id)) {
              badgeList.value.splice(i, 1);
              i--;
            }
          }
        },
        (error) => {
          console.log(error);
        }
      );
    }
  } catch (error) {
    console.log(error);
  }
};
onMounted(() => {
  getInitUserInfo();
});
</script>

<template>
  <div class="info-container">
    <div>
      <h2 class="text-center">유저 정보</h2>
      <button class="del">회원탈퇴</button>
    </div>
    <div class="user-container">
      <div class="user-box">
        <div>
          <img src="@/assets/images/Logo.png" alt="" />
        </div>
        <p>{{ userinfo.nickname }}</p>
        <div v-if="props.nickname">
          <TagComponent :level="userinfo.tier" :tagList="badgeList" />
        </div>
        <div v-else>
          <TagComponent :level="userinfo.tier" />
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

.del {
  padding-left: 20%;
}
</style>
