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
    min-width: 18vh;
    margin: 0 3px 0 3px;
    background-color: $primary-color;

    p {
        // color: rgba(81, 60, 58, 1);
        color: white;
        text-align: center;
        font-weight: 700;
        font-size: medium;
        vertical-align: middle;
        margin-top: 3%;
    }
}
</style>
