<template>
  <el-container style="min-height: 100vh; border: 1px solid #eee">
      <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246);height: 100%; box-shadow: 2px 0px 6px rgba(0,21,41,0.35);">
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"/>
      </el-aside>
      <el-container>
        <el-header style=" border-bottom: 1px solid #ccc">
          <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :staff="staff"/>
        </el-header>
        <el-main>
          <!-- 当前页面的子路由会在router-view里面展示-->
          <router-view @refreshStaff="getStaff"/>
        </el-main>
      </el-container>
    </el-container>
</template>

<script>

// import request from "@/utils/request";
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'Manage',
  data(){
    return{

      collapseBtnClass:'el-icon-s-fold',
      isCollapse:false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg:'headerBg',
      staff:{}
    }
  },
  created(){
    this.getStaff()
  },
  components: {
    Aside,
    Header
  },
  methods:{
    collapse(){
      this.isCollapse = !this.isCollapse
      if(this.isCollapse){
        this.sideWidth = 64
        this.collapseBtnClass ='el-icon-s-unfold'
        this.logoTextShow = false
      }
      else{
        this.sideWidth = 200
        this.collapseBtnClass ='el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    getStaff(){
      let id = localStorage.getItem("staff") ? JSON.parse(localStorage.getItem("staff")).id :""
      this.$request.get("/staff/id/" + id)
      .then(res =>{
        this.staff = res.data
      })
    }
  }
}
</script>


