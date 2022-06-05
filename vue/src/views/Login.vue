<template>
  <div class="wrapper">

    <div style="margin:200px auto; background-color: #fff;width:350px; height: 300px;padding:10px; border-radius: 5px">
      <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>登录</b></div>
      <el-form :model="staff" :rules="rules" ref="staffForm">
        <el-form-item prop="id">
          <el-input placeholder="请输入员工号" size="medium" style="margin: 10px 0px" prefix-icon="el-icon-user" v-model="staff.id"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0px" prefix-icon="el-icon-lock" show-password v-model="staff.password"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0px;text-align: center">
          <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
          <el-button type="warning" size="small" autocomplete="off" @click="getPassword">忘记密码</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div style="color: white">
      1  管理员
      2 审核员
      3 售货员
      4 仓库管理员
      5 数据统计员
      6 采购员
    </div>

  </div>
</template>

<script>
import {setRoutes} from "@/router";

export default {
  name: "Login",
  data(){
    return {
      staff:{},
      rules: {
        id: [
          { required: true, message:"请输入员工号", trigger:'blur'},
          { min:8, max:10, message:"长度请在8个字符",trigger:'blur'}
        ],
        password: [
          { required: true, message:"请输入密码", trigger:'blur'},
          { min:3, max:10, message:"长度请在3到10个字符",trigger:'blur'}
        ]
      }
    }
  },
  methods:{
    login(){
      this.$refs["staffForm"].validate((valid) => {
        if (valid) {
          this.$request.post("/staff/login",this.staff)
              .then(res => {
                if(res.code === '200'){
                  localStorage.setItem("staff", JSON.stringify(res.data))
                  localStorage.setItem("menus", JSON.stringify(res.data.menus))
                  setRoutes()
                  this.$message.success("登录成功");
                  this.$router.push("/")
                }
                else{
                  this.$message.error(res.msg);
                }
              })
        } else {
          return false;
        }
      });

    },
    getPassword(){

      this.$router.push("/get_password")

    },
  }

}
</script>

<style>
.wrapper{
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC466B, #3F5EF8);
  overflow: hidden;
}
</style>