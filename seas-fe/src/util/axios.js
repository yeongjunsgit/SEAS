// axios.js

import axios from "axios";
import { useauthControllerStore } from "@/stores/authController.js";

// export default instance;
import { inject } from "vue";

export function localAxios() {
  // 인스턴스 생성
  const instance = axios.create({
    baseURL: "https://i10a609.p.ssafy.io/api", // 기본 URL 설정
    timeout: 5000, // 요청 시간 초과 설정
    headers: {
      "Content-Type": "application/json",
      // 추가적인 헤더 설정 가능
    },
  });

  // 인터셉터 추가
  instance.interceptors.request.use(
    (config) => {
      // 요청 전 로직 추가 가능
      // pinia 스토어 불러오기
      const authStore = useauthControllerStore();
      const accessToken = authStore.myAccessToken;

      // if (accessToken) {
      //     config.headers.Authorization = `Bearer ${accessToken}`;
      //   }
      config.headers.Authorization = `Bearer ${accessToken}`;

      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  instance.interceptors.response.use(
    async (response) => {
      // 응답 후 로직 추가 가능
      return response;
    },
    async (error) => {
      // 오류 응답에서 상태 코드를 가져옴
      console.log(error);
      const status = error.status;

      // 상태 코드가 401이면서 메시지가 "Unauthorized"일 경우 access Token이 만료됨
      if (status === 401 && error.response.message === "Unauthorized") {
        try {
          const newStore = useauthControllerStore();

          // access Token을 재발급 받는 요청
          const { data } = await axios.post("/auth/refresh", {
            memberId: newStore.myName,
            grantType: newStore.myGrantType,
            accessToken: newStore.myAccessToken,
            refreshToken: newStore.myRefreshToken,
          });

          // Store에 새로운 access Token 저장
          const store = inject("store");
          store.myAccessToken = data.data.accessToken;

          // 새로 받은 access Token으로 이전 요청 다시 보내기
          const config = error.config;
          config.headers.Authorization = `Bearer ${data.accessToken}`;
          return axios.request(config);
        } catch (refreshError) {
          // refresh token이 만료되었거나 다른 문제로 실패한 경우 로그인 페이지로 이동하거나 다른 작업을 수행
          console.error("Failed to refresh access token");
          // 예를 들어 로그인 페이지로 리다이렉트하거나 다른 작업 수행
          return Promise.reject(refreshError);
        }
      }

      // access Token 만료 오류가 아닌 다른 오류의 경우 오류 처리
      return Promise.reject(error);
    }
  );

  return instance;
}
