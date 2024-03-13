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

                    <v-col cols="9">
                        <v-row>
                            <v-col cols="2">
                                <v-img src="@/assets/imgs/defaultDiaryCover.svg" aspect-ratio="1" height="120"
                                    width="120" class="ml-5 mt-3">
                                </v-img>
                            </v-col>
                            <v-col cols="10">
                                <v-row>
                                    <v-col cols="12" style="display: flex; align-items: center;">
                                        <span style="font-size: xx-larger;" class="mr-5 mb-3">
                                            标题
                                        </span>
                                        <v-text-field variant="underlined" v-model="diaryTitle" label="请输入日记的标题"
                                            outlined></v-text-field>
                                    </v-col>
                                </v-row>
                                <v-row>
                                    <v-col cols="12">
                                        <v-radio-group v-model="selectedItem" inline>
                                            <v-radio label="仅自己可见" value="0"></v-radio>
                                            <v-radio label="所有人可见" value="1"></v-radio>
                                            <v-radio label="指定好友可见" value="2"></v-radio>
                                        </v-radio-group>
                                    </v-col>
                                </v-row>
                            </v-col>
                        </v-row>


                        <v-row style="text-align: center;">
                            <v-col cols="12">
                                <div id="tinydemo"></div>
                            </v-col>
                        </v-row>
                    </v-col>
                </v-row>
            </v-main>
        </v-container>
    </v-app>
</template>

<script>
import tinymce from 'tinymce/tinymce'; //tinymce核心文件
import Editor from '@tinymce/tinymce-vue';

import 'tinymce/models/dom'; // 引入dom模块。从 Tinymce6，开始必须有此模块导入
import 'tinymce/themes/silver'; //默认主题
import 'tinymce/icons/default'; //引入编辑器图标icon，不引入则不显示对应图标

/* 引入编辑器插件
 * 位于 ./node_modules/tinymce/plugins 目录下，版本不同，插件会有所差异。根据自己版本来导入，若不存在的，不能导入，会报错。
 */
