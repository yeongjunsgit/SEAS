<script setup>
import { useRouter } from "vue-router";
const router = useRouter();

const goMenu = () => {
    router.push({ path: "/menu" });
};
</script>

<template>
    <div class="home-container">
        <video muted autoplay loop>
            <source src="@/assets/videos/home.mp4" type="video/mp4" />
            <strong>Your browser does not support the video tag.</strong>
        </video>
        <!-- 홈 화면 좌측 부분 -->
        <div class="main-content">
            <div>
                <h1>분당 CS 10개</h1>
                <h1>카드를 통한 간단한 암기</h1>
                <h1>퀴즈를 통한 복기</h1>
                <h1>점수를 통한 랭킹 시스템</h1>
            </div>
            <button class="wave-button" @click="goMenu()">
                <span class="wave"></span>
                <p>Get Started</p>
            </button>
        </div>
        <!-- 홈 화면 우측 부분 -->
        <div></div>
    </div>
</template>

<style scoped lang="scss">
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

video {
    object-fit: cover;
    position: fixed;
    min-width: 100%;
    min-height: 100%;
    width: 100%;
    height: 100%;
    z-index: -1;
}

.main-content {
    margin-top: 10%;
    color: white;
    text-align: center;

    h1 {
        margin-bottom: 5%;
    }
}
.wave-button {
    display: inline-block;
    width: 150px;
    height: 60px;
    position: relative;
    font-size: 20px;
    cursor: pointer;
    border: 3px solid white;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 1px 3px rgba(black, 0.2);
    background: transparent;
    transform: translate3d(0, 0, 0);
    z-index: 1;
    text-align: center;

    .wave {
        opacity: 1;
        position: absolute;
        bottom: 3%;
        left: 50%;
        background: rgb(45, 171, 255);
        width: 300px;
        height: 300px;
        margin-left: -150px;
        margin-bottom: -301px;
        transform-origin: 48% 52%;
        border-radius: 43%;
        vertical-align: middle;

        transition-duration: 1.5s;
        transition-property: margin-bottom;
        transition-timing-function: ease;
        z-index: 2;
    }

    &:hover .wave {
        margin-bottom: -240px;
        animation: rotate 2000ms infinite linear;
    }

    p {
        top: 20%;
        position: relative;
        z-index: 3;
        transition: 1s ease-out;
    }

    &:hover p {
        color: black;
    }
}

@keyframes rotate {
    from {
        transform: rotate(0deg);
    }
    from {
        transform: rotate(360deg);
    }
}
</style>
