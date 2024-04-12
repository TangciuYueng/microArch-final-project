<template>
    <!-- 用于写主页面的内容 -->
    <v-main class="full-height">
                        <!-- 注册表单 -->
                        <v-form v-if="!registerSuccess" v-model="form" @submit.prevent="onSubmit">
                    <!-- 左边那一块表单，包括校区，姓名，学号等 -->
                    <v-container style="position:relative;left:200px;top:170px;margin-left: 48px;">
                        <!-- 选择头像 -->
                        <v-row justify="start">
                            <input
                                type="file"
                                ref="fileInput"
                                style="display: none;"
                                @input="laybackCheck"
                                @change="handleFileChange"
                            >
                            <v-btn
                                v-if="avatar == ''"
                                @click="openFilePicker();"
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
                                <!-- 设置用户名 -->
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
                                <!-- 电话号码 -->
                                <v-text-field
                                    v-model="phone"
                                    style="margin-left: 20px; max-width: 30%; height: 85px;"
                                    label="电话号码"
                                    placeholder="请输入电话号码"
                                    prepend-inner-icon="mdi-phone"
                                    variant="outlined"
                                    density="compact"
                                    color="#105645"
                                    size="40"
                                    :rules="[required]"
                                    @input="checkRegisterInput()">
                                </v-text-field>
                            </v-col>
                        </v-row>
                        <!-- 输入验证码 -->
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
                        <!-- 输入密码 -->
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
                                placeholder="请输入邮箱"
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

                   
                </v-form>



    </v-main>
</template>

<script>
</script>

<style scoped>
.full-height {
    min-height: 100vh;
    /* 设置最小高度为视口高度的100% */
}
</style>