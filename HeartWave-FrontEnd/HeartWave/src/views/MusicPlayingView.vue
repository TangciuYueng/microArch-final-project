<template>
    <v-main class="full-height">
        <img :src="getImgSrc('../assets/music-playing.png')" class="background">
        
        <div class="content">
            <label class="song" :title="song"> {{ song }} </label>
            <img :src="getImgSrc('../assets/playing-listening.svg')" class="listening-img">
            <label class="listening"> {{ listening }}人正在听 </label>

            <div class="song-info">
                <label class="album-title"> 专辑： </label>
                <label class="album" :title="album"> {{ album }} </label>
                <label class="artist-title"> 歌手： </label>
                <label class="artist" :title="artist"> {{ artist }} </label>
            </div>
            
            <div class="mid">
                <div class="album-img-container">
                    <img :src="getImgSrc('../assets/music-playing.png')" class="album-img">
                </div>
                <div style="display: inline; width: 6%;"></div>
                <div class="lyric-container" align="center">
                    <div style="height: 20px;"></div>
                    <div v-for="(item, index) in lyric">
                        <label
                            :class="getLyricStyle(index)"
                            :style="{
                                opacity: index >= currentLine - 4 && index <= currentLine + 2 ? '100%' : '0%',
                                transform: 'translateY(' + lyricDist + 'px)'
                            }">
                            {{ item }}
                        </label>
                        <br>
                    </div>
                </div>

                <v-btn @click="lyricFlip()"> flip </v-btn>
            </div>

            <div class="tool-bar">
                <img 
                    :src="getImgSrc(isLiked ? '../assets/liked.svg' : '../assets/like.svg')"
                    class="tool-icon"
                    @click="handleSongLiked()"
                    :title="isLiked ? '取消喜欢' : '喜欢'">
                <img :src="getImgSrc('../assets/download.svg')" class="tool-icon" title="下载">
                <img :src="getImgSrc('../assets/share.svg')" class="tool-icon" title="分享">
                <img :src="getImgSrc('../assets/circulate.svg')" class="tool-icon" title="循环播放">
            </div>
            
            <div class="similar-room-prompt">
                <label class="emotion-keyword-title"> 情绪识别关键词： </label>
                <label class="emotion-keyword"> {{ getEmotionKeywords() }} </label>
                <br>
                <label class="similar-room-title"> 这些音乐室正在播放...... </label>
            </div>

            <div class="similar-room-container">
                <v-card class="similar-room-card">
                    <v-card-item>
                        <similar-room-rec
                            :avatar-url="similarMusicRooms[0].avatarUrl"
                            :room-name="similarMusicRooms[0].roomName"
                            :leader-name="similarMusicRooms[0].leaderName"
                        ></similar-room-rec>
                    </v-card-item>
                    <hr style="margin-top: -35px;">
                    <v-card-item>
                        <similar-room-rec
                            :avatar-url="similarMusicRooms[1].avatarUrl"
                            :room-name="similarMusicRooms[1].roomName"
                            :leader-name="similarMusicRooms[1].leaderName"
                        ></similar-room-rec>
                    </v-card-item>
                </v-card>
                <div style="display: inline; width: 5%;"></div>
                <v-card class="similar-room-card">
                    <v-card-item>
                        <similar-room-rec
                            :avatar-url="similarMusicRooms[2].avatarUrl"
                            :room-name="similarMusicRooms[2].roomName"
                            :leader-name="similarMusicRooms[2].leaderName"
                        ></similar-room-rec>
                    </v-card-item>
                    <hr style="margin-top: -35px;">
                    <v-card-item>
                        <similar-room-rec
                            :avatar-url="similarMusicRooms[3].avatarUrl"
                            :room-name="similarMusicRooms[3].roomName"
                            :leader-name="similarMusicRooms[3].leaderName"
                        ></similar-room-rec>
                    </v-card-item>
                </v-card>
            </div>
        </div>
    </v-main>
</template>

<script>
import MusicRoomRec from '@/components/MusicRoomRec';
import SimilarRoomRec from '@/components/SimilarRoomRec.vue';
export default {
    components: {
        MusicRoomRec,
        SimilarRoomRec
    },
    data: () => ({
        song: "La solitudine",
        listening: 0,
        album: "Laura Pausini & Dearst yangsk",
        artist: "Laura Pausini",
        isLiked: true,
        currentLine: 4,  //默认第一句歌词前有三行空行占位，从4开始
        lyricDist: 0,
        lyric: [
            "",
            "",
            "",
            "TEST1",
            "TEST2",
            "TEST3",
            "TEST4",
            "作词 : Pietro Cremonesi/Federico Cavalli",
            "作曲 : Pietro Cremonesi/angelo valsiglio",
            "Marco se n'è andato e non ritorna più marco 走了，不再回来",
            "Il treno delle 7:30 senza lui\n7点30分的列车上没有了他",
            "è un cuore di metallo senza l'anima\n心已冷，灵魂毫无生气",
            "Nel freddo del matino grigio di città\n在这个城市阴黯的早晨",
            "A scuola il banco è vuoto, Marco è dentro me",
            "TEST5",
            "TEST6",
            "TEST7",
            "TEST8",
            "",
            "",
            ""
        ],
        emotionKeywords: [
            "忧伤",
            "思念"
        ],
        similarMusicRooms: [
        {
                avatarUrl: "../assets/recommend/REC1.png",
                roomName: "考研党看过来~",
                leaderName: "deidei"
            },
            {
                avatarUrl: "../assets/recommend/REC2.png",
                roomName: "一起自习一起加油！",
                leaderName: "莫益萌"
            },
            {
                avatarUrl: "../assets/recommend/REC3.png",
                roomName: "哥哥的小屋",
                leaderName: "王大卫"
            },
            {
                avatarUrl: "../assets/recommend/REC4.png",
                roomName: "春天的草丛有只羊",
                leaderName: "苏家铭"
            },
        ]
    }),
    methods: {
        getImgSrc: function(url) {
            return new URL(url, import.meta.url).href;
        },
        getLyricStyle: function(index) {
            if (index + 1 == this.currentLine)
                return 'current';
            else if (index == this.currentLine)
                return 'forward-1';
            else if (index - 1 == this.currentLine)
                return 'forward-2';
            else if (index - 2 == this.currentLine)
                return 'forward-3';
            else if (index + 2 == this.currentLine)
                return 'pre-1';
            else if (index + 3 == this.currentLine)
                return 'pre-2';
            else if (index + 4 == this.currentLine)
                return 'pre-3';
        },
        lyricFlip: function() {
            if (this.currentLine == this.lyric.length - 3)
                return;

            this.currentLine++;
            this.lyricDist -= 25;
        },
        getEmotionKeywords: function() {
            var ret = "";

            for (let i = 0; i < this.emotionKeywords.length; i++) {
                if (i == this.emotionKeywords.length - 1)
                    ret += this.emotionKeywords[i];
                else
                    ret += (this.emotionKeywords[i] + ", ")
            }

            return ret;
        },
        handleSongLiked: function() {
            this.isLiked = !this.isLiked;
        }
    }
}
</script>

