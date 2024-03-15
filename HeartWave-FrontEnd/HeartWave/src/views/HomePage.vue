<template>
  <!-- 用于写主页面的内容 -->
  <v-main class="full-height">
    <v-container fluid>
      <v-row style="min-height: 88vh">
        <!-- 第一列，宽度为第二列的3倍，背景颜色为白色 -->
        <v-col cols="8" class="white-background scrollable-column">
          <v-row style="min-height: 10%">
            <v-responsive max-width="350" max-height="50">
              <v-spacer></v-spacer>
              <v-text-field
                clearable
                label="请输入搜索关键词"
                prepend-icon="mdi-magnify"
              ></v-text-field
            ></v-responsive>
          </v-row>
          <v-spacer></v-spacer>
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
                  <friend-card :friends="friends"></friend-card>
                </v-col>
              </v-row>
            </v-col>
            <v-col cols="8">
              <tips-card :music="recommendMusic"></tips-card>
            </v-col>
          </v-row>

          <!-- 第二行，推荐的歌单 -->
          <v-row style="min-height: 25%" class="">
            <v-col cols="12"
              ><recommend-playlists-card
                :playlists="recommendPlaylists"
              ></recommend-playlists-card
            ></v-col>
          </v-row>

          <!-- 第三行推荐的音乐室 -->
          <v-row style="min-height: 25%" class="">
            <v-col>
              <v-card
                style="
                  background-color: rgba(107, 196, 174, 0.3098);
                  height: 150px;
                "
                >推荐的音乐室</v-card
              >
            </v-col>
          </v-row>
        </v-col>
        <!-- 第二列，背景颜色为绿色 -->
        <v-col cols="4" class="green-background scrollable-column">
          <!-- 这里放置第二列的内容 -->
          <profile-card
            :profileImgSrc="profileImgSrc"
            :avatarSrc="avatarSrc"
            :username="username"
            :ipLocation="ipLocation"
          ></profile-card>
          <emotions-card></emotions-card>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>
  
<script>
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
    friends: [
      {
        id: 1,
        avatar: "https://cdn.vuetifyjs.com/images/john.png",
        mood: 123,
        music: "歌曲1",
      },
      {
        id: 2,
        avatar: "https://cdn.vuetifyjs.com/images/john.png",
        mood: 34,
        music: "歌曲2",
      },
      {
        id: 3,
        avatar: "https://cdn.vuetifyjs.com/images/john.png",
        mood: 345,
        music: "歌曲4",
      },
      // Add more friends as needed
    ],
    recommendMusic: [
      {
        id: 1,
        cover: "https://cdn.vuetifyjs.com/images/cards/docks.jpg",
        title: "love story",
      },
      {
        id: 2,
        cover: "https://cdn.vuetifyjs.com/images/cards/foster.jpg",
        title: "时间煮雨",
      },
      {
        id: 3,
        cover: "https://cdn.vuetifyjs.com/images/cards/road.jpg",
        title: "陌路飞雪",
      },
      {
        id: 4,
        cover: "https://cdn.vuetifyjs.com/images/cards/halcyon.png",
        title: "S+M",
      },
    ],
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
    avatarSrc: "https://cdn.vuetifyjs.com/images/profiles/marcus.jpg",
    username: "S+M",
    ipLocation: "New York",
  }),
  methods: {},
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
</style>