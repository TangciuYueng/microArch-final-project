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
                1
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
            const date = new Date();
            const numWeeks = 52; // 假设一年有 52 周
            const weeks = [];

            for (let i = 0; i < numWeeks; i++) {
                const weekStart = new Date(date.getFullYear(), 0, 1).getTime() + i * 7 * 24 * 60 * 60 * 1000;
                const weekEnd = weekStart + 6 * 24 * 60 * 60 * 1000;
                weeks.push({ start: new Date(weekStart), end: new Date(weekEnd) });
            }

            return weeks;
        },
    },
    methods: {

    },
}



</script>
  