<template>
    <v-app>
        <v-container fluid>
            <v-row>
                <v-col cols="10">
                    <v-row style="text-align: center; background-color: rgba(17, 141, 110, 0.55); height: 8vh;">
                        <v-col cols="12">
                            <v-icon size="xx-large" color="white" class="mb-3" style="cursor: pointer;">mdi-menu-left-outline</v-icon>
                            <span style="color: white; font-size: xx-large;">{{ currentYear }}</span>
                            <v-icon size="xx-large" color="white" class="mb-3" style="cursor: pointer;">mdi-menu-right-outline</v-icon>
                        </v-col>
                    </v-row>
                    <v-row justify="space-around" align="center"
                        style="background-color: rgba(17, 141, 110, 0.55); color: white; font-size: large; font-weight: bold; height: 6vh;">
                        <v-col v-for="(day, index) in weekDays" :key="index" cols="auto">
                            {{ day }}
                        </v-col>
                    </v-row>
                </v-col>

                <v-col cols="2" style="margin: 0; padding: 0;">
                    <div style="background-color: rgba(17, 141, 110, 0.55); height: 14vh; padding: 0;"></div>
                    <div style="text-align: center; padding-left: 10px; box-shadow: -5px 0 0 rgba(0, 0, 0, 0.1); height: 86vh;">
                        <v-btn size="x-large" color="#99E7D6" style="margin-top: 5vh; ">
                            记录生活
                        </v-btn>
                        <div class="mt-10" style="font-size: large;">
                            这些好友最近更新了随笔，快去ta的主页看看吧～
                        </div>
                        <div v-for="friend in updatedFriends" class="mt-5" style="text-align: left;">
                            <v-row dense>
                                <v-col cols="4">
                                    <v-avatar size="60">
                                        <img :src="friend.avatarSrc" alt="avatar" />
                                    </v-avatar>
                                </v-col>
                                <v-col cols="8">
                                    <div>{{ friend.username }}</div>
                                    <div>
                                        <v-icon x-large color="red">mdi-emoticon-happy</v-icon>
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
        weekDays: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],

        updatedFriends: [
            { avatarSrc: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'sjm大帅哥', emotionValue: 55 },
            { avatarSrc: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'sjm大帅哥', emotionValue: 55 },
            { avatarSrc: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'sjm大帅哥', emotionValue: 55 },
            { avatarSrc: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'sjm大帅哥', emotionValue: 55 },
            { avatarSrc: 'https://cdn.vuetifyjs.com/images/parallax/material.jpg', username: 'sjm大帅哥', emotionValue: 55 },
        ]
    }),
    methods: {
    },
}
</script>

<style scoped>
.full-height {
    min-height: 100vh;
    /* 设置最小高度为视口高度的100% */
}
</style>