<script setup>
import TagComponent from "@/components/commons/TagComponent.vue";
import { ref } from "vue";

const rankerList = ref([
    { name: "김싸피", badge: [1], score: 3000 },
    { name: "최싸피", badge: [2, 3], score: 1231 },
    { name: "홍싸피", badge: [1, 2, 3, 4], score: 1442 },
    { name: "임싸피", badge: [1, 2, 3], score: 1232 },
    { name: "이싸피", badge: [0], score: 9292 },
    { name: "배싸피", badge: [1, 2, 3], score: 1231 },
    { name: "엄싸피", badge: [4], score: 6463 },
    { name: "송싸피", badge: [1, 2, 3], score: 3901 },
    { name: "양싸피", badge: [5], score: 2302 },
]);

const badgeList = ref([
    { title: "CS 선장" },
    { title: "알고리즘 항해사" },
    { title: "네트워크 항해사" },
    { title: "자료구조 항해사" },
    { title: "데이터베이스 항해사" },
    { title: "컴퓨터구조 항해사" },
    { title: "운영체제 항해사" },
]);
</script>

<template>
    <div class="ranking-container temp-font">
        <div class="ranking-board">
            <img src="@/assets/images/mypage_paper.png" alt="" />
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
                                v-for="(ranker, index) in rankerList"
                                :key="index"
                            >
                                <td>{{ index + 1 }}</td>
                                <td>{{ ranker.name }}</td>
                                <td class="badge-container">
                                    <div
                                        v-for="(badge, index) in ranker.badge"
                                        :key="index"
                                    >
                                        <TagComponent
                                            :title="badgeList[badge].title"
                                        />
                                    </div>
                                </td>
                                <td>{{ ranker.score }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>
<style scoped lang="scss">
@import "@/assets/style/main.scss";

.ranking-container {
    background-image: url("@/assets/images/ranking_bg.jpg");
    background-size: cover;
    width: 100%;
    height: 200vh;
    position: absolute;
    z-index: -1;
}

.ranking-board {
    display: flex;
    flex-direction: column;
    justify-content: center;
    min-height: 100%;

    img {
        align-self: center;
        width: 80%;
        height: 80%;
        z-index: -1;
        position: absolute;
    }

    .board {
        position: relative;
        z-index: 1;
        display: flex;
        flex-direction: column;
        text-align: center;
        align-items: center;

        .top-ranker {
            display: flex;
            justify-content: space-around;
            width: 60%;
            height: 30vh;
            margin: 2% 0 10% 0;

            .ranker {
                display: flex;
                flex-direction: column;
                width: 25%;
                max-width: 630px;
                height: 100%;
                border: 1px solid black;

                .ranker-background {
                    width: 100%;
                    height: 100%;
                    position: relative;
                    background-image: url("@/assets/images/Logo.png");
                    background-size: contain;
                    background-position: center;
                }
            }
            .first {
            }

            .second {
                margin-top: 10%;
            }
            .third {
                margin-top: 18%;
            }
        }

        .rank-table {
            width: 60%;
            min-height: 50vh;
            padding-top: 5%;
            display: flex;
            flex-direction: column;
            font-size: larger;

            td {
                min-height: 40px;
                border-bottom: 1px solid black;
            }

            .badge-container {
                display: flex;
                text-align: center;
                margin-top: 1%;
                overflow: hidden;
            }
        }
    }
}
</style>
