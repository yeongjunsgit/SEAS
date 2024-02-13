import { ref } from "vue";
import { defineStore } from "pinia";

// 로그인에서 사용될 Store 선언
export const useauthControllerStore = defineStore("authController", () => {
    const myName = ref(null);
    const myAccessToken = ref(null);
    const myRefreshToken = ref(null);
    const myGrantType = ref(null);

    return { myName, myAccessToken, myRefreshToken, myGrantType };
});
