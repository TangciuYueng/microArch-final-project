<template>
    <div class="container">
        <img :src="getImgSrc(avatarUrl)" class="rec-avatar">
        <label class="room-name" :title="roomName"> {{ roomName }} </label>
        <label class="leader-name" :title="leaderName"> 群主：{{ leaderName }} </label>
    </div>
</template>

<script>
export default {
    props: {
        avatarUrl: String,
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
    width: 100%;
    height: 140px;
    padding: 10px;
    padding-top: 5px;
}
.rec-avatar {
    position: relative;
    top: 4px;
    left: 5%;
    height: 110px;
    width: 110px;
    border-radius: 9px;
    cursor: pointer;
    transition: 0.3s;
}
.rec-avatar:hover {
    opacity: 0.7;
}
.room-name {
    position: relative;
    display: inline-block;
    top: -60px;
    left: 12%;
    width: 60%;
    height: 36px;
    white-space: nowrap;
    overflow-x: hidden;
    text-overflow: ellipsis;
    font-weight: 500;
    font-size: 24px;
    caption-side: bottom;
}
.leader-name {
    position: relative;
    display: inline-block;
    top: -48px;
    left: 35%;
    width: 60%;
    height: 36px;
    white-space: nowrap;
    overflow-x: hidden;
    text-overflow: ellipsis;
    font-weight: lighter;
    font-size: 24px;
}
</style>