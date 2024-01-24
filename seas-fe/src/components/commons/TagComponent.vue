<script setup>
const props = defineProps(["title"]);
</script>

<template>
    <div class="container text-font">
        <p>{{ title }}</p>
    </div>
</template>

<style scoped lang="scss">
@import "@/assets/style/main.scss";
.container {
    border: 3px solid $primary-color;
    border-radius: 20px;
    padding: 1%;
    min-width: 18vh;
    margin: 0 3px 0 3px;

    p {
        color: rgba(81, 60, 58, 1);
        text-align: center;
        font-weight: bolder;
        font-size: medium;
    }
}
</style>
