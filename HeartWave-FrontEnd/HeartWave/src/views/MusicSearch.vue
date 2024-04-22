<template>
    <v-app>
        <v-container fluid>
            <v-row>
                <v-col cols="8">
                    <v-row>
                        <v-col col="6">
                            <span style="color: rgb(60, 153, 130); font-size:xx-large; font-weight: bold;">
                                {{ keyWord }}
                            </span>
                            <span style="font-size: medium; font-style: oblique;">
                                找到{{ singleMusicNum }}首单曲，{{ musicListNum }}个歌单，{{ musicRoomNum }}个音乐室
                            </span>
                        </v-col>
                        <v-col col="6">
                            <div style="display: flex; align-items: center;">
                                <v-text-field v-model="search" label="搜索" placeholder="请输入心情/歌曲名称进行搜索" outlined
                                    rounded></v-text-field>
                                <v-icon class="ml-3 mb-5" size="x-large" style="cursor: pointer;">mdi-magnify</v-icon>
                            </div>
                        </v-col>
                    </v-row>

                    <div class="tabs">
                        <div v-for="(tab, index) in tabs" :key="index"
                            :class="{ 'tab': true, 'active': activeTab === index }" @click="handleActiveTab(index)">
                            {{ tab }}
                        </div>
                    </div>

                    <v-container style="height: 700px; overflow-y: auto;">
                        <v-data-table :items="selectedList">
                        </v-data-table>
                    </v-container>
                </v-col>
                <v-col cols="4" style="background-color: #D5F0EA;">
                    <v-card class="profile-card" :style="{ backgroundImage: 'url(' + profileImgSrc + ')' }">
                        <v-card-text>
                            <v-avatar size="100">
                                <img :src="avatarSrc" alt="avatar" style="cursor: pointer;"
                                    @click="this.$emit('BrowsingPersonalHomepage', userId)" />
                            </v-avatar>
                            <div style="font-size: 20px;" class="mt-2">{{ username }}</div>
                            <div style="font-size: 12px;">
                                <v-icon color="red">mdi-heart</v-icon>
                                IP属地: {{ ipLocation }}
                            </div>
                            <div style="font-size: larger;" class="mt-5">
                                <v-row>
                                    <v-col cols="4">
                                        <div>好友</div>
                                        <div>{{ friendNum }}</div>
                                    </v-col>
                                    <v-col cols="4">
                                        <div>心情值</div>
                                        <div>{{ emotionValue }}</div>
                                    </v-col>
                                    <v-col cols="4">
                                        <div>访客</div>
                                        <div>{{ visitorNum }}</div>
                                    </v-col>
                                </v-row>
                            </div>
                        </v-card-text>
                    </v-card>
                    <v-card class="mt-10" style="background-color: rgba(255, 255, 255, 0.5); padding: 5%;">
                        <v-row dense>
                            <v-col cols="3" v-for="icon in profileIcons" style="text-align: center;">
                                <div v-if="icon.value !== 'favor'"
                                    @click="this.$emit('detialPlayListEvent', icon.value)">
                                    <v-icon color="red" size="40" style="cursor: pointer;">{{ icon.icon }}</v-icon>
                                    <div style="cursor: pointer;">{{ icon.text }}
                                    </div>
                                </div>
                                <div v-else @click="favorDialog = true">
                                    <v-icon color="red" size="40" style="cursor: pointer;">{{ icon.icon }}</v-icon>
                                    <div style="cursor: pointer;">{{ icon.text }}
                                    </div>
                                </div>
                            </v-col>
                        </v-row>
                    </v-card>
                    <div style="font-size: 20px;" class="mt-3">我创建的歌单></div>
                    <v-row dense>
                        <v-col cols="4" v-for="music in selfCreatedMusicList">
                            <v-card style="text-align: center;" color="transparent"
                                @click="this.$emit('detialPlayListEvent', 'created')" hover>
                                <v-img :src="music.src" cover></v-img>
                                <v-card-text
                                    style="display: flex; justify-content: space-between; align-items: center;">
                                    <span style="font-size: larger;">{{ music.text }}</span>
                                    <v-icon color="white" size="30">
                                        mdi-play-circle
                                    </v-icon>
                                </v-card-text>
                            </v-card>
                        </v-col>
                    </v-row>
                    <div style="font-size: 20px;" class="mt-3">我管理的歌单></div>
                    <v-row dense>
                        <v-col cols="4" v-for="music in selfCreatedMusicList">
                            <v-card style="text-align: center;" color="transparent"
                                @click="this.$emit('detialPlayListEvent', 'admin')" hover>
                                <v-img :src="music.src" cover></v-img>
                                <v-card-text
                                    style="display: flex; justify-content: space-between; align-items: center;">
                                    <span style="font-size: larger;">{{ music.text }}</span>
                                    <v-icon color="white" size="30">
                                        mdi-play-circle
                                    </v-icon>
                                </v-card-text>
                            </v-card>
                        </v-col>
                    </v-row>
                </v-col>
            </v-row>
        </v-container>
        <v-dialog v-model="favorDialog" max-width="800">
            <v-card>
                <v-card-title>关心好友</v-card-title>
                <v-card-text>
                    <v-data-table :items="friends" :items-per-page="5" class="elevation-1">
                        <template v-slot:item.avatar="{ item }">
                            <v-avatar size="32">
                                <img :src="item.avatar" />
                            </v-avatar>
                        </template>
                        <template v-slot:item.mood="{ item }">
                            {{ item.mood }}
                        </template>
                    </v-data-table>
                </v-card-text>
            </v-card>
        </v-dialog>
    </v-app>
