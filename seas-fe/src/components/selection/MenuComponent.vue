<script setup>
import { useRouter } from "vue-router";

const props = defineProps(["title", "content", "path"]);

const router = useRouter();

const goToPage = () => {
    router.push({ path: props.path });
};

// 이미지와 비디오의 경로
const imageUrl = "https://d2qkxc1ity7pm2.cloudfront.net/images/menu.png";
const videoUrl = "https://d2qkxc1ity7pm2.cloudfront.net/videos/menu.mp4";

// 이미지와 비디오를 사전 로딩하는 함수
onMounted(() => {
    preloadResource(imageUrl, "image");
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
    <div class="menu flex-center">
        <div class="menu-text">
            <div class="title text-font">
                <p>{{ title }}</p>
            </div>
            <div class="content text-font">
                <p>{{ content }}</p>
            </div>
        </div>
        <div class="flex-center">
            <button class="menu-button" @click="goToPage">
                <p>바로가기</p>
            </button>
        </div>
    </div>
</template>

<style scoped lang="scss">
@import url("@/assets/style/main.scss");

.menu {
    flex-direction: column;
    justify-content: space-evenly;
    margin-top: 10%;
    position: relative;
    background-image: url("https://d2qkxc1ity7pm2.cloudfront.net/images/menu.png");
    background-size: contain;
    min-width: 300px;
    max-height: 430px;

    .menu-text {
        text-align: center;
    }

    &:hover {
        transition-duration: 0.5s;
        scale: 1.1;
    }

    .title {
        font-size: 50px;
        font-weight: bold;
    }

    .content {
        font-size: 25px;
    }
}
.menu-button p {
    margin-top: 5%;
}
</style>
