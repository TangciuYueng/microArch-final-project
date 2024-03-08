<template>
    <!-- 用于写主页面的内容 -->
    <v-main class="full-height">
        <v-container fluid>
            <v-row style="min-height:88vh;">
                <!-- 第一列，宽度为第二列的3倍，背景颜色为白色 -->
                <v-col cols="8" class="white-background scrollable-column">
                    <v-row>
                        <v-responsive max-width="350" max-height="50">
                            <v-spacer></v-spacer>
                            <v-text-field clearable label="请输入搜索关键词"
                                prepend-icon="mdi-magnify"></v-text-field></v-responsive>
                    </v-row>
                    <!-- 第一行，显示今日天气、天气小贴士、以及需要关心的朋友 -->
                    <v-row>
                        <v-col>
                            <!-- 天气卡片 -->
                            <v-row><weather-card></weather-card></v-row>
                            <!-- 需要关心的人 -->
                            <v-row></v-row>
                        </v-col>

                        <!-- 天气小贴士 -->
                        <v-col>

                        </v-col>
                    </v-row>

                    <!-- 推荐的歌单 -->
                    <v-row>

                    </v-row>

                    <!-- 推荐的音乐室 -->
                    <v-row>
                    </v-row>

                </v-col>
                <!-- 第二列，背景颜色为绿色 -->
                <v-col cols="4" class="green-background scrollable-column">
                    <!-- 这里放置第二列的内容 -->
                </v-col>
            </v-row>
        </v-container>
    </v-main>
</template>
  
<script>
import WeatherCard from "@/components/WeatherCard.vue"
export default {
    components: {
        WeatherCard,
    },
    //导出组件
    data: () => ({
        form: false,
        userAccount: null,
        password: null,
        loading: false,
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

.white-background {
    background: linear-gradient(to bottom, rgba(232, 249, 245, 1), white);
}

.green-background {
    background-color: #D5F0EA;
}

.scrollable-column {
    overflow-y: auto;
    /* 在垂直方向上溢出时显示垂直滚动条 */
    max-height: 88vh;
    /* 设置最大高度为88vh，超出部分将显示滚动条 */
}
</style>