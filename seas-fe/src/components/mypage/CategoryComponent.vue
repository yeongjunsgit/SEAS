<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";

const props = defineProps(["type"]);

const favoriteDatas = ref();
const incorrectDatas = ref();

onMounted(async () => {
  try {
    if (props.type == "즐겨찾기") {
      await axios
        .get("https://i10a609.p.ssafy.io/api/mypage/flashcard/favorite")
        .then((response) => (favoriteDatas.value = response.data.data))
        .catch((error) => console.log(error));
    }
    // if (props.type == "오답노트") {
    else {
      await axios
        .get("https://i10a609.p.ssafy.io/api/mypage/incorrect")
        .then((response) => (incorrectDatas.value = response.data.data))
        .catch((error) => console.log(error));
    }
  } catch (error) {
    console.error(error);
  }

  console.log(favoriteDatas.value);
  console.log(incorrectDatas.value);
});

const categories = [
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

function popup(categoryName, flashcardIds) {
  var url = `mypage/popup?category=${categoryName}&cardNum=${flashcardIds}`;
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
      <h1>{{ incorrectDatas }}</h1>
      <tr class="categories" v-for="Data in favoriteDatas">
        <th>{{ Data.categoryName }}</th>
        <td>
          <p
            v-for="flashcardId in Data.flashcardIds"
            @click="popup(Data.categoryName, flashcardId)"
          >
            {{ flashcardId }}
          </p>
        </td>
      </tr>
      <tr class="categories" v-for="Data in incorrectDatas">
        <th>{{ Data.categoryName }}</th>
        <td>
          <p
            v-for="flashcardId in Data.flashcardIds"
            @click="popup(Data.categoryName, flashcardId)"
          >
            {{ flashcardId }}
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
}
</style>
