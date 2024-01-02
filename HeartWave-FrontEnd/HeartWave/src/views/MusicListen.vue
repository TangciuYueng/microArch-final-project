<!-- eslint-disable vue/multi-word-component-names -->
<template>
    <div>
        <div>
            <DefaultBar />
            音乐收听
        </div>
        <!-- 把所有音乐展示 -->
        <v-card flat>
            <v-card-title class="d-flex align-center pe-2">
            <v-icon icon="mdi-music"></v-icon> &nbsp;
            MUSIC
            <v-spacer></v-spacer>

            <!-- <v-text-field
                v-model="search"
                prepend-inner-icon="mdi-magnify"
                density="compact"
                label="搜索音乐"
                single-line
                flat
                hide-details
                variant="solo-filled"
            ></v-text-field> -->
            </v-card-title>
            <v-divider></v-divider>
            <v-btn @click="getAllMusic">获取所有音乐</v-btn>
        </v-card>
        <div>
            <h2>这是所有音乐:</h2>
            {{ allMusic }}
        </div>
        <v-text-field v-model="music_id" label="输入音乐ID播放音乐" outlined solo prepend-icon="mdi-music"></v-text-field>
        {{ music_id }}
        <v-btn @click="playMusicById">播放音乐</v-btn>
        <v-app>

            <MusicPlayer />
        </v-app>
        
        <!-- <v-btn @click="downloadAndPlayAudio">Download and Play Audio</v-btn> -->
    </div>
    
</template>
<script>
// import HelloWorld from '@/components/HelloWorld.vue'
import DefaultBar from '@/layouts/default/AppBar.vue'
import MusicPlayer from '@/components/MusicPlayer.vue';
import axios from 'axios'
export default {
    
    data: () => ({
        search:"",
        allMusic: '',
        music_id: null,

    }),
    components:{
        DefaultBar,
        MusicPlayer,
    },
    mounted() {

    },
    methods: {
        playMusicById(){
            try {
				axios.get(`http://localhost:8888/api/music_listen/music/play/${this.music_id}`)
				.then((response) => {
					// 请求成功时的处理
					console.log(response)
				})
				.catch((error) => {
				// 请求失败时的处理
				console.error('Error:', error);
				});
			} catch (error) {
				this.error = error;
			}
        },
        getAllMusic(){
            try {
				axios.get(`http://localhost:8888/api/music_listen/music/by_page/1`)
				.then((response) => {
					// 请求成功时的处理
                    this.allMusic = response.data.musics
					console.log(response)
				})
				.catch((error) => {
				// 请求失败时的处理
				console.error('Error:', error);
				});
			} catch (error) {
				this.error = error;
			}
        },
        playLocalMusic(){

        },
        downloadAndPlayAudio() {
            const backendUrl = 'http://localhost:8888/api/music_listen/music/download_music'; // 替换成你的后端服务地址
            const fileName = 'abc.wav'; // 替换成实际的音频文件名

            axios({
                url: backendUrl,
                method: 'GET',
                responseType: 'arraybuffer',
                params: {
                    filePath: fileName,
                },
            }).then(response => {
                this.playAudio(response.data);
            }).catch(error => {
                console.error('Error downloading audio:', error);
            });
        },
        playAudio (audioBlob) {
            const audioUrl = URL.createObjectURL(audioBlob);
            const audioElement = new Audio(audioUrl);
            audioElement.play();
        },
    },
    computed: {
      display () {
        return this.$vuetify.display
      },
    },


}
</script>

<style>
</style>