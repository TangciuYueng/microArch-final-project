<template>
    <!-- 顶部的导航栏 -->
    <DefaultBar />
    <v-container fluid>
        <v-row>
            <v-col v-for="day in days" :key="day" class="text-center">
                {{ day }}
            </v-col>
        </v-row>
        <v-divider></v-divider>
        <v-row v-for="week in weeks" :key="week">
            <v-col v-for="day in days" :key="day" class="text-center">
                {{ week * 7 + day - startDay }}
            </v-col>
        </v-row>
    </v-container>
</template>
  
<script>
import DefaultBar from '@/layouts/default/AppBar.vue'
// import DefaultView from '@/layouts/default/View.vue'
export default {
    //导出组件
    components: {
        DefaultBar
    },
    data: () => ({
        form: false,
        userAccount: null,
        password: null,
        loading: false,
        days: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
        startDay: 3, // Wednesday
        numWeeks: 5,
    }),
    computed: {
        weeks() {
            return Array.from({ length: this.numWeeks }, (_, i) => i);
        },
    },
    methods: {

    },
}



</script>
  