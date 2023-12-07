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

            
    
        </v-card>
        <!--  -->
    </div>
    
</template>
<script>
// import HelloWorld from '@/components/HelloWorld.vue'
import DefaultBar from '@/layouts/default/AppBar.vue'
export default {
    
    data: () => ({

    }),
    components:{
        DefaultBar,
    },
    mounted() {

    },
    methods: {
        
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