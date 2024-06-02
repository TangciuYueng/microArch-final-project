<template>
    <!-- 用于写主页面的内容 -->
    <v-main class="full-height scrollable-container">
        <!-- 提示用户填写偏好测试的作用以及重要性 -->
        <v-row no-gutters class="d-flex justify-center">
            <v-sheet :elevation="5" class="ma-3 pa-4 d-flex flex-column justify-center align-center tips" border
                rounded>
                <div>
                    <h2 class="text-center">音乐情绪偏好设置</h2>
                    <p>通过填写这份问卷，您将帮助我们了解您在不同情绪下的音乐偏好。这样我们可以为您提供更加个性化的音乐推荐，让每一首歌都能贴合您的心情。</p>
                    <p>请根据每个场景下的描述，选择您在该情境下更偏好听的音乐类型。这将有助于我们更好地满足您的音乐需求，提升您的聆听体验。</p>
                    <p><strong>注意事项：</strong></p>
                    <ul>
                        <li>每个问题都没有对错之分，只需选择您最真实的喜好。</li>
                        <li>请确保您在安静的环境中进行问卷填写，以便准确反映您的音乐偏好。</li>
                    </ul>
                </div>
            </v-sheet>
        </v-row>
        <v-row no-gutters class="half-height">
            <v-col cols="6" class="pa-0 d-flex justify-center align-center">
                <v-sheet :elevation="5" class="sheet-size d-flex flex-column justify-center align-center" border
                    rounded>
                    <div class="text-center">
                        <h3>当你感到内心欢乐的时候</h3>
                        <p>你偏好听哪种类型的歌曲？</p>
                        <v-row style="margin: 0% 2% 0% 2%;">
                            <v-col v-for="song in Playlists" :key="song.title" cols="3">
                                <v-card>
                                    <v-img :src="song.cover" class="align-end"
                                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" width="100%" cover>
                                        <!-- 名字和播放按钮 -->
                                        <div class="d-flex">
                                            <v-card-title class="text-white max-text-length" style="font-size: medium;"
                                                v-text="song.title" />
                                            <v-btn class="ms-2" icon="mdi-play-circle-outline" variant="text"
                                                style="font-size: 20px;color: white;" />
                                        </div>
                                    </v-img>
                                </v-card>
                            </v-col>
                        </v-row>

                        <v-radio-group>
                            <v-radio label="轻快的流行歌曲，进一步提振心情，享受快乐的氛围，比如上述1号歌曲" value="happy"></v-radio>
                            <v-radio label="抒情的慢歌，以体验情感的多样性，比如上述2号歌曲" value="sad"></v-radio>
                            <v-radio label="平静的轻音乐，享受一种内心的宁静和满足感，比如上述3号歌曲" value="peace"></v-radio>
                            <v-radio label="动感的摇滚乐或电子音乐，更加充满活力和激情，比如上述4号歌曲" value="aggressive"></v-radio>
                        </v-radio-group>
                    </div>
                </v-sheet>
            </v-col>
            <v-col cols="6" class="pa-0 d-flex justify-center align-center">
                <v-sheet :elevation="5" class="sheet-size d-flex flex-column justify-center align-center" border
                    rounded>
                    <div class="text-center">
                        <h3>在经历失落的夜晚，你内心伤感</h3>
                        <p>你偏好听哪种类型的歌曲？</p>
                        <v-row style="margin: 0% 2% 0% 2%;">
                            <v-col v-for="song in Playlists" :key="song.title" cols="3">
                                <v-card>
                                    <v-img :src="song.cover" class="align-end"
                                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" width="100%" cover>
                                        <!-- 名字和播放按钮 -->
                                        <div class="d-flex">
                                            <v-card-title class="text-white max-text-length" style="font-size: medium;"
                                                v-text="song.title" />
                                            <v-btn class="ms-2" icon="mdi-play-circle-outline" variant="text"
                                                style="font-size: 20px;color: white;" />
                                        </div>
                                    </v-img>
                                </v-card>
                            </v-col>
                        </v-row>
                        <v-radio-group>
                            <v-radio label="轻快的流行歌曲，从悲观的情绪中寻找希望和乐观，比如上述1号歌曲" value="happy"></v-radio>
                            <v-radio label="抒情的慢歌，让人在悲伤中感到被理解和安慰，比如上述2号歌曲" value="sad"></v-radio>
                            <v-radio label="平静的轻音乐，减少内心的不安和焦虑，比如上述3号歌曲" value="peace"></v-radio>
                            <v-radio label="动感的摇滚乐或电子音乐，释放内心的压抑和不满，比如上述4号歌曲" value="aggressive"></v-radio>
                        </v-radio-group>
                    </div>
                </v-sheet>
            </v-col>
        </v-row>
        <v-row no-gutters class="half-height">
            <v-col cols="6" class="pa-0 d-flex justify-center align-center">
                <v-sheet :elevation="5" class="sheet-size d-flex flex-column justify-center align-center" border
                    rounded>
                    <div class="text-center">
                        <h3>当一切顺遂内心平静时</h3>
                        <p>你偏好听哪种类型的歌曲？</p>
                        <v-row style="margin: 0% 2% 0% 2%;">
                            <v-col v-for="song in Playlists" :key="song.title" cols="3">
                                <v-card>
                                    <v-img :src="song.cover" class="align-end"
                                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" width="100%" cover>
                                        <!-- 名字和播放按钮 -->
                                        <div class="d-flex">
                                            <v-card-title class="text-white max-text-length" style="font-size: medium;"
                                                v-text="song.title" />
                                            <v-btn class="ms-2" icon="mdi-play-circle-outline" variant="text"
                                                style="font-size: 20px;color: white;" />
                                        </div>
                                    </v-img>
                                </v-card>
                            </v-col>
                        </v-row>
                        <v-radio-group>
                            <v-radio label="轻快的流行歌曲，增添生活的愉悦感，比如上述1号歌曲" value="happy"></v-radio>
                            <v-radio label="抒情的慢歌，唤起深层次的情感体验，比如上述2号歌曲" value="sad"></v-radio>
                            <v-radio label="平静的轻音乐，维持或增强现有的宁静状态，比如上述3号歌曲" value="peace"></v-radio>
                            <v-radio label="动感的摇滚乐或电子音乐，激发热情和动力，比如上述4号歌曲" value="aggressive"></v-radio>
                        </v-radio-group>
                    </div>
                </v-sheet>
            </v-col>
            <v-col cols="6" class="pa-0 d-flex justify-center align-center">
                <v-sheet :elevation="5" class="sheet-size d-flex flex-column justify-center align-center" border
                    rounded>
                    <div class="text-center">
                        <h3>当内心情绪激烈、波澜起伏时</h3>
                        <p>你偏好听哪种类型的歌曲？</p>
                        <v-row style="margin: 0% 2% 0% 2%;">
                            <v-col v-for="song in Playlists" :key="song.title" cols="3">
                                <v-card>
                                    <v-img :src="song.cover" class="align-end"
                                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" width="100%" cover>
                                        <!-- 名字和播放按钮 -->
                                        <div class="d-flex">
                                            <v-card-title class="text-white max-text-length" style="font-size: medium;"
                                                v-text="song.title" />
                                            <v-btn class="ms-2" icon="mdi-play-circle-outline" variant="text"
                                                style="font-size: 20px;color: white;" />
                                        </div>
                                    </v-img>
                                </v-card>
                            </v-col>
                        </v-row>
                        <v-radio-group>
                            <v-radio label="轻快的流行歌曲，以积极的节奏带动心情，比如上述1号歌曲" value="happy"></v-radio>
                            <v-radio label="抒情的慢歌，深入探索和表达情感，比如上述2号歌曲" value="sad"></v-radio>
                            <v-radio label="平静的轻音乐，帮助安抚情绪，寻找内心的宁静，比如上述3号歌曲" value="peace"></v-radio>
                            <v-radio label="动感的摇滚乐或电子音乐，释放紧张情绪，感受力量与活力，比如上述4号歌曲" value="aggressive"></v-radio>
                        </v-radio-group>
                    </div>
                </v-sheet>
            </v-col>
        </v-row>
    </v-main>
