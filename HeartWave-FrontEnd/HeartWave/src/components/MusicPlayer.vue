<template>
    <div>
      <h2>音乐播放器</h2>
      <button class="music-button" @click="toggleAudio">{{ isPlaying ? '暂停' : '播放' }}</button>
      <audio ref="audio" :src="audioSrc"></audio>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        isPlaying: false,
        audioSrc: '/public/abc.wav', // 替换为实际的音乐文件路径
      };
    },
    methods: {
      toggleAudio() {
        const audio = this.$refs.audio;
        this.isPlaying ? audio.pause() : audio.play();
        this.isPlaying = !this.isPlaying;
      },
    },
  };
  </script>
  
  <style scoped>
  /* 样式可以根据需要进行定制 */
  .music-button {
    background: linear-gradient(45deg, #ff8a00, #e52e71);
    border: none;
    color: white;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
    border-radius: 8px;
    transition: background 0.3s ease;
  }

  .music-button:hover {
    background: linear-gradient(45deg, #e52e71, #ff8a00);
  }
  </style>
  