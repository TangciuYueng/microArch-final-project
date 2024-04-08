<template>
    <!-- 注册界面 -->
    <div class="Screen">
        <div class="Register">
            <div class="title text-h3 font-weight-bold"> 注 册</div>
            <!-- 显示机票背景图片 -->
            <v-img
                cover
                :src="getImgSrc(registerSuccess ? '../assets/invitation-success.jpg' : '../assets/invitation.jpg')"
                style="margin-top: 20px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);">
                <!-- 注册表单 -->
                <v-form v-if="!registerSuccess" v-model="form" @submit.prevent="onSubmit">
                    <!-- 左边那一块表单，包括校区，姓名，学号等 -->
                    <v-container style="position:relative;left:200px;top:170px;margin-left: 48px;">
                        <!-- 选择校区 -->
                        <v-row justify="start">
                            <input
                                type="file"
                                ref="fileInput"
                                style="display: none;"
                                @change="handleFileChange"
                            >
                            <v-btn
                                v-if="avatar == ''"
                                @click="openFilePicker(); checkRegisterInput()"
                                class="avatar-button"
                                height="160">
                                选择头像
                            </v-btn>
                            <img
                                v-else
                                :src="'data:image/jpg;base64,' + avatar"
                                style="width: 160px; height: 160px;"
                                @click="avatar = ''"
                                class="avatar-img"
                            >
                            <v-col>
                                <v-text-field
                                    v-model="username"
                                    style="margin-left: 20px; max-width: 30%; height: 85px;"
                                    label="用户名"
                                    placeholder="请输入用户名"
                                    prepend-inner-icon="mdi-account"
                                    variant="outlined"
                                    density="compact"
                                    color="#105645"
                                    :rules="[required]"
                                    @input="checkRegisterInput()">
                                </v-text-field>
                                <v-text-field
                                    v-model="phone"
                                    style="margin-left: 20px; max-width: 30%; height: 85px;"
                                    label="电话号码"
                                    placeholder="请输入电话号码"
                                    prepend-inner-icon="mdi-phone"
                                    variant="outlined"
                                    density="compact"
                                    color="#105645"
                                    :rules="[required]"
                                    @input="checkRegisterInput()">
                                </v-text-field>
                            </v-col>
                        </v-row>
                        <!-- 输入姓名和学号 -->
                        <v-row justify="start">
                            <v-text-field
                                v-model="smsCode"
                                style="max-width: 25.5%; height: 80px;"
                                label="验证码"
                                placeholder="请输入验证码"
                                prepend-inner-icon="mdi-counter"
                                variant="outlined"
                                density="compact"
                                color="#105645"
                                :rules="[required]"
                                @input="checkRegisterInput()">
                            </v-text-field>
                            <v-btn class="sms-button" width="150" height="49" @click="getSmsCode(phone)"> 获取验证码 </v-btn>
                        </v-row>
                        <!-- 输入性别 -->
                        <v-row justify="start">
                            <v-text-field
                                v-model="password"
                                style="max-width: 18%; height: 80px;"
                                label="密码"
                                placeholder="请设置密码"
                                prepend-inner-icon="mdi-lock"
                                variant="outlined"
                                density="compact"
                                color="#105645"
                                :rules="[required]"
                                type="password"
                                clearable
                                @input="checkRegisterInput()">
                            </v-text-field>
                            <v-text-field
                                v-model="passwordCertain"
                                style="max-width: 18%; height: 80px; margin-left: 5.5%;"
                                label="确认密码"
                                placeholder="请确认密码"
                                prepend-inner-icon="mdi-lock"
                                variant="outlined"
                                density="compact"
                                color="#105645"
                                :rules="[required]"
                                type="password"
                                clearable
                                @input="checkRegisterInput()">
                            </v-text-field>
                        </v-row>
                        <v-row justify="start">
                            <v-text-field
                                v-model="email"
                                style="max-width: 25.5%; height: 80px;"
                                label="邮箱"
                                placeholder="请输入密码"
                                prepend-inner-icon="mdi-email"
                                variant="outlined"
                                density="compact"
                                color="#105645"
                                :rules="[required]"
                                clearable
                                @input="checkRegisterInput()">
                            </v-text-field>
                            <v-text-field
                                v-model="age"
                                style="max-width: 12%; height: 80px; margin-left: 4%;"
                                label="年龄"
                                placeholder="请输入年龄"
                                prepend-inner-icon="mdi-counter"
                                variant="outlined"
                                density="compact"
                                color="#105645"
                                :rules="[required]"
                                clearable
                                @input="checkRegisterInput()">
                            </v-text-field>
                        </v-row>
                        <v-row justify="start">
                            <v-text-field
                                v-model="region"
                                style="max-width: 20%; height: 80px;"
                                label="地区"
                                placeholder="请输入地区"
                                prepend-inner-icon="mdi-web"
                                variant="outlined"
                                density="compact"
                                color="#105645"
                                :rules="[required]"
                                clearable
                                @input="checkRegisterInput()">
                            </v-text-field>
                            <span style="color:rgb(115,115,115); position:relative; top: 10px; left: 6%;">性别：</span>
                            <v-radio-group
                                v-model="gender"
                                inline
                                density="compact"
                                style="display: inline-block; position:relative; top: 8px; left: 5%;"
                                @input="checkRegisterInput()">
                                <v-radio label="男" value="male" color="#105645" style="margin-left: 2%;"></v-radio>
                                <v-radio label="女" value="female" color="#105645" style="margin-left: 2%;"></v-radio>
                            </v-radio-group>
                            
                        </v-row>
                    </v-container>

                    <!-- 确认按钮 -->
                    <v-btn
                        :loading="registerLoading"
                        :class="'submit-button-' + (inputPass ? 'pass' : 'error')"
                        type="submit"
                        variant="elevated"
                        width="160"
                        height="60"
                        @click="submitRegisterForm"
                        :active="!inputPass">
                        {{ inputPass ? "接 受" : inputErrorInfo }}
                    </v-btn>
                    <v-btn
                        :loading="registerLoading"
                        class="cancel-button"
                        type="submit"
                        variant="elevated"
                        width="160"
                        height="60"
                        @click="$router.push('/')">
                        拒 绝
                    </v-btn>
                </v-form>
            </v-img>
        </div>
    </div>
