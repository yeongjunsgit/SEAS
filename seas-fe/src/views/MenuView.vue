<script setup>
import { onMounted } from "vue";
import MenuComponent from "@/components/selection/MenuComponent.vue";
const titles = ["카드", "퀴즈", "랭킹"];
const contents = [
    ["플래시 카드를 이용한 간단한 암기를 통해 선장이 될 준비를 하세요!"],
    ["지닌 CS 지식으로 퀴즈를 풀고 본인의 몸 값을 올리세요!"],
    ["올라간 자신의 몸 값을 확인하세요!"],
];
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
    animation: fadeInUp 1s;
}

video {
    filter: brightness(60%);
}
</style>
