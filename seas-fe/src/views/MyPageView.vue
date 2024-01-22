<script setup>
import MyPageComponent from "@/components/mypage/MyPageComponent.vue";
</script>

<template>
  <v-container>
    <div class="background"></div>
    <div class="rotate">
      <br />
      <br />
      <h1 class="text-white mx-10">My Page</h1>

      <v-row>
        <v-col cols="6">
          <MyPageComponent />
        </v-col>
        <v-col cols="6">
          <MyPageComponent />
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>

<style scoped lang="scss">
.background {
  rotate: 90deg;
  width: 100%;
  height: 100vh;
  overflow: hidden;
  margin: 0;
  background-image: url("@/assets/images/mypage_background.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}
.rotate {
  rotate: -90deg;
}
</style>
