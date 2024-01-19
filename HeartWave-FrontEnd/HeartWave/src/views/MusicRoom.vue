<template>
    <!-- 顶部的导航栏 -->
    <!-- 用于写主页面的内容 -->
    <v-main class="full-height">
        音乐室
    </v-main>
</template>
  
<script>
export default {
    //导出组件
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
</style>