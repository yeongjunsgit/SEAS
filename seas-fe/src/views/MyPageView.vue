<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import UserInfoComponent from "@/components/mypage/UserInfoComponent.vue";
import CategoryComponent from "@/components/mypage/CategoryComponent.vue";
import LineChartVue from "@/components/mypage/LineChart.vue";
import GrassComponentVue from "@/components/mypage/GrassComponent.vue";
import { getLineChart } from "@/api/mypage.js";
import { useauthControllerStore } from "@/stores/authController";
import axios from "axios";

const userStore = useauthControllerStore();
const user_access_token = userStore.myAccessToken;

const router = useRouter();

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

// 전역 Axios 사용
const getInitLineChart = () => {
  // axios함수를 통해 데이터를 불러온다.
  getLineChart(
    ({ data }) => {
      categoryObj.value = data.data;

      loaded.value = true;
    },
    (error) => {
      console.log(error);
    }
  );
};

const removeStorage = function () {
  sessionStorage.removeItem("myName");
  sessionStorage.removeItem("accessToken");
  sessionStorage.removeItem("refreshToken");
  sessionStorage.removeItem("myGrantType");
};

const getInitDelete = () => {
  router.push({ name: "home" });

  removeStorage();
  userStore.resetState();

  axios.delete("https://i10a609.p.ssafy.io/api/auth/quit", {
    headers: {
      Authorization: `Bearer ${user_access_token}`,
      "Content-Type": "application/json",
    },
  });
};

const confirmDelete = async () => {
  const userConfirmed = window.confirm("정말 탈퇴하시려구요..?");

  if (userConfirmed) {
    await getInitDelete();
  } else {
    console.log("휴");
  }
};

onMounted(async () => {
  loaded.value = false;
  try {
    getInitLineChart();
  } catch (error) {
    console.error(error);
  }
});
</script>

<template>
  <div class="mypage-background text-font">
    <div class="container">
      <div class="mypage-component-background userinfo">
        <div style="text-align: center">
          <UserInfoComponent />
          <button class="del" @click="confirmDelete()">회원탈퇴</button>
        </div>
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
  width: 80vw;
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
  place-items: center;
  grid-template-columns: repeat(3, 1fr);
}

.transparent-card {
  background: transparent;
  padding-inline: 20px;
  width: 350px;
}
.mycard {
  display: grid;
  place-items: center;
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
    padding-top: 70px;
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

.del {
  border: 1px solid black;
  border-radius: 3cap;
  padding-inline: 5px;
  padding-top: 3px;
  &:hover {
    background-color: rgba(255, 0, 0, 0.5);
  }
}
</style>
