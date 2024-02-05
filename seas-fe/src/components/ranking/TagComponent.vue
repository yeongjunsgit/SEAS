<script setup>
const props = defineProps(["title", "tagNo"]);
</script>

<template>
    <div
        :class="{
            container: true,
            'text-font': true,
            'master-tag': tagNo === 0,
        }"
    >
        <p>{{ title }}</p>
    </div>
</template>

<style scoped lang="scss">
@import "@/assets/style/main.scss";
.container {
    border-radius: 20px;
    min-width: 15vh;
    margin: 1px 3px 0 3px;
    padding-top: 1px;
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

.master-tag {
    background-color: rgba(255, 0, 0, 1);
    border-color: red;
}
</style>
