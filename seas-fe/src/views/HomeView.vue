<script setup>
import { useRouter } from "vue-router";
const router = useRouter();

const goMenu = () => {
    router.push({ path: "/menu" });
};
</script>

<template>
    <div class="home_container">
        <div></div>
        <!-- 홈 화면 우측 부분 -->
        <div>
            <div></div>
            <button class="main__wave_button" @click="goMenu()">
                <span class="wave"></span>
                <p>Button</p>
            </button>
        </div>
    </div>
</template>

<style scoped lang="scss">
.home_container {
    display: flex;
    width: 100%;
    height: 100%;
    justify-content: space-around;
}
.main__wave_button {
    display: inline-block;
    width: 100px;
    height: 61px;
    position: relative;
    font-size: 20px;
    cursor: pointer;
    padding: 0.8em 1em;
    border: 3px solid #333;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 1px 3px rgba(black, 0.2);
    background: transparent;
    transform: translate3d(0, 0, 0);
    z-index: 1;

    .wave {
        opacity: 1;
        position: absolute;
        bottom: 3%;
        left: 50%;
        background: rgb(0, 0, 0);
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

    &:hover p {
        position: relative;
        z-index: 3;
        transition: 1s ease-out;
        color: white;
        &:not(:hover) {
            transition: 1s ease-out;
        }
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
