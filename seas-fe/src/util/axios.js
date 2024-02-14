// axios.js

import axios from "axios";
import { useauthControllerStore } from "@/stores/authController.js";

// export default instance;
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

      config.headers.Authorization = `Bearer ${accessToken}`;

      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  instance.interceptors.response.use(
    (response) => {
      console.log(response);
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
    (error) => {
      return Promise.reject(error);
    }
  );
  return instance;
}
