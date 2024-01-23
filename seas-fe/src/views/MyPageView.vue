<script setup>
import MyPageComponent from "@/components/mypage/MyPageComponent.vue";
// import RadarChart from "@/components/mypage/RadarChart.vue";
</script>

<template>
  <v-app class="mypage-background">
    <v-container>
      <br />
      <br />
      <h1 class="text-white mx-10">My Page</h1>
      <v-row class="mypage-component-background justify-center">
        <!-- <MyPageComponent /> -->
        <v-col cols="12">
          <h2>유저정보</h2>
        </v-col>

        <v-col cols="6">
          <v-row class="user-box justify-center">
            <v-col cols="5" class="text-center">
              <div>이미지</div>
              <p>김싸피</p>
              <p>현상금액</p>
              <p>전체 푼 횟수 : 40</p>
              <p>정답률 : 75%</p>
            </v-col>
            <v-col cols="7">
              <p>차트</p>
              <div>
                <!-- <RadarChart /> -->
              </div>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="5" class="grass">
          <div>잔디</div>
        </v-col>
      </v-row>
      <v-row
        class="mypage-component-background"
        align="center"
        justify="center"
      >
        <v-col v-for="i in 6" :key="i" cols="4">
          <v-card class="mx-auto mt-7" max-width="344">
            <v-card-item>
              <div>
                <div class="text-overline mb-1">카테고리</div>
                <div>그래프</div>
              </div>
            </v-card-item>
          </v-card>
        </v-col>
      </v-row>
      <v-row class="mypage-component-background">
        <v-col cols="6">
          <div><h1>즐겨찾기</h1></div>
        </v-col>
        <v-col cols="6">
          <div><h1>오답노트</h1></div>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<style scoped lang="scss">
.mypage-background {
  overflow: hidden;
  height: auto;
  background-image: url("@/assets/images/mypage_background.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}
.rotate {
  rotate: -90deg;
}
.mypage-component-background {
  width: 100%;
  height: fit-content;
  overflow: hidden;
  padding: 50px;
  background-image: url("@/assets/images/mypage_paper.png");
  background-size: cover;
  background-repeat: no-repeat;
  background-position-x: center;
}
.user-box {
  margin-left: 50px;
  border: 2px solid black;
  border-radius: 2cap;
}
.grass {
  margin-left: 20px;
  border: 2px solid black;
  border-radius: 2cap;
}
</style>
