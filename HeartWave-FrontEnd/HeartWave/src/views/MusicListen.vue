<template>
    <v-app>
        <v-content>
            <v-container fluid>
                <v-row>
                    <v-col cols="8">
                        <div style="display: flex; align-items: center;">
                            <v-toolbar-title>情绪FM></v-toolbar-title>
                            <v-text-field v-model="search" prepend-icon="mdi-magnify" label="搜索"
                                placeholder="请输入心情/歌曲名称进行搜索" outlined rounded></v-text-field>
                        </div>

                        <v-row>
                            <v-col cols="6">
                                <v-card class="custom-card" rounded hover>
                                    <div class="left-side" rounded style="position: relative;">
                                        <v-row>
                                            <v-col cols="5">
                                                <v-img src="@/assets/imgs/ruler.svg" contain></v-img>
                                                <div class="tick">
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
                                                    style="margin: 10%;" cover></v-img>
                                                <div
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
                                <v-card style="background-color: rgba(107, 196, 174, 0.3098); height: 150px;" hover>
                                    <div class="mt-5 ml-5">
                                        <v-row>
                                            <v-col v-for="(item, index) in items.slice(0, 4)" :key="index" cols="3">
                                                <v-icon>{{ item.icon }}</v-icon>
                                                <div>{{ item.text }}</div>
                                            </v-col>
                                        </v-row>
                                        <v-row>
                                            <v-col v-for="(item, index) in items.slice(4, 8)" :key="index" cols="3">
                                                <v-icon>{{ item.icon }}</v-icon>
                                                <div>{{ item.text }}</div>
                                            </v-col>
                                        </v-row>
                                    </div>
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
                                    <v-row>
                                        <v-col cols="2" v-for="item in musics">
                                            <div style="position: relative;">
                                                <v-img :aspect-ratio="1" class="bg-white" :src="item.src"
                                                    style="margin: 10%; max-height: 140px; max-width: 140px;"
                                                    cover></v-img>
                                                <div
                                                    style="position: absolute; top: 80%; left: 70%; transform: translate(-50%, -50%);">
                                                    <v-icon color="white" x-large>
                                                        mdi-play-circle
                                                    </v-icon>
                                                </div>
                                            </div>
                                            <div class="text-center">
                                                {{ item.text }}
                                            </div>
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
                                            <v-card class="d-flex align-center" color="transparent">
                                                <!-- Left side (image) -->
                                                <v-img :src=music.src cover style="max-width: 100px; max-height: 100px;"></v-img>
                                                <!-- Center content (title and description) -->
                                                <v-card-text>
                                                    <div>
                                                        <h3>{{ music.title }}</h3>
                                                        <p>{{ music.text }}</p>
                                                    </div>
                                                </v-card-text>

                                                <!-- Right side (play icon) -->
                                                <v-card-actions>
                                                    <v-icon color="white" size="50">
                                                        mdi-play-circle
                                                    </v-icon>
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
                    <v-col cols="4">
                        bbbb
                    </v-col>
                </v-row>
            </v-container>
        </v-content>
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
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: 'aaa' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: 'aaa' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: 'aaa' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', text: 'aaa' },
        ],
        singleMusics: [
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', title: 'Tieaa', text: 'aaa' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', title: 'Tieaa', text: 'aaa' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', title: 'Tieaa', text: 'aaa' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', title: 'Tieaa', text: 'aaa' },
            { src: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', title: 'Tieaa', text: 'aaa' },
        ]
    }),
    methods: {

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
    top: 20%;
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
</style>