import 'tinymce/skins/ui/oxide/skin.min.css'
import 'tinymce/plugins/advlist'; //高级列表
import 'tinymce/plugins/anchor'; //锚点
import 'tinymce/plugins/autolink'; //自动链接
import 'tinymce/plugins/autoresize'; //编辑器高度自适应,注：plugins里引入此插件时，Init里设置的height将失效
import 'tinymce/plugins/autosave'; //自动存稿
import 'tinymce/plugins/charmap'; //特殊字符
import 'tinymce/plugins/code'; //编辑源码
import 'tinymce/plugins/codesample'; //代码示例
import 'tinymce/plugins/directionality'; //文字方向
import 'tinymce/plugins/emoticons'; //表情
import 'tinymce/plugins/fullscreen'; //全屏
import 'tinymce/plugins/help'; //帮助
import 'tinymce/plugins/image'; //插入编辑图片
import 'tinymce/plugins/importcss'; //引入css
import 'tinymce/plugins/insertdatetime'; //插入日期时间
import 'tinymce/plugins/link'; //超链接
import 'tinymce/plugins/lists'; //列表插件
import 'tinymce/plugins/media'; //插入编辑媒体
import 'tinymce/plugins/nonbreaking'; //插入不间断空格
import 'tinymce/plugins/pagebreak'; //插入分页符
import 'tinymce/plugins/preview'; //预览
import 'tinymce/plugins/quickbars'; //快速工具栏
import 'tinymce/plugins/save'; //保存
import 'tinymce/plugins/searchreplace'; //查找替换
import 'tinymce/plugins/table'; //表格
import 'tinymce/plugins/template'; //内容模板
import 'tinymce/plugins/visualblocks'; //显示元素范围
import 'tinymce/plugins/visualchars'; //显示不可见字符
import 'tinymce/plugins/wordcount'; //字数统计
export default {
    components: {
        Editor
    },
    //导出组件
    data: () => ({
        tabs: ['图片', '音乐',],
        activeTab: 0,

        diaryTitle: '',
        selectedItem: '',

        content: '',

        defaultCover: '',
    }),
    methods: {
        handleActiveTab(index) {
            this.activeTab = index;
            this.selectedList = this.totalList[index];
        }
    },
    mounted() {
        tinymce.init({
            selector: '#tinydemo',
            language: 'zh-Hans',
            promotion: false, //Upgrade是否开启
            branding: true, //tiny技术支持信息是否显示
            plugins: 'codesample preview searchreplace autolink directionality visualblocks visualchars image link media template table charmap pagebreak nonbreaking anchor insertdatetime advlist lists wordcount autosave', //使用image插件
            menu: {
                file: { title: '文件', items: 'newdocument' },
                edit: { title: '编辑', items: 'undo redo | cut copy paste pastetext | selectall' },
                insert: { title: '插入', items: 'image link media | template hr' },
                view: { title: '查看', items: 'visualaid' },
                format: { title: '格式', items: 'bold italic underline strikethrough superscript subscript | formats | removeformat' },
                table: { title: '表格', items: 'inserttable tableprops deletetable | cell row column' },
                tools: { title: '工具', items: 'spellchecker code' },
            },
            toolbar: 'preview codesample code forecolor backcolor styles directionality fontfamily fontsize searchreplace image  media link alignleft aligncenter alignright template table charmap pagebreak nonbreaking anchor insertdatetime advlist lists wordcount autosave ', //工具栏显示
            line_height_formats: '1 1.2 1.4 1.6 2', //行高
            font_size_formats: '12px 14px 16px 18px 20px 22px 24px 28px 32px 36px 48px 56px 72px', //字体大小
            font_family_formats: '微软雅黑=Microsoft YaHei,Helvetica Neue,PingFang SC,sans-serif;苹果苹方=PingFang SC,Microsoft YaHei,sans-serif;宋体=simsun,serif;仿宋体=FangSong,serif;黑体=SimHei,sans-serif;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;',

            file_browser_callback_types: 'file image media',//这个根据个人需要配置
            file_picker_callback: function (callback, value, meta) {
                console.log(meta.filetype) //打印上传的类型
                console.log(343434) // 输出343434
                // // 要先模拟出一个input用于上传本地文件
                var input = document.createElement('input')
                input.setAttribute('type', 'file')
                // 你可以给input加accept属性来限制上传的文件类型
                // 例如：input.setAttribute('accept', '.jpg,.png')
                input.setAttribute('accept', '.ppt,.pptx,.jpg, .jpeg, .png, .gif,.pdf, .txt, .zip, .rar, .7z, .doc, .docx, .xls, .xlsx,.mp3, .mp4')
                input.click()
                input.onchange = function () {
                    var file = this.files[0]
                    console.log(this.files)//文件 整体列表
                    console.log(file) //文件的五种类型
                    console.log(file.name) //文件的name名
                    // 下方被注释掉的是官方的一个例子
                    // 放到下面给大家参考
                    var reader = new FileReader()
                    reader.onload = function () {
                        console.log(window.tinymce) //打印tinymce编辑器里面的参数
                        // Note: Now we need to register the blob in TinyMCEs image blob
                        // registry. In the next release this part hopefully won't be
                        // necessary, as we are looking to handle it internally.
                        var id = 'blobid' + (new Date()).getTime()
                        var blobCache = window.tinymce.activeEditor.editorUpload.blobCache
                        var base64 = reader.result.split(',')[1]
                        var blobInfo = blobCache.create(id, file, base64)
                        console.log(id) //获取时间
                        console.log(file) //文件的五种类型
                        console.log(base64) //解码bese64
                        console.log(file.name) //文件的name名
                        console.log(blobInfo) //创造 blob缓存总结（id、file、base64）
                        console.log(blobInfo.blobUri()) //
                        blobCache.add(blobInfo) //

                        // call the callback and populate the Title field with the file name
                        callback(blobInfo.blobUri(), { text: file.name, title: file.name })
                    }
                    reader.readAsDataURL(file)
                }
            }
        })
    }
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