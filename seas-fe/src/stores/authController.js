import { ref } from "vue";
import { defineStore } from "pinia";

// 로그인에서 사용될 Store 선언
export const useauthControllerStore = defineStore("authController", () => {
  const myName = ref(null);
  const myAccessToken = ref(null);
  const myRefreshToken = ref(null);
  const myGrantType = ref(null);

  // const myName = ref("super");
  // const myAccessToken = ref(
  //   "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MSIsImV4cCI6MTcwNzc0OTkxNn0.wbL2qm5qO1KQSU1EkPvDS6TLKYgozHugzxPmDhiGbtdYTY3t-WZFxciPI9ttry0uv8SSAKVyOAiueObr-GjLSA"
  // );
  // const myRefreshToken = ref(
  //   "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdXBlciIsImV4cCI6MTcwODQwNzA5Nn0.KVCmYNaP4IduCMJiQ23t7-eUD3mma2NjvMCmQI6f62baD4QXgekSUH8B84QmVBxSe3rwQuTnBMzcQXLFJjSF3A"
  // );
  // const myGrantType = ref("Bearer ");

  return { myName, myAccessToken, myRefreshToken, myGrantType };
});
