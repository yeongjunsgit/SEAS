<script setup>
// 부모로부터 전달된 `close` 이벤트를 처리합니다.
const emit = defineEmits(["close"]);

const rankerInfo = defineProps(["rankerInfo"]);

// 모달 닫기 함수
const outerClick = () => {
    emit("close");
};
</script>

<template>
    <div class="modal-overlay" @click="outerClick">
        <div class="modal-content" @click.stop>
            <!-- 모달 내용을 슬롯으로 받아옵니다. -->
            <slot :rankerInfo="rankerInfo"></slot>
        </div>
    </div>
</template>

<style scoped lang="scss">
@import "@/assets/style/main.scss";
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 4;
}

.modal-content {
    background: white;
    padding: 20px;
    border-radius: 8px;
    text-align: center;
}

.search-button {
    border: 2px solid $primary-color;
    border-radius: 10px;
    margin-left: 10px;
    padding: 2% 1% 0 1%;
    min-width: 50px;

    &:hover {
        background-color: $primary-color;
        color: white;
        transition-duration: 0.5s;
    }
}
</style>
