<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const goMyPage = () => {
  router.push({ path: "/mypage" });
};
</script>

<template>
  <div>
    <v-toolbar class="transparent">
      <v-container>
        <v-row justify="end">
          <v-col cols="4">
            <v-toolbar-title class="text-center">SEAS</v-toolbar-title>
          </v-col>
          <v-col cols="4" class="align-self-center">
            <v-toolbar-items>
              <v-btn>카드</v-btn>
              <v-btn>퀴즈</v-btn>
              <v-btn>랭킹</v-btn>
              <v-divider></v-divider>
              <v-btn @click="goMyPage()">마이페이지</v-btn>
              <v-btn>로그아웃</v-btn>
            </v-toolbar-items>
          </v-col>
        </v-row>
      </v-container>
    </v-toolbar>
  </div>
</template>

<style scoped lang="scss">
.transparent {
  background: transparent !important;
  box-shadow: none !important;
}
</style>
