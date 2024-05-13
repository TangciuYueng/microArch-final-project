<template>
  <v-app>
    <v-container fluid>
      <v-app-bar
        style="background-color: rgba(17, 141, 110, 0.55); text-align: center; font-size: xx-large; color: white;"
      >
        <v-toolbar-title>记录生活</v-toolbar-title>
      </v-app-bar>
      <v-main>
        <v-row>
          <v-col cols="3" style="height: 800px; box-shadow: 5px 0 0 rgba(0, 0, 0, 0.1);">
            <div style="font-size: 36px; text-align: center; margin-bottom: 5px;">
              素材库
            </div>

            <div class="tabs">
              <div
                v-for="(tab, index) in tabs"
                :key="index"
                :class="{ 'tab': true, 'active': activeTab === index }"
                @click="handleActiveTab(index)"
              >
                {{ tab }}
              </div>
            </div>
            <!-- <v-container v-if="activeTab === 0" style="height: 700px; overflow-y: auto;">
                            <v-row>
                                <template v-for="(image, imgIdx) in imageLayout" :key="imgIdx">
                                    <v-col :cols="image.cols">
                                        <v-img :src="`https://picsum.photos/500/300?image=${image.cols * 20}`"
                                            height="100%" cover></v-img>
                                    </v-col>

                                    <v-col v-if="image.children" class="d-flex flex-column" cols="6">
                                        <v-row>
                                            <v-col v-for="(children, childIdx) in image.children" :key="childIdx"
                                                :cols="children.cols">
                                                <v-img
                                                    :src="`https://picsum.photos/500/300?image=${children.cols + childIdx}`"
                                                    height="100%" cover></v-img>
                                            </v-col>
                                        </v-row>
                                    </v-col>
                                </template>
</v-row>
</v-container> -->
            <v-container v-if="activeTab === 0" style="height: 700px; overflow-y: auto;">
              <v-data-table-virtual
                show-select
                select-strategy="single"
                :items="items"
                :search="search"
                :items-per-page="8"
                item-key="id"
                :headers="headers"
              >
                <template #top>
                  <v-toolbar flat color="white">
                    <v-toolbar-title>歌曲</v-toolbar-title>
                    <v-spacer />
                    <v-text-field
                      v-model="search"
                      label="搜索"
                      single-line
                      rounded
                      hide-details
                    />
                    <v-icon class="ml-3" style="cursor: pointer;">
                      mdi-magnify
                    </v-icon>
                  </v-toolbar>
                </template>
                <template #headers="{ columns, isSorted, getSortIcon, toggleSort }">
                  <tr>
                    <template v-for="column in columns" :key="column.key">
                      <td>
                        <span class="table-header" @click="() => toggleSort(column)">{{
                          column.title }}</span>
                        <template v-if="isSorted(column)">
                          <v-icon :icon="getSortIcon(column)" />
                        </template>
                      </td>
                    </template>
                  </tr>
                </template>
                <template #no-data>
                  <div>暂无数据~</div>
                </template>
              </v-data-table-virtual>
            </v-container>
          </v-col>

          <v-col cols="9">
            <v-row>
              <v-col cols="2" />
              <v-col cols="2">
                <!-- <v-img src="@/assets/imgs/defaultDiaryCover.svg" aspect-ratio="1" height="120"
                                    width="120" class="ml-5 mt-3">
                                </v-img> -->
                <button
                  v-if="diaryCover == ''"
                  type="button"
                  class="form-avatar"
                  @click="openFilePicker()"
                >
                  选择封面
                </button>
                <img v-else :src="diaryCover" class="form-avatar-img" @click="diaryCover = ''">
                <input ref="fileInput" type="file" style="display: none;" @change="handleImgSelected">
              </v-col>
              <v-col cols="6">
                <v-row>
                  <v-col cols="12" style="display: flex; align-items: center;">
                    <span style="font-size: 36px;" class="mr-5 mb-3">
                      标题
                    </span>
                    <v-text-field
                      v-model="diaryTitle"
                      variant="underlined"
                      label="请输入日记的标题"
                      outlined
                    />
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="12" class="d-flex align-center">
                    <v-radio-group v-model="selectedItem" inline>
                      <v-radio label="仅自己可见" value="0" />
                      <v-radio label="所有人可见" value="1" />
                      <v-radio label="指定好友可见" value="2" />
                    </v-radio-group>
                    <v-spacer />
                    <v-btn
                      color="rgba(60, 153, 130, 0.6)"
                      style="font-weight: 500;"
                      class="mb-5"
                      @click="handleAddDiary"
                    >
                      保存
                    </v-btn>
                  </v-col>
                </v-row>
              </v-col>
              <v-col cols="2" />
            </v-row>


            <v-row style="text-align: center;">
              <v-col cols="1" />
              <v-col cols="10">
                <vue3-tinymce v-model="content" :setting="setting" />
              </v-col>
              <v-col cols="1" />
            </v-row>
          </v-col>
        </v-row>
      </v-main>
    </v-container>
  </v-app>

  <v-dialog v-model="dialog" max-width="600">
    <v-card>
      <v-card-title>
        <span>随笔</span>
      </v-card-title>
      <v-card-text>
        随笔保存成功
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" text @click="handleConfirmAddDiary">
          确认
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import Vue3Tinymce from '@jsdawn/vue3-tinymce';

