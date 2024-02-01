<script setup>
import { ref, onMounted, watch, computed } from "vue";
import { useRouter } from "vue-router";
// import { storeToRefs } from "pinia";

// false가 로그인 창, true가 회원가입 창
const active = ref(false);

const transitionContainer = function () {
    active.value = !active.value;
};

// 해당 페이지가 실행되었을 때, 즉시 실행

const router = useRouter();
// const userStore = useUserStore();

const loginInfo = ref({
    id: "",
    password: "",
});

const signupInfo = ref({
    id: "",
    password: "",
    passwordCheck: "",
    name: "",
    email: "",
});

const password = ref(null);

const login = async () => {
    // await userStore.login(loginInfo.value);
    router.push("/");
};

const checkSignup = () => {
    if (
        signupInfo.value.id.length < 4 ||
        signupInfo.value.password == "" ||
        signupInfo.value.passwordCheck == "" ||
        signupInfo.value.name == "" ||
        signupInfo.value.email == "" ||
        signupInfo.value.address == ""
    ) {
        alert("모든 정보를 정확히 입력하시오.");
    } else if (signupInfo.value.password !== signupInfo.value.passwordCheck) {
        alert("비밀번호가 일치하지 않습니다.");
    } else if (isDuplicated.value) {
        alert("중복된 아이디입니다.!");
    } else {
        doSignup();
    }
};

const doSignup = async () => {
    // await userStore.signup(signupInfo.value);
    loginInfo.value.id = signupInfo.value.id;
    active.value = false;
    password.value.focus();
};

// 회원가입 id 입력칸에서 벗어날 때마다 실행하여 아이디의 중복 체크
const duplicateMessage = ref("");
const checkId = () => {
    if (signupInfo.value.id.length < 4) {
        duplicateMessage.value = "아이디는 4자리 이상이어야 합니다";
    } else {
        checkDuplicate();
    }
};

const checkDuplicate = async () => {
    await userStore.idCheck({ userId: signupInfo.value.id });
    if (isDuplicated.value) {
        // alert("이미 사용 중인 아이디입니다. 다른 아이디를 입력해주세요.");
        duplicateMessage.value = "이미 사용 중인 아이디입니다";
    } else {
        // alert("사용 가능한 아이디입니다.");
        duplicateMessage.value = "사용 가능한 아이디입니다";
    }
};
</script>

<template>
    <div class="main-container">
        <div class="login-box">
            <div class="container" :class="{ active: active }" id="container">
                <div class="form-container sign-up">
                    <form @submit.prevent="checkSignup">
                        <h1>Create Account</h1>
                        <input
                            type="text"
                            placeholder="ID"
                            v-model="signupInfo.id"
                            @keyup="checkId"
                        />
                        <p class="duplicate-text">{{ duplicateMessage }}</p>
                        <input
                            type="password"
                            placeholder="Password"
                            v-model="signupInfo.password"
                        />
                        <input
                            type="password"
                            placeholder="Password Confirm"
                            v-model="signupInfo.passwordCheck"
                        />
                        <input
                            type="text"
                            placeholder="Name"
                            v-model="signupInfo.name"
                        />
                        <input
                            type="email"
                            placeholder="Email"
                            v-model="signupInfo.email"
                        />
                        <button>Sign Up</button>
                    </form>
                </div>
                <div class="form-container sign-in">
                    <form @submit.prevent="login">
                        <h1>Sign in</h1>
                        <input
                            type="text"
                            placeholder="ID"
                            v-model="loginInfo.id"
                        />
                        <input
                            type="password"
                            placeholder="Password"
                            ref="password"
                            v-model="loginInfo.password"
                            @keyup.enter="submit"
                        />
                        <a href="#" id="remember">Forgot your password?</a>
                        <button>Sign in</button>
                    </form>
                </div>
                <div class="toggle-container">
                    <div class="toggle">
                        <div class="toggle-panel toggle-left">
                            <a href="/"
                                ><img
                                    src="https://d2qkxc1ity7pm2.cloudfront.net/images/Logo.png"
                                    alt=""
                            /></a>
                            <h1 class="title-font">SEAS</h1>
                            <h3>CS 공부 사이트</h3>
                            <p class="description-text">
                                Enter your personal details to use all of site
                                features
                            </p>
                            <button
                                class="hidden"
                                id="login"
                                @click="
                                    transitionContainer(active),
                                        changeBackgroundColor
                                "
                            >
                                Sign In
                            </button>
                        </div>
                        <div class="toggle-panel toggle-right">
                            <a href="/"
                                ><img
                                    src="https://d2qkxc1ity7pm2.cloudfront.net/images/Logo.png"
                                    alt=""
                            /></a>
                            <h1 class="title-font">SEAS</h1>
                            <h3>CS 공부 사이트</h3>
                            <p class="description-text">
                                Register with your personal details to use all
                                of the site features
                            </p>
                            <button
                                class="hidden"
                                id="register"
                                @click="
                                    transitionContainer(active),
                                        changeBackgroundColor
                                "
                            >
                                Sign Up
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <video muted autoplay loop>
                <source
                    src="https://d2qkxc1ity7pm2.cloudfront.net/videos/login_compressed.mp4"
                    type="video/mp4"
                />
            </video>
        </div>
    </div>
</template>

<style scoped lang="scss">
@import "@/assets/style/main.scss";

