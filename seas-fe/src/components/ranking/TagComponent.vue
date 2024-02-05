<script setup>
import { ref } from "vue";
const props = defineProps(["tagCount"]);

const levelList = [
    "선원",
    "포수장",
    "갑판장",
    "항해사",
    "일등항해사",
    "부선장",
    "선장",
];
</script>

<template>
    <div class="container" :class="{ 'master-tag': tagCount == 6 }">
        <p>{{ levelList[tagCount] }}</p>
    </div>
</template>

<style scoped lang="scss">
@import "@/assets/style/main.scss";
.container {
    display: flex;
    justify-content: center;
    border-radius: 20px;
    background-color: $primary-color;

    p {
        // color: rgba(81, 60, 58, 1);
        color: white;
        text-align: center;
        font-weight: 700;
        font-size: medium;
        display: flex;
        justify-content: center;
    }
}

.master-tag {
    background-color: #ff0000;
}
</style>
