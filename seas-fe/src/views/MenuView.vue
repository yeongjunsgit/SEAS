<script setup>
import MenuComponent from "@/components/selection/MenuComponent.vue";
const titles = ["암기", "퀴즈", "마이페이지"];
const contents = ["안녕하세요", "퀴즈입니다", "마이페이지입니다."];
const paths = ["/flashcard", "/quiz", "/mypage"];
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
</template>

<style scoped lang="scss">
@import url("@/assets/style/main.scss");
.menu-container {
    background-image: url("@/assets/images/menu_background.jpg");
    background-size: cover;
    width: 100%;
    height: 100%;
    position: absolute;
    display: flex;
    justify-content: space-evenly;
    z-index: 0;
}
</style>
