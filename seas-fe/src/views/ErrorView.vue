<script setup>
import { useRouter } from "vue-router";
const router = useRouter();

const goMenu = () => {
    router.push({ path: "/" });
};
</script>

<template>
    <div class="error-container">
        <div class="container">
            <p class="title">페이지를 찾을 수 없습니다.</p>
            <p class="text">에러가 발생했거나 찾을 수 없는 페이지 입니다.</p>
            <button class="wave-button text-font" @click="goMenu()">
                <span class="wave"></span>
                <p>메인으로</p>
            </button>
        </div>
    </div>
</template>

<style scoped lang="scss">
@import url("@/assets/style/main.scss");
.error-container {
    width: 100%;
    height: 100%;
    position: absolute;
    background-image: url("@/assets/images/error.png");
    background-size: cover;
    background-position: 0 70%;
    z-index: 1;

    .container {
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        text-align: center;
        align-items: center;
        position: fixed;
        left: 50vw;
        top: 50vh;

        width: 700px;
        height: 250px;
        padding: 1%;
        .wave-button {
            text-align: center;
            width: 200px;
            padding-bottom: 3vh;

            &:hover p {
                color: white;
                transition: 1s;
            }
        }

        .title {
            font-size: xxx-large;
            font-weight: bolder;
        }
        .text {
            font-size: larger;
        }
    }
}
</style>
