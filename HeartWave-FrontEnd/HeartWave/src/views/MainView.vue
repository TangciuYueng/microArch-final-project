<template>
  <!-- 专门用来写左边的导航栏 -->
  <v-navigation-drawer expand-on-hover rail color="#F4FFFC">
    <!-- 头像 -->
    <v-list>
      <v-list-item
        :prepend-avatar="'data:image/jpg;base64,' + userAvatar"
        :title="username"
        :subtitle="userEmail"
      />
    </v-list>

    <v-divider />
    <!-- 下面的导航 -->
    <v-list density="compact" nav>
      <v-list-item prepend-icon="mdi-home" title="主 页" value="1" @click="navigateTo('主页')" />
      <v-list-item prepend-icon="mdi-music" title="音 乐 广 场" value="2" @click="navigateTo('音乐广场')" />
      <v-list-item prepend-icon="mdi-chat" title="音 乐 室" value="3" @click="navigateTo('音乐室')" />
      <v-list-item prepend-icon="mdi-pen" title="随 笔 中 心" value="4" @click="navigateTo('随笔中心')" />
      <v-list-item prepend-icon="mdi-cog" title="个 人 设 置" value="10" @click="navigateTo('设置')" />
      <v-list-item prepend-icon="mdi-logout" title="退 出 登 录" value="11" @click="navigateTo('登录')" />
    </v-list>
  </v-navigation-drawer>

  <v-card>
    <v-layout>
      <!-- 显示主页 -->
      <home-page v-if="page == 1" @browsing-personal-homepage="handleBrowsingEvent" />
      <!-- 显示音乐广场 -->
      <music-listen
        v-if="page == 2"
        @search-event="handleSearchEvent"
        @play-music-event="handlePlayMusicEvent"
        @browsing-personal-homepage="handleBrowsingEvent"
        @detial-play-list-event="handleDetailPlayList"
      />
      <!-- 显示音乐室 -->
      <music-room v-if="page == 3" />
      <!-- 显示随笔中心 -->
      <diary v-if="page == 4" @add-diary-event="handleAddDiary" @browsing-personal-homepage="handleBrowsingEvent" />
      <!-- 显示设置界面 -->
      <setting v-if="page == 10" @browsing-personal-homepage="handleBrowsingEvent" />

      <music-search
        v-if="page == 5"
        @browsing-personal-homepage="handleBrowsingEvent"
        @detial-play-list-event="handleDetailPlayList"
      />
      <add-diary v-if="page == 6" @add-diary-event="handleAddDiarySuccessfully" />

      <img
        v-if="page == 7"
        :src="getImgSrc('../assets/retract.svg')"
        class="retract-button"
        title="getback"
        @click="page = lastPage;"
      >
      <music-playing-view v-if="page == 7" />


      <!-- 音乐播放器 -->
      <music-player @click="lastPage = (page == 7 ? lastPage : page); page = 7;" />

      <personal-homepage v-if="page == 8" :user-id="userId" />
      <play-list
        v-if="page == 9"
        :play-list-type="playListType"
        :play-list-id="playListId"
        :last-page="lastPage"
        @browsing-personal-homepage="handleBrowsingEvent"
        @last-page-event="handleLastPageEvent"
        @detial-play-list-event="handleDetailPlayList"
      />
    </v-layout>
  </v-card>
</template>
<script>
import { user, ws } from '../main.js';
import MusicPlayer from '@/components/MusicPlayer.vue'
import HomePage from '@/views/HomePage.vue'
import MusicListen from '@/views/MusicListen.vue'
import MusicRoom from '@/views/MusicRoom.vue'
import diary from '@/views/Diary.vue'
import MusicSearch from '@/views/MusicSearch.vue'
import AddDiary from '@/views/AddDiary.vue'
import MusicPlayingView from './MusicPlayingView.vue'
import PersonalHomepage from './PersonalHomepage.vue'
import PlayList from '@/views/PlayList.vue'
import setting from '@/views/Settings.vue'
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
        MusicPlayingView,
        PersonalHomepage,
        PlayList,
        setting
    },
    data: () => ({
        page: 1,
        lastPage: 1,
        username: user.name,
        userId: user.id,
        userAvatar: user.avatar,
        userEmail: user.email,
        loading: false,
        playListType: null,
        playListId: 1,
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
                case '设置':
                    this.page = 10;
                    break;
                case '登录':
                    this.page = 11;
                    this.$router.push('/');  // 使用 Vue Router 的 push 方法进行跳转
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
        getImgSrc: function (url) {
            return new URL(url, import.meta.url).href;
        },
        handleBrowsingEvent(userId) {
            this.page = 8;
            console.log(this.page)
            // console.log(this.userId);
            this.userId = userId;
        },
        handleAddDiarySuccessfully() {
            this.page = 4;
            console.log(this.page);
        },
        handlePlayMusicEvent() {
            this.page = 7;
            // record last page for page back
            this.lastPage = 2;
            console.log(this.page);
        },
        handleDetailPlayList(value, id) {
            // record last page for page back
            this.lastPage = this.page;
            if (value == 'favor') {
                // 跳转去关注的人的列表
                this.page = 110;
            } else {
                this.page = 9;
                console.log(this.page);
                const valueMapper = {
                    'created': '创建歌单',
                    'admin': '管理歌单',
                    'recommend': '推荐歌单',
                    'download': '下载歌单',
                    'like': '收藏歌单',
                    'recent': '最近收听',
                }
                this.playListType = valueMapper[value];
                this.playListId = id;
                // console.log(this.playListType)
            }
        },
        handleLastPageEvent(lastPage) {
            this.page = lastPage;
            console.log('back to', lastPage);
        }
    }
};
</script>

<style scoped>
.retract-button {
    position: fixed;
    top: 30px;
    left: 5%;
    height: 72px;
    opacity: 1;
    z-index: 1;
    cursor: pointer;
    transition: 0.3s;
}

.retract-button:hover {
    opacity: 0.5;
}
</style>