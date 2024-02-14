import { localAxios } from "@/util/axios";

const local = localAxios();

function getCardData(category, success, fail) {
  local.get(`/flashcard?category=${category}`).then(success).catch(fail);
}

function deleteLike(cardId, success, fail) {
  local.delete(`/flashcard/${cardId}/favorite`).then(success).catch(fail);
}

function postLike(cardId, success, fail) {
  local.post(`/flashcard/${cardId}/favorite`).then(success).catch(fail);
}

function sendCardID(data, success, fail) {
  local.patch(`/flashcard/weight`, data).then(success).catch(fail);
}

function getLikeCard(cardId, success, fail) {
  local.post(`/flashcard/${cardId}`).then(success).catch(fail);
}

export { getCardData, deleteLike, postLike, sendCardID, getLikeCard };
