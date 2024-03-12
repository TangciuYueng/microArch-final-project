<template>
    <!-- 顶部的导航栏 -->
    <!-- 用于写主页面的内容 -->
    <v-main class="full-height">
        <div class="music-room-top">
            <button
                :class="menu != 'users' ? 'music-room-menu' : 'music-room-menu-clicked'"
                @click="menu = 'users'; isChatShow = false;"
            >
                好友
            </button>
            <button
                :class="menu != 'musicRooms' ? 'music-room-menu' : 'music-room-menu-clicked'"
                @click="menu = 'musicRooms'; isChatShow = false;"
            >
                我加入的音乐室
            </button>
            <button
                :class="menu != 'square' ? 'music-room-menu' : 'music-room-menu-clicked'"
                @click="menu = 'square'; isChatShow = false;"
            >
                音乐室广场
            </button>
            <label v-if="menu != 'square' && isChatShow" class="music-room-title"> {{ getTitle() }} </label>
        </div>
        
        <div class="flex-container">
            <!-- 左部列表 -->
            <div class="list-container" :style="{ backgroundColor: menu == 'square' ? '#F1F1F1' : '#6BC4AE4F' }">
                <music-room-item
                    v-if="menu != 'square'"
                    v-for="item in (menu == 'users' ? users : musicRooms)"
                    :avatar-url="item.avatarUrl"
                    :username="item.username"
                    :time="item.time"
                    :status="item.status"
                    :song="item.song"
                    @click="clickListItem(item)"
                ></music-room-item>

                <div v-else>
                    <input
                        type="text"
                        v-model="keyword"
                        placeholder="请输入心情/音乐室名称进行搜索"
                        class="search-input"
                    />
                    <img :src="getImgSrc('../assets/search.svg')" class="search-icon">
                    
                    <!-- 模拟词云图 -->
                    <label class="keyword-1"> 甜蜜 </label>
                    <label class="keyword-2"> 平静 </label>
                    <label class="keyword-3"> 高考 </label>
                    <label class="keyword-4"> 愉快 </label>
                    <label class="keyword-5"> 考研 </label>
                    <label class="keyword-6"> 悲伤 </label>
                    <label class="keyword-7"> 亲情 </label>
                    <label class="keyword-8"> 失恋 </label>
                </div>
            </div>

            <!-- 中间聊天区 -->
            <div v-if="isChatShow" class="chat-container">
                <div class="chat-record-area">
                    <chat-record
                        v-for="item in chatRecords"
                        :avatar-url="item.avatarUrl"
                        :text="item.text"
                        :if-sender="item.ifSender"
                    ></chat-record>
                </div>
                <div style="height: 0.25%; background-color: #0000003D;"></div>
                <div class="chat-tools-area">
                    <img src="../assets/chat-sing.svg">
                    <img src="../assets/chat-emoji.svg">
                    <img src="../assets/chat-music.svg">
                    <img src="../assets/chat-picture.svg">
                    <img src="../assets/chat-gift.svg">
                    <v-btn class="send-button"> 发送 </v-btn>
                    <br>

                    <textarea class="chat-input"></textarea>
                </div>
            </div>

            <!-- 右侧信息栏 -->
            <div v-if="isChatShow" class="side-info-container">
                <label class="side-info-button"> 音乐室成员 > </label>
                <br>
                <img v-for="url in musicRoomMembers" :src="getImgSrc(url)" class="side-info-member">
                <br>
                
                <label class="side-info-button"> 音乐室歌单 > </label>
                <br>
                <img v-for="url in albums" :src="getImgSrc(url)" class="side-info-album">
                <img :src="getImgSrc('../assets/addAlbum.svg')" class="side-info-album" style="padding: 2%;">
                <br>

                <label class="side-info-button"> 当前播放 > </label>
                <div class="playing-container">
                    <img :src="getImgSrc('../assets/playing.png')" class="playing-img">
                    <label class="playing-song"> Nevada </label>
                    <label class="playing-artist"> Vicetone </label>
                    <label class="playing-time"> 00:20 / 03:28 </label>
                </div>
            </div>

            <!-- 音乐室推荐区 -->
            <div v-if="menu =='square'" class="music-room-recommend-container">
                <v-btn class="music-room-create-button"> 一键创建我的音乐室 </v-btn>

                <div class="emotion-recommend">
                    <label class="emotion-recommend-header"> 根据您最近的情绪我们给您推荐以下音乐室 > </label>
                    <music-room-rec
                        :avatar-url="musicRoomRecs[0].avatarUrl"
                        :priority="musicRoomRecs[0].priority"
                        :room-name="musicRoomRecs[0].roomName"
                        :leader-name="musicRoomRecs[0].leaderName"
                    ></music-room-rec>
                    <music-room-rec
                        :avatar-url="musicRoomRecs[1].avatarUrl"
                        :priority="musicRoomRecs[1].priority"
                        :room-name="musicRoomRecs[1].roomName"
                        :leader-name="musicRoomRecs[1].leaderName"
                    ></music-room-rec>
                    <hr style="margin-top: -30px;">
                    <music-room-rec
                        :avatar-url="musicRoomRecs[2].avatarUrl"
                        :priority="musicRoomRecs[2].priority"
                        :room-name="musicRoomRecs[2].roomName"
                        :leader-name="musicRoomRecs[2].leaderName"
                    ></music-room-rec>
                    <music-room-rec
                        :avatar-url="musicRoomRecs[3].avatarUrl"
                        :priority="musicRoomRecs[3].priority"
                        :room-name="musicRoomRecs[3].roomName"
                        :leader-name="musicRoomRecs[3].leaderName"
                    ></music-room-rec>
                    <hr style="margin-top: -30px;">
                    <music-room-rec
                        :avatar-url="musicRoomRecs[4].avatarUrl"
                        :priority="musicRoomRecs[4].priority"
                        :room-name="musicRoomRecs[4].roomName"
                        :leader-name="musicRoomRecs[4].leaderName"
                    ></music-room-rec>
                    <music-room-rec
                        :avatar-url="musicRoomRecs[5].avatarUrl"
                        :priority="musicRoomRecs[5].priority"
                        :room-name="musicRoomRecs[5].roomName"
                        :leader-name="musicRoomRecs[5].leaderName"
                    ></music-room-rec>
                </div>

                <div class="popular-recommend">
                    <label class="popular-recommend-header"> 热门音乐室 > </label>
                    <music-room-rec
                        :avatar-url="musicRoomRecs[6].avatarUrl"
                        :priority="musicRoomRecs[6].priority"
                        :room-name="musicRoomRecs[6].roomName"
                        :leader-name="musicRoomRecs[6].leaderName"
                    ></music-room-rec>
                    <music-room-rec
                        :avatar-url="musicRoomRecs[7].avatarUrl"
                        :priority="musicRoomRecs[7].priority"
                        :room-name="musicRoomRecs[7].roomName"
                        :leader-name="musicRoomRecs[7].leaderName"
                    ></music-room-rec>
                    <hr style="margin-top: -30px;">
                    <music-room-rec
                        :avatar-url="musicRoomRecs[8].avatarUrl"
                        :priority="musicRoomRecs[8].priority"
                        :room-name="musicRoomRecs[8].roomName"
                        :leader-name="musicRoomRecs[8].leaderName"
                    ></music-room-rec>
                    <music-room-rec
                        :avatar-url="musicRoomRecs[9].avatarUrl"
                        :priority="musicRoomRecs[9].priority"
                        :room-name="musicRoomRecs[9].roomName"
                        :leader-name="musicRoomRecs[9].leaderName"
                    ></music-room-rec>
                    <hr style="margin-top: -30px;">
                    <music-room-rec
                        :avatar-url="musicRoomRecs[10].avatarUrl"
                        :priority="musicRoomRecs[10].priority"
                        :room-name="musicRoomRecs[10].roomName"
                        :leader-name="musicRoomRecs[10].leaderName"
                    ></music-room-rec>
                    <music-room-rec
                        :avatar-url="musicRoomRecs[11].avatarUrl"
                        :priority="musicRoomRecs[11].priority"
                        :room-name="musicRoomRecs[11].roomName"
                        :leader-name="musicRoomRecs[11].leaderName"
                    ></music-room-rec>
                </div>
            </div>
        </div>
    </v-main>
    
