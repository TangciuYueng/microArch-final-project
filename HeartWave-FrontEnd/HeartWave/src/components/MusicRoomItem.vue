<template>
    <span class="music-room-item">
        <img :src="getImgSrc(avatarUrl)" alt="image src invalid" class="item-avatar">
        <label class="item-username"> {{ username }} </label>
        <label class="item-time"> {{ time }} </label>
        <div class="item-div">
            <img :src="getStatusImgSrc()" alt="image src invalid" class="item-status">
            <label class="item-song"> {{ formatStatus() }} </label>
        </div>
    </span>
</template>

<script>
export default {
    props: {
        avatarUrl: String,
        username: String,
        time: String,
        status: Number,
        song: String
    },
    data() {
        return {

        }
    },
    methods: {
        getImgSrc: function(url) {
            return new URL(url, import.meta.url).href;
        },
        getStatusImgSrc: function() {
            switch (this.status) {
                case 1:
                    return this.getImgSrc('../assets/listening.svg');
                    break;
                case 2:
                    return this.getImgSrc('../assets/singing.svg');
                    break;
                default:
                    return null;
            }
        },
        formatStatus: function() {
            switch (this.status) {
                case 1:
                    return "正在听：" + this.song;
                    break;
                case 2:
                    return "正在唱：" + this.song;
                    break;
                default:
                    return null;
            }
        }
    },
}
</script>

<style>
.music-room-item {
    display: block;
    height: 108px;
    width: 100%;  /* 已经被div包围，无需再设置宽度 */
    background-color: #F5F5F5;
    transition: 0.3s;
}
.music-room-item:hover {
    background-color: #E5E5E5;
}
.item-avatar {
    position: relative;
    top: 20px;
    left: 4%;
    height: 68px;
    width: 68px;
    border-radius: 34px;
}
.item-username {
    position: relative;
    display: inline-block;
    top: -15px;
    left: 10%;
    width: 55%;
    height: 30px;
    overflow: hidden;
    font-weight: 500;
    font-size: 20px;
}
.item-time {
    position: relative;
    display: inline-block;
    top: -25px;
    left: 14%;
    font-weight: 500;
    font-size: 14px;
}
.item-div {
    position: relative;
    top: -30px;
    left: 20%;
}
.item-status {
    position: relative;
    top: 15px;
    left: 7%;
    height: 28px;
    width: 28px;
}
.item-song {
    position: relative;
    display: inline-block;
    top: 15px;
    left: 8%;
    width: 60%;
    height: 26px;
    overflow: hidden;
    font-weight: 500;
    font-size: 16px;
}
</style>