<template>
    <v-app>
        <v-container fluid>
            <v-app-bar
                style="background-color: rgba(17, 141, 110, 0.55); text-align: center; font-size: xx-large; color: white;">
                <v-toolbar-title>记录生活</v-toolbar-title>
            </v-app-bar>
            <v-main>

                <v-row>
                    <v-col cols="3" style="height: 800px; box-shadow: 5px 0 0 rgba(0, 0, 0, 0.1);">
                        <div style="font-size: 36px; text-align: center; margin-bottom: 5px;">
                            素材库
                        </div>

                        <div class="tabs">
                            <div v-for="(tab, index) in tabs" :key="index"
                                :class="{ 'tab': true, 'active': activeTab === index }" @click="handleActiveTab(index)">
                                {{ tab }}
                            </div>
                        </div>
                    </v-col>

                    <v-col cols="9" style="">
                        <!-- <trumbowyg v-model="content" ></trumbowyg> -->
                    </v-col>
                </v-row>
            </v-main>
        </v-container>
    </v-app>
</template>

<script>
// import Trumbowyg from 'vue-trumbowyg';
// import 'trumbowyg/dist/ui/trumbowyg.css';

export default {
    components: {
        // Trumbowyg
    },
    //导出组件
    data: () => ({
        tabs: ['图片', '音乐',],
        activeTab: 0,

        content: null,
    }),
    methods: {
        handleActiveTab(index) {
            this.activeTab = index;
            this.selectedList = this.totalList[index];
        }
    },
}
</script>

<style scoped>
.tabs {
    border-bottom: rgba(60, 153, 130, 0.6) solid 5px;
}

.tab {
    display: inline-block;
    padding: 10px 20px;
    cursor: pointer;
    margin-right: 5px;
    border-top-left-radius: 20px;
    border-top-right-radius: 20px;
}

.active {
    background-color: rgba(60, 153, 130, 0.6);
    /* 选中的背景颜色 */
    color: white;
    /* 选中的字体颜色 */
}

.tab:hover:not(.active) {
    background-color: rgba(84, 161, 142, 0.4);
    /* 悬停时的背景颜色 */
    color: white;
    /* 悬停时的字体颜色 */
}
</style>