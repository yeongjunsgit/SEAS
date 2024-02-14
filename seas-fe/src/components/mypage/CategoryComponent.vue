<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";
import { getFavorite, getIncorrect } from "@/api/mypage.js";
import { getLikeCard } from "@/api/card.js";

const props = defineProps(["type", "apiUrl"]);
const datas = ref([]);

const cardKwd = ref({});

onMounted(async () => {
  try {
    if (props.type == "즐겨찾기") {
      getFavorite(
        ({ data }) => {
          console.log(data);
          datas.value = data.data;
        },
        (error) => {
          console.log(error);
        }
      );
      await datas.value.forEach((data) => {
        data.flashcardIds.forEach((cardNum) => {
          getLikeCard(
            cardNum,
            ({ data }) => {
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

  try {
    if (props.type == "즐겨찾기") {
      await datas.value.forEach((data) => {
        data.flashcardIds.forEach((cardNum) => {
          getLikeCard(
            cardNum,
            ({ data }) => {
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
  var url = `mypage/popupcard?category=${categoryName}&cardNum=${flashcardIds}`;
  var name = "";
  var option =
    "width = 500, height = 500, top = 100, left = 200, location = no, scrollbars = no, resizeable = no";
  window.open(url, name, option);
}

function popupQuiz(categoryId, quizId) {
  var url = `mypage/popupquiz?categoryId=${categoryId}&quizId=${quizId}`;
  var name = "";
  var option =
    "width = 500, height = 500, top = 100, left = 200, location = no, scrollbars = no, resizeable = no";
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
        카드 페이지에서 즐겨찾기를 설정한 키워드에 대한 설명을 보여드립니다.
      </div>
      <div class="tooltip-content" v-if="wrongAnswer">
        퀴즈 페이지에서 최근에 틀린 문제들을 다시 보여드립니다.
      </div>
    </div>

    <table>
      <tr class="categories" v-for="(categoryEach, i) in categoryArr" :key="i">
        <th>{{ categoryEach }}</th>
        <td v-for="data in datas">
          <p
            v-if="data.categoryName == categoryEach"
            v-for="flashcardId in data.flashcardIds"
            @click="popupCard(data.categoryName, flashcardId)"
          >
            {{ cardKwd[flashcardId] }},
          </p>
          <p
            v-if="data.categoryName == categoryEach"
            v-for="quizId in data.quizIds"
            @click="popupQuiz(data.categoryId, quizId)"
          >
            {{ quizId }}
          </p>
        </td>
      </tr>
    </table>
  </div>
</template>

<style scoped lang="scss">
@import url("@/assets/style/main.scss");

.my-flashcards {
  margin-left: 40px;
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
.categories {
  display: flex;
  justify-content: space-between;
}
tr {
  border-bottom: 1px solid black;
  width: 380px;
  align-items: center;
}
th {
  width: 30%;
}
td {
  display: flex;
  flex-wrap: wrap;
}
p {
  margin-right: 5px;
  color: rgb(57, 57, 176);
  cursor: pointer;
  line-height: 0.7;
}
</style>
