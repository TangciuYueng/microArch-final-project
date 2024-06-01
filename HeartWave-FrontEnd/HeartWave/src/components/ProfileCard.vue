<template>
  <v-card
    class="profile-card"
    :style="{ backgroundImage: 'url(' + profileImgSrc + ')' }"
    style="max-width: 100%; max-height: 100%; object-fit: cover"
  >
    <v-card-text>
      <v-avatar size="100" @click="this.$emit('BrowsingPersonalHomepage', username)">
        <img
          :src="avatarSrc"
          alt="avatar"
          style="width: 100px; height: 100px; object-fit: cover; cursor: pointer;"
          
        />
      </v-avatar>
      <div style="font-size: 20px" class="mt-2">
        {{ username }}
      </div>
      <div style="font-size: 12px">
        <v-icon color="red">
          mdi-heart
        </v-icon>
        IP属地: {{ ipLocation }}
      </div>
      <div style="font-size: larger" class="mt-5">
        <v-btn @click="centerDialogVisible = true">
          心情打卡
        </v-btn>
      </div>
    </v-card-text>
  </v-card>

  <el-dialog
    v-model="centerDialogVisible"
    width="700"
    align-center
    class="clock-in-dialog"
  >
    <div class="clock-in-title">
      心情打卡
    </div>
    <div v-if="Step1Flag" class="clock-in-text">
      请选择以下符合你心情的标签（最多选3项）......
    </div>
    <div v-if="Step1Flag" class="dialog-button-content">
      <div class="button-row">
        <v-btn
          color="#FFA0A0"
          :class="{ selected: selectedButtons.includes(1) }"
          class="custom-button text-white"
          @click="toggleButton(1)"
        >
          愉悦
        </v-btn>
        <v-btn
          color="#6948BC"
          :class="{ selected: selectedButtons.includes(2) }"
          class="custom-button text-white"
          @click="toggleButton(2)"
        >
          忧伤
        </v-btn>
        <v-btn
          color="#D850C5"
          :class="{ selected: selectedButtons.includes(3) }"
          class="custom-button text-white"
          @click="toggleButton(3)"
        >
          兴奋
        </v-btn>
        <v-btn
          color="#69BC48"
          :class="{ selected: selectedButtons.includes(4) }"
          class="custom-button text-white"
          @click="toggleButton(4)"
        >
          激动
        </v-btn>
        <v-btn
          color="#BCBA48"
          :class="{ selected: selectedButtons.includes(5) }"
          class="custom-button text-white"
          @click="toggleButton(5)"
        >
          烦躁
        </v-btn>
      </div>
      <div class="button-row">
        <v-btn
          color="#2AB0B9"
          :class="{ selected: selectedButtons.includes(6) }"
          class="custom-button text-white"
          @click="toggleButton(6)"
        >
          平静
        </v-btn>
        <v-btn
          color="#AD385D"
          :class="{ selected: selectedButtons.includes(7) }"
          class="custom-button text-white"
          @click="toggleButton(7)"
        >
          愤怒
        </v-btn>
        <v-btn
          color="#4D5352"
          :class="{ selected: selectedButtons.includes(8) }"
          class="custom-button text-white"
          @click="toggleButton(8)"
        >
          紧张
        </v-btn>
        <v-btn
          color="#4873BC"
          :class="{ selected: selectedButtons.includes(9) }"
          class="custom-button text-white"
          @click="toggleButton(9)"
        >
          抑郁
        </v-btn>
        <v-btn
          color="#BC6948"
          :class="{ selected: selectedButtons.includes(10) }"
          class="custom-button text-white"
          @click="toggleButton(10)"
        >
          焦虑
        </v-btn>
      </div>
    </div>
    <el-button
      v-if="Step1Flag"
      class="clock-in-button"
      @click="nextStep"
    >
      下一步
    </el-button>

    <div v-if="Step1Flag == false">
      <div class="selected-buttons">
        <div class="clock-in-text-2">
          你选择的标签是：
        </div>
        <div
          v-for="buttonId in selectedButtons"
          :key="buttonId"
          :style="{ backgroundColor: getButtonColor(buttonId) }"
          class="color-box"
        >
          {{ getButtonText(buttonId) }}
        </div>
      </div>

      <div class="clock-in-text-2" style="margin-top: 3%">
        请调节各个情绪的占比（%）：
      </div>
      <el-slider
        v-for="buttonId in selectedButtons"
        :key="buttonId"
        v-model="sliderValues[buttonId]"
        show-input
        style="margin-top: 5%"
        :style="{ '--el-slider-main-bg-color': getButtonColor(buttonId) }"
      />
    </div>

    <el-button
      v-if="Step1Flag == false"
      class="clock-in-button"
      @click="FinishClockIn"
    >
      完成
    </el-button>
  </el-dialog>
</template>

<script>
import { ElMessage } from "element-plus";

