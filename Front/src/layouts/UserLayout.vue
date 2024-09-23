<template>
  <div id="userLayout">
    <a-layout style="min-height: 100vh">
      <a-layout-header class="header">
        <a-space>
          <img src="../assets/dog.jpg" class="logo" />
          <div><h2>凌动之心OJ</h2></div>
        </a-space>
      </a-layout-header>
      <a-layout-content class="content">
        <h2>用户登录</h2>
        <router-view />
      </a-layout-content>
      <!--      <a-layout-footer class="footer">-->
      <!--        <a href="https://github.com/Beginnersling/OJjudgment.git" target="_blank"> 个人主页 </a>-->
      <!--      </a-layout-footer>-->
    </a-layout>
  </div>
</template>
<style scoped>
#userLayout {
  text-align: center;
  background: url("https://p6.itc.cn/q_70/images03/20231206/96a8cb47f84f4317a42a7288f3f2815e.jpeg");
}

#userLayout .header {
  margin-top: 16px;
}

#userLayout .content {
  margin-bottom: 16px;
  padding: 20px;
}

#userLayout .footer {
  background: #efefef;
  padding: 16px;
  position: sticky;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
#userLayout .logo {
  width: 64px;
  height: 64px;
}
</style>
<script></script>
