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
            <home-page v-if="page == 1" @BrowsingPersonalHomepage="handleBrowsingEvent"></home-page>
            <!-- 显示音乐广场 -->
            <music-listen v-if="page == 2" @searchEvent="handleSearchEvent" @playMusicEvent="handlePlayMusicEvent"
                @BrowsingPersonalHomepage="handleBrowsingEvent"
                @detialPlayListEvent="handleDetailPlayList"></music-listen>
            <!-- 显示音乐室 -->
            <music-room v-if="page == 3"></music-room>
            <!-- 显示随笔中心 -->
            <diary v-if="page == 4" @addDiaryEvent="handleAddDiary" @BrowsingPersonalHomepage="handleBrowsingEvent">
            </diary>
            <music-search v-if="page == 5" @BrowsingPersonalHomepage="handleBrowsingEvent"
                @detialPlayListEvent="handleDetailPlayList"></music-search>
            <add-diary v-if="page == 6" @addDiaryEvent="handleAddDiarySuccessfully"></add-diary>

            <img v-if="page == 7" :src="getImgSrc('../assets/retract.svg')" class="retract-button"
                @click="page = lastPage;" title="getback">
            <music-playing-view v-if="page == 7"></music-playing-view>
            <!-- 音乐播放器 -->
            <music-player @click="lastPage = (page == 7 ? lastPage : page); page = 7;"></music-player>

            <personal-homepage v-if="page == 8" :userId="userId"></personal-homepage>
            <play-list v-if="page == 9" @BrowsingPersonalHomepage="handleBrowsingEvent" :playListType="playListType"
                :playListId="playListId"></play-list>
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
import PersonalHomepage from './PersonalHomepage.vue'
import PlayList from '@/views/PlayList.vue'
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
    },
    data: () => ({
        page: 1,
        lastPage: 1,
        userAccount: null,
        password: null,
        loading: false,
        playListType: null,
        playListId: 1,
        userId: 1,
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
        }
    },
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