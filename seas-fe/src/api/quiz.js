import { localAxios } from "@/util/axios";

const local = localAxios();

function getQuizList(categoryId, success, fail) {
    local.get(`/quiz/${categoryId}`).then(success).catch(fail);
}

function getHint(quizId, success, fail) {
    local.get(`/quiz/${quizId}/hint`).then(success).catch(fail);
}

function sendAnswer(quizId, param, success, fail) {
    local
        .get(`/quiz/${quizId}/answer`, { params: param })
        .then(success)
        .catch(fail);
}

export { getQuizList, getHint, sendAnswer };
