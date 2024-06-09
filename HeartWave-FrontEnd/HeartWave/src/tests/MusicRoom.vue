<template>
  <div v-if="isCreateMusicRoomShow" class="music-room-create-container">
    <v-form ref="form">
      <label class="form-label">音乐室名称</label>
      <input
        v-model="newMusicRoom.name"
        type="text"
        placeholder="请输入音乐室的名字"
        class="form-input"
      />

      <label class="form-label">音乐室简介</label>
      <textarea
        v-model="newMusicRoom.intro"
        placeholder="请输入音乐室的简介"
        class="form-input"
        style="height: 80px;"
      ></textarea>

      <label class="form-label">音乐室进入权限</label>
      <v-radio-group v-model="newMusicRoom.auth" class="form-radio">
        <v-radio label="公开可见" value="public" />
        <v-radio label="私有仅通过管理员邀请可进" value="private" />
      </v-radio-group>

      <label class="form-label">音乐室头像</label>
      <button
        v-if="newMusicRoom.avatar == ''"
        type="button"
        class="form-avatar"
        @click="openFilePicker"
      >
        选择文件
      </button>
      <img
        v-else
        :src="newMusicRoom.avatar"
        class="form-avatar-img"
        @click="clearAvatar"
      />
      <input ref="fileInput" type="file" style="display: none;" @change="handleImgSelected" />

      <label class="form-label">音乐室初始歌单</label>
      <v-btn class="form-button">
        从我的歌单中选择
      </v-btn>

      <label class="form-label">邀请好友</label>
      <v-btn class="form-button">
        点击选择好友
      </v-btn>

      <br />
      <v-btn class="submit-button" @click="handleSubmit">提交审核</v-btn>
    </v-form>

    <v-dialog v-model="newMusicRoomDialog" max-width="500px">
      <v-card class="new-music-room-dialog" align="center">
        <v-card-title class="dialog-title">提交成功！</v-card-title>
        <v-card-text class="dialog-content">
          审核结果将在一个工作日内通知！
        </v-card-text>
        <v-card-actions>
          <v-btn class="dialog-button" @click="newMusicRoomDialog = false">确定</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isCreateMusicRoomShow: true,
      newMusicRoom: {
        name: '',
        intro: '',
        auth: '',
        avatar: '',
        friends: []
      },
      newMusicRoomDialog: false
    };
  },
  methods: {
    validateForm() {
      const { name, intro, auth, avatar, friends } = this.newMusicRoom;

      // 校验音乐室名称
      if (name.length === 0 || name.length > 50 || /[@$%]/.test(name)) {
        return false;
      }

      // 校验音乐室简介
      if (intro.length > 1000 || /[@$%]/.test(intro)) {
        return false;
      }

      // 校验音乐室进入权限
      if (auth !== 'public' && auth !== 'private') {
        return false;
      }

      // 校验邀请好友
      if (friends.length > 200 || friends.includes('nonexistentFriend')) {
        return false;
      }

      // 校验音乐室头像
      if (avatar && (!/\.(jpg|png)$/.test(avatar) || this.isFileTooLarge(avatar))) {
        return false;
      }

      return true;
    },

    isFileTooLarge(file) {
      // 这里应该包含你的文件大小校验逻辑，假设已经实现
      // 例如：检查文件大小是否超过2MB
      // return file.size > 2 * 1024 * 1024;
      return false;
    },

    handleImgSelected(event) {
      const file = event.target.files[0];
      if (file && /\.(jpg|png)$/.test(file.name) && !this.isFileTooLarge(file)) {
        this.newMusicRoom.avatar = URL.createObjectURL(file);
      } else {
        alert('请选择有效的图片文件（jpg或png格式，大小不超过2MB）');
      }
    },

    openFilePicker() {
      this.$refs.fileInput.click();
    },

    clearAvatar() {
      this.newMusicRoom.avatar = '';
    },

    handleSubmit() {
      if (this.validateForm()) {
        this.newMusicRoomDialog = true;
      } else {
        alert('表单校验失败，请检查输入内容。');
      }
    }
  }
};
</script>

<style scoped>
.music-room-create-container {
  /* 你的样式 */
}

.form-label {
  /* 你的样式 */
}

.form-input {
  /* 你的样式 */
}

.form-avatar {
  /* 你的样式 */
}

.form-avatar-img {
  /* 你的样式 */
}

.form-button {
  /* 你的样式 */
}

.submit-button {
  /* 你的样式 */
}

.new-music-room-dialog {
  /* 你的样式 */
}

.dialog-title {
  /* 你的样式 */
}

.dialog-content {
  /* 你的样式 */
}

.dialog-button {
  /* 你的样式 */
}
</style>
