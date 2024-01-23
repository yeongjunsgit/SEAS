<script setup>
import MenuComponent from "@/components/selection/MenuComponent.vue";

const contents = ["안녕하세요", "퀴즈입니다", "마이페이지입니다."];
</script>

<template>
    <div class="menu-container">
        <MenuComponent title="Memorize" :content="contents[0]" path="/card" />
        <MenuComponent title="Quiz" :content="contents[1]" path="/quiz" />
        <MenuComponent title="Mypage" :content="contents[2]" path="/mypage" />
    </div>
</template>

<style scoped lang="scss">
@import url("@/assets/style/main.scss");
.menu-container {
    background-image: url("@/assets/images/menu_background.jpg");
    background-size: cover;
    width: 100%;
    height: 100%;
    display: flex;
    position: absolute;
    justify-content: space-evenly;
    z-index: 0;
}
</style>