</template>

<script>
import RecommendPlaylistsCard from "@/components/RecommendPlaylistsCard.vue";
export default {
    name: 'MyComponent',
    components: {
        RecommendPlaylistsCard,
    },
    data() {
        return {
            selectedSongs: {
                celebration: '',
                sadNight: '',
                meditation: '',
                workout: ''
            },
            Playlists: [
                {
                    id: 1,
                    cover: "src/assets/album/暖暖.jpg",
                    title: "暖暖",
                    url:"src/components/HOPE-T,接个吻，开一枪 - 锦里.mp3"
                },
                {
                    id: 2,
                    cover: "src/assets/album/damedane.jpg",
                    title: "damedane",
                    url:"src/components/HOPE-T,接个吻，开一枪 - 锦里.mp3"
                },
                {
                    id: 3,
                    cover: "src/assets/album/Reborn.jpg",
                    title: "Reborn",
                    url:"src/components/HOPE-T,接个吻，开一枪 - 锦里.mp3"
                },
                {
                    id: 4,
                    cover: "src/assets/album/多索雷斯假日.jpg",
                    title: "多索雷斯假日",
                    url:"src/components/HOPE-T,接个吻，开一枪 - 锦里.mp3"
                },
            ],


        }
    }
}
</script>

<style scoped>
.full-height {
    min-height: 100vh;
    /* 设置最小高度为视口高度的100% */
}

.scrollable-container {
    overflow-y: auto;
    /* 当内容溢出时，显示垂直滚动条 */
}

.half-height {
    height: 50%;
    /* 设置高度为父容器的一半 */
}

.sheet-size {
    height: 87%;
    width: 85%;
    /* 设置宽高为父容器的85% */
    background: linear-gradient(to bottom, rgba(232, 249, 245, 1), white);
    padding: 20px;
}

.tips {
    width: 92.5%;
    /* 设置宽高为父容器的85% */
    background: linear-gradient(to bottom, rgba(232, 249, 245, 1), white);
}

.album-cover {
    cursor: pointer;
    /* 设置鼠标悬停时显示为指针 */
    max-width: 100%;
    border-radius: 8px;
    /* 设置封面图片为圆角 */
}
.max-text-length {
  max-width: 90px;
  /* 设置最大宽度，根据需要进行调整 */
  white-space: nowrap;
  /* 防止文本换行 */
  overflow: hidden;
  /* 隐藏超出最大宽度的文本 */
  text-overflow: ellipsis;
  /* 使用省略号表示被隐藏的文本 */
}

</style>