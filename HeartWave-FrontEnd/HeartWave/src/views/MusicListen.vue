<template>
    <v-app>
        <v-container fluid>
            <v-row>
                <v-col cols="8">
                    <div style="display: flex; align-items: center;">
                        <v-toolbar-title>情绪FM></v-toolbar-title>
                        <v-text-field v-model="search" label="搜索" placeholder="请输入心情/歌曲名称进行搜索" outlined
                            rounded></v-text-field>
                        <v-icon class="ml-3 mb-5" size="x-large" style="cursor: pointer;"
                            @click="searchEvent">mdi-magnify</v-icon>
                    </div>

                    <v-row>
                        <v-col cols="6">
                            <v-card class="custom-card" rounded hover>
                                <div class="left-side" rounded style="position: relative;">
                                    <v-row>
                                        <!-- 需要求改为二维 向后端传四个 0-1 的值 -->
                                        <v-col cols="5">
                                            <v-img src="@/assets/imgs/ruler.svg" contain></v-img>
                                            <div class="tick" :style="{ top: tickerTop }">
                                                <div class="tick-line"></div>
                                                <div class="tick-circle"></div>
                                            </div>
                                        </v-col>
                                        <v-col cols="7" class="four-line-container">
                                            <div class="text-block">快乐</div>
                                            <div class="text-block">平静</div>
                                            <div class="text-block">忧愁</div>
                                            <div class="text-block">悲伤</div>
                                        </v-col>
                                    </v-row>
                                </div>
                                <div class="right-side">
                                    <v-row>
                                        <v-col cols="6">
                                            <!-- 右侧内容 -->
                                            <v-card-title primary-title>
                                                <span class="headline">Card Title</span>
                                            </v-card-title>
                                            <v-card-text>
                                                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                                            </v-card-text>
                                        </v-col>

                                        <v-col cols="6" style="position: relative;">
                                            <v-img :aspect-ratio="1" class="bg-white"
                                                src="https://cdn.vuetifyjs.com/images/parallax/material.jpg"
                                                style="margin: 10%;" @click="this.$emit('playMusicEvent')" cover>
                                            </v-img>
                                            <div @click="this.$emit('playMusicEvent')"
                                                style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);">
                                                <v-icon color="white" x-large>
                                                    mdi-play-circle
                                                </v-icon>
                                            </div>
                                        </v-col>
                                    </v-row>
                                </div>
                            </v-card>
                        </v-col>
                        <v-col cols="6">
                            <v-card style="background-color: rgba(107, 196, 174, 0.3098); height: 150px;">
                                <v-row dense>
                                    <v-col v-for="(item, index) in items" :key="index" cols="3">
                                        <v-card hover color="transparent">
                                            <v-card-text>
                                                <div
                                                    style="display: flex; flex-direction: column; align-items: center; text-align: center;">
                                                    <v-icon>{{ item.icon }}</v-icon>
                                                    <span>{{ item.text }}</span>
                                                </div>
                                            </v-card-text>
                                        </v-card>
                                    </v-col>
                                </v-row>
                            </v-card>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="12">

                            <v-card hover :style="{
                            background: 'linear-gradient(103deg, #D2F1FC 0%, #D8F0F1 9%, #D5F0EA 24%, rgba(213, 240, 234, 0.9901) 75%, rgba(201, 222, 247, 0.7905) 97%, rgba(221, 202, 237, 0.3098) 102%)',
                            height: '250px'
                        }">
                                <v-card-title>
                                    根据您最近的情绪我们给您推荐以下歌单>
                                </v-card-title>
                                <v-row style="padding: 15px;">
                                    <v-col cols="2" v-for="item in musics">
                                        <v-card hover class="mt-5"
                                            @click="this.$emit('detialPlayListEvent', 'recommend')">
                                            <v-img :src="item.src" class="align-end"
                                                gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" height="140px"
                                                cover>
                                                <!-- 名字和播放按钮 -->
                                                <div class="d-flex">
                                                    <v-card-title class="text-white max-text-length" v-text="item.text"
                                                        style="font-size: medium;"></v-card-title>
                                                    <v-spacer></v-spacer>
                                                    <v-btn icon="mdi-play-circle-outline" variant="text"
                                                        style="font-size: 20px; color: white;"></v-btn>
                                                </div>
                                            </v-img>
                                        </v-card>
                                    </v-col>
                                </v-row>
                            </v-card>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="12">
                            <v-card hover :style="{
                            background: 'linear-gradient(103deg, #D2F1FC 0%, #D8F0F1 9%, #D5F0EA 24%, rgba(213, 240, 234, 0.9901) 75%, rgba(201, 222, 247, 0.7905) 97%, rgba(221, 202, 237, 0.3098) 102%)',
                        }">
                                <v-card-title>
                                    根据您最近的情绪我们给您推荐以下单曲>
                                </v-card-title>
                                <v-row dense>
                                    <v-col cols="6" v-for="music in singleMusics">
                                        <v-card class="d-flex align-center" color="transparent"
                                            @click="this.$emit('playMusicEvent')">
                                            <!-- Left side (image) -->
                                            <v-img :src=music.src cover
                                                style="max-width: 100px; max-height: 100px;"></v-img>
                                            <!-- Center content (title and description) -->
                                            <v-card-text>
                                                <div>
                                                    <h3>{{ music.title }}</h3>
                                                    <p>{{ music.text }}</p>
                                                </div>
                                            </v-card-text>

                                            <!-- Right side (play icon) -->
                                            <v-card-actions>
                                                <v-btn icon="mdi-play-circle-outline" variant="text"
                                                    style="font-size: 20px; color: #4D6A65;"></v-btn>
                                            </v-card-actions>
                                        </v-card>
                                    </v-col>
                                    <v-col cols="6">

                                    </v-col>
                                </v-row>
                            </v-card>
                        </v-col>
                    </v-row>
                </v-col>
                <v-col cols="4" style="background-color: #D5F0EA;">
                    <v-card class="profile-card" :style="{ backgroundImage: 'url(' + profileImgSrc + ')' }">
                        <v-card-text>
                            <v-avatar size="100" style="cursor: pointer;"
                                @click="this.$emit('BrowsingPersonalHomepage', userId)">
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
                                <v-img :src="music.src" class="align-end"
                                    gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" height="140px" cover>
                                    <!-- 名字和播放按钮 -->
                                    <div class="d-flex">
                                        <v-card-title class="text-white max-text-length" v-text="music.text"
                                            style="font-size: medium;"></v-card-title>
                                        <v-spacer></v-spacer>
                                        <v-btn icon="mdi-play-circle-outline" variant="text"
                                            style="font-size: 20px; color: white;"></v-btn>
                                    </div>
                                </v-img>
                            </v-card>
                        </v-col>
                    </v-row>
                    <div style="font-size: 20px;" class="mt-3">我管理的歌单></div>
                    <v-row dense>
                        <v-col cols="4" v-for="music in selfCreatedMusicList">
                            <v-card style="text-align: center;" color="transparent"
                                @click="this.$emit('detialPlayListEvent', 'admin')" hover>
                                <v-img :src="music.src" class="align-end"
                                    gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" height="140px" cover>
                                    <!-- 名字和播放按钮 -->
                                    <div class="d-flex">
                                        <v-card-title class="text-white max-text-length" v-text="music.text"
                                            style="font-size: medium;"></v-card-title>
                                        <v-spacer></v-spacer>
                                        <v-btn icon="mdi-play-circle-outline" variant="text"
                                            style="font-size: 20px; color: white;"></v-btn>
                                    </div>
                                </v-img>
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
        tickerTop: '20%',
        items: [
            { icon: 'mdi-star', text: 'Star' },
            { icon: 'mdi-heart', text: 'Heart' },
            { icon: 'mdi-share', text: 'Share' },
            { icon: 'mdi-download', text: 'Download' },
            { icon: 'mdi-camera', text: 'Camera' },
            { icon: 'mdi-alarm', text: 'Alarm' },
            { icon: 'mdi-wallet', text: 'Wallet' },
            { icon: 'mdi-car', text: 'Car' }
        ],
        musics: [
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: 'THE KEY' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: 'アイドル' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: '张国荣金曲' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: '香港の夜' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: '宇宙戦艦' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: '星空のディスタンス' },
        ],
        singleMusics: [
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', title: 'Tieaa', text: 'aaa' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', title: 'Tieaa', text: 'aaa' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', title: 'Tieaa', text: 'aaa' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', title: 'Tieaa', text: 'aaa' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', title: 'Tieaa', text: 'aaa' },
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
        userId: 1,
        ipLocation: '上海',
        friendNum: 24,
        emotionValue: 78,
        visitorNum: 234,

        favorDialog: false,
        
        friends: [
            { avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Alice', emotionValue: '😊' },
            { avatar: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'Bob', emotionValue: '😄' },
            // 添加更多好友数据
        ],
    }),
    methods: {
        searchEvent() {
            console.log('searchEvent');
            this.$emit('searchEvent');
        },
    },
}
</script>

