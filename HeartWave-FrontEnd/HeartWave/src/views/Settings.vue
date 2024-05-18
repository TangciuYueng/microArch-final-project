<template>
  <v-snackbar
    v-model="ifSnackBarShow"
    timeout="3000"
    color="#105645"
    style="margin-bottom: 90vh;">
    {{ snackBarMessage }}
  </v-snackbar>

  <!-- 用于写主页面的内容 -->
  <v-main class="full-height">
    <v-img
      :src="getImgSrc('../assets/settings.jpg')"
      style="height: 90vh; width: 100%; position: fixed; top: 0; left: 0;">
    </v-img>
    
    <!-- 注册表单 -->
    <v-form v-model="userInfoForm" @submit.prevent="onSubmit">
      <!-- 左边那一块表单，包括校区，姓名，学号等 -->
      <v-container style="position: relative; left: 7%; top: 256px;">
        <v-row>
          <label
            :style="{
              color: change.avatar ? '#105645' : '#7BAE9E',
              fontSize: '19px'
            }">
            头像{{ change.avatar ? '*' : '' }}
          </label>
          <label
            :style="{
              color: change.name ? '#105645' : '#7BAE9E',
              fontSize: '19px',
              marginLeft: '15.3%'
            }">
            用户名{{ change.name ? '*' : '' }}
          </label>
        </v-row>
        <!-- 选择头像 -->
        <v-row justify="start" style="margin-top: 20px;">
          <input
            ref="fileInput"
            type="file"
            style="display: none;"
            @input="laybackCheck"
            @change="handleFileChange"
          >
          <img
            :src="'data:image/jpg;base64,' + userInfo.avatar"
            class="avatar-img"
            @click="openFilePicker();"
          >
          <v-col>
            <!-- 设置用户名 -->
            <v-text-field
              v-model="userInfo.name"
              :readonly="loading"
              style="margin-top: -10px; margin-left: 4.5%; max-width: 34%; height: 76px;"
              placeholder="请输入用户名"
              prepend-inner-icon="mdi-account"
              variant="outlined"
              density="compact"
              color="#7BAE9E"
              :rules="[required]"
              @input="checkSetInput()">
            </v-text-field>
            <v-row>
              <v-col style="margin-left: 2%; margin-top: -12px; max-width: 22%;">
                <label
                  :style="{
                    color: change.gender ? '#105645' : '#7BAE9E',
                    fontSize: '19px',
                    marginLeft: '12%'
                  }">
                  性别{{ change.gender ? '*' : '' }}
                </label>
                <br>
                <v-radio-group
                  v-model="userInfo.gender"
                  :readonly="loading"
                  inline
                  density="comfortable"
                  style="display: inline-block; width: 100%; margin-left: 7%; margin-top: 12px;"
                  @input="checkSetInput()">
                  <v-radio label="男" value="MALE" color="#105645"></v-radio>
                  <v-radio label="女" value="FEMALE" color="#105645" style="margin-left: 14%;"></v-radio>
                </v-radio-group>
              </v-col>
              <v-col style="margin-top: -12px; max-width: 22%;">
                <label
                  :style="{
                    color: change.age ? '#105645' : '#7BAE9E',
                    fontSize: '19px',
                    marginLeft: '-6%'
                  }">
                  年龄{{ change.age ? '*' : '' }}
                </label>
                <v-text-field
                  v-model="userInfo.age"
                  :readonly="loading"
                  style="max-width: 75%; height: 76px; margin-left: -6%; margin-top: 10px;"
                  placeholder="请输入年龄"
                  prepend-inner-icon="mdi-counter"
                  variant="outlined"
                  density="compact"
                  color="#7BAE9E"
                  :rules="[required]"
                  clearable
                  @input="checkSetInput()">
                </v-text-field>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
        <v-row style="margin-top: -19px;">
          <label
            :style="{
              color: change.email ? '#105645' : '#7BAE9E',
              fontSize: '19px'
            }">
            邮箱{{ change.email ? '*' : '' }}
          </label>
          <label
            :style="{
              color: change.region ? '#105645' : '#7BAE9E',
              fontSize: '19px',
              marginLeft: '21.7%'
            }">
            地区{{ change.region ? '*' : '' }}
          </label>
        </v-row>
        <!-- 输入验证码 -->
        <v-row style="margin-top: 20px;">
          <v-text-field
            v-model="userInfo.email"
            :readonly="loading"
            style="max-width: 22%; height: 76px;"
            placeholder="请输入邮箱"
            prepend-inner-icon="mdi-email"
            variant="outlined"
            density="compact"
            color="#7BAE9E"
            :rules="[required]"
            clearable
            @input="checkSetInput()">
          </v-text-field>
          <v-text-field
            v-model="userInfo.region"
            :readonly="loading"
            style="margin-left: 3%; max-width: 22%; height: 76px;"
            placeholder="请输入地区"
            prepend-inner-icon="mdi-web"
            variant="outlined"
            density="compact"
            color="#7BAE9E"
            :rules="[required]"
            clearable
            @input="checkSetInput()">
          </v-text-field>
        </v-row>
      </v-container>
    </v-form>

    <v-btn
      :loading="loading"
      class="submit-button"
      color="#3C92A6"
      @click="handleSettingsSubmit">
      保存
    </v-btn>
  </v-main>
