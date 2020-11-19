<template>
  <div ref="navbarUser" class="container-navbar-userinfo hide">
    <div class="make-absolute">
      <header>
        <div class="profile-image">
          
          <!-- <img
            :src="
              getUserInfo().profile === null
                ? 'https://cdns.iconmonstr.com/wp-content/assets/preview/2012/240/iconmonstr-user-20.png'
                : getUserInfo().profile
            "
            alt=""
          /> -->
          <svg id="onion" data-name="레이어 1" xmlns="http://www.w3.org/2000/svg" width="24" height="23.83" viewBox="0 0 24 23.83">
            <path class="cls-1" d="M24.76,11.17a5.88,5.88,0,0,0-.21-1,11.75,11.75,0,0,0-14.2-8.72,4.1,4.1,0,0,0-.5.14A12.33,12.33,0,0,0,1.5,16.86c1.62,5,5.84,8,12.09,8a10,10,0,0,0,10.82-7.37A12.85,12.85,0,0,0,24.76,11.17Zm-1.45,7a10.07,10.07,0,0,1-1,2,9.9,9.9,0,0,1-9,4.07A10.78,10.78,0,0,1,1.56,14.53a11.09,11.09,0,0,1-.06-1.41c.21-7.4,8-13.29,14.74-11A12.4,12.4,0,0,1,23.31,18.16Z" transform="translate(-1 -1.09)"/>
            <path class="cls-1" d="M13.38,2.86A10.24,10.24,0,0,0,2.89,12.79c0,.15,0,.33,0,.48A10.17,10.17,0,0,0,13.77,22.7a8,8,0,0,0,.85,0,9,9,0,0,0,8.67-9v-.39A10.21,10.21,0,0,0,13.38,2.86ZM22.7,13.35a8.64,8.64,0,0,1-8.58,8.7c-.35,0-.68,0-1-.06a9.09,9.09,0,0,1-9.35-8.81v-.41a9.56,9.56,0,0,1,9.61-9.41A9.62,9.62,0,0,1,22.7,13.3Z" transform="translate(-1 -1.09)"/>
            <path class="cls-1" d="M18.69,7.75A6.48,6.48,0,0,0,10.55,5a8.86,8.86,0,0,0-5.33,8.37,7.72,7.72,0,0,0,8.16,6.58c.12,0,.24,0,.36,0a6.64,6.64,0,0,0,7.19-6.07A9.25,9.25,0,0,0,18.69,7.75Zm-4.6,11.53a4.94,4.94,0,0,1-.65,0,7.25,7.25,0,0,1-7.72-6.6A7.94,7.94,0,0,1,13,5.25a7.74,7.74,0,0,1,7,7.93A6,6,0,0,1,14.09,19.28Z" transform="translate(-1 -1.09)"/>
            <path class="cls-1" d="M13,6.34A6.46,6.46,0,0,0,7,12.06,6.29,6.29,0,0,0,13.41,18c.15,0,.3,0,.44,0a4.68,4.68,0,0,0,4.75-4.63A6.79,6.79,0,0,0,13,6.34Zm.18,10.88A5.68,5.68,0,0,1,8.14,14,4.61,4.61,0,0,1,9.7,8.14a4.54,4.54,0,0,1,5.89.2,7.63,7.63,0,0,1,2.12,3.25C18.86,14.83,17.13,17.25,13.21,17.22Z" transform="translate(-1 -1.09)"/>
            <path class="cls-1" d="M13.35,8.43A4.36,4.36,0,0,0,8.75,12a4.63,4.63,0,0,0,4.84,4.16,2.91,2.91,0,0,0,3.45-3A4.25,4.25,0,0,0,13.35,8.43Zm2.36,6.25a3.25,3.25,0,0,1-3.62.77,3.84,3.84,0,0,1-2.6-3.16,1.88,1.88,0,0,1,.8-1.88c2.21-2,4.27-1.77,5.42.5A3.17,3.17,0,0,1,15.71,14.68Z" transform="translate(-1 -1.09)"/>
            <path class="cls-1" d="M15.71,11.23c-.06,0-.09,0-.15,0-1.47-.35-3,.15-4.33-.79-.09-.09-.38.14-.62.2a.18.18,0,0,1-.12,0,3.48,3.48,0,0,0,1.24,3.83,2.22,2.22,0,0,0,2.77.18A2.93,2.93,0,0,0,15.71,11.23Zm-.62,1.59a2.35,2.35,0,0,1-1.88,1.83H13a.45.45,0,0,1-.27-.06,1.59,1.59,0,0,1-.32-.12.87.87,0,0,1-.27-.14A3.67,3.67,0,0,1,11,11.65c0-.18,0-.65.56-.42a5.75,5.75,0,0,0,2.57.24C15.12,11.47,15.33,12.06,15.09,12.82Z" transform="translate(-1 -1.09)"/>
            <path class="cls-1" d="M14,13.06c-.09.62-.38,1-1,.83a1.12,1.12,0,0,1-.92-1c0-.64.62-.53,1-.59C13.62,12.26,14.12,12.44,14,13.06Z" transform="translate(-1 -1.09)"/>
          </svg>

          
        </div>
        <div class="userinfo">
          <span>{{ getUserInfo().nickname }} </span>
          <span>{{ getUserInfo().email }} </span>
        </div>
      </header>
      <main>
        <button @click="$router.push(`/store/${getUserInfo().email}`)" v-if="getUserInfo().type == 1">
        
