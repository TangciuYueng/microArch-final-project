<template>
  <!-- 用于写主页面的内容 -->
  <v-main class="full-height">
    <v-container fluid>
      <v-row style="min-height: 88vh">
        <!-- 第一列，宽度为第二列的3倍，背景颜色为白色 -->
        <v-col cols="8" class="white-background scrollable-column">
          <v-row style="min-height: 10%">
            <v-responsive max-width="350" max-height="50">
              <v-spacer />
              <v-text-field
                clearable
                label="请输入搜索关键词"
                prepend-icon="mdi-magnify"
              />
            </v-responsive>
          </v-row>
          <v-spacer />
          <!-- 第一行，显示今日天气、天气小贴士、以及需要关心的朋友 -->
          <v-row style="min-height: 40%">
            <v-col cols="4">
              <v-row>
                <v-col>
                  <weather-card>123</weather-card>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <friend-card :friends="friends" />
                </v-col>
              </v-row>
            </v-col>
            <v-col cols="8">
              <tips-card :music="recommendMusic" />
            </v-col>
          </v-row>

          <!-- 第二行，推荐的歌单 -->
          <v-row style="min-height: 25%" class="">
            <v-col cols="12">
              <recommend-playlists-card
                :playlists="recommendPlaylists"
              />
            </v-col>
          </v-row>

          <!-- 第三行推荐的音乐室 -->
        </v-col>
        <!-- 第二列，背景颜色为绿色 -->
        <v-col cols="4" class="green-background scrollable-column">
          <!-- 这里放置第二列的内容 -->
          <profile-card
            :profile-img-src="profileImgSrc"
            :avatar-src="'data:image/jpg;base64,' + avatarSrc"
            :username="username"
            :ip-location="ipLocation"
            @browsing-personal-homepage="preHandleBrowsingEvent"
          />
          <emotions-card class="mt-5" />
          <span class="text-title">您近一周的心情主基调为：</span>
          <div class="text-mood">
            快乐
          </div>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>
  
<script>
import { user } from '../main.js';
import WeatherCard from "@/components/WeatherCard.vue";
import FriendCard from "@/components/FriendCard.vue";
import TipsCard from "@/components/TipsCard.vue";
import RecommendPlaylistsCard from "@/components/RecommendPlaylistsCard.vue";
import RecommendMusicRoomCard from "@/components/RecommendMusicRoomCard.vue";
import ProfileCard from "@/components/ProfileCard.vue";
import EmotionsCard from "@/components/EmotionsCard.vue";

export default {
  components: {
    WeatherCard,
    FriendCard,
    TipsCard,
    RecommendPlaylistsCard,
    RecommendMusicRoomCard,
    ProfileCard,
    EmotionsCard
  },
  //导出组件
  data: () => ({
    form: false,
    userAccount: null,
    password: null,
    loading: false,
    testWord: "test",
    friends: [ {
        id: 1,
        avatar: "https://cdn.vuetifyjs.com/images/john.png",
        mood: 84,
        music: "东方红",
      },
      {
        id: 2,
        avatar: "https://q4.itc.cn/q_70/images03/20240405/39ec09deda3a41d79e03897b0fdf68a0.jpeg",
        mood: 34,
        music: "夜空...",
      },
      {
        id: 3,
        avatar: "https://q7.itc.cn/q_70/images03/20240423/6d236fae5c8f44ed9b60d977f32debb7.jpeg",
        mood: 55,
        music: "Lover",
      },
      // Add more friends as needed
    ],
    recommendMusic: [ {
        id: 1,
        cover: "https://cdn.vuetifyjs.com/images/cards/docks.jpg",
        title: "向云端",
      },
      {
        id: 2,
        cover: "https://cdn.vuetifyjs.com/images/cards/foster.jpg",
        title: "IF YOU",
      },
      {
        id: 3,
        cover: "https://cdn.vuetifyjs.com/images/cards/road.jpg",
        title: "盛夏的果实",
      },
      {
        id: 4,
        cover: "https://cdn.vuetifyjs.com/images/cards/halcyon.png",
        title: "love story",
      }, ],
    recommendPlaylists: [
      {
        id: 1,
        cover: "https://cdn.vuetifyjs.com/images/cards/plane.jpg",
        title: "love story歌单",
      },
      {
        id: 2,
        cover: "https://cdn.vuetifyjs.com/images/cards/docks.jpg",
        title: "时间煮雨鸽蛋",
      },
      {
        id: 3,
        cover: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
        title: "陌路飞雪葛丹",
      },
      {
        id: 4,
        cover: "https://cdn.vuetifyjs.com/docs/images/cards/purple-flowers.jpg",
        title: "S+M个蛋",
      },
      {
        id: 5,
        cover: "https://cdn.vuetifyjs.com/images/cards/house.jpg",
        title: "歌单5",
      },
    ],
    profileImgSrc: "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
    avatarSrc: "",
    username: "",
    userId: null,
    ipLocation: "New York",
    
  }),
  mounted() {
    this.avatarSrc = user.avatar;
    this.userId = user.id;
    this.username = user.name;
  },
  methods: {
    preHandleBrowsingEvent(username){
            // console.log(this.userId);
            this.username = username;
            console.log("username:" , this.username);
            // 通过username找userId,待完成
            this.$emit('BrowsingPersonalHomepage', this.userId);
        },
  }
};
</script>

<style scoped>
.full-height {
  min-height: 100vh;
  /* 设置最小高度为视口高度的100% */
}
.custom-card {
  justify-content: space-between;
  height: 160px;
}
.white-background {
  background: linear-gradient(to bottom, rgba(232, 249, 245, 1), white);
}

.green-background {
  background-color: #d5f0ea;
}

.scrollable-column {
  overflow-y: auto;
  /* 在垂直方向上溢出时显示垂直滚动条 */
  max-height: 88vh;
  /* 设置最大高度为88vh，超出部分将显示滚动条 */
}

.text-title{
    
    font-size: 18px;
    font-weight: normal;
    line-height: 19.3px;
    letter-spacing: 1.93px;

    color: #3D3D3D;
}
.text-mood{
    opacity: 1;

    font-family: HelloFont WenYiHei;
    font-size: 30px;
    font-weight: normal;
    line-height: 19.3px;
    letter-spacing: 1.93px;

    color: #105645;

    margin-top: 5%;
    text-align: center; /* 文字水平居中 */
}
</style>