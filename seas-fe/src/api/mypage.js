import { localAxios } from "@/util/axios";

const local = localAxios();

function getUserInfo(success, fail) {
  local.get(`/mypage/my-info`).then(success).catch(fail);
}

function getUserBadge(success, fail) {
  local.get(`/mypage/badge`).then(success).catch(fail);
}

function getRadarChart(success, fail) {
  local.get(`/mypage/quiz-rate`).then(success).catch(fail);
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
  getUserInfo,
  getUserBadge,
  getRadarChart,
  getCalendarChart,
  getLineChart,
  getFavorite,
  getIncorrect,
};
