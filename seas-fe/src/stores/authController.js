import { ref, computed } from "vue";
import { defineStore } from "pinia";

// 예시
// export const useCounterStore = defineStore('counter', () => {
//   const count = ref(0)
//   const doubleCount = computed(() => count.value * 2)
//   function increment() {
//     count.value++
//   }

//   return { count, doubleCount, increment }
// })

// 로그인에서 사용될 Store 선언
export const useauthControllerStore = defineStore("authController", () => {
  const myName = ref(null);
  const myAccessToken = ref(null);
  const myRefreshToken = ref(null);
  const myGrantType = ref(null);

  return { myName, myAccessToken, myRefreshToken, myGrantType };
});
