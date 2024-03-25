<template>
    <v-snackbar v-model="snackbar" color="blue-grey" rounded="pill" style="opacity: 70%;" :timeout="1500">
        {{ snackbarText }}
    </v-snackbar>
    <v-app>
        <v-container>
            <v-row>
                <v-col cols="8" style="box-shadow: 5px 0 5px -5px rgba(0, 0, 0, 0.5),
                -5px 0 5px -5px rgba(0, 0, 0, 0.5);">
                    <v-row>
                        <v-col cols="3">
                            <v-img :src="cover" cover aspect-ratio="1/1" max-width="150" height="150"></v-img>
                        </v-col>
                        <v-col cols="9">
                            <div>
                                <v-card class="card-no-border">
                                    <v-card-title class="playlist-info">
                                        <div class="playlist-type">{{ playListType }}</div>
                                        <div class="playlist-name">{{ playListName }}</div>
                                    </v-card-title>

                                    <div class="playlist-meta">
                                        <div class="creator">{{ creator }}</div>
                                        <div class="createTime">{{ formattedCreateTime + ' 创建' }}</div>
                                    </div>
                                    <div>
                                        <div v-if="showLess" @click="toggleShowLess" class="description">{{
        limitedDescription
    }}</div>
                                        <div v-else @click="toggleShowLess" class="description show-scroll">{{
        description }}
                                        </div>
                                    </div>
                                    <div>
                                        <a v-if="showLess" @click="toggleShowLess" class="open-close">展开</a>
                                        <a v-else @click="toggleShowLess" class="open-close">收起</a>
                                    </div>
                                    <v-card-actions class="playlist-actions">
                                        <v-btn color="primary"><v-icon left>mdi-play</v-icon> 播放</v-btn>
                                        <v-btn color="red" @click="snackbar = true"><v-icon left>mdi-heart</v-icon>
                                            收藏</v-btn>
                                    </v-card-actions>
                                </v-card>
                            </div>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="12">
                            <v-data-table :items="playList" :search="search" height="500px">
                                <template v-slot:top>
                                    <v-toolbar flat color="white">
                                        <v-toolbar-title>歌曲</v-toolbar-title>
                                        <v-spacer></v-spacer>
                                        <v-text-field v-model="search" label="搜索" single-line rounded
                                            hide-details></v-text-field>
                                        <v-icon class="ml-3" style="cursor: pointer;">mdi-magnify</v-icon>
                                    </v-toolbar>
                                </template>
                                <template v-slot:[`item.cover`]="{ item }">
                                    <v-img :src="item.cover" alt="Cover" aspect-ratio="1/1" max-width="80px"
                                        height="80px"></v-img>
                                </template>
                            </v-data-table>
                        </v-col>
                    </v-row>
                </v-col>
                <v-col cols="3" style="box-shadow: 5px 0 5px -5px rgba(0, 0, 0, 0.5),
                -5px 0 5px -5px rgba(0, 0, 0, 0.5);">
                    <div style="font-size: large; font-weight: bold;">
                        收藏这个歌单的人
                    </div>
                    <hr>
                    <v-row dense class="mt-3 mb-3">
                        <v-col cols="3" v-for="user in userCollectedPlayList">
                            <v-img cover aspect-ratio="1/1" max-width="50" height="50" :src="user.avatar" style="cursor: pointer;" @click="this.$emit('BrowsingPersonalHomepage')"></v-img>
                        </v-col>

                    </v-row>

                    <div style="font-size: large; font-weight: bold;">
                        相关推荐
                    </div>
                    <hr>
                    <div v-for="playList in playListsRecommended">
                        <v-card style="text-align: center;" color="transparent" class="mt-3"
                            @click="this.$emit('detialPlayListEvent')" hover>
                            <v-img :src="playList.cover" class="align-end"
                                gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" height="110px" cover>
                                <!-- 名字和播放按钮 -->
                                <div class="d-flex">
                                    <v-card-title class="text-white max-text-length"
                                        v-text="playList.name + ' by ' + playList.creator"
                                        style="font-size: medium;"></v-card-title>
                                    <v-spacer></v-spacer>
                                    <v-btn icon="mdi-play-circle-outline" variant="text"
                                        style="font-size: 20px; color: white;"></v-btn>
                                </div>
                            </v-img>
                        </v-card>
                    </div>
                </v-col>
            </v-row>
        </v-container>
    </v-app>
</template>

