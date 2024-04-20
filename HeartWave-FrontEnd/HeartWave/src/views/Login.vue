<template>
    <!-- <script src="./src/snow.js"></script> -->
    <!-- 登陆界面 -->
    <div class="login">
        <div class="login-input">
            <div class="title"> Welcome to HeartWave! </div>
            <label class="sign-up-label"> Don't have an account? </label>
            <a href="/register" class="sign-up-link"> Sign Up </a>
            
            <!-- 使用了 @submit.prevent 监听表单的提交事件，并调用 onSubmit 方法进行处理。.prevent 修饰符阻止了表单的默认提交行为，从而可以使用自定义的提交方法进行处理。 -->
            <v-form v-model="form" @submit.prevent="onSubmit" style="margin-left: 10%; margin-top: 40px;">
                <v-text-field
                    v-model="phone"
                    style="max-width: 80%; height: 80px;"
                    :readonly="loading"
                    label="Phone"
                    placeholder="Please enter your phone number"
                    prepend-inner-icon="mdi-phone"
                    variant="outlined"
                    density="compact"
                    color="#105645"
                    :rules="[required]"
                    clearable
                    @input="checkLoginInput">
                </v-text-field>
                <v-text-field
                    v-model="userAccount"
                    :readonly="loading"
                    style="max-width: 80%; height: 80px; margin-top: 10px;"
                    label="Account"
                    placeholder="Please enter your account"
                    prepend-inner-icon="mdi-account"
                    variant="outlined"
                    density="compact"
                    color="#105645"
                    :rules="[required]"
                    clearable
                    @input="checkLoginInput">
                </v-text-field>
                <v-text-field
                    v-model="password"
                    :readonly="loading"
                    style="max-width: 80%; height: 80px; margin-top: 10px;"
                    label="Password"
                    placeholder="Please enter your password"
                    prepend-inner-icon="mdi-lock"
                    type="password"
                    variant="outlined"
                    density="compact"
                    color="#105645"
                    :rules="[required]"
                    clearable
                    @input="checkLoginInput">
                </v-text-field>
                <v-text-field
                    v-model="verifyCode"
                    :readonly="loading"
                    style="max-width: 80%; height: 80px; margin-top: 10px;"
                    label="VerifyCode"
                    placeholder="Please enter verify code"
                    prepend-inner-icon="mdi-image-check"
                    variant="outlined"
                    density="compact"
                    color="#105645"
                    :rules="[required]"
                    clearable
                    @input="checkLoginInput">
                </v-text-field>

                <v-row style="margin-top: 10px;">
                    <img :src="verifyInfo.img" alt="verify image missing" class="verify-image" @click="fetchVerifyInfo">
                    <v-btn
                        :loading="loading"
                        class="login-button"
                        :color="inputPass ? '#105645' : '#606060'"
                        type="submit"
                        variant="elevated"
                        width="220"
                        height="60"
                        @click="loginHandler">
                        Login
                    </v-btn>
                </v-row>
                <v-row style="margin-top: 10px;">
                    <label class="refetch-info-1"> Can't see clearly? </label>
                    <label class="refetch-info-2"> Press the picture to refresh </label>
                    <v-btn
                        :loading="loading"
                        class="forgot-password-button"
                        color="gray"
                        type="submit"
                        variant="text"
                        width="180"
                        @click="forgotPasswordHandler">
                        Forgot password?
                    </v-btn>
                </v-row>
            </v-form>
            
            <v-btn style="position: absolute; left: 0; top: 80%;" @click="this.$router.push('/main-view')"> 系统入口 </v-btn>
            <div class="other-method-label"> Log in with other methods </div>
            <hr>
            <div class="other-method-icons">
                <img src="../assets/login/chrome.svg">
                <img src="../assets/login/weibo.svg">
                <img src="../assets/login/QQ.svg">
                <img src="../assets/login/wechat.svg">
            </div>
        </div>
        <div class="login-image"></div>
    </div>
</template>
  
