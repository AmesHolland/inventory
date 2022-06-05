<template>
  <el-menu  style="min-height: 100vh"
           background-color="rgb(48, 65, 86)"
           text-color="#FFF"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
           @select="handleSelect">
<!--            :default-openeds="opens"-->

    <div style="height: 60px;line-height: 60px; text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 25px;position: relative;top:3px;margin-right:5px" >
      <b style="color: white; font-size: 20px" v-show="logoTextShow">库存管理系统</b>
    </div>

    <div v-for="item in menus" :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.name }}</span>
          </template>
        </el-menu-item>
      </div>
      <div v-else >
        <el-submenu :index="item.id + ''">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.name }}</span>
          </template>
          <div v-for="subItem in item.children" :key="subItem.id">
            <el-menu-item :index="subItem.path"  v-if=" (!/detail/.test(subItem.path)) && (!/add/.test(subItem.path))">
              <i :class="subItem.icon"></i>
              <span slot="title">{{ subItem.name }}</span>
            </el-menu-item>
          </div>
        </el-submenu>
      </div>
    </div>

<!--    <el-menu-item index="/home">-->
<!--      <template slot="title">-->
<!--        <i class="el-icon-house"></i>-->
<!--        <span slot="title">主页</span>-->
<!--      </template>-->
<!--    </el-menu-item>-->
<!--    <el-submenu index="2">-->
<!--      <template slot="title">-->
<!--        <i class="el-icon-menu"></i>-->
<!--        <span slot="title">系统管理</span>-->
<!--      </template>-->
<!--      <el-menu-item index="/role">-->
<!--        <i class="el-icon-position"></i>-->
<!--        <span slot="title">角色管理</span>-->
<!--      </el-menu-item>-->
<!--      <el-menu-item index="/menu">-->
<!--        <i class="el-icon-menu"></i>-->
<!--        <span slot="title">菜单管理</span>-->
<!--      </el-menu-item>-->
<!--      <el-menu-item index="/file">-->
<!--        <i class="el-icon-document"></i>-->
<!--        <span slot="title">文件管理</span>-->
<!--      </el-menu-item>-->
<!--    </el-submenu>-->
<!--    <el-submenu index="3">-->
<!--      <template slot="title">-->
<!--        <i class="el-icon-menu"></i>-->
<!--        <span slot="title">信息管理</span>-->
<!--      </template>-->
<!--      <el-menu-item index="/staff">-->
<!--        <i class="el-icon-user"></i>-->
<!--        <span slot="title">员工管理</span>-->
<!--      </el-menu-item>-->
<!--      <el-menu-item index="/supply">-->
<!--        <i class="el-icon-user"></i>-->
<!--        <span slot="title">供应商管理</span>-->
<!--      </el-menu-item>-->
<!--      <el-menu-item index="/client">-->
<!--        <i class="el-icon-user"></i>-->
<!--        <span slot="title">客户管理</span>-->
<!--      </el-menu-item>-->
<!--      <el-menu-item index="/product">-->
<!--        <i class="el-icon-goods"></i>-->
<!--        <span slot="title">商品管理</span>-->
<!--      </el-menu-item>-->
<!--      <el-menu-item index="/cargo">-->
<!--        <i class="el-icon-user"></i>-->
<!--        <span slot="title">货物管理</span>-->
<!--      </el-menu-item>-->
<!--    </el-submenu>-->
  </el-menu>

</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapse: Boolean,
    logoTextShow: Boolean,
  },
  data(){
    return{
      menus: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : [],
      opens: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v =>v.id + '')  : []
    }
  },
  methods:{
    handleSelect(index){
      this.$route.fullPath.split("/")
    }
  }

}
</script>

<style scoped>

</style>