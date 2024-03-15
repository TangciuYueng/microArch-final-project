<template>
    <v-main class="full-height">
        <div class="content">
            <label>123</label>
        </div>
        <img :src="getImgSrc('../assets/music-playing.png')" class="background">
        
    </v-main>
</template>

<script>
export default {
    data: () => ({
        
    }),
    methods: {
        getImgSrc: function(url) {
            return new URL(url, import.meta.url).href;
        },
    }
}
</script>

<style scoped>
.full-height {
    min-height: 100vh;
    /* 设置最小高度为视口高度的100% */
}
.background {
    position: fixed;
    height: 83vh;
    width: 100%;
    z-index: 1;
}
.content {
    position: relative;
    height: 83vh;
    z-index: 2;
}
</style>