import { localAxios } from "@/util/axios";

const local = localAxios();

function getRankList(param, success, fail) {
    local.get(`/ranking/list`, { params: param }).then(success).catch(fail);
}

function rankerSearch(input, success, fail) {
    local.get(`/ranking/search?search=${input}`).then(success).catch(fail);
}

export { getRankList, rankerSearch };