</template>
  
<script>
// import HelloWorld from '@/components/HelloWorld.vue'
import { sendSmsRegister, register } from '@/axios/login'
export default {
    data: () => ({
        //控制此时页面的状态
        registerLoading: false,   //表示默认的提交表单的按钮状态不是正在加载中的
        form: false,             //表单状态
        registerSuccess: false,
        //用户填写的信息
        avatar: "",
        username: "",
        phone: "",
        smsInfo: "123456",
        smsCode: "",
        password: "",
        passwordCertain: "",
        email: "",
        age: 0,
        gender: "",
        region: "",
        //输入错误提示
        inputPass: false,
        inputErrorInfo: "",
        //获取当前时间和日期
        Time: "",                  //当前时间
        date: "",                  //当前日期
        //学校所有的学院名称
        instituteList:['机械与能源学院', '生命科学与技术学院', '铁道与城市轨道交通研究院', '物理科学与工程学院','建筑与城市规划学院','汽车学院','数学科学学院','土木工程学院','海洋与地球科学学院','设计创意学院','医学院','新生院','电子信息与工程学院','法学院','人文学院','外国语学院','环境科学与工程学院','体育教学部','艺术与传媒学院','经济与管理学院','马克思主义学院','政治与国际关系学院','中德工程学院','测绘与地理信息学院','航空航天与力学学院','软件学院','中德学院','材料科学与工程学院','化学科学与工程学院','交通运输工程学院','口腔医学院','上海国际知识产权学院','同济大学附属医院','校医院'],
        gradeList:['大一','大二','大三','大四','大五','研一','研二','研三','博士生及以上']
    }),
    methods: {
        getImgSrc: function(url) {
            return new URL(url, import.meta.url).href;
        },
        //阻止浏览器默认提交表单的空函数
        onSubmit: function() {

        },
        //处理忘记密码逻辑
        forgotPasswordHandler() {
            //将用户重定向到重新设置密码的界面
            this.$router.push('/set-code')

        },

        //获取手机验证码
        getSmsCode(phone) {
            sendSmsRegister(phone).then(res => {
                console.log(res);
                if (!res.ifSend) {
                    alert("验证码未发送，请检查号码填写！");
                    return;
                }

                this.smsInfo = res.code;
            }, err => {
                console.log(err);
            });
        },
        checkRegisterInput: function() {
            if (this.avatar == '') {
                this.inputErrorInfo = "请选择头像！";
                this.inputPass = false;
                return;
            }

            if (this.smsInfo == '') {
                this.inputErrorInfo = "请获取验证码！";
                this.inputPass = false;
                return;
            }

            if (this.smsCode != this.smsInfo) {
                this.inputErrorInfo = "请检查验证码是否正确！";
                this.inputPass = false;
                return;
            }

            if (this.password != this.passwordCertain) {
                this.inputErrorInfo = "两次密码输入不一致！";
                this.inputPass = false;
                return;
            }

            if (!this.form) {
                this.inputErrorInfo = "请填写完整信息！";
                this.inputPass = false;
                return;
            }

            this.inputErrorInfo = "";
            this.inputPass = true;
        },
        //用户填完所有的表单项目，进行注册提交
        submitRegisterForm() {
            if (!this.inputPass)
                return;

            //提交表单内容，与后端交互，判断是否注册成功
            this.registerLoading = true

            register({
                name: this.username,
                phone: this.phone,
                password: this.password,
                email: this.email,
                age: this.age,
                region: this.region,
                avatar: this.avatar,
                gender: this.gender
            }).then((res) => {
                this.registerLoading = false;
                this.registerSuccess = true;
                console.log(res);

                setTimeout(() => {
                    this.$router.push("/");
                }, 5000);
            }, (err) => {
                this.registerLoading = false;
                alert("提交失败，请重新提交！");
                console.log(err);
            });
        },
        required(v) {
            return !!v || 'Field is required'
        },
        openFilePicker() {
            this.$refs.fileInput.click();
        },
        handleFileChange: function(event) {
            const file = event.target.files[0];

            if (!file.type.startsWith('image/')) {
                alert('请选择图片文件');
                return;
            }
            
            const reader = new FileReader();

            reader.onload = () => {
                // 将文件转换为base64编码
                const base64String = reader.result.split(",")[1];
                this.avatar = base64String; // 将base64编码存储在Vue实例中
            };

            reader.readAsDataURL(file);
        }
    },
}
</script>

