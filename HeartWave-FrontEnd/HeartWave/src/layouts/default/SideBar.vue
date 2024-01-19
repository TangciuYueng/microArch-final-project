<template>
  <!-- 专门用来写左边的导航栏 -->
  <v-navigation-drawer expand-on-hover rail color="#F4FFFC">
    <!-- 头像 -->
    <v-list>
      <v-list-item prepend-avatar="https://randomuser.me/api/portraits/women/85.jpg" title="Sandra Adams"
        subtitle="sandra_a88@gmailcom"></v-list-item>
    </v-list>

    <v-divider></v-divider>
    <!-- 下面的导航 -->
    <v-list density="compact" nav>
      <v-list-item @click="navigateTo('主页')" prepend-icon="mdi-folder" title="主 页"></v-list-item>
      <v-list-item @click="navigateTo('音乐广场')" prepend-icon="mdi-music" title="音 乐 广 场"></v-list-item>
      <v-list-item @click="navigateTo('音乐室')" prepend-icon="mdi-chat" title="音 乐 室"></v-list-item>
      <v-list-item @click="navigateTo('随笔中心')" prepend-icon="mdi-pen" title="随 笔 中 心"></v-list-item>
    </v-list>
  </v-navigation-drawer>

  <v-card>
        <v-layout>
            <diary></diary>
            <!-- 音乐播放器 -->
            <music-player></music-player>
        </v-layout>
    </v-card>
</template>
<script>
import MusicPlayer from '@/components/MusicPlayer.vue'
import diary from '@/views/Diary.vue'
export default {
  //导出组件
  components: {
    MusicPlayer,
    diary
  },
  methods: {
    navigateTo(routeName) {
      // 根据传入的 routeName 使用编程式导航进行跳转
      switch (routeName) {
        case '主页':
          this.$router.push('/home-page');
          break;
        case '音乐广场':
          this.$router.push('/music-listen');
          break;
        case '音乐室':
          this.$router.push('/music-room');
          break;
        case '随笔中心':
          this.$router.push('/diary');
          break;
        // 添加其他路由的处理
      }
    },
  },
};
</script>

