<template>
  <div class="wrapper">

    <div style="margin:200px auto; background-color: #fff;width:350px; height: 350px;padding:10px; border-radius: 5px">
      <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>找回密码</b></div>
      <el-form :model="staff" :rules="rules" ref="staffForm">
        <el-form-item prop="id">
          <el-input placeholder="请输入员工号" size="medium" style="margin: 7px 0px" prefix-icon="el-icon-user" v-model="staff.id"></el-input>
        </el-form-item>
        <el-form-item prop="tele">
          <el-input placeholder="请输入手机号" size="medium" style="margin: 7px 0px" prefix-icon="el-icon-lock" v-model="staff.tele"></el-input>
        </el-form-item>
        <el-form-item prop="stName">
          <el-input placeholder="请输入验证码" size="medium" style="margin: 7px 0px;width:200px" prefix-icon="el-icon-lock" v-model="vcode"></el-input>
          <el-button style="margin-left: 10px" size="medium" type="success" @click="GetVerifyCode"> 获取验证码</el-button>
        </el-form-item>
        <el-form-item style="margin: 10px 0px;text-align: center">
          <el-button type="primary" size="medium" autocomplete="off" @click="getPassword">找回密码</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
        >
      <span>您的密码为: {{ staff.password }}</span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="yes">确认并跳转至登录界面</el-button>
  </span>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "GetPassword",
  data(){
    return {
      staff:{},
      rules: {
        id: [
          { required: true, message:"请输入员工号", trigger:'blur'},
          { min:8, max:10, message:"长度请在8个字符",trigger:'blur'}
        ],
        tele: [
          { required: true, message:"请输入手机号", trigger:'blur'},
          { min:11, max:11, message:"长度请在11个字符",trigger:'blur'}
        ]
      },
      dialogVisible : false,
      vcode : ""
    }
  },
  methods:{
    getPassword(){
      this.$refs["staffForm"].validate((valid) => {
        if (valid) {
          this.$request.post("/staff/get_password",this.staff)
              .then(res => {
                if(res.code === '200'){
                  this.staff = res.data
                  this.$message.success("查找成功");
                  this.dialogVisible = true;
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
    GetVerifyCode(){
      this.$message.success("已发送验证码，请注意接收")
    },
    yes(){
      this.dialogVisible = false;
      this.$router.push("/login")
    }
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