</template>

<script>
export default {
    //导出组件
    data: () => ({
        userId: 1,
        keyWord: '欢快',
        singleMusicNum: 166,
        musicListNum: 80,
        musicRoomNum: 33,

        selectedList: [],

        totalList: [
            [
                { "音乐名称": "快乐崇拜", "歌手": "潘玮柏/张韶涵", "专辑": "aa", "时长": 24 },
                { "音乐名称": "欢乐颂", "歌手": "未知歌手", "专辑": "未知专辑", "时长": 0 },
                { "音乐名称": "欢乐之歌", "歌手": "未知歌手", "专辑": "未知专辑", "时长": 0 },
                { "音乐名称": "欢乐时光", "歌手": "Joy Artist", "专辑": "Joyful Album", "时长": 30 },
                { "音乐名称": "欢乐节日", "歌手": "Happy Band", "专辑": "Festive Collection", "时长": 28 },
                { "音乐名称": "开心欢乐", "歌手": "Smiley Singer", "专辑": "Cheerful Hits", "时长": 33 },
                { "音乐名称": "欢乐万岁", "歌手": "Joyful Singers", "专辑": "Eternal Happiness", "时长": 26 },
                { "音乐名称": "欢乐之夜", "歌手": "Happiness Ensemble", "专辑": "Night of Joy", "时长": 31 },
                { "音乐名称": "欢乐星期天", "歌手": "Joyful Voices", "专辑": "Sunny Day Sounds", "时长": 29 },
                { "音乐名称": "欢乐气氛", "歌手": "Merry Melodies", "专辑": "Happy Vibes", "时长": 27 },
                { "音乐名称": "欢乐时刻", "歌手": "Lively Artist", "专辑": "Joyful Moments", "时长": 32 }
            ],
            [
                { id: 1, name: "Relaxing Vibes", owner: "John Doe", tracks: 15 },
                { id: 2, name: "Workout Hits", owner: "Jane Smith", tracks: 10 },
            ],
            [
                { id: 1, name: "Chill Zone", members: 20, privacy: "public" },
                { id: 2, name: "Jam Session", members: 15, privacy: "private" },
            ],
            [
                { id: 1, title: "Golden Melodies", artist: "Alicia Keys", year: 2022 },
                { id: 2, title: "Summer Breeze", artist: "Maroon 5", year: 2021 },
            ],
            [
                { id: 1, username: "musiclover123", followers: 500, following: 300 },
                { id: 2, username: "rockstar22", followers: 1000, following: 800 },
            ]
        ],

        profileIcons: [
            { icon: 'mdi-download', text: '本地/下载', value: 'download' },
            { icon: 'mdi-account', text: '关注', value: 'favor' },
            { icon: 'mdi-heart', text: '我喜欢', value: 'like' },
            { icon: 'mdi-radio', text: '最近收听', value: 'recent' },
        ],
        selfCreatedMusicList: [
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: 'aaa' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: 'aaa' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: 'aaa' },
        ],
        selfCollectedMusicList: [
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: 'aaa' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: 'aaa' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: 'aaa' },
        ],
        profileImgSrc: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg',
        avatarSrc: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg',
        username: 'deidei',
        ipLocation: '上海',
        friendNum: 24,
        emotionValue: 78,
        visitorNum: 234,

        tabs: ['单曲', '歌单', '音乐室', '专辑', '用户'],
        activeTab: 0,

        favorDialog: false,
        friends: [
            { avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Alice', emotionValue: '😊' },
            { avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Bob', emotionValue: '😄' },
            // 添加更多好友数据
        ],
    }),
    methods: {
        handleActiveTab(index) {
            this.activeTab = index;
            this.selectedList = this.totalList[index];
        }
    },
    mounted() {
        this.selectedList = this.totalList[0];
    }
}
</script>

<style scoped>
.profile-card {
    background-size: cover;
    box-shadow: 0 5px 6px rgba(0, 0, 0, 0.5);
    /* 下面两个圆角 */
    border-radius: 0 0 50px 50px;
    min-height: 300px;
    text-align: center;
    color: white;
}

.tabs {
    border-bottom: rgba(60, 153, 130, 0.6) solid 5px;
}

.tab {
    display: inline-block;
    padding: 10px 20px;
    cursor: pointer;
    margin-right: 5px;
    border-top-left-radius: 20px;
    border-top-right-radius: 20px;
}

.active {
    background-color: rgba(60, 153, 130, 0.6);
    /* 选中的背景颜色 */
    color: white;
    /* 选中的字体颜色 */
}

.tab:hover:not(.active) {
    background-color: rgba(84, 161, 142, 0.4);
    /* 悬停时的背景颜色 */
    color: white;
    /* 悬停时的字体颜色 */
}
</style>