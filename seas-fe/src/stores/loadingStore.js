// pinia store 생성
import { defineStore } from "pinia";
import { ref, computed } from "vue";

// 로그인에서 사용될 Store 선언
export const usePageStore = defineStore("page", () => {
    const visitedPages = ref({
        home: false,
        menu: false,
        authenicate: false,
        premypage: false,
        flashcard: false,
        ranking: false,
        quiz: false,
    });

    const markPageAsVisited = (pageName) => {
        console.log("페이지 방문처리가 되었습니다.");
        visitedPages[pageName] = true;
    };

    const shouldDelay = (pageName) => {
        return !visitedPages[pageName];
    };

    return {
        visitedPages,
        markPageAsVisited,
        shouldDelay,
    };
});
