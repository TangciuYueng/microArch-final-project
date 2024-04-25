<template>
    <!-- 重置密码界面 -->
    <div class="reset-code-final">
        <div class="reset-input">
            <div class="title">找 回 密 码</div>
                <div v-if="username != null && phone != null && resetSuccess == false">
                    <div class="info"> 根据您输入的信息找到以下用户信息，请确认： </div>

                    <div class="info-container">
                        <label style="color: #303030; font-size: 20px;"> 用户名： </label>
                        <label style="color: #105645; font-size: 20px; font-family: 'Poppins-Regular';"> {{ username }} </label>
                    </div>

                    <div class="info-container">
                        <label style="color: #303030; font-size: 20px;"> 电话： </label>
                        <label style="color: #105645; font-size: 20px; font-family: 'Poppins-Regular';"> {{ phone }} </label>
                    </div>

                    <div class="info"> 确认无误后，请设置您的新密码，并对其进行确认 </div>
                    
                    <!-- 使用了 @submit.prevent 监听表单的提交事件，并调用 onSubmit 方法进行处理。.prevent 修饰符阻止了表单的默认提交行为，从而可以使用自定义的提交方法进行处理。 -->
                    <v-form v-model="form" @submit.prevent="onSubmit">
                        <v-row style="margin-top: 20px;">
                            <!-- 输入用户名 -->
                            <v-text-field
                                v-model="newPassword"
                                :readonly="loading"
                                :rules="[required]"
                                style="max-width: 45%; margin-left: 27.5%;"
                                clearable
                                label="新密码"
                                placeholder="请输入新密码"
                                prepend-inner-icon="mdi-lock"
                                variant="outlined"
                                color="#105645"
                                type="password"
                                @input="checkResetInput">
                            </v-text-field>
                        </v-row>
                        <v-row style="margin-top: 20px;">
                            <!-- 输入手机号 -->
                            <v-text-field
                                v-model="verifyNewPassword"
                                :readonly="loading"
                                :rules="[required]"
                                style="max-width: 45%; margin-left: 27.5%;"
                                label="确认新密码"
                                placeholder="请确认新密码"
                                prepend-inner-icon="mdi-lock"
                                variant="outlined"
                                color="#105645"
                                type="password"
                                clearable
                                @input="checkResetInput">
                            </v-text-field>
                        </v-row>
                            
                        <!-- 其他表单项和按钮 -->
                        <br>
                        <v-container>
                            <v-row justify="center">
                                <v-btn
                                    :loading="loading"
                                    :class="'submit-button-' + (inputPass ? 'pass' : 'error')"
                                    type="submit"
                                    variant="elevated"
                                    width="160"
                                    height="55"
                                    @click="submitHandler">
                                    {{ inputPass ? '确 认' : inputErrorInfo }}
                                </v-btn>
                            </v-row>
                        </v-container>
                    </v-form>
                </div>

                <div v-else-if="resetSuccess == true" align="center">
                    <img src="../assets/reset-success.svg" class="reset-icon">
                    <br>
                    <label class="message-success"> 修改完成！正在返回登录页... </label>
                </div>

                <div v-else align="center">
                    <img src="../assets/404.svg" class="reset-icon">
                    <br>
                    <label class="message-404"> 验证信息缺失， </label>
                    <a href="/" style="font-size: 24px;"> 返回登录页 </a>
                </div>
        </div>
        <div class="reset-image"></div>
    </div>
</template>
  
<script>
// import HelloWorld from '@/components/HelloWorld.vue'
import { resetUser } from '../main.js';
import { resetPassword } from '@/axios/login'
import { number } from 'echarts'
export default {
    data: () => ({
        form: false,
        username: null,
        phone: null,
        newPassword: "",
        verifyNewPassword: "",
        inputPass: false,
        inputErrorInfo: "请输入用户名！",
        loading: false,
        resetSuccess: false
    }),
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
        checkResetInput: function() {
            if (this.newPassword == "") {
                this.inputErrorInfo = "请输入新密码！";
                this.inputPass = false;
                return;
            }

            if (this.verifyNewPassword != this.newPassword) {
                this.inputErrorInfo = "两次输入密码不一致！";
                this.inputPass = false;
                return;
            }

            this.inputErrorInfo = "";
            this.inputPass = true;
        },
        //处理登录逻辑
        submitHandler() {
            //检查表单是否有效，如果无效，则返回
            if (!this.form || !this.inputPass) return

            //执行登录逻辑，成功后重定向到主页
            this.loading = true;
            const userId = resetUser.id;
            console.log(userId);

            resetPassword({
                userId: userId,
                newPassword: this.newPassword
            }).then(res => {
                console.log(res);

                resetUser.id = 0;
                resetUser.name = null;
                resetUser.phone = null;
                this.resetSuccess = true;

                setTimeout(() => {
                    this.loading = false;
                    this.$router.push('/');
                }, 5000);
            }, err => {
                this.resetSuccess = false;
                console.log(err);
            });
        }
    },
    mounted() {
        this.username = resetUser.name;
        this.phone = resetUser.phone;
    }
}
</script>

<style scoped>
@font-face {
    font-family: Poppins-Regular; /* 自定义字体名称 */
    src: url('../assets/fonts/Poppins/Poppins-Regular.ttf') format('truetype'); /* 字体文件的路径 */
}

.reset-code-final {
    height: 100vh;
    overflow: hidden;
}
.reset-input {
    display: inline-block;
    width: 60%;
    height: 100vh;
    overflow: hidden;
    background-color: #10564510;
}
.reset-image {
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
.info-container {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
}
:deep(.v-field__input) {  /* 输入框输入内容 */
    font-size: 20px;
    color: #105645;
}
.submit-button-pass {
    position: relative;
    left: 0%;
    top: -30px;
    margin-top: 40px;
    background-color: #105645;
    color: white;
    font-size: 20px;
    font-weight: 300;
}
.submit-button-error {
    position: relative;
    left: 0%;
    top: -30px;
    margin-top: 40px;
    background-color: #631a1a;
    color: white;
    font-size: 14px;
    font-weight: 300;
}
.reset-icon {
    width: 160px;
    height: 160px;
    margin-top: 10px;
    margin-bottom: 20px;
}
.message-success {
    color: #105645;
    font-size: 24px;
}
.message-404 {
    color: #631a1a;
    font-size: 24px;
}
</style>