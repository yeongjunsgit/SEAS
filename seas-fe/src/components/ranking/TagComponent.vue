<script setup>
import { ref } from "vue";
const props = defineProps(["rankerInfo", "tagCount"]);
const levelList = [
    "선원",
    "포수장",
    "갑판장",
    "항해사",
    "일등항해사",
    "부선장",
    "선장",
];

// 말풍선 ==================================

const tagList = ref([
    { title: "알고리즘" },
    { title: "네트워크" },
    { title: "자료구조" },
    { title: "데이터베이스" },
    { title: "컴퓨터구조" },
    { title: "운영체제" },
]);

const isTooltipVisible = ref(false);

const showTooltip = () => {
    isTooltipVisible.value = true;
};

const hideTooltip = () => {
    isTooltipVisible.value = false;
};
</script>

<template>
    <div class="container-wrapper">
        <div
            class="container"
            :class="{ 'master-tag': props.tagCount == 6 }"
            @mouseover="showTooltip"
            @mouseleave="hideTooltip"
        >
            <p>{{ levelList[tagCount] }}</p>
            <div v-if="isTooltipVisible" class="tooltip">
                <p v-for="tag in props.rankerInfo" :key="tag">
                    {{ tagList[tag].title }}
                </p>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss">
@import "@/assets/style/main.scss";
.container-wrapper {
    position: relative;
    cursor: pointer;
}

.container {
    display: flex;
    justify-content: center;
    border-radius: 20px;
    background-color: $primary-color;

    .tooltip {
        position: absolute;
        top: -30px;
        left: 50%;
        transform: translateX(-50%);
        background-color: #f1f1f1;
        padding: 4px;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
        visibility: hidden;
        opacity: 0;
        display: flex;
        z-index: 3;

        p {
            padding: 0 10px 0 10px;
            white-space: nowrap;
            color: black;
        }
    }

    &:hover .tooltip {
        visibility: visible;
        opacity: 1;
    }

    p {
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
