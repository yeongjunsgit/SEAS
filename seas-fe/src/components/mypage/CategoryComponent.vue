<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";
import { getFavorite, getIncorrect } from "@/api/mypage.js";
import { getLikeCard } from "@/api/card.js";
import { useauthControllerStore } from "@/stores/authController.js";

const userStore = useauthControllerStore();
const user_access_token = userStore.myAccessToken;

const props = defineProps(["type"]);
const datas = ref([]);
const cardKwd = ref({});

onMounted(async () => {
    try {
        if (props.type == "즐겨찾기") {
            const res = await axios
                .get(
                    "https://i10a609.p.ssafy.io/api/mypage/flashcard/favorite",
                    {
                        headers: {
                            Authorization: `Bearer ${user_access_token}`,
                            "Content-Type": "application/json",
                        },
                    }
                )
                .then((response) => (datas.value = response.data.data))
                .catch((error) => console.log(error));

            datas.value.forEach((data) => {
                console.log("foreach data", data);
                data.flashcardIds.forEach((cardNum) => {
                    console.log("cardNum : ", cardNum);
                    getLikeCard(
                        cardNum,
                        ({ data }) => {
                            console.log(data);
                            cardKwd.value = {
                                ...cardKwd.value,
                                [cardNum]: data.data.keyword,
                            };
                        },
                        (error) => {
                            console.log(error);
                        }
                    );
                });
            });
        } else {
            getIncorrect(
                ({ data }) => {
                    datas.value = data.data;
                },
                (error) => {
                    console.log(error);
                }
            );
        }
    } catch (error) {
        console.error(error);
    }
});

const categoryArr = [
    "데이터베이스",
    "네트워크",
    "자료구조",
    "알고리즘",
    "컴퓨터구조",
    "운영체제",
];

const likes = ref(false);
const wrongAnswer = ref(false);

function helpMouseOver(type) {
    if (type === "즐겨찾기") {
        likes.value = true;
    } else if (type === "오답노트") {
        wrongAnswer.value = true;
    }
}
function showToolTips(bool) {
    likes.value = false;
    wrongAnswer.value = false;
}

function popupCard(categoryName, flashcardIds) {
    var url = `mypage/popupcard?category=${categoryName}&cardNum=${flashcardIds}&auth=${user_access_token}`;
    var name = "";
    var option =
        "width = 800, height = 600, top = 100, left = 200, location = no, scrollbars = no, resizeable = no";
    window.open(url, name, option);
}

function popupQuiz(categoryId, quizId) {
    var url = `mypage/popupquiz?categoryId=${categoryId}&quizId=${quizId}&auth=${user_access_token}`;
    var name = "";
    var option =
        "width = 800, height = 600, top = 100, left = 200, location = no, scrollbars = no, resizeable = no";
    window.open(url, name, option);
}
</script>

<template>
    <div class="my-flashcards">
        <div class="type">
            <h1>{{ type }}</h1>
            <img
                @mouseover="helpMouseOver(type)"
                @mouseout="showToolTips(false)"
                class="help"
                src="@/assets/images/help.png"
                alt="도움"
            />
            <div class="tooltip-content" v-if="likes">
                카드 페이지에서 즐겨찾기를 설정한 키워드에 대한 설명을
                보여드립니다.
            </div>
            <div class="tooltip-content" v-if="wrongAnswer">
                퀴즈 페이지에서 최근에 틀린 문제들을 다시 보여드립니다.
            </div>
        </div>

        <table>
            <tr
                class="categories"
                v-for="(categoryEach, i) in categoryArr"
                :key="i"
            >
                <th>{{ categoryEach }}</th>
                <template v-for="data in datas">
                    <td v-if="data.categoryName == categoryEach">
                        <div class="scrollable-td">
                            <p
                                v-for="flashcardId in data.flashcardIds"
                                @click="
                                    popupCard(data.categoryName, flashcardId)
                                "
                            >
                                {{ cardKwd[flashcardId] }},
                            </p>
                            <p
                                v-for="quizId in data.quizIds"
                                @click="popupQuiz(data.categoryId, quizId)"
                            >
                                {{ quizId }}
                            </p>
                        </div>
                    </td>
                </template>
            </tr>
        </table>
    </div>
</template>

<style scoped lang="scss">
@import url("@/assets/style/main.scss");

.my-flashcards {
    margin-left: 40px;
    height: 100%;

    table {
        height: 100%;
    }
}
.type {
    display: flex;
    align-items: center;
}
h1 {
    width: 105px;
}
.help {
    width: 20px;
    height: auto;
    object-fit: cover;
    margin-left: 10px;
}
.tooltip-content {
    position: absolute;
    margin-left: 115px;
    margin-bottom: 65px;
    font-size: 15px;
    background: #fefefe;
    color: #555;
    box-shadow: 0 0 6px 1px rgba(0, 0, 0, 0.15);
    padding: 4px 8px;
    border-radius: 4px;
    pointer-events: none;
    transition: opacity 0.4s;
}
.tooltip-content:after {
    border-top: 10px solid #fefefe;
    border-left: 10px solid transparent;
    border-right: 10px solid transparent;
    border-bottom: 0px solid transparent;
    content: "";
    position: absolute;
    top: 25px;
    left: 4px;
}

table {
    border-collapse: collapse;
    min-height: 300px;
}
.categories {
    display: flex;
}

tr {
    border-bottom: 1px solid black;
    width: 380px;
    align-items: center;
    height: 14%;
}
th {
    width: 30%;
    min-width: 114px;
    padding-top: 1%;
}
td div {
    display: flex;
    flex-wrap: wrap;
}
.scrollable-td {
    max-height: 45px; /* 스크롤 가능한 최대 높이 설정 */
    overflow-y: scroll; /* 수직 스크롤 적용 */
    padding: 1% 0 1% 0;
    p {
        margin: 5px 5px 0 0;
        color: rgb(57, 57, 176);
        cursor: pointer;
        line-height: 0.7;
    }

    &::-webkit-scrollbar {
        width: 0; /* 스크롤 바의 너비를 0으로 설정하여 숨김 */
    }
}
</style>
