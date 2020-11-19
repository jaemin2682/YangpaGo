!<template>
  <div class="background">
      <img class="logo" src="@/assets/images/logo/logo.gif"/>
  </div>
</template>

<script>
export default {
  data() {
    return {
    };
  },
  mounted() {
    setTimeout(()=>{ 
        document.querySelector('.logo').animate( {opacity:'0', left:'-10px', top:'-10px'}, 1000 );  
        this.$router.push({ name: "Intro"}); 
    }, 2500);
  },
};
</script>

<style scoped>
 .logo{
    position: absolute;
    left:0;
    top:0;
    width:100%;
    height:100%;
    -webkit-transition: opacity 5s ease-in;
 }

 .background{
    background-color: white;
    background-size: cover;
 }

</style>
