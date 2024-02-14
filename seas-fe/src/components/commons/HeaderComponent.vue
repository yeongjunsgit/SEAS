<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
// import { useUserStore } from "@/stores/user-store";
// import { storeToRefs } from "pinia";

// const userStore = useUserStore();
// const { isLogin } = storeToRefs(userStore);
const router = useRouter();

// 임시 변수
const isLogin = ref(false);

const movePage = (destination) => {
  // 로그인 임시 코드
  if (destination == "/auth") {
    isLogin.value = true;
  } else {
    router.replace({ path: destination });
  }
};

const logout = () => {
  // 임시 코드
  isLogin.value = false;
  console.log("Logged out");
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
          <button v-if="!isLogin" @click="movePage('/auth')">로그인 / 회원가입</button>

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
