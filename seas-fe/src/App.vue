<script setup>
import { RouterLink, RouterView } from "vue-router";
import HeaderComponent from "./components/commons/HeaderComponent.vue";
</script>

<template>
    <header>
        <HeaderComponent />
    </header>

    <RouterView />
</template>

<style lang="scss">
* {
    margin: 0px;
    padding: 0px;
}
a {
    text-decoration: none;
}
</style>
