import { localAxios } from "@/util/axios";

const local = localAxios();

function loginRequest(payload, success, fail) {
  local.post(`/auth/signin`, payload).then(success).catch(fail);
}

function signupRequest(payload, success, fail) {
  local.post(`/auth/signup`, payload).then(success).catch(fail);
}

function checkIdRequest(payload, success, fail) {
  console.log(payload);
  local.post(`/auth/check-id`, payload).then(success).catch(fail);
}

function refreshRequest(payload, success, fail) {
  local.post(`/auth/refresh`, payload).then(success).catch(fail);
}

export { loginRequest, signupRequest, checkIdRequest, refreshRequest };
