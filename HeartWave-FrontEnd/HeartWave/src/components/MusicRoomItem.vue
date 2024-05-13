<template>
  <span class="music-room-item">
    <img :src="avatar" alt="image src invalid" class="item-avatar">
    <label class="item-username" :title="username"> {{ username }} </label>
    <label class="item-time"> {{ formatTime(time) }} </label>
    <div class="item-div">
      <img :src="getStatusImgSrc()" alt="image src invalid" class="item-status">
      <label class="item-song" :title="song"> {{ formatStatus() }} </label>
    </div>
  </span>
</template>

<script>
export default {
    props: {
        avatar: String,
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
        },
        formatTime: function(time) {
            var originDate = new Date(time);  //最后聊天时间
            var currentDate = new Date();     //当前时间
            var difference = currentDate.getTime() - originDate.getTime();  //相差时长（毫秒）
            const hourDiff = difference / (1000 * 60 * 60);                 //相差时长（小时）

            var todayStartDate = new Date();  //今日0点时间
                todayStartDate.setHours(0);
                todayStartDate.setMinutes(0);
                todayStartDate.setSeconds(0);
                todayStartDate.setMilliseconds(0);

            const todayDifference = currentDate.getTime() - todayStartDate.getTime();  //今天经过的时长（毫秒）

            //日期相同，显示时和分
            if (currentDate.getFullYear() == originDate.getFullYear() && currentDate.getMonth() == originDate.getMonth() && currentDate.getDay() == originDate.getDay()) {
                const min = originDate.getMinutes();
                return originDate.getHours() + ":" + (min < 10 ? "0" + min : min);
            }
            //日期不同且相差少于24小时，为昨天
            else if (hourDiff > 0 && hourDiff <= 24) {
                return "昨天";
            }
            //相差24~48小时，若总相差时长 < 今天经过的时长 + 24小时则是前天，否则是昨天
            else if (hourDiff > 24 && hourDiff <= 48) {
                if (difference >= todayDifference + 1000 * 60 * 60 * 24)
                    return "前天";
                else
                    return "昨天";
            }
            //相差48~72小时，若总相差时长 < 今天经过的时长 + 48小时则是大前天（显示月和日），否则是前天
            else if (hourDiff > 48 && hourDiff <= 72) {
                if (difference >= todayDifference + 1000 * 60 * 60 * 24 * 2)
                    return (originDate.getMonth() + 1) + "/" + originDate.getDate();
                else
                    return "前天";
            }
            //相差72小时以上，若年相等，显示月和日
            else if (currentDate.getFullYear() == originDate.getFullYear())
                return (originDate.getMonth() + 1) + "/" + originDate.getDate();
            //若年不相等，显示年月日
            else
                return originDate.getFullYear() + "/" + (originDate.getMonth() + 1) + "/" + originDate.getDate();
        }
    }
}
</script>

<style scoped>
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
    width: 45%;
    height: 30px;
    white-space: nowrap;
    overflow-x: hidden;
    text-overflow: ellipsis;
    font-weight: 500;
    font-size: 20px;
}
.item-time {
    position: relative;
    display: inline-block;
    text-align: right;
    width: 20%;
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
    white-space: nowrap;
    overflow-x: hidden;
    text-overflow: ellipsis;
    font-weight: 500;
    font-size: 16px;
}
</style>