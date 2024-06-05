<template>
  <v-app>
    <v-container fluid>
      <v-row>
        <v-col cols="10">
          <v-row style="text-align: center; background-color: rgba(17, 141, 110, 0.55); height: 8vh;">
            <v-col cols="12">
              <v-icon
                size="xx-large"
                color="white"
                class="mb-3"
                style="cursor: pointer;"
                @click="yearDecrement"
              >
                mdi-menu-left-outline
              </v-icon>
              <span style="color: white; font-size: xx-large;">{{ currentYear }}</span>
              <v-icon
                size="xx-large"
                color="white"
                class="mb-3"
                style="cursor: pointer;"
                @click="yearIncrement"
              >
                mdi-menu-right-outline
              </v-icon>
            </v-col>
          </v-row>
          <v-row
            justify="space-around"
            align="center"
            style="background-color: rgba(17, 141, 110, 0.55); color: white; font-size: large; font-weight: bold; height: 6vh;"
          >
            <v-col v-for="(day, index) in weekDays" :key="index" cols="auto">
              {{ day }}
            </v-col>
          </v-row>

          <v-row>
            <v-col
              cols="12"
              style="text-align: center; color: black; font-size: xx-large; font-weight: bolder;"
            >
              <v-icon
                xx-large
                color="black"
                class="mb-1"
                style="cursor: pointer;"
                @click="monthDecrement"
              >
                mdi-menu-left-outline
              </v-icon>
              {{ monthEnglish[month] }}
              <v-icon
                xx-large
                color="black"
                class="mb-1"
                style="cursor: pointer;"
                @click="monthIncrement"
              >
                mdi-menu-right-outline
              </v-icon>
            </v-col>
          </v-row>

          <v-row v-for="week in weeksInMonth" :key="week" class="d-flex justify-center">
            <div v-for="(day, index) in getDaysInWeek(week)" :key="index" style="width: 14%;">
              <!-- <v-card v-if="day !== null" class="text-center" hover -->
              <!-- style="height: 100px; margin-top: 5px; margin-left: 3px; margin-right: 3px;"> -->
              <v-img
                v-if="day !== null"
                src="https://cdn.vuetifyjs.com/images/parallax/material.jpg"
                cover
                class="diary-date"
                @click="dialog = true"
              >
                <div style="font-size: xx-large; font-weight: 700; color: rgba(255, 255, 255, 0.9);">
                  {{ day }}
                </div>
              </v-img>

              <!-- </v-card> -->
            </div>
          </v-row>
        </v-col>

        <v-col cols="2" style="margin: 0; padding: 0;">
          <div style="background-color: rgba(17, 141, 110, 0.55); height: 14vh; padding: 0;" />
          <div
            style="text-align: center; padding-left: 10px; box-shadow: -5px 0 0 rgba(0, 0, 0, 0.1); height: 86vh;"
          >
            <v-btn size="x-large" color="#99E7D6" style="margin-top: 5vh;" @click="$emit('addDiaryEvent')">
              记录生活
            </v-btn>
            <div class="mt-10" style="font-size: large;">
              这些好友最近更新了随笔，快去ta的主页看看吧～
            </div>
            <div v-for="friend in updatedFriends" class="mt-5" style="text-align: left;">
              <v-row dense style="cursor: pointer;" @click="$emit('BrowsingPersonalHomepage', friend.id)">
                <v-col cols="4">
                  <v-avatar size="60">
                    <img :src="friend.avatarSrc" alt="avatar">
                  </v-avatar>
                </v-col>
                <v-col cols="8">
                  <div>{{ friend.username }}</div>
                  <div>
                    <v-icon x-large color="red">
                      mdi-emoticon-happy
                    </v-icon>
                    {{ friend.emotionValue }}
                  </div>
                </v-col>
              </v-row>
            </div>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
  <v-dialog v-model="dialog" max-width="600">
    <template #activator="{ on }" />

    <v-card>
      <v-card-title>心情随笔</v-card-title>
      <v-card-text>
        <p>今天的心情真好！😊</p>
      </v-card-text>
      <v-card-actions>
        <v-btn color="red" text @click="dialog = false">
          关闭
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>

export default {
    //导出组件
    data: () => ({
        form: false,
        userAccount: null,
        password: null,
        loading: false,

        currentYear: new Date().getFullYear(),
        weekDays: [
 'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat' 
],
        month: new Date().getMonth(),

        updatedFriends: [
            { id: 1, avatarSrc: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'sjm大帅哥', emotionValue: 55 },
            { id: 2, avatarSrc: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'sjm大帅哥', emotionValue: 55 },
            { id: 3, avatarSrc: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'sjm大帅哥', emotionValue: 55 },
            { id: 4, avatarSrc: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'sjm大帅哥', emotionValue: 55 },
            { id: 5, avatarSrc: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'sjm大帅哥', emotionValue: 55 },
        ],
        monthEnglish: [
            'Jan', // January
            'Feb', // February
            'Mar', // March
            'Apr', // April
            'May', // May
            'Jun', // June
            'Jul', // July
            'Aug', // August
            'Sep', // September
            'Oct', // October
            'Nov', // November
            'Dec' // December
        ],
        weeksInMonth: null,

        dialog: false,
        diaryContent: '今天的心情真好！😊\n分享一下今天的快乐！🎉\n希望明天也能继续保持好心情！🌈',
    }),
    mounted() {
        this.weeksInMonth = this.getWeeksInMonth()
    },
    methods: {
        getDaysInWeek(week) {
            const firstDay = new Date(this.currentYear, this.month, 1).getDay();
            const totalDays = new Date(this.currentYear, this.month + 1, 0).getDate();

            const start = 7 * week - firstDay + 1;
            const end = start + 7;

            const days = [];
            for (let day = start; day < end; day++) {
                if (day > 0 && day <= totalDays) {
                    days.push(day);
                } else {
                    days.push(null);
                }
            }

            return days;
        },
        getWeeksInMonth() {
            const firstDay = new Date(this.currentYear, this.month, 1).getDay();
            const totalDays = new Date(this.currentYear, this.month + 1, 0).getDate();
            const weeks = Math.ceil((totalDays + firstDay) / 7);
            return Array.from({ length: weeks }, (v, i) => i);
        },
        yearDecrement() {
            this.currentYear -= 1;
            this.weeksInMonth = this.getWeeksInMonth();
        },
        yearIncrement() {
            this.currentYear += 1;
            this.weeksInMonth = this.getWeeksInMonth();
        },
        monthDecrement() {
            this.month -= 1;
            if (this.month < 0) {
                this.month = 11;
                this.currentYear -= 1;
            }
            this.weeksInMonth = this.getWeeksInMonth()
        },
        monthIncrement() {
            this.month += 1;
            if (this.month > 11) {
                this.month = 0;
                this.currentYear += 1;
            }
            this.weeksInMonth = this.getWeeksInMonth()
        },
    },
}
</script>

<style scoped>
.diary-date {
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100px;
    margin-top: 5px;
    margin-left: 3px;
    margin-right: 3px;
    cursor: pointer;
    transition: box-shadow 0.3s;
}

.diary-date:hover {
    box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.3);
}
</style>