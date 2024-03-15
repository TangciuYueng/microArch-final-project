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
            <v-list-item @click="navigateTo('主页')" prepend-icon="mdi-home" title="主 页" value="1"></v-list-item>
            <v-list-item @click="navigateTo('音乐广场')" prepend-icon="mdi-music" title="音 乐 广 场" value="2"></v-list-item>
            <v-list-item @click="navigateTo('音乐室')" prepend-icon="mdi-chat" title="音 乐 室" value="3"></v-list-item>
            <v-list-item @click="navigateTo('随笔中心')" prepend-icon="mdi-pen" title="随 笔 中 心" value="4"></v-list-item>
        </v-list>
    </v-navigation-drawer>

    <v-card>
        <v-layout>
            <!-- 显示主页 -->
            <home-page v-if="page==1"></home-page>
            <!-- 显示音乐广场 -->
            <music-listen v-if="page==2" @searchEvent="handleSearchEvent"></music-listen>
            <!-- 显示音乐室 -->
            <music-room v-if="page==3"></music-room>
            <!-- 显示随笔中心 -->
            <diary v-if="page==4" @addDiaryEvent="handleAddDiary"></diary>
            <music-search v-if="page==5"></music-search>
            <add-diary v-if="page==6"></add-diary>

            <music-playing-view v-if="page==7"></music-playing-view>
            <!-- 音乐播放器 -->
            <music-player @click="page = 7"></music-player>
        </v-layout>
    </v-card>
</template>
<script>
import MusicPlayer from '@/components/MusicPlayer.vue'
import HomePage from '@/views/HomePage.vue'
import MusicListen from '@/views/MusicListen.vue'
import MusicRoom from '@/views/MusicRoom.vue'
import diary from '@/views/Diary.vue'
import MusicSearch from '@/views/MusicSearch.vue'
import AddDiary from '@/views/AddDiary.vue'
import MusicPlayingView from './MusicPlayingView.vue'
export default {
    //导出组件
    components: {
        MusicPlayer,
        diary,
        HomePage,
        MusicListen,
        MusicRoom,
        MusicSearch,
        AddDiary,
        MusicPlayingView
    },
    data: () => ({
        page: 1,
        userAccount: null,
        password: null,
        loading: false,
    }),
    methods: {
        navigateTo(routeName) {
            // 根据传入的 routeName 使用编程式导航进行跳转
            switch (routeName) {
                case '主页':
                    this.page = 1;
                    break;
                case '音乐广场':
                    this.page = 2;
                    break;
                case '音乐室':
                    this.page = 3;
                    break;
                case '随笔中心':
                    this.page = 4;
                    break;
                // 添加其他路由的处理
            }
            console.log(this.page);
        },
        handleSearchEvent() {
            this.page = 5;
            console.log(this.page);
        },
        handleAddDiary() {
            this.page = 6;
            console.log(this.page);
        },
    },
};
</script>
  
  