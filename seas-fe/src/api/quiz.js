import { localAxios } from "@/util/axios";

const local = localAxios();

function getQuizList(categoryId, success, fail) {
    local.get(`/quiz/${categoryId}`).then(success).catch(fail);
}

function getHint(categoryId, quizId, success, fail) {
    local.get(`/quiz/hint/${categoryId}/${quizId}`).then(success).catch(fail);
}

function sendAnswer(categoryId, quizId, userInput, success, fail) {
    if (userInput == null) userInput = " ";
    local
        .post(`/quiz/answer/${categoryId}/${quizId}`, userInput)
        .then(success)
        .catch(fail);
}

function getResult(success, fail) {
    local.get(`/quiz/result`).then(success).catch(fail);
}

function getInitRank(success, fail) {
    local.get(`/quiz/current-tier`).then(success).catch(fail);
}

function getResultRank(prevTier, success, fail) {
    console.log(prevTier);
    local.post(`/quiz/tier`, { prevTier: prevTier }).then(success).catch(fail);
}
export {
    getQuizList,
    getHint,
    sendAnswer,
    getResult,
    getInitRank,
    getResultRank,
};
