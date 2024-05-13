<template>
  <v-card class="custom-card friend-card">
    <v-card-title>关心一下他们吧></v-card-title>
    <v-card-text>
      <v-row dense>
        <v-col v-for="friend in friends" :key="friend.id">
          <div class="friend-little-card">
            <img :src="friend.avatar" class="avatar">
            <div class="mood">
              ❤️{{ friend.mood }}
            </div>
            <div class="music">
              ♫ {{ friend.music }}
            </div>
            <button class="message-button">
              留言
            </button>
          </div>
        </v-col>
        <v-col>
          <button class="more-button">
            更多
          </button>
          <!-- <div style="margin-top: 20%; font-size: x-small;">查看更多好友近况</div> -->
        </v-col>
      </v-row>
    </v-card-text>
  </v-card>
</template>

<script>

export default {
  props: {
    friends: {
      type: Array,
      required: true,
    },
  },
};
</script>

<style scoped>
.friend-card {
  /* margin-bottom: 16px; */
    background: linear-gradient(104deg, #6E85F6 -7%, #9DC4ED 9%, #A1D0ED 35%, #CAFCF1 94%);
    box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.302);
}
.friend-little-card{
    display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}
.avatar {
  height: 30px;
  border-radius: 50%;
}

.mood {
  
}
.music{
    
}
.message-button,
.more-button {
  /* margin-top: 16px; */
  
height: 30px;
width: 60px;
transform: rotate(0deg);
border-radius: 5px;
opacity: 1;

background: #605E5E;
color: white;
    
    
}
button:hover {
  background-color: rgb(38, 37, 37);
  color: white;
}
.more-button{
    /* transform: rotate(90deg); */
    border-radius: 2px;
    opacity: 1;
    width: 60px;
    background: rgba(60, 153, 130, 0.3098);
    margin-top: 30%;
    
}
</style>