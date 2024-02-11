import { localAxios } from "@/util/axios";

const local = localAxios();

function getQuizList(categoryId, success, fail) {
  local.get(`/quiz/${categoryId}`).then(success).catch(fail);
}

function getHint(categoryId, quizId, success, fail) {
  local.get(`/quiz/hint/${categoryId}/${quizId}`).then(success).catch(fail);
}

function sendAnswer(categoryId, quizId, userInput, success, fail) {
  local.post(`/quiz/answer/${categoryId}/${quizId}`, userInput).then(success).catch(fail);
}

export { getQuizList, getHint, sendAnswer };
