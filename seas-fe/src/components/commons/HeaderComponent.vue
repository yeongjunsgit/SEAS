<script setup>
import { ref, watch } from "vue";
import { useRouter } from "vue-router";
import { useauthControllerStore } from "@/stores/authController.js";
import { logOutRequest } from "@/api/login.js";
import { local } from "d3";
// import { storeToRefs } from "pinia";

const userStore = useauthControllerStore();
const isLogin = ref(false);
const router = useRouter();

watch(userStore, () => {
  if (userStore != null) {
    isLogin.value = userStore.isAuthenticated;
  }
});

const movePage = (destination) => {
  router.replace({ path: destination });
};

const removeStorage = function () {
  localStorage.removeItem("myName");
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
  localStorage.removeItem("myGrantType");
};

const logout = () => {
  // isLogin.value = false;
  logOutRequest(
    ({ data }) => {
      console.log("로그아웃 백에서 해줘 : " + data.data);
      removeStorage();
      userStore.resetState();
      console.log("Logged out");
      movePage("/");
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div class="header-container">
    <div class="header-content">
      <div class="home-menus"></div>
      <div class="home-title title-font" @click="movePage('/')">SEAS</div>
      <div class="home-menus header-font">
        <div class="menu">
          <button @click="movePage('/flashcard')">카드</button>
          <button @click="movePage('/quiz')">
            <p>퀴즈</p>
          </button>
          <button @click="movePage('/ranking')">랭킹</button>
        </div>

        <div class="menu">
          <button v-if="!isLogin" @click="movePage('/auth')">
            로그인 / 회원가입
          </button>

          <div v-else>
            <button @click="movePage('/mypage')">마이페이지</button>
            <button @click="logout">로그아웃</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
@import "@/assets/style/main.scss";

.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  min-height: 80px;
  background: transparent !important;
  box-shadow: none !important;
  padding-top: 1%;

  .header-content {
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    width: 100%;

    .home-title {
      width: 33%;
      color: white;
      font-weight: bolder;
      font-size: 3vw;
      text-align: center;

      &:hover {
        cursor: pointer;
      }
    }
    .home-menus {
      width: 33%;
      display: flex;
      flex-wrap: nowrap;
      color: white;
      gap: 1vw;

      font-size: 1.3vw;
      font-weight: bold;

      button {
        padding: 0 10px 0 10px;
      }
    }
  }
}
</style>
