<template>
    <!-- 重置密码界面 -->
    <img src="../assets/loginBackgroundImage.png" class="background">

    <v-card class="findCode">
        <div class="title">找 回 密 码</div>
        <!-- 使用了 @submit.prevent 监听表单的提交事件，并调用 onSubmit 方法进行处理。.prevent 修饰符阻止了表单的默认提交行为，从而可以使用自定义的提交方法进行处理。 -->
        
        <div v-if="username != null && phone != null && resetSuccess == false">
            <div class="info-container">
                <label style="color: #303030; font-size: 20px;"> 用户名： </label>
                <label style="color: #105645; font-size: 20px;"> {{ username }} </label>
            </div>

            <div class="info-container">
                <label style="color: #303030; font-size: 20px;"> 电话： </label>
                <label style="color: #105645; font-size: 20px;"> {{ phone }} </label>
            </div>
            
            <v-form v-model="form" @submit.prevent="onSubmit">
                <v-row>
                    <!-- 输入用户名 -->
                    <v-col cols="12" md="12">
                        <v-text-field
                            v-model="newPassword"
                            :readonly="loading"
                            :rules="[required]"
                            class="input"
                            clearable
                            label="新密码"
                            placeholder="请输入新密码"
                            prepend-inner-icon="mdi-lock"
                            variant="outlined"
                            density="compact"
                            color="#105645"
                            type="password"
                            @input="checkResetInput"
                            ></v-text-field>
                    </v-col>
                    <!-- 输入手机号 -->
                    <v-col cols="12" md="12">
                        <v-text-field
                            v-model="verifyNewPassword"
                            :readonly="loading"
                            :rules="[required]"
                            label="确认新密码"
                            placeholder="请确认新密码"
                            prepend-inner-icon="mdi-lock"
                            variant="outlined"
                            density="compact"
                            color="#105645"
                            type="password"
                            clearable
                            class="input"
                            @input="checkResetInput"></v-text-field>
                    </v-col>
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
                            height="50"
                            @click="submitHandler">
                            {{ inputPass ? '确 认' : inputErrorInfo }}
                        </v-btn>
                    </v-row>
                </v-container>
            </v-form>
        </div>

        <div v-else-if="resetSuccess == true" align="center">
            <img src="../assets/reset-success.svg" style="margin-bottom: 20px;">
            <br>
            <label class="message-success"> 修改完成！正在返回登录页... </label>
        </div>

        <div v-else align="center">
            <img src="../assets/404.svg" style="margin-bottom: 20px;">
            <br>
            <label class="message-404"> 验证信息缺失， </label>
            <a href="/" style="font-size: 24px;"> 返回登录页 </a>
        </div>
    </v-card>
</template>
  
<script>
// import HelloWorld from '@/components/HelloWorld.vue'
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
            const userId = parseInt(localStorage.getItem("resetUserId"));
            console.log(userId);

            resetPassword({
                userId: userId,
                newPassword: this.newPassword
            }).then(res => {
                console.log(res);
                localStorage.removeItem("resetUserId");
                localStorage.removeItem("resetUsername");
                localStorage.removeItem("resetPhone");
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
        this.username = localStorage.getItem("resetUsername");
        this.phone = localStorage.getItem("resetPhone");
    }
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
    margin-top: 10px;
    margin-bottom: 30px;
}
.findCode {
    position: absolute;
    left: 45%;
    top: 50%;
    width: 30%;
    margin: -190px 0 0 -175px;
    padding: 30px;
    border-radius: 5px;
    overflow: hidden;
}
.info-container {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
}
.input {
    margin: 0;
    margin-left: 20%;
    width: 60%;
    height: 45px;
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
.message-success {
    color: #105645;
    font-size: 24px;
}
.message-404 {
    color: #631a1a;
    font-size: 24px;
}
</style>