<script>
export default {
    data() {
        return {
            playList: [
                {
                    id: 1,
                    cover: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg',
                    name: 'Song 1',
                    artist: 'Artist 1',
                    album: 'Album 1',
                    duration: '3:45'
                },
                {
                    id: 2,
                    cover: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg',
                    name: 'Song 2',
                    artist: 'Artist 2',
                    album: 'Album 2',
                    duration: '4:02'
                },
                {
                    id: 3,
                    cover: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg',
                    name: 'Song 3',
                    artist: 'Artist 3',
                    album: 'Album 3',
                    duration: '5:15'
                },
                {
                    id: 4,
                    cover: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg',
                    name: 'Song 3',
                    artist: 'Artist 3',
                    album: 'Album 3',
                    duration: '5:15'
                },
                {
                    id: 5,
                    cover: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg',
                    name: 'Song 3',
                    artist: 'Artist 3',
                    album: 'Album 3',
                    duration: '5:15'
                },
                {
                    id: 6,
                    cover: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg',
                    name: 'Song 3',
                    artist: 'Artist 3',
                    album: 'Album 3',
                    duration: '5:15'
                },
                {
                    id: 7,
                    cover: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg',
                    name: 'Song 3',
                    artist: 'Artist 3',
                    album: 'Album 3',
                    duration: '5:15'
                },
                {
                    id: 8,
                    cover: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg',
                    name: 'Song 3',
                    artist: 'Artist 3',
                    album: 'Album 3',
                    duration: '5:15'
                },
                {
                    id: 9,
                    cover: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg',
                    name: 'Song 3',
                    artist: 'Artist 3',
                    album: 'Album 3',
                    duration: '5:15'
                },
            ],
            playListName: '我好中意她',
            search: '',
            creator: 'S+M',
            createTime: new Date(),
            description: "I wholeheartedly believe that online classes **offer significant advantages over** traditional face-to-face classes, **providing not only** convenience and flexibility **but also** enhancing **pedagogical efficiency**.In today's fast-paced modern world, **mastering the skill of time and energy management is fundamental for** students. Online classes **play a vital role in** facilitating this **by eliminating the need for commuting and allowing students to choose specific content they wish to focus on**, rather than attending every class.Moreover, **through the utilization of advanced devices and software such as** microphones and video sharing platform, instructors **can effectively convey knowledge and perspectives**. This enhanced efficiency is a clear advantage over traditional in-person classes, **where limitations** such as large auditorium sizes or classroom noise can **hinder student access** to course material.In conclusion, embracing technology and modern teaching method of online classes can greatly benefit both students and educators in the ever-evolving landscape of education.",
            showLess: true,
            maxChars: 100,
            snackbar: false,
            snackbarText: "收藏成功",
            snackbarColor: "success",
            userCollectedPlayList: [
                {id: 1, avatar: "https://cdn.vuetifyjs.com/images/parallax/material.jpg", username: 's+m' },
                {id: 2, avatar: "https://cdn.vuetifyjs.com/images/parallax/material.jpg", username: 's+m' },
                {id: 3, avatar: "https://cdn.vuetifyjs.com/images/parallax/material.jpg", username: 's+m' },
                {id: 4, avatar: "https://cdn.vuetifyjs.com/images/parallax/material.jpg", username: 's+m' },
                {id: 5, avatar: "https://cdn.vuetifyjs.com/images/parallax/material.jpg", username: 's+m' },
                {id: 6, avatar: "https://cdn.vuetifyjs.com/images/parallax/material.jpg", username: 's+m' },
            ],
            playListsRecommended: [
                { cover: "https://cdn.vuetifyjs.com/images/parallax/material.jpg", name: 'lonely Me', creator: "mvv" },
                { cover: "https://cdn.vuetifyjs.com/images/parallax/material.jpg", name: 'lonely Me', creator: "mvv" },
                { cover: "https://cdn.vuetifyjs.com/images/parallax/material.jpg", name: 'lonely Me', creator: "mvv" },
                { cover: "https://cdn.vuetifyjs.com/images/parallax/material.jpg", name: 'lonely Me', creator: "mvv" },
                { cover: "https://cdn.vuetifyjs.com/images/parallax/material.jpg", name: 'lonely Me', creator: "mvv" },
            ],
        };
    },
    methods: {
        toggleShowLess() {
            this.showLess = !this.showLess;
            console.log('showLess', this.showLess)
        },
        openSnackbar() {
            this.snackbar = true;
        },
    },
    computed: {
        formattedCreateTime() {
            const options = { year: "numeric", month: "2-digit", day: "2-digit" };
            return this.createTime.toLocaleDateString("en-US", options);
        },
        limitedDescription() {
            return this.description.length > this.maxChars ? this.description.slice(0, this.maxChars) + "..." : this.description;
        }
    },
    props: {
        playListType: {
            type: String,
            default: '默认歌单',
        },
        cover: {
            type: String,
            default: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg',
        }
    },
};
</script>

<style scoped>
.card-no-border {
    border: none !important;
    box-shadow: none !important;
}

.playlist-info {
    display: flex;
    flex-direction: row;
    align-items: center;
}

.playlist-type {
    background: linear-gradient(91deg, rgba(153, 231, 214, 0.62) -9%, rgba(159, 223, 203, 0.46) 18%, rgba(160, 223, 203, 0.51) 35%, rgba(110, 177, 196, 0.27) 98%);
    padding: 5px;
    margin: 5px;
    font-size: medium;
    border-radius: 30%;
    box-shadow: 5px 0 5px -5px rgba(0, 0, 0, 0.5),
        -5px 0 5px -5px rgba(0, 0, 0, 0.5);
}

.playlist-name {
    font-size: x-large;
    margin-left: 15px;
}

.playlist-meta {
    display: flex;
    justify-content: row;
}

.creator {
    font-size: small;
    font-weight: bold;
    margin-right: 10px;
}

.createTime {
    font-size: small;
}

.playlist-actions {
    display: flex;
    justify-content: row;
}

.description {
    overflow: hidden;
}

.open-close {
    right: 0;
    color: rgb(0, 119, 255);
    text-decoration: underline;
    cursor: pointer;
}

</style>