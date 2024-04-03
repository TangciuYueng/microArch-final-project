<template>
  <v-card class="emotions-card" rounded hover>
    <!-- <v-card-title>一周心情表</v-card-title> -->
  
    <div ref="chart" style="width: 100%; height: 100%;"></div>


  </v-card>
  
    
</template>

<script>

import * as echarts from 'echarts';

export default {
  props: {
    
  },
  data() {
    return {
      selectItems: [
        { value: "1 Week", label: "1 Week" },
        { value: "6 Months", label: "6 Months" },
        { value: "待定", label: "待定" },
      ],
      selectedValue: "6 Months",
      moodData: [70, 80, 60, 90, 50, 75, 85] // 一周心情值数据
    };
  },
  mounted() {
    // 在 mounted 钩子函数中初始化图表
    this.initChart();
  },
  methods: {
    handleSelectionChange() {
      console.log("Selected value:", this.selectedValue);
      // 在这里可以执行你想要的操作，使用选中的值
    },
    initChart() {
      // 基于准备好的 DOM，初始化 ECharts 实例
      const chartDom = this.$refs.chart;
      const myChart = echarts.init(chartDom);

      // 指定图表的配置项和数据
      const option = {
        grid: {
    containLabel: true, // 设置为 true 可以使得图表布局在包含坐标轴标签时自动缩放
    width: '85%', // 设置宽度
    height: '70%' // 设置高度
},
        title: {
          text: '一周心情值'
        },
        tooltip: {},
        xAxis: {
          type: 'category',
          data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        },
        yAxis: {
          type: 'value',
          min: 0,
          max: 100
        },
        color: {
    type: 'linear',
    x: 0,
    y: 0,
    x2: 0,
    y2: 1,
    colorStops: [{
      offset: 0, color: '#B0E881' // 渐变起始颜色
    }, {
      offset: 0.49, color: '#B0E881' // 渐变中间颜色
    }, {
      offset: 1, color: '#72F7C5' // 渐变结束颜色
    }],
    global: false // 缺省为 true
  },
        series: [{
          name: '心情值',
          type: 'bar',
          data: this.moodData, // 心情值数据
        }]
      };

      // 使用刚指定的配置项和数据显示图表
      myChart.setOption(option);
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
  padding-top: 20px;
  padding-left: 10px;
}
.emotions-card > div {
  width: 100%;
  height: 100%;
}
</style>
