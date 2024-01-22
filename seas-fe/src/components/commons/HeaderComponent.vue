<script setup>
import { useRouter } from "vue-router";
const router = useRouter();

const goHome = () => {
    router.push({ path: "/" });
};

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
                        <v-toolbar-title
                            class="home-title text-center text-h4 font-weight-bold"
                            @click="goHome()"
                            >SEAS</v-toolbar-title
                        >
                    </v-col>
                    <v-col cols="4" class="align-self-center">
                        <v-toolbar-items>
                            <v-btn class="home-menus">카드</v-btn>
                            <v-btn class="home-menus">퀴즈</v-btn>
                            <v-btn class="home-menus">랭킹</v-btn>
                            <v-divider></v-divider>
                            <v-btn class="home-menus" @click="goMyPage()"
                                >마이페이지</v-btn
                            >
                            <v-btn class="home-menus">로그아웃</v-btn>
                        </v-toolbar-items>
                    </v-col>
                </v-row>
            </v-container>
        </v-toolbar>
    </div>
</template>

<style scoped lang="scss">
@font-face {
    font-family: "Ahnjunggeun";
    src: url("@/assets/fonts/Ahnjunggeun.ttf");
}
@font-face {
    font-family: "caveat";
    src: url("@/assets/fonts/caveat.ttf");
}
.home-title {
    font-family: "caveat";
    color: white;
    &:hover {
        cursor: pointer;
    }
}
.home-menus {
    font-family: "Ahnjunggeun";
    font-size: larger;
    font-weight: bold;
    color: white;
}
.transparent {
    background: transparent !important;
    box-shadow: none !important;
}
</style>
