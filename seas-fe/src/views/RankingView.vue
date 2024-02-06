<script setup>
import TagComponent from "@/components/ranking/TagComponent.vue";
import Modal from "@/components/ranking/Modal.vue";
import { ref } from "vue";

const rankerList = ref([
    { name: "김싸피더안드로메다", tag: [1], score: 3000 },
    { name: "최싸피", tag: [2, 3], score: 1231 },
    { name: "홍싸피", tag: [1, 2, 3, 4], score: 1442 },
    { name: "임싸피", tag: [1, 2, 3], score: 1232 },
    { name: "이싸피", tag: [0], score: 9292 },
    { name: "배싸피", tag: [1, 2, 3], score: 1231 },
    { name: "엄싸피", tag: [4], score: 6463 },
    { name: "송싸피", tag: [0, 1, 2, 3, 4, 5], score: 3901 },
    { name: "양싸피", tag: [5], score: 2302 },
]);

// 검색 ====================================
const userInput = ref("");
const searchByName = () => {
    console.log(userInput.value);
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
                <div class="top-ranker">
                    <div class="ranker second">
                        <h1>2nd</h1>
                        <div class="ranker-background"></div>
                        <h2>최싸피</h2>
                    </div>
                    <div class="ranker first">
                        <h1>1ST</h1>
                        <div class="ranker-background"></div>
                        <h2>최싸피</h2>
                    </div>
                    <div class="ranker third">
                        <h1>3rd</h1>
                        <div class="ranker-background"></div>
                        <h2>최싸피</h2>
                    </div>
                </div>

                <div class="rank-table">
                    <div class="search-container">
                        <label for="searchName">사용자 이름 검색 :</label>
                        <div class="input-container">
                            <input
                                id="searchName"
                                type="text"
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
                            <tr
                                v-for="(ranker, rankerIdx) in rankerList"
                                :key="rankerIdx"
                                class="non-header"
                            >
                                <td>{{ rankerIdx + 1 }}</td>
                                <td @click="openModal(ranker)" class="detail">
                                    {{ ranker.name }}
                                </td>
                                <td class="tag-container">
                                    <!-- name: "홍싸피", tag: [1, 2, 3, 4], score: 1442 -->
                                    <TagComponent
                                        :tagCount="ranker.tag.length"
                                        :rankerInfo="ranker.tag"
                                    />
                                </td>
                                <td>
                                    <div>
                                        {{ ranker.score }}
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
            <h2>사용자 이름: {{ rankerInfo.name }}</h2>
            <p>
                레벨:
                <TagComponent
                    :tagCount="rankerInfo.tag.length"
                    :rankerInfo="rankerInfo.tag"
                />
            </p>
            <p>보유 태그: {{ rankerInfo.tag }}</p>
            <p>현상금: {{ rankerInfo.score }}</p>
            <button @click="closeModal" class="search-button">
                Close Modal
            </button>
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
                flex-wrap: wrap;
                justify-content: end;
                padding: 1% 0 1% 0;
                z-index: 1;
                border-top: 2px double black;
                width: 100%;

                label {
                    margin: 1% 1% 0 0;
                    font-size: larger;
                }
                .input-container {
                    display: flex;

                    input {
                        padding: 2% 0 0 2%;
                        border: 2px solid black;
                        border-radius: 10px;
                        font-size: large;
                        font-weight: bold;
                        width: 80%;
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
                    margin-left: 10px;
                    padding: 2% 1% 0 1%;
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
