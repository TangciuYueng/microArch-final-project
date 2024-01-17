<template>
    <!-- 顶部的导航栏 -->
    <v-card>
        <v-layout>
            <!-- 侧边的导航栏 -->
            <SideBar />

            <!-- 用于写主页面的内容 -->
            <v-main class="full-height">
                首页
            </v-main>
            <music-player></music-player>
        </v-layout>
    </v-card>
</template>
  
<script>
import SideBar from '@/layouts/default/SideBar.vue'
import MusicPlayer from '@/components/MusicPlayer.vue'
// import DefaultView from '@/layouts/default/View.vue'
export default {
    //导出组件
    components: {
        SideBar,
        MusicPlayer
    },
    data: () => ({
        form: false,
        userAccount: null,
        password: null,
        loading: false,
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
</style>ƒ