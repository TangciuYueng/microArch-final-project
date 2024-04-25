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
                        <v-data-table v-if="activeTab === 0" :items="musics" :headers="totalHeaders[activeTab]"
                            v-model:page="musicPage">
                            <template v-slot:headers="{ columns, isSorted, getSortIcon, toggleSort }">
                                <tr>
                                    <template v-for="column in columns" :key="column.key">
                                        <td>
                                            <span class="table-header" @click="() => toggleSort(column)">{{
                                    column.title }}</span>
                                            <template v-if="isSorted(column)">
                                                <v-icon :icon="getSortIcon(column)"></v-icon>
                                            </template>
                                        </td>
                                    </template>
                                </tr>
                            </template>
                            <template v-slot:item.actions="{ item }">
                                <v-icon style="cursor: pointer;" size="large">
                                    mdi-play
                                </v-icon>
                            </template>
                            <template v-slot:bottom>
                                <div class="text-center pt-2">
                                    <v-pagination v-model="musicPage" :length="musicPageCount"
                                        :total-visible="musicPageVisible" @input="onMusicPageChange"></v-pagination>
                                </div>
                            </template>
                            <template v-slot:no-data>
                                <div>暂无数据~</div>
                            </template>
                        </v-data-table>
                        <v-data-table v-else-if="activeTab === 1" :items="playLists" :headers="totalHeaders[activeTab]"
                            v-model:page="playListPage">
                            <template v-slot:headers="{ columns, isSorted, getSortIcon, toggleSort }">
                                <tr>
                                    <template v-for="column in columns" :key="column.key">
                                        <td>
                                            <span class="table-header" @click="() => toggleSort(column)">{{
                                    column.title }}</span>
                                            <template v-if="isSorted(column)">
                                                <v-icon :icon="getSortIcon(column)"></v-icon>
                                            </template>
                                        </td>
                                    </template>
                                </tr>
                            </template>
                            <template v-slot:bottom>
                                <div class="text-center pt-2">
                                    <v-pagination v-model="playListPage" :length="playListPageCount"
                                        :total-visible="playListPageVisible" @input="onPlayListPageChange"></v-pagination>
                                </div>
                            </template>
                            <template v-slot:no-data>
                                <div>暂无数据~</div>
                            </template>
                        </v-data-table>
                        <v-data-table v-else-if="activeTab === 2" :items="musicRooms" :headers="totalHeaders[activeTab]"
                            v-model:page="musicRoomPage">
                            <template v-slot:headers="{ columns, isSorted, getSortIcon, toggleSort }">
                                <tr>
                                    <template v-for="column in columns" :key="column.key">
                                        <td>
                                            <span class="table-header" @click="() => toggleSort(column)">{{
                                    column.title }}</span>
                                            <template v-if="isSorted(column)">
                                                <v-icon :icon="getSortIcon(column)"></v-icon>
                                            </template>
                                        </td>
                                    </template>
                                </tr>
                            </template>
                            <template v-slot:bottom>
                                <div class="text-center pt-2">
                                    <v-pagination v-model="musicRoomPage" :length="musicRoomPageCount"
                                        :total-visible="musicRoomPageVisible" @input="onMusicRoomPageChange"></v-pagination>
                                </div>
                            </template>
                            <template v-slot:no-data>
                                <div>暂无数据~</div>
                            </template>
                        </v-data-table>
                        <v-data-table v-else-if="activeTab === 3" :items="albums" :headers="totalHeaders[activeTab]"
                            v-model:page="albumPage">
                            <template v-slot:headers="{ columns, isSorted, getSortIcon, toggleSort }">
                                <tr>
                                    <template v-for="column in columns" :key="column.key">
                                        <td>
                                            <span class="table-header" @click="() => toggleSort(column)">{{
                                    column.title }}</span>
                                            <template v-if="isSorted(column)">
                                                <v-icon :icon="getSortIcon(column)"></v-icon>
                                            </template>
                                        </td>
                                    </template>
                                </tr>
                            </template>
                            <template v-slot:bottom>
                                <div class="text-center pt-2">
                                    <v-pagination v-model="albumPage" :length="albumPageCount"
                                        :total-visible="albumPageVisible" @input="onAlbumPageChange"></v-pagination>
                                </div>
                            </template>
                            <template v-slot:no-data>
                                <div>暂无数据~</div>
                            </template>
                        </v-data-table>
                        <v-data-table v-else-if="activeTab === 4" :items="users" :headers="totalHeaders[activeTab]"
                            v-model:page="userPage">
                            <template v-slot:headers="{ columns, isSorted, getSortIcon, toggleSort }">
                                <tr>
                                    <template v-for="column in columns" :key="column.key">
                                        <td>
                                            <span class="table-header" @click="() => toggleSort(column)">{{
                                    column.title }}</span>
                                            <template v-if="isSorted(column)">
                                                <v-icon :icon="getSortIcon(column)"></v-icon>
                                            </template>
                                        </td>
                                    </template>
                                </tr>
                            </template>
                            <template v-slot:bottom>
                                <div class="text-center pt-2">
                                    <v-pagination v-model="userPage" :length="userPageCount"
                                        :total-visible="userPageVisible" @input="onUserPageChange"></v-pagination>
                                </div>
                            </template>
                            <template v-slot:no-data>
                                <div>暂无数据~</div>
                            </template>
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
                    <v-data-table :items="friends" :items-per-page="5" v-model:page="friendPage" class="elevation-1"
                        :headers="friendHeaders">
                        <template v-slot:headers="{ columns, isSorted, getSortIcon, toggleSort }">
                            <tr>
                                <template v-for="column in columns" :key="column.key">
                                    <td>
                                        <span class="table-header" @click="() => toggleSort(column)">{{
                                    column.title }}</span>
                                        <template v-if="isSorted(column)">
                                            <v-icon :icon="getSortIcon(column)"></v-icon>
                                        </template>
                                    </td>
                                </template>
                            </tr>
                        </template>
                        <template v-slot:item.avatar="{ item }">
                            <v-avatar size="32">
                                <img :src="item.avatar" />
                            </v-avatar>
                        </template>
                        <template v-slot:item.mood="{ item }">
                            {{ item.mood }}
                        </template>
                        <template v-slot:bottom>
                            <div class="text-center pt-2">
                                <v-pagination v-model="friendPage" :length="friendPageCount"
                                    :total-visible="friendPageVisible" @input="onFriendPageChange"></v-pagination>
                            </div>
                        </template>
                        <template v-slot:no-data>
                            <div>暂无数据~</div>
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

        musics: [
            { id: 1, name: "快乐崇拜", singer: "潘玮柏/张韶涵", album: "aa", duration: 24 },
            { id: 2, name: "欢乐颂", singer: "未知歌手", album: "未知专辑", duration: 0 },
            { id: 3, name: "欢乐之歌", singer: "未知歌手", album: "未知专辑", duration: 0 },
            { id: 4, name: "欢乐时光", singer: "Joy Artist", album: "Joyful Album", duration: 30 },
            { id: 5, name: "欢乐节日", singer: "Happy Band", album: "Festive Collection", duration: 28 },
            { id: 6, name: "开心欢乐", singer: "Smiley Singer", album: "Cheerful Hits", duration: 33 },
            { id: 7, name: "欢乐万岁", singer: "Joyful Singers", album: "Eternal Happiness", duration: 26 },
            { id: 8, name: "欢乐之夜", singer: "Happiness Ensemble", album: "Night of Joy", duration: 31 },
            { id: 9, name: "欢乐星期天", singer: "Joyful Voices", album: "Sunny Day Sounds", duration: 29 },
            { id: 10, name: "欢乐气氛", singer: "Merry Melodies", album: "Happy Vibes", duration: 27 },
            { id: 11, name: "欢乐时刻", singer: "Lively Artist", album: "Joyful Moments", duration: 32 }
        ],
        musicPage: 1,
        musicPageCount: 3,
        musicPageVisible: 3,

        playLists: [
            { id: 1, name: "Relaxing Vibes", creator: "John Doe", tracks: 15 },
            { id: 2, name: "Workout Hits", creator: "Jane Smith", tracks: 10 },
        ],
        playListPage: 1,
        playListPageCount: 3,
        playListPageVisible: 3,

        musicRooms: [
            { id: 1, name: "Chill Zone", members: 20, },
            { id: 2, name: "Jam Session", members: 15, },
        ],
        musicRoomPage: 1,
        musicRoomPageCount: 3,
        musicRoomPageVisible: 3,

        albums: [
            { id: 1, title: "Golden Melodies", artist: "Alicia Keys", year: 2022 },
            { id: 2, title: "Summer Breeze", artist: "Maroon 5", year: 2021 },
        ],
        albumPage: 1,
        albumPageCount: 3,
        albumPageVisible: 3,

        users: [
            { id: 1, username: "musiclover123", },
            { id: 2, username: "rockstar22", },
        ],
        userPage: 1,
        userPageCount: 3,
        userPageVisible: 3,

        totalHeaders: [
            [
                { title: "音乐名称", value: "name", },
                { title: "歌手", value: "singer", },
                { title: "专辑", value: "album", },
                { title: "时长", value: "duration", },
                { title: "操作", value: "actions", },
            ],
            [
                { title: "歌单名称", value: "name", },
                { title: "创作者", value: "creator", },
                { title: "歌曲数量", value: "tracks", },
            ],
            [
                { title: '音乐室名称', value: 'name', },
                { title: '成员数量', value: 'members', },
            ],
            [
                { title: '专辑名称', value: 'title', },
                { title: '作者', value: 'artist', },
                { title: '创作时间', value: 'year', },
            ],
            [
                { title: '用户名称', value: 'username', },
            ],
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
        friendHeaders: [
            { title: '头像', value: "avatar", },
            { title: '用户名', value: "username", },
            { title: '心情值', value: "emotionValue", },
        ],
        friends: [
            { id: 1, avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Alice', emotionValue: '😊' },
            { id: 2, avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Bob', emotionValue: '😄' },
            { id: 3, avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Bob', emotionValue: '😄' },
            { id: 4, avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Bob', emotionValue: '😄' },
            { id: 5, avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Bob', emotionValue: '😄' },
            { id: 6, avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Bob', emotionValue: '😄' },
            { id: 7, avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Bob', emotionValue: '😄' },
            { id: 9, avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Bob', emotionValue: '😄' },
            { id: 10, avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Bob', emotionValue: '😄' },
            { id: 11, avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Bob', emotionValue: '😄' },
            { id: 12, avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Bob', emotionValue: '😄' },
            { id: 13, avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Bob', emotionValue: '😄' },
            { id: 14, avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Bob', emotionValue: '😄' },
            { id: 15, avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Bob', emotionValue: '😄' },
        ],
        friendPage: 1,
        friendPageCount: 3,
        friendPageVisible: 3,
    }),
    methods: {
        handleActiveTab(index) {
            this.activeTab = index;
        },
        onFriendPageChange(page) {
            // wait for append data to the friend list
            this.friendPage = page;
        },
        onMusicPageChange(page) {
            // wait for append data to the music list
            this.friendPage = page;
        },
        onPlayListPageChange(page) {
            // wait for append data to the play list list
            this.friendPage = page;
        },
        onMusicRoomPageChange(page) {
            // wait for append data to the music room list
            this.friendPage = page;
        },
        onAlbumPageChange(page) {
            // wait for append data to the album list
            this.friendPage = page;
        },
        onUserPageChange(page) {
            // wait for append data to the user list
            this.friendPage = page;
        },
    },
    mounted() {
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

.table-header {
    cursor: pointer;
    font-size: large;
    font-weight: 600;
}
</style>