import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import MenuView from "@/views/MenuView.vue";
import AuthenicateView from "@/views/AuthenicateView.vue";
import MyPageView from "@/views/MyPageView.vue";
import FlashcardView from "@/views/FlashcardView.vue";
import RankingView from "@/views/RankingView.vue";
import QuizView from "@/views/QuizView.vue";
import PopupCardView from "@/views/PopupCardView.vue";
import PopupQuizView from "@/views/PopupQuizView.vue";
import PreMyPageView from "@//views/PreMyPageView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "home",
            component: HomeView,
        },
        {
            path: "/menu",
            name: "menu",
            component: MenuView,
        },
        {
            path: "/auth",
            name: "authenicate",
            component: AuthenicateView,
        },
        {
            path: "/mypage",
            name: "premypage",
            component: PreMyPageView,
            children: [
                {
                    path: "",
                    name: "mypage",
                    component: MyPageView,
                },
                {
                    path: "popupcard",
                    name: "popupcard",
                    component: PopupCardView,
                },
                {
                    path: "popupquiz",
                    name: "popupquiz",
                    component: PopupQuizView,
                },
            ],
        },
        {
            path: "/flashcard/",
            name: "flashcard",
            component: FlashcardView,
        },
        {
            path: "/ranking",
            name: "ranking",
            component: RankingView,
        },
        {
            path: "/quiz",
            name: "quiz",
            component: QuizView,
        },
        // 예외 처리 페이지
        {
            path: "/:pathMatch(.*)*",
            component: () => import("@/views/ErrorView.vue"),
        },
    ],
});

router.beforeEach((to, from, next) => {
    const isAuthenticated = asdf;

    if (to.name !== "auth" && !isAuthenticated) {
        next({ name: "auth" }); // 로그인 페이지로 리다이렉트
    } else {
        next(); // 계속 진행
    }
});

export default router;
