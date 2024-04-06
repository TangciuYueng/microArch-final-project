<template>
    <div class="container">
        <div v-if="active">
            <img :src="getImgSrc(cover)" class="background">

            <v-btn @click="lyricFlip()" style="position: fixed; margin-left: 10%;"> flip </v-btn>
            <label class="song"> {{ song }} </label>
            <br>
            <label class="artist"> {{ artist }} </label>
            <br>
            <label class="time"> {{ time }} / {{ total }} </label>
            <br>
            
            <div class="lyric-container" align="center">
                <div style="height: 40px;"></div>
                <div v-for="(item, index) in lyric">
                    <label
                        :class="getLyricStyle(index)"
                        :style="{
                            opacity: index >= currentLine - 3 && index <= currentLine + 1 ? '100%' : '0%',
                            transform: 'translateY(' + lyricDist + 'px)'
                        }">
                        {{ item }}
                    </label>
                    <br>
                </div>
            </div>
        </div>

        <div v-else>
            
        </div>
    </div>
</template>

<script>
export default {
    props: {
        cover: String,
        active: Boolean,
        song: String,
        artist: String,
        time: String,
        total: String,
        lyric: []
    },
    data: () => ({
        currentLine: 3,
        lyricDist: 0
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
            else if (index + 2 == this.currentLine)
                return 'pre-1';
            else if (index + 3 == this.currentLine)
                return 'pre-2';
        },
        lyricFlip: function() {
            if (this.currentLine == this.lyric.length - 2)
                return;

            this.currentLine++;
            this.lyricDist -= 24;
        },
    }
}
</script>

<style scoped>
.container {
    width: 100%;
    height: 440px;
}
.background {
    position: fixed;
    width: 21%;
    height: 440px;
    opacity: 0.3;
    filter: brightness(0.7);
}
.song {
    position: relative;
    display: inline-block;
    top: 20px;
    left: 7%;
    width: 85%;
    color: #3D3D3D;
    font-weight: 500;
    font-size: 27px;
    white-space: nowrap;
    overflow-x: hidden;
    text-overflow: ellipsis;
}
.artist {
    position: relative;
    display: inline-block;
    top: 15px;
    left: 7%;
    width: 85%;
    color: #3D3D3D;
    font-weight: lighter;
    font-size: 18px;
    white-space: nowrap;
    overflow-x: hidden;
    text-overflow: ellipsis;
}
.time {
    position: relative;
    display: inline-block;
    left: 7%;
    top: 12px;
    text-wrap: nowrap;
}
.lyric-container .pre-1 {
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 21px;
    color: rgb(0, 0, 0, 0.5);
    transition: 0.3s;
    height: 30px;
}
.lyric-container .pre-2 {
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 21px;
    color: rgb(0, 0, 0, 0.6);
    transition: 0.3s;
    height: 30px;
}
.lyric-container .current {
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 25px;
    color: black;
    transition: 0.3s;
    height: 30px;
}
.lyric-container .forward-1 {
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 21px;
    color: rgb(0, 0, 0, 0.6);
    transition: 0.3s;
    height: 30px;
}
.lyric-container .forward-2 {
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 21px;
    color: rgb(0, 0, 0, 0.5);
    transition: 0.3s;
    height: 30px;
}
</style>