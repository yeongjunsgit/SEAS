import { localAxios } from "@/util/axios";

const local = localAxios();

function getMyInfo(success, fail) {
  local.get(`/mypage/my-info`).then(success).catch(fail);
}

function getUserInfo(nickname, success, fail) {
  local.get(`/mypage/my-info?nickname=${nickname}`).then(success).catch(fail);
}

function getMyBadge(success, fail) {
  local.get(`/mypage/badge`).then(success).catch(fail);
}

function getUserBadge(nickname, success, fail) {
  local.get(`/mypage/badge?nickname=${nickname}`).then(success).catch(fail);
}

function getMyRadarChart(success, fail) {
  local.get(`/mypage/quiz-rate`).then(success).catch(fail);
}

function getRadarChart(nickname, success, fail) {
  local.get(`/mypage/quiz-rate?nickname=${nickname}`).then(success).catch(fail);
}

function getCalendarChart(success, fail) {
  local.get(`/mypage/history`).then(success).catch(fail);
}

function getLineChart(success, fail) {
  local.get(`/mypage/graph`).then(success).catch(fail);
}

function getFavorite(success, fail) {
  local.get(`/mypage/flashcard/favorite`).then(success).catch(fail);
}

function getIncorrect(success, fail) {
  local.get(`/mypage/incorrect`).then(success).catch(fail);
}
export {
  getMyInfo,
  getUserInfo,
  getMyBadge,
  getUserBadge,
  getMyRadarChart,
  getRadarChart,
  getCalendarChart,
  getLineChart,
  getFavorite,
  getIncorrect,
};
