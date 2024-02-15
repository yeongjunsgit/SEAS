import { localAxios } from "@/util/axios";

const local = localAxios();

function getRankList(success, fail) {
    local.get(`/ranking/list`).then(success).catch(fail);
}

function rankerSearch(input, success, fail) {
    local.get(`/ranking/search?search=${input}`).then(success).catch(fail);
}

export { getRankList, rankerSearch };
