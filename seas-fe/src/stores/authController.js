import { ref, computed } from "vue";
import { defineStore } from "pinia";

// 로그인에서 사용될 Store 선언
export const useauthControllerStore = defineStore("authController", () => {
    const myName = ref(null);
    const myAccessToken = ref(null);
    const myRefreshToken = ref(null);
    const myGrantType = ref(null);

    const isAuthenticated = computed(() => {
        // myAccessToken이 존재하면 true, 아니면 false 반환
        return !!myAccessToken.value;
    });

    const resetState = () => {
        myName.value = null;
        myAccessToken.value = null;
        myRefreshToken.value = null;
        myGrantType.value = null;
    };

    return {
        myName,
        myAccessToken,
        myRefreshToken,
        myGrantType,
        isAuthenticated,
        resetState,
    };
});
