<template>
  <v-card class="emotions-card" rounded hover>
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

</style>