video {
    filter: brightness(70%);
}

.main-container {
    position: absolute;
    // background-image: url($url-path + "images/map.jpg");
    background-size: cover;
    height: 100%;
    width: 100%;
}
.login-box {
    height: 100%;
    display: flex;
    justify-content: center;
    max-height: 60%;
}

/* 메인 컨테이너에 대한 스타일링: 둥근 모서리, 그림자 및 크기 제한 */
.container {
    background-color: #fff;
    border-radius: 30px;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.35);
    position: relative;
    overflow: hidden;
    width: 768px;
    max-width: 100%;
    min-height: 480px;
    margin-top: 8%;
    animation: fadeInUp 1.5s;
}

/* 컨테이너 내의 문단 스타일링 */
.container p {
    line-height: 20px;
    letter-spacing: 0.3px;
}
.duplicate-text {
    margin: 0;
    font-size: 12px;
    font-weight: 500;
}
.description-text {
    margin: 20px 0;
    font-size: 14px;
}

/* 컨테이너 내의 span 스타일링 */
.container span {
    font-size: 12px;
}

/* 컨테이너 내의 앵커 링크 스타일링 */
.container a {
    color: #333;
    font-size: 13px;
    text-decoration: none;
    margin: 15px 0 10px;
}

/* 컨테이너 내의 버튼 스타일링 */
.container button {
    background-color: $primary-color;
    color: #fff;
    font-size: 12px;
    padding: 10px 45px;
    border: 1px solid transparent;
    border-radius: 8px;
    font-weight: 600;
    letter-spacing: 0.5px;
    text-transform: uppercase;
    margin-top: 10px;
    cursor: pointer;
}

/* 컨테이너 내에서 "hidden" 클래스를 가진 버튼에 대한 스타일링 */
.container button.hidden {
    background-color: transparent;
    border-color: #fff;
}

/* 컨테이너 내의 양식 스타일링 */
.container form {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    padding: 0 27px;
    height: 100%;
}

/* 컨테이너 내의 입력 필드 스타일링 */
.container input {
    background-color: #eee;
    border: none;
    margin: 8px 0;
    padding: 10px 15px;
    font-size: 13px;
    border-radius: 8px;
    width: 100%;
    outline: none;
}

/* 절대 위치의 form-container 스타일링, 전체 높이와 부드러운 전환을 갖음 */
.form-container {
    position: absolute;
    top: 0;
    height: 100%;
    transition: all 0.6s ease-in-out;
}

/* 로그인 영역 스타일링 */
.sign-in {
    left: 0;
    width: 50%;
    z-index: 2;
}

/* 컨테이너가 활성 상태일 때의 로그인 영역 스타일링 */
.container.active .sign-in {
    transform: translateX(100%);
}

/* 가입 영역 스타일링 */
.sign-up {
    left: 0;
    width: 50%;
    opacity: 0;
    z-index: 1;
}
/* 컨테이너가 활성 상태일 때의 가입 영역 스타일링 */
.container.active .sign-up {
    transform: translateX(100%);
    opacity: 1;
    z-index: 5;
    animation: move 0.6s;
}

/* 움직임 애니메이션 */
@keyframes move {
    0%,
    49.99% {
        opacity: 0;
        z-index: 1;
    }
    50%,
    100% {
        opacity: 1;
        z-index: 5;
    }
}

/* 토글 컨테이너 스타일링: 절대 위치, 전체 높이, 반원 모양의 모서리 둥근 테두리 스타일 */
.toggle-container {
    position: absolute;
    top: 0;
    left: 50%;
    width: 50%;
    height: 100%;
    overflow: hidden;
    transition: all 0.6s ease-in-out;
    border-radius: 150px 0 0 100px;
    z-index: 1000;
}

/* 컨테이너가 활성 상태일 때의 토글 컨테이너 스타일링 */
.container.active .toggle-container {
    transform: translateX(-100%);
    border-radius: 0 150px 100px 0;
}

/* 토글 버튼 스타일링 */
.toggle {
    background-color: $primary-color;
    height: 100%;
    background: linear-gradient(to left, $primary-color, $gradation-color);
    color: #fff;
    position: relative;
    left: -100%;
    height: 100%;
    width: 200%;
    transform: translateX(0);
    transition: all 0.6s ease-in-out;
}

/* 컨테이너가 활성 상태일 때의 토글 스타일링 */
.container.active .toggle {
    transform: translateX(50%);
}

/* 토글 패널 스타일링 */
.toggle-panel {
    position: absolute;
    width: 50%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    padding: 0 30px;
    text-align: center;
    top: 0;
    transform: translateX(0);
    transition: all 0.6s ease-in-out;
}

/* 토글의 왼쪽 부분 스타일링 */
.toggle-left {
    transform: translateX(-200%);
}

/* 컨테이너가 활성 상태일 때의 왼쪽 토글 스타일링 */
.container.active .toggle-left {
    transform: translateX(0);
}

/* 토글의 오른쪽 부분 스타일링 */
.toggle-right {
    right: 0;
    transform: translateX(0);
}

/* 컨테이너가 활성 상태일 때의 오른쪽 토글 스타일링 */
.container.active .toggle-right {
    transform: translateX(200%);
}

#remember {
    color: $primary-color;
}

img {
    width: 50%;
}
</style>