</template>

<script>
import { user } from '@/main';
import { updateUserInfo } from '../axios/login.js';
export default {
  data() {
    return {
      userInfoForm: false,
      userInfo: {
        avatar: user.avatar,
        name: user.name,
        gender: user.gender,
        age: user.age,
        email: user.email,
        region: user.region
      },
      change: {
        avatar: false,
        name: false,
        gender: false,
        age: false,
        email: false,
        region: false
      },
      ifSnackBarShow: false,
      snackBarMessage: "",
      loading: false
    }
  },
  methods: {
    getImgSrc: function(url) {
      return new URL(url, import.meta.url).href;
    },
    //阻止浏览器默认提交表单的空函数
    onSubmit: function() {

    },
    laybackCheck: function() {
      setTimeout(this.checkRegisterInput, 100);
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
        this.userInfo.avatar = base64String; // 将base64编码存储在Vue实例中
        this.checkSetInput();
      };

      reader.readAsDataURL(file);
    },
    openFilePicker() {
      this.$refs.fileInput.click();
    },
    required(v) {
      return !!v || 'Field is required'
    },
    checkSetInput: function() {
      this.change.avatar = this.userInfo.avatar != user.avatar;
      this.change.name = this.userInfo.name != user.name;
      this.change.gender = this.userInfo.gender != user.gender;
      this.change.age = this.userInfo.age != user.age;
      this.change.email = this.userInfo.email != user.email;
      this.change.region = this.userInfo.region != user.region;
    },
    handleSettingsSubmit: function() {
      if (!this.userInfoForm) {
        this.snackBarMessage = "用户信息表单填写不完整！";
        this.ifSnackBarShow = true;
        return;
      }

      if (!this.change.avatar && !this.change.name && !this.change.gender && !this.change.age && !this.change.email && !this.change.region) {
        this.snackBarMessage = "用户信息未改变，无需提交";
        this.ifSnackBarShow = true;
        return;
      }

      this.loading = true;

      updateUserInfo({
        id: user.id,
        name: this.change.name ? this.userInfo.name : null,
        avatar: this.change.avatar ? this.userInfo.avatar : null,
        gender: this.change.gender ? this.userInfo.gender : null,
        age: this.change.age ? this.userInfo.age : null,
        email: this.change.email ? this.userInfo.email : null,
        region: this.change.region ? this.userInfo.region : null
      }).then(res => {
        console.log(res);
        user.name = this.change.name ? res.name : user.name;
        user.avatar = this.change.avatar ? res.avatar : user.avatar;
        user.gender = this.change.gender ? res.gender : user.gender;
        user.age = this.change.age ? res.age : user.age;
        user.email = this.change.email ? res.email : user.email;
        user.region = this.change.region ? res.region : user.region;
        this.change.avatar = false;
        this.checkSetInput();

        this.loading = false;
        this.snackBarMessage = "设置成功";
        this.ifSnackBarShow = true;
      }, err => {
        console.log(err);
        this.loading = false;
        this.snackBarMessage = "设置失败";
        this.ifSnackBarShow = true;
      });
    }
  }
}
</script>

<style scoped>
.full-height {
  min-height: 100vh;
  /* 设置最小高度为视口高度的100% */
}
.avatar-img {
  width: 160px;
  height: 160px;
  transition: 0.3s;
}
.avatar-img:hover {
  cursor: pointer;
  opacity: 0.75;
}
:deep(.v-field__input) {
  margin-top: 4px;
  font-size: 18px;
  color: #105645;
}
.submit-button {
  position: relative;
  top: 385px;
  left: 64%;
  width: 12.5%;
  height: 57px;
  color: white;
  font-size: 20px;
}
</style>