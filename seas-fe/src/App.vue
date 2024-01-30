<script setup>
import { RouterView } from "vue-router";
import HeaderComponent from "./components/commons/HeaderComponent.vue";
</script>

<template>
    <header>
        <HeaderComponent />
    </header>
    <div class="main_conatiner">
        <RouterView />
    </div>
</template>

<style lang="scss">
* {
    margin: 0px;
    padding: 0px;
}
a {
    text-decoration: none;
}

header {
    position: absolute;
    z-index: 1;
    width: 100%;
}

.main_container {
    position: relative;
    z-index: 2;
}
</style>
