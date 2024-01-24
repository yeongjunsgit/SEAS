import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import MenuView from "@/views/MenuView.vue";
import AuthenicateView from "@/views/AuthenicateView.vue";
import MyPageView from "@/views/MyPageView.vue";
import FlashcardView from "@/views/FlashcardView.vue";
import RankingView from "@/views/RankingView.vue";
import QuizView from "@/views/QuizView.vue";

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
      path: "/mypage/",
      name: "mypage",
      component: MyPageView,
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
    // {
    //   path: '/about',
    //   name: 'about',
    //   // route level code-splitting
    //   // this generates a separate chunk (About.[hash].js) for this route
    //   // which is lazy-loaded when the route is visited.
    //   component: () => import('../views/AboutView.vue')
    // }
  ],
});

export default router;