<svg version="1.1" id="레이어_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px"
	 y="0px" viewBox="0 0 26 26" style="enable-background:new 0 0 26 26;" xml:space="preserve">
<g>
	<path d="M24.9,9.2l-1.3-5.4c-0.2-0.9-1.1-1.6-2-1.6H4.4c-1,0-1.8,0.7-2,1.6L1.1,9.2c-0.3,1.2,0,2.3,0.7,3.3c0,0.1,0.1,0.1,0.2,0.2
		c0.1,0,0.1,0.1,0.2,0.2l0.1,0.1v8.7c0,1.2,0.9,2.1,2.1,2.1h17.3c1.2,0,2.1-0.9,2.1-2.1v-8.7l0.2-0.2c0.1-0.1,0.1-0.1,0.2-0.2
		C24.9,11.6,25.2,10.4,24.9,9.2z M13.9,4H17l0.7,5.9c0.1,0.6-0.1,1.2-0.5,1.6c-0.4,0.4-0.9,0.6-1.5,0.6c-1,0-1.9-0.9-1.9-2V4z
		 M8.3,9.9L9,4h3.1v6.2c0,1.1-0.9,2-2,2c-0.5,0-1-0.2-1.4-0.6C8.4,11.1,8.2,10.5,8.3,9.9z M3.2,11.4c-0.4-0.5-0.6-1.1-0.4-1.8L4.1,4
		h3.2l-0.8,6.4c-0.1,1-0.9,1.7-1.9,1.7C3.8,12.1,3.3,11.6,3.2,11.4z M22,22H4v-8.3l0.7,0.1c0.9,0,1.8-0.4,2.5-1.1l0.3-0.3l0.3,0.3
		c0.7,0.7,1.6,1.1,2.6,1.1c0.9,0,1.8-0.4,2.5-1l0.3-0.3l0.3,0.2c0.7,0.7,1.6,1.1,2.6,1.1c0.9,0,1.8-0.4,2.5-1.1l0.3-0.3l0.3,0.3
		c0.7,0.7,1.6,1.1,2.5,1.1l0.7-0.1V22z M22.8,11.4c-0.2,0.3-0.7,0.7-1.5,0.7c-1,0-1.8-0.8-1.9-1.7L18.7,4l3.1,0l1.4,5.7
		C23.4,10.3,23.2,10.9,22.8,11.4z"/>