</template>
  
<script>
import MusicRoomItem from '../components/MusicRoomItem.vue';
import ChatRecord from '../components/ChatRecord.vue';
import MusicRoomRec from '../components/MusicRoomRec.vue';

export default {
    //导出组件
    components: {
        MusicRoomItem,
        ChatRecord,
        MusicRoomRec
    },
    data: () => ({
        form: false,
        userAccount: null,
        password: null,
        loading: false,
        menu: "",
        isChatShow: false,
        keyword: "",
        users: [
            {
                avatarUrl: "../assets/USR1.jpg",
                username: "无聊的人",
                time: "21:13",
                status: 1,
                song: "Hello central! Give me heaven——The Carter Family"
            },
            {
                avatarUrl: "../assets/USR5.jpg",
                username: "David Wang",
                time: "12:55",
                status: 1,
                song: "Price Tag——Jessie J、B.o.B"
            },
            {
                avatarUrl: "../assets/USR3.jpg",
                username: "陌路飞雪+梦溪凤翔",
                time: "昨天",
                status: 1,
                song: "消愁——毛不易"
            },
            {
                avatarUrl: "../assets/USR4.jpg",
                username: "精神测绘人",
                time: "21:13",
                status: 2,
                song: "讲不出再见——谭咏麟"
            }
        ],
        musicRooms: [
            {
                avatarUrl: "../assets/GRP1.png",
                username: "考研闲聊音乐室",
                time: "12:00",
                status: 1,
                song: "真心英雄——周华健",
                size: 100
            },
            {
                avatarUrl: "../assets/GRP2.png",
                username: "阳光总在风雨后",
                time: "12:00",
                status: 1,
                song: "明天会更好——群星",
                size: 65
            },
            {
                avatarUrl: "../assets/GRP3.png",
                username: "乌云背后有蓝天",
                time: "12:00",
                status: 2,
                song: "戒烟——李荣浩",
                size: 33
            },
            {
                avatarUrl: "../assets/GRP4.png",
                username: "喵星人交流音乐室",
                time: "12:00",
                status: 1,
                song: "猫的舞步",
                size: 156
            }
        ],
        chatRecords: [
            {
                avatarUrl: "../assets/USR1.jpg",
                text: "hello",
                ifSender: true
            },
            {
                avatarUrl: "../assets/USR2.png",
                text: "你好！",
                ifSender: false
            },
            {
                avatarUrl: "../assets/USR1.jpg",
                text: "晚上来不来",
                ifSender: true
            },
            {
                avatarUrl: "../assets/USR2.png",
                text: "爽唱！",
                ifSender: false
            },
            {
                avatarUrl: "../assets/USR1.jpg",
                text: "去哪里搞，几点",
                ifSender: true
            },
            {
                avatarUrl: "../assets/USR2.png",
                text: "6点吃完饭呗，我开房",
                ifSender: false
            },
            {
                avatarUrl: "../assets/USR1.jpg",
                text: "造！",
                ifSender: true
            },
            {
                avatarUrl: "../assets/USR2.png",
                text: "okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！",
                ifSender: false
            },
            {
                avatarUrl: "../assets/USR1.jpg",
                text: "你干嘛！",
                ifSender: true
            },
        ],
        musicRoomMembers: [
            "../assets/MEM1.png",
            "../assets/MEM2.png",
            "../assets/MEM3.png",
            "../assets/MEM4.png",
            "../assets/MEM5.png",
        ],
        albums: [
            "../assets/ALBUM1.png",
            "../assets/ALBUM2.png"
        ],
        musicRoomRecs: [
            {
                avatarUrl: "../assets/REC1.png",
                priority: 1,
                roomName: "考研党看过来~",
                leaderName: "deidei"
            },
            {
                avatarUrl: "../assets/REC2.png",
                priority: 4,
                roomName: "一起自习一起加油！",
                leaderName: "莫益萌"
            },
            {
                avatarUrl: "../assets/REC3.png",
                priority: 2,
                roomName: "哥哥的小屋",
                leaderName: "王大卫"
            },
            {
                avatarUrl: "../assets/REC4.png",
                priority: 5,
                roomName: "春天的草丛有只羊",
                leaderName: "苏家铭"
            },
            {
                avatarUrl: "../assets/REC5.png",
                priority: 6,
                roomName: "一起K歌！",
                leaderName: "储岱泽"
            },
            {
                avatarUrl: "../assets/REC6.png",
                priority: 7,
                roomName: "二次元交流音乐室",
                leaderName: "杨滕超"
            },
            {
                avatarUrl: "../assets/REC7.png",
                priority: 1,
                roomName: "唯有爱和美食不可辜负",
                leaderName: "deidei"
            },
            {
                avatarUrl: "../assets/REC8.png",
                priority: 4,
                roomName: "兔兔有什么坏心思呢",
                leaderName: "莫益萌"
            },
            {
                avatarUrl: "../assets/REC9.png",
                priority: 2,
                roomName: "千里之行始于足下",
                leaderName: "王大卫"
            },
            {
                avatarUrl: "../assets/REC10.png",
                priority: 5,
                roomName: "失恋解救药丸",
                leaderName: "苏家铭"
            },
            {
                avatarUrl: "../assets/REC11.png",
                priority: 6,
                roomName: "二次元最大",
                leaderName: "储岱泽"
            },
            {
                avatarUrl: "../assets/REC12.png",
                priority: 7,
                roomName: "音游人基地",
                leaderName: "杨滕超"
            },
        ],
        current: {
            username: "",
            size: 0
        }
    }),
    methods: {
        clickListItem: function(item) {
            this.current.username = item.username;
            this.current.size = (this.menu == 'musicRooms') ? item.size : 0;
            this.isChatShow = true;
        },
        getTitle: function() {
            if (this.menu == 'musicRooms')
                return this.current.username + '(' + this.current.size + "人)";
            else
                return this.current.username;
        },
        getImgSrc: function(url) {
            return new URL(url, import.meta.url).href;
        }
    },
}
</script>

