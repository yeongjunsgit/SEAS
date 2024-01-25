<script setup>
import { useRouter } from "vue-router";
const router = useRouter();

const goHome = () => {
    router.push({ path: "/" });
};

const goLogin = () => {
    router.push({ path: "/auth" });
};

const goMyPage = () => {
    router.push({ path: "/mypage" });
};

const goCard = () => {
    router.push({ path: "/flashcard" });
};

const goRank = () => {
    router.push({ path: "/ranking" });
};
</script>

<template>
    <div class="header-container">
        <v-toolbar class="transparent">
            <v-container>
                <v-row justify="end">
                    <v-col cols="4">
                        <v-toolbar-title
                            class="home-title text-center text-h4 font-weight-bold title-font"
                            @click="goHome()"
                            >SEAS</v-toolbar-title
                        >
                    </v-col>
                    <v-col cols="4" class="align-self-center">
                        <v-toolbar-items class="header-button">
                            <v-btn class="home-menus" @click="goCard()"
                                >카드</v-btn
                            >
                            <v-btn class="home-menus">퀴즈</v-btn>
                            <v-btn class="home-menus" @click="goRank()"
                                >랭킹</v-btn
                            >
                            <v-divider></v-divider>
                            <v-btn class="home-menus" @click="goLogin()"
                                >로그인</v-btn
                            >
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
@import "@/assets/style/main.scss";

.header-container {
    min-height: 80px;
    vertical-align: middle;
}
.home-title {
    color: white;
    &:hover {
        cursor: pointer;
    }
}
.home-menus {
    font-size: larger;
    font-weight: bold;
    color: white;
}
.transparent {
    background: transparent !important;
    box-shadow: none !important;
}
</style>
