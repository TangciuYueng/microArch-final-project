<template>
  <v-main class="full-height">
    <v-container fluid>
      <v-row
        style="height: 50%; text-align: center"
        :style="{
          'background-image': 'url(' + backgroundImg + ')',
          'background-size': 'cover',
        }"
      >
        <v-col>
          <v-row>
            <v-col>
              <v-avatar size="90">
                <v-img alt="John" :src="avatarImg"></v-img>
              </v-avatar>
            </v-col>
          </v-row>
          <v-row style="margin-top: 0%">
            <v-col>
              <div class="user-name">{{ userName }}</div>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <div class="ip-location">ip属地：{{ ipLocation }}</div>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-btn class="btn" x-large>关注</v-btn>
              <v-btn class="btn" x-large>写留言</v-btn>
            </v-col>
          </v-row>

          <div class="variable-container info-word">
            <div class="variable-item">
              <div class="variable-name">留言</div>
              <div class="variable-value">{{ messageCount }}</div>
            </div>
            <div class="variable-item">
              <div class="variable-name">好友</div>
              <div class="variable-value">{{ friendCount }}</div>
            </div>
            <div class="variable-item">
              <div class="variable-name">心情</div>
              <div class="variable-value">{{ moodValue }}</div>
            </div>
            <div class="variable-item">
              <div class="variable-name">访客</div>
              <div class="variable-value">{{ visitorCount }}</div>
            </div>
            <div class="variable-item">
              <div class="variable-name">日记</div>
              <div class="variable-value">{{ diaryCount }}</div>
            </div>
            <div class="variable-item">
              <div class="variable-name">歌单</div>
              <div class="variable-value">{{ playlistCount }}</div>
            </div>
          </div>
        </v-col>
      </v-row>
      <!-- 一周心情和歌单 -->
      <v-row style="height: 50%;">
        <v-col cols="5">
          <v-card
            style="
              border-radius: 10px 10px 0px 0px;
              opacity: 1;
              background: #f0f0f0;
            "
          >
            <v-card-title>TA的心情</v-card-title>
            <v-card-text>
              <emotions-card style="margin: 0% 3% 2% 3%"></emotions-card>
            </v-card-text>
          </v-card>
        </v-col>
        <v-col cols="7">
          <v-card
            style="
              border-radius: 10px 10px 0px 0px;
              opacity: 1;
              background: #f0f0f0;
              height: 100%;
            "
          >
            <v-card-title>TA的歌单</v-card-title>
            <v-row style="margin: 0% 2% 0% 2%;">
          <v-col
            v-for="song in playlists"
            :key="song.title"
            >
            <v-card >
                <v-img
                :src="song.cover"
                class="align-end"
                gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                height="270px"
                
                
                cover
                >
                <!-- 名字和播放按钮 -->
                <div class="d-flex">
                    <v-card-title class="text-white max-text-length" v-text="song.title" style="font-size:x-large;"></v-card-title>
                    <v-btn
                        class="ms-2"
                        icon="mdi-play-circle-outline"
                        variant="text"
                        style="font-size: 30px;color: white;"
                    ></v-btn>
                </div>
                
                </v-img>

                
            </v-card>
            </v-col>
        </v-row>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
import EmotionsCard from "@/components/EmotionsCard.vue";

export default {
  components: {
    EmotionsCard,
  },
  //导出组件
  data: () => ({
    backgroundImg: "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
    avatarImg: "https://cdn.vuetifyjs.com/images/john.jpg",
    userName: "S+M",
    ipLocation: "上海",
    messageCount: 0,
    friendCount: 0,
    moodValue: 0,
    visitorCount: 0,
    diaryCount: 0,
    playlistCount: 0,
    playlists:[
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
      
    ]
  }),
  methods: {},
};
</script>

<style scoped>
.centered-row {
  display: flex;
  justify-content: center;
  align-items: center;
}
.variable-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 2% 0% 2% 0%;
}

.variable-item {
  margin-right: 5%;
  margin-left: 5%;
}

.variable-name {
}

.variable-value {
  margin-top: 60%;
}

.user-name {
  font-family: "Franklin Gothic Medium", "Arial Narrow", Arial, sans-serif;
  font-size: 28px;
  font-weight: normal;
  line-height: 38px;
  letter-spacing: 0px;

  color: #ffffff;
}
.ip-location {
  font-size: 15px;
  font-weight: normal;
  line-height: 34px;
  letter-spacing: 0px;

  color: #ffffff;
}

.info-word {
  font-family: PingFang SC;
  font-size: 25px;
  font-weight: normal;
  line-height: 17px;
  letter-spacing: 0em;

  color: #ffffff;
}

.btn {
  font-family: 思源黑体;
  font-size: 20px;
  font-weight: bold;
  line-height: normal;
  letter-spacing: 0px;

  color: #ffffff;
  background: #6ec4ae;
  padding: 0.5% 2% 2.1% 2%;

  text-align: center;
  margin: 0% 2% 1% 2%;
}
</style>