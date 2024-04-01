<template>
    <div class="container">
        <div v-if="active">
            <img :src="getImgSrc(cover)" class="background">

            <label class="song"> {{ song }} </label>
            <br>
            <label class="artist"> {{ artist }} </label>
            <br>
            <label class="time"> {{ time }} / {{ total }} </label>
            <br>
            
            <div class="lyric-container" align="center">
                <label class="pre-1" style="margin-top: 50px;"> {{ lyric[0] }} </label>
                <br>
                <label class="pre-2"> {{ lyric[1] }} </label>
                <br>
                <label class="current"> {{ lyric[2] }} </label>
                <br>
                <label class="forward-1"> {{ lyric[3] }} </label>
                <br>
                <label class="forward-2"> {{ lyric[4] }} </label>
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
    methods: {
        getImgSrc: function(url) {
            return new URL(url, import.meta.url).href;
        }
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
    display: inline-block;
    margin-top: 15px;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 21px;
    color: rgb(0, 0, 0, 0.5);
}
.lyric-container .pre-2 {
    display: inline-block;
    margin-top: 15px;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 21px;
    color: rgb(0, 0, 0, 0.6);
}
.lyric-container .current {
    display: inline-block;
    margin-top: 15px;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 25px;
    color: black;
}
.lyric-container .forward-1 {
    display: inline-block;
    margin-top: 15px;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 21px;
    color: rgb(0, 0, 0, 0.6);
}
.lyric-container .forward-2 {
    display: inline-block;
    margin-top: 15px;
    font-family: 思源黑体;
    font-weight: 500;
    font-size: 21px;
    color: rgb(0, 0, 0, 0.5);
}
</style>