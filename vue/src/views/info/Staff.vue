<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入员工姓名" suffix-icon="el-icon-search" v-model="name"/>
      <el-select v-model="role" clearable placeholder="请选择员工类型">
        <el-option
            v-for="item in roles"
            :key="item.name"
            :label="item.name"
            :value="item.name">
        </el-option>
      </el-select>
      <el-input style="width: 200px" placeholder="请输入员工地址" suffix-icon="el-icon-location" class="ml-5" v-model="address"/>

      <el-button class="ml-5"  type="primary" @click="load()">搜索</el-button>
      <el-button type="warning" @click="reset()">重置</el-button>
    </div>

    <div style="margin: 10px 0;">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>

      <el-popconfirm
          style="margin-left: 10px"
          confirm-button-text="确定"
          cancel-button-text="取消"
          icon="el-icon-info"
          icon-color="red"
          title="确定删除吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

      <el-upload action="http://localhost:9090/staff/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button style="margin-left: 10px" type="primary">导入<i class="el-icon-bottom"></i></el-button>
      </el-upload>


      <el-button style="margin-left: 10px" type="primary" @click="exp">导出<i class="el-icon-top"></i></el-button>
    </div>

    <el-table
        :data="tableData"
        stripe border height="500"
        :header-cell-class-name="headerBg"
        @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column prop="id" label="员工号" width="140"></el-table-column>
      <el-table-column prop="role" label="类型" width="180"></el-table-column>
      <el-table-column prop="name" label="姓名" width="140"></el-table-column>
      <el-table-column prop="gender" label="性别" width="50"></el-table-column>
      <el-table-column prop="tele" label="电话"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text="确定"
              cancel-button-text="取消"
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="handleDel(scope.row.id)"
          >
            <el-button style="margin-left: 10px" type="danger" slot="reference">删除<i class="el-icon-delete"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total=total>
      </el-pagination>
    </div>

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="姓名">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="员工类型">
          <el-select v-model="form.role" clearable placeholder="请选择员工类型">
            <el-option
                v-for="item in roles"
                :key="item.name"
                :label="item.name"
                :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.tele" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <template>
            <el-radio v-model="form.gender" label="男" >男</el-radio>
            <el-radio v-model="form.gender" label="女">女</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Staff",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      name:"",
      role:"",
      address:"",
      dialogFormVisible:false,
      multipleSelection:[],
      form:{},
      headerBg:'headerBg',
      roles:[]
    }
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      this.$request.get("/staff/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          role: this.role,
          address: this.address
        }
      })
          .then(res =>{
            this.tableData = res.data.records;
            this.total = res.data.total;
          })
      this.$request.get("/role")
          .then(res =>{
            this.roles = res.data
          })
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum;
      this.load();
    },
    reset(){
      this.name = ""
      this.type = ""
      this.address = ""
      this.load()
    },
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    save(){

      // 可以给取消按钮加个@click="cancel"，
      // 然后定义cancel方法，cancel() { this.dialogFormVisible=false  this.load() },
      this.$request.post("/staff",this.form)
          .then(res =>{
            if(res.code ==='200'){
              this.$message.success("保存成功");
              this.dialogFormVisible = false;
              this.load()
            }else{
              this.$message.error("保存失败");
            }
          })
    },
    handleEdit(row){
      this.form = Object.assign({},row) // 将row拷贝到空对象中 解决没点确定数据改变的问题
      // this.form = row
      this.dialogFormVisible = true

    },
    handleDel(id){
      this.$request.delete("/staff/" + id)
          .then(res =>{
            if(res.code === '200'){
              this.$message.success("删除成功");
              this.load()
            }else{
              this.$message.error("删除失败");
            }
          })
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val;
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id) //[{},{},{}] => [ , , ,]
      this.$request.post("/staff/del/batch",ids)
          .then(res => {
            if(res.data){
              this.$message.success("批量删除成功");
              this.load()
            }else{
              this.$message.error("批量删除失败");
            }
          })
    },
    exp(){
      window.open("http://localhost:9090/staff/export")
    },
    handleExcelImportSuccess(){
      this.$message.success("导入成功");
      this.load();
    }
  }
}
</script>

<style>
.headerBg{
  background-color: lightgray!important;
}
</style>