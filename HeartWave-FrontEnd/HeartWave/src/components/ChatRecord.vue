<template>
    <div v-if="!ifSender" class="container-left">
        <img class="avatar" :src="getImgSrc()">
        <div class="text-container-left">
            <p :style="getTextStyle()" class="text"> {{ text }} </p>
        </div>
    </div>

    <div v-else class="container-right">
        <div class="text-container-right">
            <p :style="getTextStyle()" class="text"> {{ text }} </p>
        </div>
        <img class="avatar" :src="getImgSrc()">
    </div>
</template>

<script>
export default {
    props: {
        avatarUrl: "",
        text: "",
        ifSender: true
    },
    methods: {
        getTextStyle: function() {
            return {
                backgroundColor: this.ifSender ? "#45CE9A" : "#6BC4AE4F"
            }
        },
        getImgSrc: function() {
            return new URL(this.avatarUrl, import.meta.url).href;
        }
    }
}
</script>

<style scoped>
.container-left {
    margin-top: 15px;
    margin-left: 3%;
}
.container-right {
    margin-top: 15px;
    margin-right: -15%;
}
.avatar {
    display: inline-block;
    height: 42px;
    border-radius: 21px;
}
.text-container-left {
    position: relative;
    display: inline-block;
    top: -16px;
    left: 2%;
    width: 80%;
}
.text-container-right {
    position: relative;
    display: inline-block;
    top: -16px;
    right: 2%;
    width: 80%;
    text-align: right;  /* 组件右对齐 */
}
.text {
    display: inline-block;
    border-radius: 5px;
    padding: 8px;
}
</style>