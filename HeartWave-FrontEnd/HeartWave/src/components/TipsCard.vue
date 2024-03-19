<template>
    <v-card hover class="tips-card">
        <v-card-title class="tips-title">天气心情小贴士</v-card-title>
        <v-card-text>
            <div class="tips-text">清明节作为一个传统节日，有着悼念逝者、
                祭扫墓地的习俗。而如果碰巧是在雨天，更能让人感受到一份悲凉的气息。
                因此，在这样的日子里，适合听一些慢节奏、抒情的歌曲，以表达内心的感受。</div>
        </v-card-text>
        <v-card-subtitle style="">根据今天的天气或许您适合听以下歌曲></v-card-subtitle>
        <v-container fluid>
            <v-row dense>
                <v-col v-for="song in music" :key="song.title">
                    <v-card>
                        <v-img :src="song.cover" class="align-end" gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                            height="141px" cover>
                            <div class="d-flex">
                                <v-card-title class="text-white max-text-length" v-text="song.title"
                                    style="font-size: medium;"></v-card-title>

                                <v-spacer></v-spacer>
                                <v-btn class="ms-2" icon="mdi-play-circle-outline" variant="text"
                                    style="font-size: 20px;color: white;"></v-btn>
                            </div>

                        </v-img>


                    </v-card>
                </v-col>
            </v-row>
        </v-container>
    </v-card>
</template>

<script>
export default {
    props: {
        music: {
            type: Array,
            required: true,
        },
    },
};
</script>

<style scoped>
.tips-card {
    transform: rotate(0deg);
    border-radius: 15px;
    opacity: 1;

    background: linear-gradient(91deg, rgba(153, 231, 214, 0.62) -9%, rgba(159, 223, 203, 0.46) 18%, rgba(160, 223, 203, 0.51) 35%, rgba(110, 177, 196, 0.27) 98%);

    box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.302);
}

.tips-title {
    font-family: STYuanti;
    font-size: 20px;
    font-weight: normal;
    line-height: 43px;
    letter-spacing: 0px;

    color: #646464;
}

.tips-text {

    font-size: 16px;
    font-weight: normal;
    line-height: 25px;
    letter-spacing: 0px;

    color: #3D3D3D;
}

.max-text-length {
    max-width: 80px;
    /* 设置最大宽度，根据需要进行调整 */
    white-space: nowrap;
    /* 防止文本换行 */
    overflow: hidden;
    /* 隐藏超出最大宽度的文本 */
    text-overflow: ellipsis;
    /* 使用省略号表示被隐藏的文本 */
}
</style>