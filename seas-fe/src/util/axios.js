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
      // pinia 스토어 불러오기
      const authStore = useauthControllerStore();
      const accessToken = authStore.myAccessToken;

      if (accessToken) {
        // Check if 'headers' property exists in 'response', if not, create it
        if (!response.headers) {
          response.headers = {};
        }
        // Add 'accessToken' property to 'headers'
        response.headers.accessToken = accessToken;
      }

      return response;
    },
    async (error) => {
      // 오류 응답에서 상태 코드를 가져옴
      console.log(error);
      const status = error.response.data.status;

      // 상태 코드가 400이면서 메시지가 "Unauthorized"일 경우 access Token이 만료됨
      if (
        status === 400 &&
        error.response.data.message === "토큰의 유효 기간이 만료되었습니다."
      ) {
        try {
          const newStore = useauthControllerStore();

          // access Token을 재발급 받는 요청
          const { data } = await instance.post(
            "/auth/refresh",
            {
              memberId: newStore.myName,
              grantType: newStore.myGrantType,
              accessToken: newStore.myAccessToken,
              refreshToken: newStore.myRefreshToken,
            },
            function (response) {
              console.log(response);
            },
            function (error) {
              console.log(error);
            }
          );
          // Store에 새로운 access Token 저장
          newStore.myAccessToken = data.data.accessToken;

          // 새로 받은 access Token으로 이전 요청 다시 보내기
          const config = error.config;
          console.log(config);
          config.headers.Authorization = `Bearer ${newStore.myAccessToken}`;
          return axios.request(config);
        } catch (refreshError) {
          // refresh token이 만료되었거나 다른 문제로 실패한 경우 로그인 페이지로 이동
          console.error("Failed to refresh access token");
          console.log(error);
          alert("로그인 정보가 만료되어 다시 로그인이 필요합니다.");
          router.push("/auth");
        }
      }

      // access Token 만료 오류가 아닌 다른 오류의 경우 오류 처리
      return Promise.reject(error);
    }
  );

  return instance;
}
