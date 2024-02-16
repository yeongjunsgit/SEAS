<script setup>
import { onMounted } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();

const goMenu = () => {
    router.push({ path: "/menu" });
};

// 비디오의 경로
const videoUrl =
    "https://d2qkxc1ity7pm2.cloudfront.net/videos/home_compressed.mp4";

// 비디오를 사전 로딩하는 함수
onMounted(() => {
    preloadResource(videoUrl, "video");
});

function preloadResource(url, type) {
    const link = document.createElement("link");
    link.rel = "preload";
    link.as = type;
    link.href = url;
    document.head.appendChild(link);
}
</script>

<template>
    <div class="home-container">
        <video muted autoplay loop>
            <source :src="videoUrl" type="video/mp4" />
        </video>
        <!-- 홈 화면 좌측 부분 -->
        <div class="main-content">
            <div class="text-font">
                <h1>분당 CS 10개</h1>
                <h1>카드를 통한 간단한 암기</h1>
                <h1>퀴즈를 통한 복기</h1>
                <h1>점수를 통한 랭킹 시스템</h1>
                <h1>로그인하여 사이트의 기능을 모두 사용하세요!</h1>
            </div>
            <button class="wave-button text-font" @click="goMenu()">
                <span class="wave"></span>
                <p>시작</p>
            </button>
        </div>
        <!-- 홈 화면 우측 부분 -->
        <div></div>
    </div>
</template>

<style scoped lang="scss">
@import url("@/assets/style/main.scss");

* {
    width: 100%;
    height: 100%;
}
.home-container {
    display: flex;
    width: 100%;
    height: 100%;
    justify-content: space-around;
}

.main-content {
    margin-top: 25vh;
    color: white;
    text-align: center;
    position: relative;
    animation: fadeInLeft 1.5s;

    h1 {
        margin-bottom: 5%;
    }
    p {
        margin-top: 3%;
    }
}
</style>