<style scoped>
.Screen {
    position: relative;
    width: 100vw;
    height: 100vh;
    background-image: url(../assets/loginBackgroundImage.png);
    background-size: 100%;
}
.title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #105644;
}
.Register {
    position: absolute;
    left: 22%;
    top: 30%;
    width: 1301px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    overflow: hidden;
}
.avatar-button {
    padding: 20px;
    width: 160px;
    background-color: #105645;
    color: white;
    font-weight: 300;
    font-size: 24px;
}
.avatar-img {
    transition: 0.3s;
}
.avatar-img:hover {
    cursor: pointer;
    opacity: 0.75;
}
.sms-button {
    margin-left: 40px;
    background-color: #105645;
    color: white;
    font-size: 20px;
    font-weight: 300;
}
.submit-button-pass {
    position: relative;
    left: 73%;
    top: -30px;
    background-color: #105645;
    color: white;
    font-size: 20px;
    font-weight: 300;
}
.submit-button-error {
    position: relative;
    left: 73%;
    top: -30px;
    background-color: #631a1a;
    color: white;
    font-size: 14px;
    font-weight: 300;
}
.cancel-button {
    position: relative;
    left: 60.7%;
    top: 60px;
    background-color: #6D6D6D;
    color: white;
    font-size: 20px;
    font-weight: 300;
}
</style>