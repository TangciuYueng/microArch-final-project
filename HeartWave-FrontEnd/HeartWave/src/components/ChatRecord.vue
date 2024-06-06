<template>
  <!-- 对方发送的消息样式 -->
  <div v-if="!ifSender" class="container-left">
    <!-- 对方头像 -->
    <img class="avatar" :src="'data:image/jpg;base64,' + avatar">
    
    <!-- 对方发送内容 -->
    <div class="text-container-left">
      <!-- 文本 -->
      <p v-if="type == 'TEXT'" :style="getTextStyle()" class="text">
        {{ content }}
      </p>
      <!-- 链接 -->
      <a v-else-if="type == 'URL'" :href="content" target="_blank" :style="getTextStyle()" class="text">
        {{ content }}
      </a>
      <!-- 图片 -->
      <img v-else :src="'data:image/jpg;base64,' + content" style="width: 60%;">
    </div>
  </div>

  <!-- 自己发送的消息样式 -->
  <div v-else class="container-right">
    <!-- 自己发送内容 -->
    <div class="text-container-right">
      <!-- 文本 -->
      <p v-if="type == 'TEXT'" :style="getTextStyle()" class="text">
        {{ content }}
      </p>
      <!-- 链接 -->
      <a v-else-if="type == 'URL'" :href="content" target="_blank" :style="getTextStyle()" class="text">
        {{ content }}
      </a>
      <!-- 图片 -->
      <img v-else :src="'data:image/jpg;base64,' + content" style="width: 50%;">
    </div>
    <!-- 自己头像 -->
    <img class="avatar" :src="'data:image/jpg;base64,' + avatar">
  </div>
</template>

<script>
export default {
  props: {
    avatar: "",
    content: "",
    ifSender: true,
    type: ""
  },
  methods: {
    getTextStyle: function() {
      return {
        backgroundColor: this.ifSender ? "#45CE9A" : "#6BC4AE4F"
      }
    }
  }
}
</script>

<style scoped>
.container-left {
  margin-top: 15px;
  margin-left: 3%;
}
.container-right {
  margin-top: 15px;
  margin-right: -15%;
}
.avatar {
  display: inline-block;
  width: 42px;
  height: 42px;
  border-radius: 21px;
}
.text-container-left {
  position: relative;
  display: inline-block;
  top: -16px;
  left: 2%;
  width: 80%;
}
.text-container-right {
  position: relative;
  display: inline-block;
  top: -16px;
  right: 2%;
  width: 80%;
  text-align: right;  /* 组件右对齐 */
}
.text {
  border-radius: 5px;
  display: inline-block;
  padding: 8px;
}
</style>