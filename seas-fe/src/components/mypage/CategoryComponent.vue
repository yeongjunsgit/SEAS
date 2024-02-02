<script setup>
import { useRouter } from "vue-router";

const categories = [
  "자료구조",
  "알고리즘",
  "운영체제",
  "데이터베이스",
  "네트워크",
  "컴퓨터 구조",
];

const props = defineProps(["type", "cardNum"]);

const router = useRouter();
</script>

<template>
  <div class="d-flex ml-6">
    <h1>{{ type }}</h1>
    <h4 class="align-self-center ml-2">아이콘</h4>
  </div>
  <div>1 2 3</div>
</template>

<style scoped lang="scss">
@import url("@/assets/style/main.scss");

.transparent-list {
  background: transparent;
}

// .temp {
//   display: flex;
//   flex-direction: row;
// }
</style>
