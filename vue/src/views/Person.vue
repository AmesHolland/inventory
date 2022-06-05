<template>
  <el-card style="width: 500px; padding: 20px; border: 1px solid #ccc">
    <el-form  label-width="60px" size="small">

      <div style="text-align: center">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:9090/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
          <img v-if="form.avatar" :src="form.avatar" class="avatar" >
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </div>

      <el-form-item label="员工号">
        <el-input v-model="form.id" disabled autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="类型">
        <el-input v-model="form.role" disabled autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.tele" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input type="textarea" v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item  style="text-align: center;margin-bottom: 0px">
        <el-button type="primary" @click="save">确 定</el-button>
      </el-form-item>
    </el-form>

  </el-card>
</template>

<script>
export default {
  name: "Person",
  created(){
    this.getStaff().then(res =>{
      this.form = res
    })
  },
  data(){
    return{
      form:{},
      staff:localStorage.getItem("staff") ? JSON.parse(localStorage.getItem("staff")) :{}
    }
  },
  methods:{
    async getStaff() {
      return (await this.$request.get("/staff/id/" + this.staff.id)).data
    },
    save(){
      this.$request.post("/staff",this.form)
          .then(res =>{
            if(res.code === '200'){
              this.$message.success("保存成功");

              // 触发腹肌更新User的方法
              this.$emit("refreshStaff")
              // 更新浏览器存储的用户信息
              this.getStaff().then(res=>{
                res.token = JSON.parse(localStorage.getItem("staff")).token
                localStorage.setItem("staff",JSON.stringify(res))
              })

            }else{
              this.$message.error("保存失败");
              // 触发父级更新User方法
            }
          })
    },
    handleAvatarSuccess(res) {
      this.form.avatar = res
    },

  }
}
</script>

<style>
.avatar-uploader{
  text-align: center;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  margin-left: 100px;
  margin-right: 100px;

}
.avatar-uploader:hover {
  border-color: #409EFF;

}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 158px;
  height: 158px;
  line-height:158px;

}
.avatar {
  width: 158px;
  height: 158px;
  display: block;
}
</style>