<style scoped>
.full-height {
    min-height: 100vh;
    /* 设置最小高度为视口高度的100% */
}

.custom-card {
    display: flex;
    justify-content: space-between;
    height: 150px;
}

.left-side {
    background: #4D6A65;
    flex: 1;
}

.right-side {
    background: linear-gradient(180deg, #6B7876 0%, rgba(71, 102, 97, 0.74) 52%, #4D6A65 100%);
    color: white;
    flex: 2;
}

.four-line-container {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    color: white;
    height: 150px;
    margin-top: 9px;
}

.tick {
    position: absolute;
    /* top: 20%; */
    /* 调整垂直居中位置 */
    left: 10%;
    /* 调整水平居中位置 */
    transform: translate(-50%, -50%);
    /* 使div完全居中 */
    width: 25%;
}

.tick-line {
    position: absolute;
    transform: translate(-50%, -50%);
    /* 使div完全居中 */
    width: 200%;
    border-top: 4px solid #45CE9A;
    border-radius: 20%
}

.tick-circle {
    position: absolute;
    left: 100%;
    transform: translate(-50%, -50%);
    width: 12px;
    height: 12px;
    border-radius: 50%;
    /* 将div设置为圆形 */
    background-color: #45CE9A;
}

.profile-card {
    background-size: cover;
    box-shadow: 0 5px 6px rgba(0, 0, 0, 0.5);
    /* 下面两个圆角 */
    border-radius: 0 0 50px 50px;
    min-height: 300px;
    text-align: center;
    color: white;
}

.max-text-length {
    max-width: 100px;
    /* 设置最大宽度，根据需要进行调整 */
    white-space: nowrap;
    /* 防止文本换行 */
    overflow: hidden;
    /* 隐藏超出最大宽度的文本 */
    text-overflow: ellipsis;
    /* 使用省略号表示被隐藏的文本 */
}
</style>