<script>
// import HelloWorld from '@/components/HelloWorld.vue'
import { mdiAccount, } from '@mdi/js'
import { login, getVerifyInfo } from '../axios/login.js'
export default {
    data: () => ({
        mdiAccount,

        form: false,
        phone: "",
        userAccount: "",
        password: "",
        verifyCode: "",
        verifyInfo: {
            code: "",
            img: ""
        },
        inputPass: false,
        loading: false,
        canFetchNewInfo: true,
    }),
    methods: {
        fetchVerifyInfo: function() {
            if (!this.canFetchNewInfo)
                return;

            getVerifyInfo().then(res => {
                console.log(res);
                this.verifyInfo.code = res.token;
                this.verifyInfo.img = "data:image/jpg;base64," + res.image;
                this.canFetchNewInfo = false;

                setTimeout(() => {
                    this.canFetchNewInfo = true;
                }, 5000);
            }, err => {
                console.log(err);
            });
        },
        checkLoginInput: function() {
            if (this.phone == "") {
                this.inputPass = false;
                return;
            }
            
            if (this.userAccount == "") {
                this.inputPass = false;
                return;
            }

            if (this.password == "") {
                this.inputPass = false;
                return;
            }

            if (this.verifyInfo.code == "") {
                this.inputPass = false;
                return;
            }

            if (this.verifyCode == "") {
                this.inputPass = false;
                return;
            }

            this.inputPass = true;
        },
        onSubmit() {
            //表单不合法，不提交
            if (!this.form) return

            //否则提交表单内容，与后端交互，判断是否能够登录
            this.loading = true

            setTimeout(() => (this.loading = false), 2000)
        },
        required(v) {
            return !!v || 'Field is required'
        },
        //处理登录逻辑
        loginHandler() {
            //检查表单是否有效，如果无效，则返回
            if (!this.form) return;

            //如果用户输入不通过也返回
            if (!this.inputPass) return;

            //执行登录逻辑，成功后重定向到主页
            this.loading = true;

            //检查验证码是否正确
            if (this.verifyCode.toUpperCase() != this.verifyInfo.code.toUpperCase()) {
                console.log("验证码不正确！")
                return;
            }

            login({
                name: this.userAccount,
                password: this.password,
                phone: this.phone
            }).then((res) => {
                console.log(res);
                localStorage.setItem("userId", res.id);
                localStorage.setItem("username", res.name);
                localStorage.setItem("userAvatar", res.avatar);
                localStorage.setItem("userEmail", res.email);
                
                setTimeout(() => {
                    this.loading = false;
                    this.$router.push('/main-view');
                }, 2000)
            }, (err) => {
                this.loading = false;
                console.log(err);
            });
        },
        //处理注册逻辑
        registerHandler() {
            //执行注册逻辑，将用户重定向到注册页面
            this.$router.push('/register')
        },
        //处理忘记密码逻辑
        forgotPasswordHandler() {
            //将用户重定向到重新设置密码的界面
            this.$router.push('/set-code')

        },
    },
    mounted() {
        this.fetchVerifyInfo();
        localStorage.clear();
    }
}
</script>

<style scoped>
@font-face {
    font-family: Poppins-Medium; /* 自定义字体名称 */
    src: url('../assets/fonts/Poppins/Poppins-Medium.ttf') format('truetype'); /* 字体文件的路径 */
}
@font-face {
    font-family: Poppins-Regular; /* 自定义字体名称 */
    src: url('../assets/fonts/Poppins/Poppins-Regular.ttf') format('truetype'); /* 字体文件的路径 */
}
@font-face {
    font-family: Poppins-Light; /* 自定义字体名称 */
    src: url('../assets/fonts/Poppins/Poppins-Light.ttf') format('truetype'); /* 字体文件的路径 */
}
.login {
    height: 100vh;
    overflow: hidden;
}
.login-input {
    display: inline-block;
    width: 60%;
    height: 100vh;
    overflow: hidden;
    background-color: #10564510;
}
.login-image {
    display: inline-block;
    width: 40%;
    height: 100vh;
    overflow: hidden;
    background-image: url("../assets/login/background.jpg");
}
.title {
    font-family: "Poppins-Medium";
    margin-top: 80px;
    margin-left: 10%;
    font-size: 32px;
}
.sign-up-label {
    font-family: "Poppins-Regular";
    margin-top: 10px;
    margin-left: 10%;
    font-size: 16px;
}
.sign-up-link {
    font-family: "Poppins-Regular";
    margin-top: 20px;
    font-size: 16px;
}
:deep(.v-field-label) {  /* 输入框标题 */
    font-family: "Poppins-Regular";
}
:deep(.v-field__input) {  /* 输入框输入内容 */
    font-family: "Poppins-Light";
    margin-top: 4px;
    font-size: 20px;
    color: #105645;
}
:deep(.v-messages__message) {  /* 输入框提示 */
    font-family: "Poppins-Regular";
}
.refetch-info-1 {
    font-family: "Poppins-Regular";
}
.refetch-info-2 {
    font-family: "Poppins-Regular";
    color: #105645;
}
.verify-image {
    margin-left: 10%;
    margin-bottom: 20px;
    width: 200px;
    height: 100px;
    border-radius: 10px;
    cursor: pointer;
    transition: 0.3s;
}
.verify-image:hover {
    opacity: 0.75;
}
.login-button {
    text-transform: none;
    font-family: "Poppins-Regular";
    font-size: 20px;
    margin-top: 20px;
    margin-left: 15%;
    border-radius: 30px;
}
.forgot-password-button {
    text-transform: none;
    font-family: "Poppins-Regular";
    font-size: 16px;
    margin-top: 20px;
    position: relative;
    left: 10.5%;
    top: -27px;
}
.other-method-label {
    margin-top: 60px;
    width: 100%;
    text-align: center;
    font-family: "Poppins-Regular";
    font-size: 16px;
    color: #666A72;
}
.other-method-icons {
    display: flex;
    justify-content: center;
    margin-top: 10px;
}
.other-method-icons > img {
    padding: 2%;
    margin-left: 2%;
    margin-right: 2%;
    border-radius: 5px;
    height: 80px;
    width: 80px;
    cursor: pointer;
    transition: 0.3s;
}
.other-method-icons > img:hover {
    background-color: #10564560;
}
</style>