<script setup></script>

<template>
    <div class="quiz-container">
        <div class="quiz-content">a</div>
        <div class="quiz-button-container">
            <div class="quiz-number text-font"><p>1/10</p></div>
            <button class="menu-button">힌트</button>
            <button class="menu-button">취소</button>
            <button class="menu-button">제출</button>
        </div>
    </div>
</template>

<style scoped lang="scss">
@import "@/assets/style/main.scss";

.quiz-container {
    position: absolute;
    background-image: url($url-path + "images/map.jpg");
    background-size: cover;
    height: 100%;
    width: 100%;
    display: flex;
    justify-content: space-evenly;

    .quiz-content {
        width: 50%;
        height: 80%;
        margin: 11vh 0 0 10%;
        background-image: url($url-path + "images/quiz_bg.png");
        background-size: 100% 100%;
        background-position: center;
    }

    .quiz-button-container {
        width: 30%;
        height: 80%;
        margin: 11vh 10% 0 0;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        font-size: xxx-large;
        background-color: rgba($color: #000000, $alpha: 0.1);

        .quiz-number {
            background-image: url($url-path + "images/button_background.png");
            background-size: 100% 12vh;
            background-position: center;
            height: 12vh;
            width: 40%;
            text-align: center;

            p {
                margin: 0;
                padding-top: 2vh;
            }
        }

        button {
            width: 10vh;
            padding: 3vh;
        }
    }
}
</style>
