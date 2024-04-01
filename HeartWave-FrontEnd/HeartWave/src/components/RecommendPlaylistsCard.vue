<template>
  <v-card hover class="pl-card">
    <v-card-title class="playlist-text">
      根据您最近的情绪我们给您推荐以下歌单>
    </v-card-title>
    <v-row style="margin: 0% 2% 0% 2%;">
      <v-col v-for="song in playlists" :key="song.title">
        <v-card>
          <v-img :src="song.cover" class="align-end" gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" height="157px"
            cover>
            <!-- 名字和播放按钮 -->
            <div class="d-flex">
              <v-card-title class="text-white max-text-length" v-text="song.title"
                style="font-size: medium;"></v-card-title>
              <v-btn class="ms-2" icon="mdi-play-circle-outline" variant="text"
                style="font-size: 20px;color: white;"></v-btn>
            </div>
          </v-img>
        </v-card>
      </v-col>
    </v-row>
  </v-card>
</template>
<script>

export default {
  props: {
    playlists: {
      type: Array,
      required: true,
    },
  },
};
</script>

<style scoped>
.pl-card {
  background: linear-gradient(103deg, #D2F1FC 0%, #D8F0F1 9%, #D5F0EA 24%, rgba(213, 240, 234, 0.9901) 75%, rgba(201, 222, 247, 0.7905) 97%, rgba(221, 202, 237, 0.3098) 102%);

  box-sizing: border-box;
  /* 纯白 */
  border: 1px solid #FFFFFF;

  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.302);
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

.playlist-text {
  font-family: STYuanti;
  font-size: 19px;
  font-weight: normal;
  line-height: 43px;
  letter-spacing: 0px;

  color: #646464;
}
</style>