export default {
  props: {
    profileImgSrc: {
      type: String,
      required: true,
    },
    avatarSrc: {
      type: String,
      required: true,
    },
    username: {
      type: String,
      required: true,
    },
    ipLocation: {
      type: String,
      required: true,
    },
  },
  data: () => ({
    centerDialogVisible: false,
    selectedButtons: [], // 跟踪用户选择的按钮
    Step1Flag: true,
    sliderValues: {}, // 存储滑块的值，情绪百分比分配
  }),
  methods: {
    test() {
      console.log(this.sliderValues);
    },
    toggleButton(buttonId) {
      if (this.selectedButtons.includes(buttonId)) {
        // 如果按钮已经被选中，则取消选择
        this.selectedButtons = this.selectedButtons.filter(
          (id) => id !== buttonId
        );
      } else {
        // 如果按钮未被选中，则进行选择，但限制最多选择3个按钮
        if (this.selectedButtons.length < 3) {
          this.selectedButtons.push(buttonId);
        }
      }
      console.log(this.selectedButtons);
    },
    nextStep() {
      this.Step1Flag = false;
    },
    FinishClockIn() {
      const sum = Object.values(this.sliderValues).reduce(
        (accumulator, currentValue) => accumulator + currentValue,
        0
      );
      if (sum === 100) {
        this.Step1Flag = true;

        this.centerDialogVisible = false;
      } else {
        ElMessage({
          showClose: true,
          message: "各情绪值之和请调整为100%",
          center: true,
          type: "warning",
        });
      }
    },
    getButtonColor(buttonId) {
      // 根据按钮ID返回对应的颜色
      switch (buttonId) {
        case 1:
          return "#FFA0A0";
        case 2:
          return "#6948BC";
        case 3:
          return "#D850C5";
        case 4:
          return "#69BC48";
        case 5:
          return "#BCBA48";
        case 6:
          return "#2AB0B9";
        case 7:
          return "#AD385D";
        case 8:
          return "#4D5352";
        case 9:
          return "#4873BC";
        case 10:
          return "#BC6948";
        default:
          return "black"; // 默认为黑色
      }
    },
    getButtonText(buttonId) {
      // 根据按钮ID返回对应的文字内容
      switch (buttonId) {
        case 1:
          return "愉悦";
        case 2:
          return "忧伤";
        case 3:
          return "兴奋";
        case 4:
          return "激动";
        case 5:
          return "烦躁";
        case 6:
          return "平静";
        case 7:
          return "愤怒";
        case 8:
          return "紧张";
        case 9:
          return "抑郁";
        case 10:
          return "焦虑";
        default:
          return ""; // 默认为空字符串
      }
    },
  },
};
</script>

<style scoped>
.profile-card {
  background-size: cover;
  box-shadow: 0 5px 6px rgba(0, 0, 0, 0.5);
  /* 下面两个圆角 */
  border-radius: 0 0 50px 50px;
  min-height: 300px;
  text-align: center;
  color: white;
}

.clock-in-dialog {
  display: flex;
  justify-content: center;
  align-items: center;
}

.clock-in-title {
  font-family: 思源黑体;
  font-size: 29px;
  font-weight: bold;
  line-height: normal;
  letter-spacing: 0px;

  color: #3d3d3d;
  text-align: center;
}

.clock-in-button {
  font-family: 思源黑体;
  font-size: 22px;
  font-weight: bold;
  line-height: normal;
  letter-spacing: 0px;

  color: #ffffff;
  background: #6ec4ae;

  text-align: center;
  margin-left: 42%;
  padding: 3% 3% 3% 3%;
}
.clock-in-text {
  font-family: 思源黑体;
  font-size: 15px;
  font-weight: 350;
  line-height: normal;
  letter-spacing: 0px;

  color: #3d3d3d;
  background: #f6f6f6;
  height: 100px;

  margin: 5% 10% 0% 10%;
  padding-left: 2%;
  padding-top: 2%;
}

.dialog-button-content {
  display: flex;
  flex-direction: column;
  padding: auto;
  /* margin-top: 5%; */
  margin: 5% 10% 0% 10%;
}

.button-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 6%;
}

.custom-button {
  font-family: 思源黑体;
  font-size: 15px;
  font-weight: 500;
  line-height: normal;
  letter-spacing: 0px;

  color: #ffffff;
  border-radius: 8px;
}

.selected {
  background-color: #c3d5dd !important; /* 设置选中按钮的背景颜色为红色 */
  color: white !important; /* 设置选中按钮的字体颜色为白色 */
}

.clock-in-text-2 {
  /* 请调节各个情绪的占比： */

  font-family: 思源黑体;
  font-size: 18px;
  font-weight: 500;
  line-height: normal;
  letter-spacing: 0px;
  margin-right: 2%;

  color: #3d3d3d;
}
.color-box {
  width: 60px;
  height: 35px;
  margin-right: 4%;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #ffffff;
  font-family: 思源黑体;
  font-size: 15px;
  font-weight: 500;
  line-height: normal;
  letter-spacing: 0px;
}
.selected-buttons {
  display: flex;
  align-items: center;
}

.el-slider {
}
</style>