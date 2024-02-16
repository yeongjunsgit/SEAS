import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const myPageStore = defineStore("mypage", () => {
  const cardKeywords = ref([]);
  const getKeywords = computed(() => cardKeywords);
  function addKeyword(keywords) {
    cardKeywords.value = keywords;
  }
  return { cardKeywords, getKeywords, addKeyword };
});
