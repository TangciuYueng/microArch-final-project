<template>
    <v-main class="full-height">
        <!-- 顶部的导航栏 -->
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
            <img
                v-if="isCreateMusicRoomShow"
                :src="getImgSrc(backButtonImgSrc)"
                class="back-button"
                @click="isCreateMusicRoomShow = false"
                @mouseover="backButtonImgSrc = '../assets/back-active.svg'"
                @mouseout="backButtonImgSrc = '../assets/back.svg'"
            >
            <label v-if="isCreateMusicRoomShow" class="music-room-create-title"> 音乐室创建 </label>
        </div>
        
        <div class="flex-container">
            <!-- 左部列表 -->
            <div class="list-container" :style="{ backgroundColor: menu == 'square' ? '#F1F1F1' : '#6BC4AE4F' }">
                <music-room-item
                    v-if="menu != 'square'"
                    v-for="item in (menu == 'users' ? users : musicRooms)"
                    :avatar="'data:image/jpg;base64,' + item.avatar"
                    :username="item.username"
                    :time="item.time"
                    :status="item.status"
                    :song="item.song"
                    @click="clickListItem(item); "
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
                        :avatar="getChatAvatar(item.ifSender)"
                        :text="item.text"
                        :if-sender="item.ifSender"
                    ></chat-record>
                </div>
                <div style="height: 0.25%; background-color: #0000003D;"></div>
                <div class="chat-input-area">
                    <div class="tool-bar">
                        <img src="../assets/chat-sing.svg">
                        <img src="../assets/chat-emoji.svg">
                        <img src="../assets/chat-music.svg">
                        <img src="../assets/chat-picture.svg">
                        <img src="../assets/chat-gift.svg">
                        <v-btn class="send-button"> 发送 </v-btn>
                    </div>

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

                <label class="side-info-button"> 当前{{ currentUser.status == 1 ? '播放' : '在唱' }} > </label>
                <music-room-current
                    :cover="currentSong.cover"
                    :active="currentSong.name != ''"
                    :song="currentSong.name"
                    :artist="currentSong.artist"
                    :time="currentSong.time"
                    :total="currentSong.total"
                    :lyric="currentSong.lyric">
                </music-room-current>
            </div>

            <!-- 音乐室推荐区 -->
            <div v-if="menu =='square' && !isCreateMusicRoomShow" class="music-room-recommend-container">
                <v-btn class="music-room-create-button" @click="isCreateMusicRoomShow = true"> 一键创建我的音乐室 </v-btn>

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

            <!-- 创建音乐室表单 -->
            <div v-if="isCreateMusicRoomShow" class="music-room-create-container">
                <v-form>
                    <label class="form-label"> 音乐室名称 </label>
                    <input
                        type="text"
                        placeholder="请输入音乐室的名字"
                        class="form-input"
                        v-model="newMusicRoom.name">

                    <label class="form-label"> 音乐室简介 </label>
                    <textarea
                        placeholder="请输入音乐室的简介"
                        class="form-input"
                        style="height: 80px;"
                        v-model="newMusicRoom.intro"></textarea>
                    
                    <label class="form-label"> 音乐室进入权限 </label>
                    <v-radio-group v-model="newMusicRoom.auth" class="form-radio">
                        <v-radio label="公开可见" value="public"></v-radio>
                        <v-radio label="私有仅通过管理员邀请可进" value="private"></v-radio>
                    </v-radio-group>

                    <label class="form-label"> 音乐室头像 </label>
                    <button
                        v-if="newMusicRoom.avatar == ''"
                        type="button"
                        @click="openFilePicker()"
                        class="form-avatar">
                        选择文件
                    </button>
                    <img
                        v-else
                        :src="newMusicRoom.avatar"
                        @click="newMusicRoom.avatar = ''"
                        class="form-avatar-img"
                    >
                    <input type="file" ref="fileInput" style="display: none;" @change="handleImgSelected">

                    <label class="form-label"> 音乐室初始歌单 </label>
                    <v-btn class="form-button"> 从我的歌单中选择 </v-btn>

                    <label class="form-label"> 邀请好友 </label>
                    <v-btn class="form-button"> 点击选择好友 </v-btn>
                    
                    <br>
                    <v-btn class="submit-button" @click="newMusicRoomDialog = true"> 提交审核 </v-btn>
                </v-form>
            </div>

            <v-dialog v-model="newMusicRoomDialog" max-width="500px">
                <v-card class="new-music-room-dialog" align="center">
                    <v-card-title class="dialog-title"> 提交成功！ </v-card-title>
                    <v-card-text class="dialog-content"> 审核结果将在一个工作日内通知！ </v-card-text>
                    <v-card-actions>
                        <v-btn class="dialog-button" @click="newMusicRoomDialog = false"> 确定 </v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </div>
    </v-main>
    
