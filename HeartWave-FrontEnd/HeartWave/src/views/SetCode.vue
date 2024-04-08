<template>
    <!-- 重置密码界面 -->
    <img src="../assets/loginBackgroundImage.png" class="background">

    <div class="findCode">
        <div class="title">找 回 密 码</div>
        <!-- 使用了 @submit.prevent 监听表单的提交事件，并调用 onSubmit 方法进行处理。.prevent 修饰符阻止了表单的默认提交行为，从而可以使用自定义的提交方法进行处理。 -->
        <v-form v-model="form" @submit.prevent="onSubmit">
            <v-row>
                <!-- 输入用户名 -->
                <v-col cols="12" md="12">
                    <v-text-field
                        v-model="username"
                        :readonly="loading"
                        :rules="[required]"
                        class="mb-2"
                        clearable
                        label="用户名"
                        prepend-inner-icon="mdi-account"
                        variant="solo"
                        color="#105645"
                        @input="checkSetpwdInput"></v-text-field>
                </v-col>
                <!-- 输入手机号 -->
                <v-col cols="12" md="8">
                    <v-text-field
                        v-model="phone"
                        :readonly="loading"
                        :rules="[required]"
                        label="手机号"
                        placeholder="输入手机号"
                        prepend-inner-icon="mdi-phone"
                        variant="solo"
                        color="#105645"
                        @input="checkSetpwdInput"></v-text-field>
                </v-col>
                <!-- 获取验证码按钮 -->
                <v-col cols="12" md="4" class="d-flex">
                    <v-btn outlined dark color="#105645" size="large" type="submit" width="130" height="55" @click="getSmsCode(); setTimeout(() => { checkSetpwdInput(); }, 2000);">
                        获取验证码
                    </v-btn>
                </v-col>
                <!-- 输入验证码 -->
                <v-col cols="12" md="12">
                    <v-text-field
                        v-model="verifyCode"
                        :readonly="loading"
                        :rules="[required]"
                        label="验证码"
                        placeholder="输入验证码"
                        prepend-inner-icon="mdi-numeric"
                        variant="solo"
                        color="#105645"
                        @input="checkSetpwdInput"></v-text-field>
                </v-col>
            </v-row>

            <!-- 其他表单项和按钮 -->
            <br>
            <v-container>
                <v-row justify="center">
                    <v-btn
                        :loading="loading"
                        type="submit"
                        variant="elevated"
                        width="130"
                        :class="'submit-button-' + (inputPass ? 'pass' : 'error')"
                        height="55"
                        @click="submitHandler">
                        {{ inputPass ? '确 认' : inputErrorInfo }}
                    </v-btn>
                </v-row>
                <v-row justify="center">
                    <label v-if="showJumpInfo" class="jump-info"> 验证成功，正在跳转...... </label>
                </v-row>
            </v-container>
        </v-form>
    </div>
</template>
  
<script>
// import HelloWorld from '@/components/HelloWorld.vue'
import { sendSmsSetPassword } from '@/axios/login'
export default {
    data: () => ({
        form: false,
        username: null,
        phone: "",
        verifyInfo: "",
        verifyCode: "",
        loading: false,
        inputPass: false,
        inputErrorInfo: "请输入用户名！",
        showJumpInfo: false
    }),
    mounted() {
        // const script = document.createElement('script')
        // script.src = './src/snow.js'
        // document.body.appendChild(script)
        localStorage.removeItem("resetUserId");
        localStorage.removeItem("resetUsername");
        localStorage.removeItem("resetPhone");
    },
    methods: {
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
        submitHandler() {
            //检查表单是否有效，如果无效，则返回
            if (!this.form || !this.inputPass)
                return;

            //执行登录逻辑，成功后重定向到主页
            this.loading = true;
            this.showJumpInfo = true;

            setTimeout(() => {
                this.loading = false;
                this.showJumpInfo = false;
                localStorage.setItem("resetUsername", this.username);
                localStorage.setItem("resetPhone", this.phone);
                this.$router.push('/reset-code');
            }, 2000);
        },
        checkSetpwdInput: function() {
            if (this.username == "") {
                this.inputErrorInfo = "请输入用户名！";
                this.inputPass = false;
                return;
            }

            if (this.phone == "") {
                this.inputErrorInfo = "请输入电话号码！";
                this.inputPass = false;
                return;
            }

            if (this.verifyInfo == "") {
                this.inputErrorInfo = "请获取验证码！";
                this.inputPass = false;
                return;
            }

            if (this.verifyCode != this.verifyInfo) {
                this.inputErrorInfo = "请检查验证码输入！";
                this.inputPass = false;
                return;
            }

            this.inputErrorInfo = "";
            this.inputPass = true;
        },
        //获取手机验证码
        getSmsCode() {
            sendSmsSetPassword({
                phone: this.phone,
                username: this.username
            }).then(res => {
                console.log(res);
                if (!res.smsInfo.ifSend) {
                    alert("验证码未发送，请检查号码填写！");
                    return;
                }

                localStorage.setItem("resetUserId", res.userId);
                this.verifyInfo = res.smsInfo.code;
            }, err => {
                console.log(err);
            });
        }
    },

}
</script>

<style scoped>
.background {
    position: fixed;
    top: 0px;
    left: 0%;
    width: 100vw;
    height: 100vh;
    opacity: 0.5;
}
.title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 42px;
    color: #105645;
    margin-bottom: 25px;
}
.findCode {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 400px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    overflow: hidden;
}
.submit-button-pass {
    position: relative;
    left: 0%;
    top: -30px;
    margin-top: 20px;
    background-color: #105645;
    color: white;
    font-size: 20px;
    font-weight: 300;
}
.submit-button-error {
    position: relative;
    left: 0%;
    top: -30px;
    margin-top: 20px;
    background-color: #631a1a;
    color: white;
    font-size: 14px;
    font-weight: 300;
}
.jump-info {
    color: #105645;
    font-size: 16px;
}
</style>