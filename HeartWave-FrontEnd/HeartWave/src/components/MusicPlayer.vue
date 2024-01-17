<template>
  <v-footer app>
    <v-container fluid class="green-background">
      <!-- 音乐播放器内容 -->
      <v-row justify="center" align="center">
        <v-col>
          <v-row align="center">
            <v-col>
              <!-- 音乐控制按钮 -->
              <v-btn icon @click="togglePlay">
                <v-icon>{{ isPlaying ? 'mdi-pause' : 'mdi-play' }}</v-icon>
              </v-btn>
            </v-col>

            <v-col>
              <!-- 音乐信息 -->
              <div>
                <div>{{ currentSong.title }}</div>
                <div>{{ currentSong.artist }}</div>
              </div>
            </v-col>

            <v-col>
              <!-- 音乐控制条 -->
              <v-slider v-model="musicProgress" max="100" thumb-label @click="changeMusicProgress"></v-slider>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
      <!-- 音频元素 -->
      <audio ref="audioPlayer" :src="currentSong.url" @timeupdate="updateMusicProgress"></audio>
    </v-container>
  </v-footer>
</template>

<script>
export default {
  data() {
    return {
      isPlaying: false,
      currentSong: {
        title: 'Song Title',
        artist: 'Artist Name',
        url: 'src/components/HOPE-T,接个吻，开一枪 - 锦里.mp3', // 替换为你的音频文件路径
      },
      musicProgress: 0,
    };
  },
  computed: {
    audioPlayer() {
      return this.$refs.audioPlayer;
    },
  },
  methods: {
    togglePlay() {
      const audioPlayer = this.$refs.audioPlayer;
      if (this.isPlaying) {
        audioPlayer.pause();
      } else {
        audioPlayer.play();
      }
      this.isPlaying = !this.isPlaying;
      
    },
    updateMusicProgress() {
      const audioPlayer = this.$refs.audioPlayer;
      this.musicProgress = (audioPlayer.currentTime / audioPlayer.duration) * 100;
      console.log(this.musicProgress)
    },
    changeMusicProgress() {
      const audioPlayer = this.$refs.audioPlayer;
      const newTime = (this.musicProgress / 100) * audioPlayer.duration;
      audioPlayer.currentTime = newTime;
    },
  },
};
</script>

<style scoped>
/* 根据需求添加样式 */
/* 添加淡绿色背景和阴影 */
.green-background {
  background-color: #DCE8E3; /* 你想要的淡绿色背景颜色 */
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.2); /* 阴影效果 */
}
</style>
