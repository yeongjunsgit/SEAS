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
  <table>
    <caption>
      <span>{{ type }}</span>
      <span>아이콘</span>
    </caption>

    <tbody>
      <tr class="mycard" v-for="(category, n) in categories" :key="n">
        <th class="card-category">{{ category }}</th>
        <td>1 2 3</td>
      </tr>
    </tbody>
  </table>
</template>

<style scoped lang="scss">
@import url("@/assets/style/main.scss");

.mycard {
  margin-left: 55px;
}
table {
  margin-left: 55px;
}
.card-category {
  padding-right: 15px;
}
th,
td {
  padding: 20px;
}
</style>
