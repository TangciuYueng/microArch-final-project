<template>
    <v-card class="weather-card custom-card" rounded hover>
    <!-- 左边部分 -->
    <v-card-text class="weather-info">
        <div class="temperature">20°C</div> <!-- 气温信息 -->
        <div class="location">上海</div> <!-- 地点信息 -->
    </v-card-text>

    <!-- 右边部分 -->
    <v-img class="weather-image" src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg" cover></v-img> <!-- 天气图片 -->
    </v-card>
  </template>
  
<script>

</script>

<style scoped>
.weather-card {
  display: flex;
  align-items: center;
  padding: auto;
  background: linear-gradient(116deg, #9A93E7 0%, rgba(145, 200, 219, 0.9698) 21%, rgba(96, 208, 200, 0.5166) 62%, rgba(40, 208, 172, 0.38) 81%);
}

.weather-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
}

.temperature {
  font-size: 36px;
  font-weight: bold;
  color: #ffffff;
  
}

.location {
  font-size: 24px;
  color: #ffffff;
  margin-top: 20px;
}

.weather-image {
  margin-right: 5%;
}
</style>