<template>
  <div style="line-height: 60px;display:flex">
    <div style="flex:1;">
      <span :class="collapseBtnClass"  style="cursor: pointer;font-size: 20px;position: center" @click="collapse"></span>
    </div>

    <el-dropdown style="width: 150px; cursor: pointer;text-align: right">
      <div style="display: inline-block">
        <img :src="staff.avatar" alt=""
             style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
        <span>{{ staff.name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>
      <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center;font-size: 12px">
        <el-dropdown-item style="font-size: 10px">
          <span style="text-decoration: none" @click="person">个人信息</span>
        </el-dropdown-item>
        <el-dropdown-item  style="font-size: 10px">
          <span style="text-decoration: none" @click="modPassword">修改密码</span>
        </el-dropdown-item>
        <el-dropdown-item  style="font-size: 10px">
          <span style="text-decoration: none" @click="logout">退出系统</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass:String,
    collapse: {type:Function},
    staff:Object
  },
  computed: {
    currentPathName () {
      return this.$store.state.currentPathName;　　//需要监听的数据
    }
  },
  data(){
    return{
    }
  },
  methods:{
    logout(){
      this.$store.commit("logout")
      this.$message.success("退出成功")
    },
    person(){
      this.$router.push("/person")
    },
    modPassword(){
      this.$router.push("/password")
    }
  }

}
</script>