<style scoped>
.full-height {
    min-height: 100vh;
    /* 设置最小高度为视口高度的100% */
}
.music-room-menu {
    height: 63px;
    width: 8%;
    background: #E3F1EC;
    transition: 0.3s;
}
.music-room-menu:hover {
    background-color: aliceblue;
    border-bottom: 5px solid #45CE9A;
}
.music-room-menu-clicked {
    height: 63px;
    width: 8%;
    background: #E3F1EC;
    background-color: aliceblue;
    border-bottom: 5px solid #45CE9A;
}
.music-room-top {
    height: 63px;
    width: 100%;
    background-color: #6BC4AE4F;
}
.music-room-title {
    position: relative;
    display: inline-block;
    top: 3px;
    left: 3%;
    font-weight: 500;
    font-size: 21px;
}
.flex-container {
    display: flex;  /* 确保三个部分横向排列 */
}
.list-container {
    display: inline-block;
    width: 24%;
    height: 80vh;
    overflow-x: hidden;
    overflow-y: auto;
}
.chat-container {
    display: inline-block;
    width: 55%;
    height: 80vh;
}
.chat-record-area {
    width: 100%;
    height: 69.875%;
    overflow-x: hidden;
    overflow-y: auto;
}
.chat-tools-area {
    width: 100%;
    height: 29.875%;
    overflow-y: auto;
}
.chat-tools-area > img {
    position: relative;
    top: 5px;
    left: 1%;
    height: 17%;
    padding: 0.8%;
    margin-right: 0.5%;
    border-radius: 10px;
    cursor: pointer;
    transition: 0.3s;
}
.chat-tools-area > img:hover {
    background-color: #DCE8E3;
}
.send-button {
    position: relative;
    top: -10px;
    left: 67%;
    background-color: #DCE8E3;
}
.chat-input {
    padding: 10px;
    width: 100%;
    height: 25vh;
    font-size: 20px;
    color: #0000009F;
}
.side-info-container {
    display: inline-block;
    width: 21%;
    height: 80vh;
    background-color: #FAF9F9;
}
.side-info-button {
    display: inline-block;
    margin-left: 10%;
    margin-top: 20px;
    font-weight: 500;
    font-size: 21px;
    color: #3D3D3D;
    transition: 0.3s;
}
.side-info-button:hover {
    cursor: pointer;
    color: #45CE9A;
}
.side-info-member {
    width: 52px;
    height: 52px;
    border-radius: 26px;
    margin-left: 4%;
    margin-top: 10px;
    padding: 1%;
    transition: 0.3s;
}
.side-info-member:hover {
    cursor: pointer;
    opacity: 0.7;
}
.side-info-album {
    width: 78px;
    height: 78px;
    border-radius: 10px;
    margin-left: 4%;
    margin-top: 10px;
    transition: 0.3s;
}
.side-info-album:hover {
    opacity: 0.7;
}
.playing-container {
    margin-top: 10px;
    margin-left: 4%;
    margin-right: 4%;
    padding: 12px;
    border-radius: 7px;
    background-color: #D8D8D8;
    height: 94px;
}
.playing-img {
    width: 68px;
    height: 68px;
    border-radius: 7px;
}
.playing-song {
    position: relative;
    top: -50px;
    left: 3%;
    font-weight: 500;
    font-size: 19px;
    color: #3D3D3D;
}
.playing-artist {
    position: relative;
    top: -28px;
    left: -21%;
    font-weight: lighter;
    font-size: 15px;
    color: #3D3D3D;
}
.playing-time {
    position: relative;
    left: 29%;
    top: -30px;
    text-wrap: nowrap;
}
.search-input {
    margin-top: 30px;
    margin-left: 15%;
    padding: 25px;
    padding-left: 40px;
    width: 70%;
    height: 40px;
    border: 2px solid black;
    border-radius: 5px;
    background-color: #FFFFFF;
    transition: 0.3s;
}
.search-input:focus {
    background-color: #E3F1EC;
}
.search-icon {
    position: relative;
    top: 6px;
    left: -67%;
}
.keyword-1 {
    position: relative;
    color: #D46A39;
    font-weight: 500;
    font-size: 44px;
    top: 80px;
    left: 60%;
}
.keyword-2 {
    position: relative;
    color: #33AD82;
    font-weight: 500;
    font-size: 41px;
    top: 100px;
    left: -12%;
}
.keyword-3 {
    position: relative;
    color: #BFAFD1;
    font-weight: 500;
    font-size: 48px;
    top: 180px;
    left: -22%;
}
.keyword-4 {
    position: relative;
    color: #D14159;
    font-weight: 500;
    font-size: 49px;
    top: 200px;
    left: -20%;
}
.keyword-5 {
    position: relative;
    color: #41ADD1;
    font-weight: 500;
    font-size: 35px;
    top: 160px;
    left: -12%;
}
.keyword-6 {
    position: relative;
    color: #4187D1;
    font-weight: 500;
    font-size: 40px;
    top: 200px;
    left: -2%;
}
.keyword-7 {
    position: relative;
    color: #D8BD14;
    font-weight: 500;
    font-size: 31px;
    top: 230px;
    left: 5%;
}
.keyword-8 {
    position: relative;
    color: #C8B3BE;
    font-weight: 500;
    font-size: 31px;
    top: 250px;
    left: -60%;
}
.music-room-recommend-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    overflow-y: scroll;
    width: 76%;
    height: 80vh;
}
.music-room-create-button {
    margin-top: 40px;
    width: 27%;
    height: 64px;
    font-weight: 500;
    font-size: 28px;
    background-color: #6EB1C470;
}
.emotion-recommend {
    margin-top: 40px;
    width: 80%;
    height: 320px;
    border-radius: 9px;
    opacity: 1;

    background: linear-gradient(106deg, #D2F1FC 0%, #D8F0F1 9%, #D5F0EA 24%, rgba(213, 240, 234, 0.9901) 75%, rgba(201, 222, 247, 0.7905) 97%, rgba(221, 202, 237, 0.3098) 102%);

    box-sizing: border-box;
    /* 纯白 */
    border: 1px solid #FFFFFF;

    box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.302);
}
.emotion-recommend-header {
    display: block;
    margin-top: 10px;
    margin-left: 2%;
    font-weight: 500;
    font-size: 19px;
    color: #646464;
}
.popular-recommend {
    margin-top: 40px;
    width: 80%;
    height: 320px;
    border-radius: 9px;
    opacity: 1;

    background: linear-gradient(106deg, #D2F1FC 0%, #D8F0F1 9%, #D5F0EA 24%, rgba(213, 240, 234, 0.9901) 75%, rgba(201, 222, 247, 0.7905) 97%, rgba(221, 202, 237, 0.3098) 102%);

    box-sizing: border-box;
    /* 纯白 */
    border: 1px solid #FFFFFF;

    box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.302);
}
.popular-recommend-header {
    display: block;
    margin-top: 10px;
    margin-left: 2%;
    font-weight: 500;
    font-size: 19px;
    color: #646464;
}
</style>