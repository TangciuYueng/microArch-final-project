<template>
  <v-app>
    <v-main id="our_container">
      <!-- <v-container fluid> -->
      <vue-particles
        id="particles-js"
        color="#663399"
        :particle-opacity="0.7"
        :particles-number="100"
        shape-type="star"
        :particle-size="6"
        lines-color="#BA55D3"
        :lines-width="1"
        :line-linked="true"
        :line-opacity="0.4"
        :lines-distance="100"
        :move-speed="1"
        :hover-effect="true"
        hover-mode="grab"
        :click-effect="true"
        click-mode="repulse"
      />
      <router-view />
      <!-- </v-container> -->
    </v-main>
    <!-- <Footer></Footer> -->
  </v-app>
</template>

<script setup>
//
</script>
<style lang="scss">
#particles-js {
  width: 100%;
  height: calc(100%);
  position: absolute;
}
#our_container{
  background: #E6E6FA
}
</style>