</template>
  
<script>
import MusicRoomItem from '../components/MusicRoomItem.vue';
import ChatRecord from '../components/ChatRecord.vue';
import MusicRoomRec from '../components/MusicRoomRec.vue';
import MusicRoomCurrent from '../components/MusicRoomCurrent.vue';
import { updateChatTime, addFriend, getFriends } from '../axios/friend.js';
export default {
    //导出组件
    components: {
        MusicRoomItem,
        ChatRecord,
        MusicRoomRec,
        MusicRoomCurrent
    },
    data: () => ({
        form: false,
        userAccount: null,
        password: null,
        loading: false,
        menu: "users",
        isChatShow: false,
        isCreateMusicRoomShow: false,
        backButtonImgSrc: "../assets/back.svg",
        keyword: "",
        newMusicRoomDialog: false,
        users: [],
        musicRooms: [
            {
                avatarUrl: "../assets/room/ROOM1.png",
                username: "考研闲聊音乐室",
                time: "12:00",
                status: 1,
                song: "真心英雄——周华健",
                size: 100
            },
            {
                avatarUrl: "../assets/room/ROOM2.png",
                username: "阳光总在风雨后",
                time: "12:00",
                status: 1,
                song: "明天会更好——群星",
                size: 65
            },
            {
                avatarUrl: "../assets/room/ROOM3.png",
                username: "乌云背后有蓝天",
                time: "12:00",
                status: 2,
                song: "戒烟——李荣浩",
                size: 33
            },
            {
                avatarUrl: "../assets/room/ROOM4.png",
                username: "喵星人交流音乐室",
                time: "12:00",
                status: 1,
                song: "猫的舞步",
                size: 156
            }
        ],
        chatRecords: [
            {
                text: "hello",
                ifSender: true
            },
            {
                text: "你好！",
                ifSender: false
            },
            {
                text: "晚上来不来",
                ifSender: true
            },
            {
                text: "爽唱！",
                ifSender: false
            },
            {
                text: "去哪里搞，几点",
                ifSender: true
            },
            {
                text: "6点吃完饭呗，我开房",
                ifSender: false
            },
            {
                text: "造！",
                ifSender: true
            },
            {
                text: "okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！okk！",
                ifSender: false
            },
            {
                text: "你干嘛！",
                ifSender: true
            },
        ],
        musicRoomMembers: [
            "../assets/member/MEM1.png",
            "../assets/member/MEM2.png",
            "../assets/member/MEM3.png",
            "../assets/member/MEM4.png",
            "../assets/member/MEM5.png",
        ],
        albums: [
            "../assets/album/ALBUM1.png",
            "../assets/album/ALBUM2.png"
        ],
        musicRoomRecs: [
            {
                avatarUrl: "../assets/recommend/REC1.png",
                priority: 1,
                roomName: "考研党看过来~",
                leaderName: "deidei"
            },
            {
                avatarUrl: "../assets/recommend/REC2.png",
                priority: 4,
                roomName: "一起自习一起加油！",
                leaderName: "莫益萌"
            },
            {
                avatarUrl: "../assets/recommend/REC3.png",
                priority: 2,
                roomName: "哥哥的小屋",
                leaderName: "王大卫"
            },
            {
                avatarUrl: "../assets/recommend/REC4.png",
                priority: 5,
                roomName: "春天的草丛有只羊",
                leaderName: "苏家铭"
            },
            {
                avatarUrl: "../assets/recommend/REC5.png",
                priority: 6,
                roomName: "一起K歌！",
                leaderName: "储岱泽"
            },
            {
                avatarUrl: "../assets/recommend/REC6.png",
                priority: 7,
                roomName: "二次元交流音乐室",
                leaderName: "杨滕超"
            },
            {
                avatarUrl: "../assets/recommend/REC7.png",
                priority: 1,
                roomName: "唯有爱和美食不可辜负",
                leaderName: "deidei"
            },
            {
                avatarUrl: "../assets/recommend/REC8.png",
                priority: 4,
                roomName: "兔兔有什么坏心思呢",
                leaderName: "莫益萌"
            },
            {
                avatarUrl: "../assets/recommend/REC9.png",
                priority: 2,
                roomName: "千里之行始于足下",
                leaderName: "王大卫"
            },
            {
                avatarUrl: "../assets/recommend/REC10.png",
                priority: 5,
                roomName: "失恋解救药丸",
                leaderName: "苏家铭"
            },
            {
                avatarUrl: "../assets/recommend/REC11.png",
                priority: 6,
                roomName: "二次元最大",
                leaderName: "储岱泽"
            },
            {
                avatarUrl: "../assets/recommend/REC12.png",
                priority: 7,
                roomName: "音游人基地",
                leaderName: "杨滕超"
            },
        ],
        currentUser: {
            id: 0,
            username: "",
            status: 1,
            size: 0
        },
        currentSong: {
            cover: "../assets/current.png",
            name: "戒烟",
            artist: "李荣浩",
            time: "00:20",
            total: "04:53",
            lyric: [
                "",
                "",
                "TEST1",
                "TEST2",
                "TEST3",
                "TEST4",
                "已经为了变得更好去掉锋芒",
                "一不小心成了你的倾诉对象",
                "电话约在从前约会的地方",
                "要陪你唱歌吃饭我结账",
                "保持优良习惯",
                "TEST5",
                "TEST6",
                "TEST7",
                "TEST8",
                "",
                ""
            ],
        },
        newMusicRoom: {
            name: "",
            intro: "",
            auth: "",
            avatar: ""
        }
    }),
    methods: {
        clickListItem: function(item) {
            this.currentUser.id = item.id;
            this.currentUser.username = item.username;
            this.currentUser.size = (this.menu == 'musicRooms') ? item.size : 0;
            this.currentUser.status = item.status;
            this.isChatShow = true;
        },
        getTitle: function() {
            if (this.menu == 'musicRooms')
                return this.currentUser.username + '(' + this.currentUser.size + "人)";
            else
                return this.currentUser.username;
        },
        getImgSrc: function(url) {
            return new URL(url, import.meta.url).href;
        },
        openFilePicker() {
            this.$refs.fileInput.click();
        },
        handleImgSelected: function(event) {
            const selectedFile = event.target.files[0];

            if (selectedFile.type.startsWith('image/')) {
                this.newMusicRoom.avatar = URL.createObjectURL(selectedFile);
            } else {
                alert('请选择图片文件');
            }
        },
        getCurrentFriendAvatar: function() {
            var filteredUsers = this.users.filter(user => user.id == this.currentUser.id);
            return filteredUsers[0].avatar;
        },
        getChatAvatar: function(ifSender) {
            return ifSender ? localStorage.getItem("userAvatar") : this.getCurrentFriendAvatar();
        }
    },
    mounted() {
        var that = this;

        getFriends({
            userId: parseInt(localStorage.getItem("userId"))
        }).then(res => {
            for (var i = 0; i < res.length; i++) {
                that.users.push({
                    id: res[i].friendId,
                    avatar: res[i].avatar,
                    username: res[i].name,
                    time: res[i].chatTime,
                    intimacy: res[i].intimacy,
                    status: 1,
                    song: ""
                });
            }
        }, err => {
            console.log(err);
        });
    }
}
</script>

