<script setup>
import { onMounted } from "vue";
import MenuComponent from "@/components/selection/MenuComponent.vue";
const titles = ["카드", "퀴즈", "랭킹"];
const contents = ["안녕하세요", "퀴즈입니다", "랭킹입니다"];
const paths = ["/flashcard", "/quiz", "/ranking"];

// 이미지와 비디오의 경로
// const imageUrl = "path/to/image.jpg";
const videoUrl =
    "https://d2qkxc1ity7pm2.cloudfront.net/videos/menu_compressed.mp4";

// 이미지와 비디오를 사전 로딩하는 함수
onMounted(() => {
    // preloadResource(imageUrl, "image");
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
    <div class="menu-container">
        <MenuComponent
            v-for="idx in titles.length"
            :key="idx"
            :title="titles[idx - 1]"
            :content="contents[idx - 1]"
            :path="paths[idx - 1]"
        />
    </div>
    <video autoplay loop lazy>
        <source :src="videoUrl" type="video/mp4" />
    </video>
</template>

<style scoped lang="scss">
@import url("@/assets/style/main.scss");
.menu-container {
    width: 100%;
    height: 100%;
    position: absolute;
    display: flex;
    justify-content: space-evenly;
    z-index: 0;
    animation: fadeInUp 1.5s;
}

video {
    // opacity: 0.7;
    object-fit: cover;
    position: fixed;
    min-width: 100%;
    min-height: 100%;
    width: 100%;
    height: 100%;
    z-index: -1;
    filter: brightness(60%);
}
</style>
