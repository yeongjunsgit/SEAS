<script setup>
import TagComponent from "@/components/ranking/TagComponent.vue";
import Modal from "@/components/ranking/Modal.vue";
import { ref, onMounted, watch } from "vue";
import { getRankList, rankerSearch } from "@/api/rank.js";

const rankList = ref();
const rankData = ref();
const userInfo = ref();

// 데이터를 받았는지 여부를 판단할 변수 선언
const isRankData = ref(false);

// watch를 이용해서 데이터가 들어오면 위의 변수를 true 값으로 바꿈
// 따라서 v-if에서 위의 변수를 토대로 판단하여 데이터가 들어와야만 변경됨
const watchRankData = watch(rankData, () => {
    if (rankData.value.length > 0) {
        isRankData.value = true;
    }
});

const getInitList = () => {
    getRankList(
        rankData.value,
        ({ data }) => {
            rankList.value = data.data.rankers;
            userInfo.value = data.data.myInfo;
            rankData.value = rankList.value; // 전체 저장해놓은 랭크 리스트를 랭크 데이터 변수에 저장
        },
        (error) => {
            console.log(error);
        }
    );
};

onMounted(() => {
    getInitList();
});

const topRankerInfo = ["second", "first", "third"];

// 검색 ====================================
const userInput = ref("");
const searchByName = () => {
    console.log("serachByName");
    rankerSearch(
        userInput.value,
        ({ data }) => {
            rankData.value = data.data;
        },
        (error) => {
            console.log(error);
        }
    );
};

// `ref` 함수를 사용하여 반응성 데이터를 선언합니다.
const isModalOpen = ref(false);
const rankerInfo = ref({});
// 모달 열기 함수
const openModal = (ranker) => {
    isModalOpen.value = true;
    rankerInfo.value = ranker;
    console.log(rankerInfo.value);
};

// 모달 닫기 함수
const closeModal = () => {
    isModalOpen.value = false;
};
</script>

<template>
    <div class="ranking-container text-font">
        <div class="ranking-board">
            <img
                src="https://d2qkxc1ity7pm2.cloudfront.net/images/mypage_paper.png"
                alt=""
            />
            <div class="board">
                <div class="ranking-title"><h1>현상금 순위 RANK</h1></div>
                <div class="top-ranker" v-if="isRankData">
                    <div
                        v-for="(index, idx) in [1, 0, 2]"
                        :key="idx"
                        class="ranker"
                        :class="topRankerInfo[idx]"
                    >
                        <h1>{{ index + 1 }}위</h1>
                        <div class="ranker-background"></div>
                        <div>
                            <h2>
                                {{ rankList[index].nickname }}
                            </h2>
                        </div>
                    </div>
                </div>

                <div class="rank-table">
                    <div class="search-container">
                        <button class="search-button" @click="getInitList">
                            초기화
                        </button>
                        <div class="input-container">
                            <label for="searchName">사용자 이름 검색 :</label>
                            <input
                                id="searchName"
                                type="text"
                                autocomplete="off"
                                v-model="userInput"
                                @keyup.enter="searchByName"
                            />
                            <button class="search-button" @click="searchByName">
                                검색
                            </button>
                        </div>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th><h3>순위</h3></th>
                                <th><h3>이름</h3></th>
                                <th><h3>태그</h3></th>
                                <th><h3>현상금액</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-if="!rankData">
                                No Data
                            </tr>
                            <tr
                                v-else
                                v-for="(ranker, rankerIdx) in rankData"
                                :key="rankerIdx"
                                class="non-header"
                            >
                                <td>{{ rankerIdx + 1 }}</td>
                                <td @click="openModal(ranker)" class="detail">
                                    {{ ranker.nickname }}
                                </td>
                                <td class="tag-container">
                                    <!-- name: "홍싸피", tag: [1, 2, 3, 4], score: 1442 -->
                                    <TagComponent
                                        :level="ranker.tier"
                                        :tagList="ranker.badgeList"
                                    />
                                </td>
                                <td>
                                    <div>
                                        {{ ranker.point }}
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <!-- 모달 컴포넌트 -->
        <Modal v-if="isModalOpen" @close="closeModal">
            <!-- 모달 내용 -->
            <h2>{{ rankerInfo.nickname }}</h2>
            <p>
                <TagComponent
                    :level="rankerInfo.tier"
                    :tagList="rankerInfo.badgeList"
                />
            </p>
            <button @click="closeModal">닫기</button>
        </Modal>
    </div>
