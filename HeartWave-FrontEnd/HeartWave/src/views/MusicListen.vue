<!-- eslint-disable vue/multi-word-component-names -->
<template>
    <div>
        <div>
            <DefaultBar />
            音乐收听
        </div>
        <!-- 把所有音乐展示 -->
        <v-card flat>
            <v-card-title class="d-flex align-center pe-2">
            <v-icon icon="mdi-music"></v-icon> &nbsp;
            MUSIC
            <v-spacer></v-spacer>

            <v-text-field
                v-model="search"
                prepend-inner-icon="mdi-magnify"
                density="compact"
                label="搜索音乐"
                single-line
                flat
                hide-details
                variant="solo-filled"
            ></v-text-field>
            </v-card-title>

            <v-divider></v-divider>
            {{ items }}
            {{ musicCount }}
            
        </v-card>
        <v-btn @click="playMusicById">playMusicById 后端</v-btn>
        <v-btn @click="getAllMusic">获取所有音乐</v-btn>
    </div>
    
</template>
<script>
// import HelloWorld from '@/components/HelloWorld.vue'
import DefaultBar from '@/layouts/default/AppBar.vue'
import axios from 'axios'
export default {
    
    data: () => ({
        search:"",
        musicCount: null,
        headers: [
            {
                text: 'Dessert (100g serving)', //表头显示的文本
                align: 'start', // 文本对齐方向
                sortable: false, //控制切换排序的功能
                value: 'name', //对应的值
            },
            { text: 'Calories', value: 'calories' },
            { text: 'Fat (g)', value: 'fat' },
            { text: 'Carbs (g)', value: 'carbs' },
        ],
        items: [
        {
            name: 'Frozen Yogurt',
            calories: 159,
            fat: 6.0,
            carbs: 24,
        },
        {
            name: 'KitKat',
            calories: 518,
            fat: 26.0,
            carbs: 65,
        }],

    }),
    components:{
        DefaultBar,
    },
    mounted() {

    },
    methods: {
        playMusicById(){
            try {
				axios.get(`http://localhost:8888/api/music_listen/music/play/1`)
				.then((response) => {
					// 请求成功时的处理
					console.log(response)
				})
				.catch((error) => {
				// 请求失败时的处理
				console.error('Error:', error);
				});
			} catch (error) {
				this.error = error;
			}
        },
        getAllMusic(){
            try {
				axios.get(`http://localhost:8888/api/music_listen/music/by_page/2`)
				.then((response) => {
					// 请求成功时的处理
					console.log(response)
				})
				.catch((error) => {
				// 请求失败时的处理
				console.error('Error:', error);
				});
			} catch (error) {
				this.error = error;
			}
        },
        playMusicById2(){

        }
    },
    computed: {
      display () {
        return this.$vuetify.display
      },
    },

}
</script>

<style>
</style>