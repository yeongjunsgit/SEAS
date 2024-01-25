<script setup>
import { useRouter } from "vue-router";

const props = defineProps(["title", "content", "path"]);

const router = useRouter();

const goToPage = () => {
    router.push({ path: props.path });
};
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
    background-image: url("@/assets/images/menu.png");
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