</g>
<text transform="matrix(1 0 0 1 7.8472 19.459)" class="st0 st1">my</text>
</svg>

          내 스토어
        </button>
        <!-- <button @click="$router.push('/setting/blog')">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            viewBox="0 0 26 26"
            fill="#727272"
          >
            <path
              d="M22.2,14.2c0.1-0.4,0.1-0.8,0.1-1.2c0-0.4,0-0.8-0.1-1.2l2.6-2C25,9.6,25.1,9.2,24.9,9l-2.5-4.3c-0.2-0.3-0.5-0.4-0.8-0.3
	l-3.1,1.2c-0.6-0.5-1.3-0.9-2.1-1.2l-0.5-3.3c-0.1-0.3-0.3-0.5-0.6-0.5h-4.9c-0.3,0-0.6,0.2-0.6,0.5L9.5,4.5C8.7,4.8,8,5.2,7.4,5.7
	L4.3,4.4C4,4.3,3.7,4.4,3.6,4.7L1.1,9C0.9,9.2,1,9.6,1.3,9.8l2.6,2c-0.1,0.4-0.1,0.8-0.1,1.2s0,0.8,0.1,1.2l-2.6,2
	C1,16.4,0.9,16.8,1.1,17l2.5,4.3c0.2,0.3,0.5,0.4,0.8,0.3l3.1-1.2c0.6,0.5,1.3,0.9,2.1,1.2l0.5,3.3c0.1,0.3,0.3,0.5,0.6,0.5h4.9
	c0.3,0,0.6-0.2,0.6-0.5l0.5-3.3c0.8-0.3,1.5-0.7,2.1-1.2l3.1,1.2c0.3,0.1,0.6,0,0.8-0.3l2.5-4.3c0.2-0.3,0.1-0.6-0.2-0.8L22.2,14.2z
	 M13,17.6c-2.5,0-4.6-2.1-4.6-4.6s2.1-4.6,4.6-4.6s4.6,2.1,4.6,4.6S15.5,17.6,13,17.6z"
            /></svg
          >계정 설정
        </button> -->
        <button @click="logout">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="24"
            height="24"
            viewBox="0 0 35 35"
            fill="#727272"
          >
            <path d="M33.19,37.17A8,8,0,0,1,33,39.7a4,4,0,0,1-4.06,2.75c-5.72.06-11.44.08-17.16,0C9,42.4,7.5,40.68,7.5,37.89V24.67h4.12q0,6.21,0,12.45c0,.82.25,1.11,1.08,1.11,5.12,0,10.26,0,15.39,0,.77,0,1.07-.29,1-1.07s0-1.33,0-2,.25-.92.85-.91H30C33.19,34,33.2,34,33.19,37.17Z" transform="translate(-7.5 -7.5)"/><path d="M11.62,24.67H7.5V12.23c0-3.06,1.58-4.68,4.64-4.71,2.45,0,4.89,0,7.33,0h8.79c3.46,0,4.93,1.49,4.93,5,0,.77,0,1.55,0,2.32s-.29.91-.93,1c-3.11.25-3.14.26-3.17-2.9,0-.85-.31-1.09-1.11-1.09q-7.64,0-15.28,0c-.79,0-1.11.22-1.11,1.08Q11.61,18.77,11.62,24.67Z" transform="translate(-7.5 -7.5)"/><path d="M28.13,28.58c-1.69,0-3.38,0-5.06,0-.69,0-1-.24-1-1,0-1.71,0-3.42,0-5.13,0-.89.36-1.08,1.13-1.07,3.22,0,6.45,0,9.68,0,.85,0,1.19-.23,1.14-1.13s0-2.07,0-3.11c0-.2-.08-.46.17-.56s.33.12.45.24c2.54,2.56,5.07,5.13,7.62,7.69.35.35.25.6-.06.91l-7.48,7.56c-.15.15-.29.39-.51.32S34,33,34,32.74c0-1.1,0-2.2,0-3.3,0-.63-.22-.87-.85-.86-1.68,0-3.37,0-5.06,0Z" transform="translate(-7.5 -7.5)"/>
          </svg>
          로그아웃
        </button>
      </main>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapMutations } from "vuex";

export default {
  data: function() {
    return {
      isShow: false
    };
  },
  methods: {
    ...mapGetters({
      getUserInfo: "user/getUserInfo"
    }),
    ...mapMutations({
      setLogout: "user/setLogout"
    }),
    logout() {
      if (confirm("로그아웃 하시겠습니까?")) {
        this.setLogout();
        location.href = "/";
      }
    },
    toggle() {
      const navbarUser = document.querySelector(".container-navbar-userinfo");
      if (navbarUser.classList.contains("hide")) {
        navbarUser.classList.remove("hide");
      } else {
        navbarUser.classList.add("hide");
      }
    }
  },
  mounted() {
    const navbarUser = document.querySelector(".container-navbar-userinfo");
    window.addEventListener("click", function(e) {
      if (
        !navbarUser.contains(e.target) &&
        !e.target.classList.contains("banner-image-edit")
      ) {
        navbarUser.classList.add("hide");
      }
    });
  }
};
</script>

<style lang="scss" scoped>
.container-navbar-userinfo {
  position: relative;
  width: 0px;
  height: 0px;
  left: -180px;
  top: 5px;
  &.hide {
    display: none;
  }
  .make-absolute {
    display: flex;
    flex-direction: column;
    position: absolute;
    background-color: white;
    border: 1px solid rgb(233, 233, 233);
    width: 240px;
    header {
      display: flex;
      border-bottom: 1px rgb(233, 233, 233) solid;
      .profile-image {
        display: flex;
        justify-content: center;
        align-items: center;
        background-image: none !important;
        svg {
          fill: #A7496D;
          margin: 15px;
        }
        img {
          width: 36px;
          height: 36px;
          border-radius: 50%;
          margin: 10px;
        }
      }
      .userinfo {
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        align-items: flex-start;
        span:nth-child(2) {
          color: rgb(179, 179, 179);
          font-size: 0.9em;
        }
      }
    }
    main {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      button {
        display: flex;
        align-items: center;
        color: #727272;
        width: 100%;
        padding: 12px 15px;
        &:hover {
          background-color: aliceblue;
        }
        svg {
          width: 24px;
          height: 24px;
          margin-right: 15px;
        }
      }
    }
  }
}

</style>
