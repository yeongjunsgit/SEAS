<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const movePage = (destination) => {
    router.push({ path: destination });
};
</script>

<template>
    <div class="header-container transparent">
        <div class="header-content">
            <div class="home-menus"></div>
            <div class="home-title title-font" @click="movePage('/')">SEAS</div>
            <div class="home-menus header-font">
                <div class="menu">
                    <button @click="movePage('/flashcard')">카드</button>
                    <button @click="movePage('/quiz')">
                        <p>퀴즈</p>
                    </button>
                    <button @click="movePage('/ranking')">랭킹</button>
                </div>

                <div class="menu">
                    <button @click="movePage('/auth')">로그인</button>
                    <button @click="movePage('/mypage')">마이페이지</button>
                    <button>로그아웃</button>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss">
@import "@/assets/style/main.scss";

.header-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    min-height: 80px;

    .header-content {
        display: flex;
        justify-content: space-evenly;
        align-items: center;
        width: 100%;

        .home-title {
            width: 20%;
            color: white;
            font-weight: bolder;
            font-size: xx-large;
            text-align: center;

            &:hover {
                cursor: pointer;
            }
        }
        .home-menus {
            width: 40%;
            display: flex;
            flex-wrap: nowrap;
            color: white;
            gap: 1vw;

            font-size: 1.3vw;
            font-weight: bold;

            button {
                padding: 0 10px 0 10px;
            }
        }
    }
}
.transparent {
    background: transparent !important;
    box-shadow: none !important;
}
</style>
