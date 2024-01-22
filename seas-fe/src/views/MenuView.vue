<script setup>
import MenuComponent from "@/components/selection/MenuComponent.vue";
</script>

<template>
    <div class="menu-container">
        <MenuComponent title="Memorize" buttonText="GO" path="/card" />
        <MenuComponent title="Quiz" buttonText="GO" path="/quiz" />
        <MenuComponent title="Mypage" buttonText="GO" path="/mypage" />
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