export default {
    components: {
        Vue3Tinymce,
    },
    //导出组件
    data: () => ({
        tabs: [ '音乐', ],
        activeTab: 0,

        search: '',

        headers: [ { title: '歌曲名称', value: 'name', }, ],
        items: [
            { id: 1, name: '最冷一天' },
            { id: 2, name: '行かないで' },
            { id: 3, name: '愛の雫' },
            { id: 4, name: '桃色吐息' },
            { id: 5, name: 'アイドル' },
            { id: 6, name: '銀の龍の背に乗って' },
            { id: 7, name: '北酒場' },
            { id: 8, name: '星空のディスタンス' },
            { id: 9, name: 'キミがいれば' },
            { id: 10, name: 'キンク' },
            { id: 11, name: '水色' },
        ],

        diaryTitle: '',
        diaryCover: '',
        selectedItem: '',

        content: '',

        defaultCover: '',

        setting: {
            language: 'zh-Hans',
            language_url: '/langs/zh-Hans.js',
            height: '550',

            toolbar:
                'undo redo | fullscreen | blocks alignleft aligncenter alignright alignjustify | link unlink | numlist bullist | image media table | fontsize forecolor backcolor | bold italic underline strikethrough | indent outdent | superscript subscript | removeformat |',
            toolbar_mode: 'sliding',
            quickbars_selection_toolbar:
                'removeformat | bold italic underline strikethrough | fontsize forecolor backcolor',
            plugins: 'link image media table lists fullscreen quickbars',
            font_size_formats: '12px 14px 16px 18px',
            link_default_target: '_blank',
            link_title: false,
            nonbreaking_force_tab: true,
        },

        imageLayout: [
            { cols: 4 },
            {
                cols: 8,
                children: [ { cols: 12 }, { cols: 12 } ],
            },
            { cols: 6 },
            { cols: 3 },
            { cols: 9 },
            { cols: 4 },
            { cols: 8 },
        ],

        dialog: false,
    }),
    methods: {
        handleActiveTab(index) {
            this.activeTab = index;
            this.selectedList = this.totalList[index];
        },
        handleAddDiary() {
            this.dialog = true;

        },
        handleConfirmAddDiary() {
            this.dialog = false;
            this.$emit('addDiaryEvent');
        },
        openFilePicker() {
            this.$refs.fileInput.click();
        },
        handleImgSelected: function (event) {
            const selectedFile = event.target.files[0];

            if (selectedFile.type.startsWith('image/')) {
                this.diaryCover = URL.createObjectURL(selectedFile);
            } else {
                alert('请选择图片文件');
            }
        },
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

.form-avatar {
    margin-top: 20px;
    height: 100px;
    width: 100px;
    border: 2px solid black;
}

.form-avatar-img {
    margin-top: 20px;
    height: 100px;
    width: 100px;
}

.table-header {
    cursor: pointer;
    font-size: large;
    font-weight: 600;
}
</style>
