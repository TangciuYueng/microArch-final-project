<template>
    <div class="container">
        <img :src="getImgSrc(avatarUrl)" class="rec-avatar">
        <label class="priority"> {{ priority }} </label>
        <label class="room-name"> {{ roomName }} </label>
        <label class="leader-name"> 群主：{{ leaderName }} </label>
        <v-btn class="join-button"> 加入 </v-btn>
    </div>
</template>

<script>
export default {
    props: {
        avatarUrl: String,
        priority: Number,
        roomName: String,
        leaderName: String
    },
    data() {
        return {

        }
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
    display: inline-block;
    width: 50%;
    height: 93px;
    padding: 10px;
    padding-left: 20px;
}
.rec-avatar {
    position: relative;
    height: 72px;
    width: 72px;
    border-radius: 9px;
}
.priority {
    position: relative;
    top: -27px;
    left: 3.5%;
    font-weight: bold;
    font-size: 24px;
}
.room-name {
    position: relative;
    display: inline-block;
    top: -40px;
    left: 7%;
    width: 50%;
    height: 24px;
    overflow: hidden;
    font-weight: 500;
    font-size: 16px;
}
.leader-name {
    position: relative;
    display: inline-block;
    top: -40px;
    left: 26%;
    width: 50%;
    height: 24px;
    overflow: hidden;
    font-weight: lighter;
    font-size: 16px;
}
.join-button {
    position: relative;
    top: -65px;
    left: 30%;
    font-weight: 500;
    font-size: 18px;
    background-color: #6EB1C4;
    width: 71px;
    height: 38px;
    border-radius: 10px;
}
</style>