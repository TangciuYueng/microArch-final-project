<template>
  <!-- 顶部的导航栏 -->
  <!-- <DefaultBar /> -->
  个人中心
</template>
  
<script>
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
    }),
    methods: {

    },
}
</script>