<style scoped>
.full-height {
    position: relative;
    min-height: 100vh;
    /* 设置最小高度为视口高度的100% */
}
.background {
    position: fixed;  /* fixed的position需要指定位置，如使用top、left，否则无法显示 */
    height: 89vh;
    width: 100%;
    top: 0px;
    left: 0%;
    opacity: 0.8;
    filter: brightness(0.6);
}
.content {
    position: relative;
    overflow-x: hidden;
    overflow-y: auto;
    height: 88vh;
}
.song {
    position: relative;
    display: inline-block;
    top: 35px;
    left: 50%;
    width: 32%;
    white-space: nowrap;
    overflow-x: hidden;
    text-overflow: ellipsis;
    font-family: 思源黑体;
    font-size: 38px;
    font-weight: bold;
    color: white;
}
.listening-img {
    position: relative;
    top: 26px;
    left: 53%;
}
.listening {
    position: relative;
    top: 23px;
    left: 53%;
    font-family: 思源黑体;
    font-size: 17px;
    font-weight: normal;
    color: white;
}
.song-info {
    position: relative;
    left: 50%;
    top: 50px;
}
.song-info > label {
    font-family: 思源黑体;
    font-size: 19px;
    font-weight: 350;
}
.album-title {
    color: white;
}
.album {
    position: relative;
    display: inline-block;
    top: 8px;
    margin-right: 2%;
    width: 20%;
    white-space: nowrap;
    overflow-x: hidden;
    text-overflow: ellipsis;
    color: #5D7DFF
}
.artist-title {
    color: white;
}
.artist {
    position: relative;
    display: inline-block;
    top: 8px;
    width: 18%;
    white-space: nowrap;
    overflow-x: hidden;
    text-overflow: ellipsis;
    color: #7670FF;
}
.mid {
    display: flex;
    justify-content: center;
    margin-left: 14%;
    margin-top: 60px;
    width: 75%;
    height: 500px;
}
.album-img-container {
    display: inline;
    width: 42%;
    height: 500px;
}
.album-img {
    width: 100%;
    height: 500px;
}
.lyric-container {
    display: inline;
    border-left: 0.5px solid white;
    border-right: 0.5px solid white;
    width: 52%;
}
.lyric-container > label {
    margin-top: 36px;
}
.lyric-container .pre-1 {
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 21px;
    color: rgb(255, 255, 255, 0.3);
    transition: 0.3s;
    height: 50px;
}
.lyric-container .pre-2 {
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 19px;
    color: rgb(255, 255, 255, 0.5);
    transition: 0.3s;
    height: 50px;
}
.lyric-container .pre-3 {
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 19px;
    color: rgb(255, 255, 255, 0.6);
    transition: 0.3s;
    height: 50px;
}
.lyric-container .current {
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 25px;
    color: white;
    transition: 0.3s;
    height: 50px;
}
.lyric-container .forward-1 {
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 21px;
    color: rgb(255, 255, 255, 0.6);
    transition: 0.3s;
    height: 50px;
}
.lyric-container .forward-2 {
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 19px;
    color: rgb(255, 255, 255, 0.5);
    transition: 0.3s;
    height: 50px;
}
.lyric-container .forward-3 {
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 19px;
    color: rgb(255, 255, 255, 0.3);
    transition: 0.3s;
    height: 50px;
}
.tool-bar {
    margin-top: 20px;
    margin-left: 11%;
}
.tool-icon {
    display: inline-block;
    padding: 1%;
    margin-left: 5%;
    height: 58px;
    background-color: #D8D8D8;
    border-radius: 29px;
    cursor: pointer;
    transition: 0.3s;
}
.tool-icon:hover {
    background-color: #909090;
}
.similar-room-prompt {
    margin-top: 20px;
    margin-left: 15%;
}
.emotion-keyword-title {
    font-weight: bold;
    font-size: 32px;
    color: white;
}
.emotion-keyword {
    font-weight: bold;
    font-size: 32px;
    color: rgb(255, 255, 255, 0.79);
}
.similar-room-title {
    font-weight: bold;
    font-size: 32px;
    color: white;
}
.similar-room-container {
    display: flex;
    margin-top: 20px;
    margin-left: 15%;
}
.similar-room-card {
    display: inline;
    width: 40%;
    height: 300px;
    background-color: #D8D8D8;
}
</style>