<style scoped>
.full-height {
    min-height: 100vh;
    /* 设置最小高度为视口高度的100% */
}
.music-room-menu {
    display: inline-block;
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
.back-button {
    position: absolute;
    height: 40px;
    top: 13px;
    left: 25%;
    cursor: pointer;
}
.music-room-create-title {
    position: absolute;
    display: inline-block;
    top: 12px;
    left: 55%;
    font-weight: bold;
    font-size: 28px;
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
.chat-input-area {
    width: 100%;
}
.tool-bar {
    height: 48px;
}
.chat-input-area img {
    position: relative;
    top: 5px;
    left: 0.5%;
    height: 80%;
    padding: 0.8%;
    margin-right: 0.5%;
    border-radius: 10px;
    cursor: pointer;
    transition: 0.3s;
}
.chat-input-area img:hover {
    background-color: #DCE8E3;
}
.send-button {
    position: relative;
    top: -10px;
    left: 66%;
    background-color: #DCE8E3;
}
.chat-input {
    display: block;
    padding: 10px;
    width: 100%;
    height: 170px;
    font-size: 20px;
    color: #0000009F;
    overflow-y: auto;
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
    margin-top: 10px;
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
    cursor: pointer;
    transition: 0.3s;
}
.side-info-member:hover {
    opacity: 0.7;
}
.side-info-album {
    width: 78px;
    height: 78px;
    border-radius: 10px;
    margin-left: 4%;
    margin-top: 10px;
    cursor: pointer;
    transition: 0.3s;
}
.side-info-album:hover {
    opacity: 0.7;
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
.music-room-create-container {
    overflow-y: scroll;
    width: 76%;
    height: 80vh;
    background: linear-gradient(180deg, rgba(153, 231, 214, 0.26) 0%, rgba(216, 216, 216, 0) 100%);
}
.form-label {
    display: inline-block;
    position: relative;
    margin-left: 25%;
    font-weight: lighter;
    font-size: 23px;
    width: 20%;
    text-align: right;
    margin-right: 5%;
}
.form-input {
    margin-top: 30px;
    padding: 15px;
    padding-left: 20px;
    width: 40%;
    height: 25px;
    border: 2px solid #BFBFBF;
    border-radius: 5px;
}
.form-radio {
    position: relative;
    display: inline-block;
    top: 50px;
}
.form-avatar {
    margin-top: 50px;
    height: 100px;
    width: 100px;
    border: 2px solid black;
}
.form-avatar-img {
    margin-top: 50px;
    height: 100px;
    width: 100px;
}
.form-button {
    margin-top: 30px;
    margin-bottom: 30px;
    font-weight: 500;
    font-size: 16px;
    border-radius: 8px;
    background-color: #3C99829A;
}
.submit-button {
    position: relative;
    top: 10px;
    left: 37.5%;
    width: 25%;
    height: 58px;
    color: #FFFFFF;
    font-weight: 500;
    font-size: 28px;
    border-radius: 12px;
    background-color: #3C7C99DC;
}
.new-music-room-dialog {
    margin-left: 60%;
    width: 65%;
    height: 194px;
    border-radius: 6px;

    background: linear-gradient(180deg, #FFFEFE 0%, #C6CBCA 100%);
}
.dialog-title {
    margin-left: 5%;
    margin-top: 25px;
    font-weight: 500;
    font-size: 34px;
    color: #41504DB8;
}
.dialog-content {
    margin-left: 3%;
    font-weight: 500;
    font-size: 17px;
    color: #41504DB8;
}
.dialog-button {
    position: relative;
    top: -10px;
    left: 33.5%;
    width: 33%;
    height: 46px;
    border-radius: 12px;
    color: #FFFFFF;
    font-weight: 400;
    font-size: 24px;
    background-color: #3C7C99DC;
}
</style>