</template>
<style scoped lang="scss">
@import "@/assets/style/main.scss";
.ranking-container {
    background-image: url($url-path + "images/ranking_bg.jpg");
    background-size: cover;
    width: 100%;
    height: 250vh;
    position: absolute;
    z-index: -1;
}

.ranking-board {
    display: flex;
    flex-direction: column;
    justify-content: center;
    height: 100%;
    align-items: center;

    .ranking-title {
        h1 {
            font-size: xxx-large;
        }
    }

    img {
        align-self: center;
        width: 80%;
        height: 80%;
        z-index: -1;
        position: absolute;
    }

    .board {
        z-index: 1;
        display: flex;
        flex-direction: column;
        text-align: center;
        align-items: center;
        justify-content: space-evenly;
        animation: fadeInUp 2s;

        height: 70%;
        width: 70%;

        // 탑 랭커 섹션 =========================
        .top-ranker {
            display: flex;
            justify-content: space-around;
            width: 80%;
            height: 30vh;
            margin: 2% 0 15% 0;

            .ranker {
                display: flex;
                flex-direction: column;
                width: 25%;
                max-width: 630px;
                height: 100%;
                border: 2px double black;

                .ranker-background {
                    width: 100%;
                    height: 100%;
                    position: relative;
                    background-image: url($url-path + "images/Logo.png");
                    background-size: contain;
                    background-position: center;
                }
            }
            .second {
                margin-top: 10%;
            }
            .third {
                margin-top: 18%;
            }
        }

        // 랭크 테이블 섹션 ===================
        .rank-table {
            font-size: larger;
            width: 80%;

            // 검색 섹션 ======================
            .search-container {
                display: flex;
                flex-wrap: nowrap;
                justify-content: space-between;
                padding: 1% 0 1% 0;
                z-index: 1;
                border-top: 2px double black;
                width: 100%;

                .input-container {
                    display: flex;
                    justify-content: space-evenly;

                    label {
                        margin: 10px 1% 0 0;
                        width: 40%;
                        font-size: larger;
                    }
                    input {
                        padding: 2% 0 0 2%;
                        border: 2px solid black;
                        border-radius: 10px;
                        font-size: large;
                        font-weight: bold;
                        width: 40%;
                        height: 100%;

                        &:focus {
                            outline: none;
                            background-color: rgba($gradation-color, 0.1);
                        }
                    }
                }

                .search-button {
                    border: 2px solid $primary-color;
                    border-radius: 10px;
                    margin: 0 10px 0 10px;
                    padding: 1% 1% 0 1%;
                    min-width: 50px;

                    &:hover {
                        background-color: $primary-color;
                        color: white;
                        transition-duration: 0.5s;
                    }
                }
            }

            // 모달 ==============================
            .detail {
                &:hover {
                    cursor: pointer;
                }
            }

            // 테이블 =============================
            table {
                width: 100%;
                border-collapse: collapse;
                padding: 0;

                tr th,
                tr td {
                    height: 50px;
                    padding-top: 1%;
                    border-bottom: 1px solid #000; /* 테두리 스타일 및 색상 지정 */
                }

                th {
                    background-color: rgba($secondary-color, 0.4);
                }

                .non-header:hover {
                    background-color: rgba($gradation-color, 0.2);
                    transition-duration: 0.5s;
                }
            }
            .tag-container {
                widows: 50%;
                padding-bottom: 1%;
            }
        }
    }
}
</style>
