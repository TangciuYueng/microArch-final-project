<template>
  <v-card class="emotions-card mt-10" rounded hover>
    <v-card-title>一周心情表</v-card-title>
    
    <el-select
      v-model="selectedValue"
      placeholder="请选择时间"
      size="large"
      style="width: 120px"
    >
      <el-option
        v-for="item in selectItems"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>
    <div>直方图不会搞</div>


  </v-card>
  <span class="text-title">您近一周的心情主基调为：</span>
  <div class="text-mood">想死</div>
    
</template>

<script>
export default {
  data() {
    return {
      selectItems: [
        { value: "1 Week", label: "1 Week" },
        { value: "6 Months", label: "6 Months" },
        { value: "待定", label: "待定" },
      ],
      selectedValue: "6 Months",
    };
  },
  methods: {
    handleSelectionChange() {
      console.log("Selected value:", selectedValue);
      // 在这里可以执行你想要的操作，使用选中的值
    },
  },
};
</script>


<style scoped>
.emotions-card {
  height: 300px;
  border-radius: 19px;
  opacity: 1;

  background: #ffffff;

  box-shadow: 0px 2.9px 27.99px 0px rgba(59, 74, 116, 0.1355);
  margin-bottom: 5%;
}
.text-title{
    
    font-size: 22.37px;
    font-weight: normal;
    line-height: 19.3px;
    letter-spacing: 1.93px;

    color: #3D3D3D;
}
.text-mood{
    opacity: 1;

    font-family: HelloFont WenYiHei;
    font-size: 40.37px;
    font-weight: normal;
    line-height: 19.3px;
    letter-spacing: 1.93px;

    color: #105645;

    margin-top: 10%;
    text-align: center; /* 文字水平居中 */
}
</style>
