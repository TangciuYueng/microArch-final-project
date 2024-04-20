<template>
    <!-- 重置密码界面 -->
    <div class="set-code">
        <div class="set-input">
            <div class="title">找 回 密 码</div>
            <div class="info"> 请输入您的用户名及对应的电话号码，并进行验证，以此让我们确定您的帐号 </div>
            <!-- 使用了 @submit.prevent 监听表单的提交事件，并调用 onSubmit 方法进行处理。.prevent 修饰符阻止了表单的默认提交行为，从而可以使用自定义的提交方法进行处理。 -->
            <v-form v-model="form" @submit.prevent="onSubmit">
                <v-row style="margin-top: 30px;">
                    <!-- 输入用户名 -->
                    <v-text-field
                        v-model="username"
                        :readonly="loading"
                        :rules="[required]"
                        style="max-width: 45%; margin-left: 27.5%;"
                        clearable
                        label="用户名"
                        prepend-inner-icon="mdi-account"
                        variant="outlined"
                        color="#105645"
                        @input="checkSetpwdInput">
                    </v-text-field>
                </v-row>
                <v-row style="margin-top: 40px;">
                    <!-- 输入手机号 -->
                    <v-text-field
                        v-model="phone"
                        :readonly="loading"
                        :rules="[required]"
                        style="max-width: 28%; margin-left: 27.5%;"
                        label="手机号"
                        placeholder="输入手机号"
                        prepend-inner-icon="mdi-phone"
                        variant="outlined"
                        color="#105645"
                        @input="checkSetpwdInput">
                    </v-text-field>
                    <!-- 获取验证码按钮 -->
                    <v-btn
                        outlined
                        dark
                        color="#105645"
                        size="large"
                        type="submit"
                        width="130"
                        height="62"
                        style="margin-left: 4%;"
                        @click="getSmsCode(); setTimeout(() => { checkSetpwdInput(); }, 2000);">
                        获取验证码
                    </v-btn>
                </v-row>
                <v-row style="margin-top: 40px;">
                    <!-- 输入验证码 -->
                    <v-text-field
                        v-model="verifyCode"
                        :readonly="loading"
                        :rules="[required]"
                        style="max-width: 45%; margin-left: 27.5%;"
                        label="验证码"
                        placeholder="输入验证码"
                        prepend-inner-icon="mdi-numeric"
                        variant="outlined"
                        color="#105645"
                        @input="checkSetpwdInput">
                    </v-text-field>
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
        <div class="set-image"></div>
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
.set-code {
    height: 100vh;
    overflow: hidden;
}
.set-input {
    display: inline-block;
    width: 60%;
    height: 100vh;
    overflow: hidden;
    background-color: #10564510;
}
.set-image {
    display: inline-block;
    width: 40%;
    height: 100vh;
    overflow: hidden;
    background-image: url("../assets/login/heartwave.jpg");
}
.title {
    width: 100%;
    margin-top: 200px;
    line-height: 50px;
    text-align: center;
    font-size: 40px;
    color: #105645;
    margin-bottom: 25px;
}
.info {
    width: 100%;
    margin-top: 20px;
    text-align: center;
    color: #606060;
    margin-bottom: 25px;
}
:deep(.v-field__input) {  /* 输入框输入内容 */
    font-size: 20px;
    color: #105645;
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