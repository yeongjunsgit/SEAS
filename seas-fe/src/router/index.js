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
import { useauthControllerStore } from "@/stores/authController.js";
import { usePageStore } from "@/stores/loadingStore.js";
import { computed } from "vue";

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
    const isAuthenticated = computed(() => {
        const isName = localStorage.getItem("myName");
        const isAccessToken = localStorage.getItem("accessToken");
        const isRefreshToken = localStorage.getItem("refreshToken");
        const isGrantType = localStorage.getItem("myGrantType");

        return (
            isName !== null &&
            isAccessToken !== null &&
            isRefreshToken !== null &&
            isGrantType !== null
        );
    }).value;

    if (
        to.name !== "home" &&
        to.name !== "popupcard" &&
        to.name !== "popupquiz" &&
        to.name !== "authenicate" &&
        !isAuthenticated
    ) {
        next({ name: "authenicate" }); // 로그인 페이지로 리다이렉트
    } else {
        const authControllerStore = useauthControllerStore();
        function loadLoginDataFromLocalStorage() {
            const savedName = localStorage.getItem("myName");
            const savedRefreshToken = localStorage.getItem("refreshToken");
            const savedMyGrantType = localStorage.getItem("myGrantType");
            const savedAccessToken = localStorage.getItem("accessToken");

            authControllerStore.myName = savedName;
            authControllerStore.myAccessToken = savedRefreshToken;
            authControllerStore.myRefreshToken = savedMyGrantType;
            authControllerStore.myGrantType = savedAccessToken;
        }
        loadLoginDataFromLocalStorage();
        // setTimeout(() => {
        //     next(); // 3초 뒤에 페이지 이동을 진행합니다.
        // }, 3000);

        // next(); // 3초 뒤에 페이지 이동을 진행합니다.

        const pageStore = usePageStore(); // usePageStore 인스턴스 생성
        const shouldDelay = pageStore.shouldDelay(to.name); // 페이지 방문 여부 확인

        if (shouldDelay) {
            pageStore.markPageAsVisited(to.name);
            setTimeout(() => {
                next(); // 3초 뒤에 페이지 이동을 진행합니다.
            }, 1500);
        } else {
            // 페이지 이동 후에 처리할 작업이 있다면 여기에 추가
            next(); // 즉시 페이지 이동을 진행합니다.
        }
    }
});

export default router;
