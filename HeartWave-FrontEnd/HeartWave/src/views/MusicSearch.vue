<template>
    <v-app>
        <v-container fluid>
            <v-row>
                <v-col cols="8">
                    <v-row>
                        <v-col col="6">
                            <span>
                                {{ keyWord }}
                            </span>
                            <span>
                                找到{{ singleMusicNum }}首单曲，{{ musicListNum }}个歌单，{{ musicRoomNum }}个音乐室
                            </span>
                        </v-col>
                        <v-col col="6">
                            <v-text-field v-model="search" prepend-icon="mdi-magnify" label="搜索"
                                placeholder="请输入心情/歌曲名称进行搜索" outlined rounded></v-text-field>

                        </v-col>
                    </v-row>

                    <v-data-table :header="headers" :items="musics">

                    </v-data-table>
                </v-col>
                <v-col cols="4" style="background-color: #D5F0EA;">
                    <v-card class="profile-card" :style="{ backgroundImage: 'url(' + profileImgSrc + ')' }">
                        <v-card-text>
                            <v-avatar size="100">
                                <img :src="avatarSrc" alt="avatar" />
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
                                <v-icon color="red" size="40">{{ icon.icon }}</v-icon>
                                <div>{{ icon.text }}</div>
                            </v-col>
                        </v-row>
                    </v-card>
                    <div style="font-size: 20px;" class="mt-3">我创建的歌单></div>
                    <v-row dense>
                        <v-col cols="4" v-for="music in selfCreatedMusicList">
                            <v-card style="text-align: center;" color="transparent">
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
                            <v-card style="text-align: center;" color="transparent">
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
    </v-app>
</template>

<script>
export default {
    //导出组件
    data: () => ({
        form: false,
        userAccount: null,
        password: null,
        loading: false,

        keyWord: '欢快',
        singleMusicNum: 166,
        musicListNum: 80,
        musicRoomNum: 33,

        headers: [
            { text: '音乐名称', value: 'musicName' },
            { text: '歌手', value: 'singer' },
            { text: '专辑', value: 'album' },
            { text: '时长', value: 'duration' },
        ],
        musics: [
            { musicName: '快乐崇拜', singer: '潘玮柏/张韶涵', album: 'aa', duration: 24 },
            { musicName: '最冷一天', singer: '张国荣', album: 'bb', duration: 37 },
            { musicName: '負けないで', singer: 'ZARD', album: 'cc', duration: 24 },
            { musicName: '愛の雫', singer: '忘了', album: 'dd', duration: 67 },
            { musicName: '北酒場', singer: '中森明菜', album: 'ff', duration: 49 },
        ],

        profileIcons: [
            { icon: 'mdi-download', text: '本地/下载' },
            { icon: 'mdi-account', text: '关注' },
            { icon: 'mdi-heart', text: '我喜欢' },
            { icon: 'mdi-radio', text: '最近收听' },
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

    }),
    methods: {

    },
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

</style>