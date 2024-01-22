<script setup>
import { useRouter } from "vue-router";

const props = defineProps(["title", "buttonText", "path"]);

const router = useRouter();

const goToPage = () => {
    router.push({ path: props.path });
};
</script>

<template>
    <div class="menu flex-center">
        <div class="menu-text">
            <h2>{{ title }}</h2>
        </div>
        <div class="flex-center">
            <button class="menu-button" @click="goToPage">
                {{ buttonText }}
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
}
</style>
