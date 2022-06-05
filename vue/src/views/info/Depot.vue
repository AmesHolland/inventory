<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入仓库编号" suffix-icon="el-icon-search" v-model="id"/>
      <el-input style="width: 200px" placeholder="请输入管理员姓名" suffix-icon="el-icon-phone" class="ml-5" v-model="stName"/>
      <el-input style="width: 200px" placeholder="请输入仓库地址" suffix-icon="el-icon-location" class="ml-5" v-model="address"/>
      <el-button class="ml-5"  type="primary" @click="load()">搜索</el-button>
      <el-button type="warning" @click="reset()">重置</el-button>
    </div>

    <div style="margin: 10px 0;">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>

      <el-upload action="http://localhost:9090/depot/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button style="margin-left: 10px" type="primary">导入<i class="el-icon-bottom"></i></el-button>
      </el-upload>


      <el-button style="margin-left: 10px" type="primary" @click="exp">导出<i class="el-icon-top"></i></el-button>
    </div>

    <el-table
        :data="tableData"
        stripe border height="500"
        :header-cell-class-name="headerBg"
        >

      <el-table-column prop="id" label="仓库编号" width="100"></el-table-column>
      <el-table-column prop="address" label="仓库地址" ></el-table-column>
      <el-table-column prop="capacity" label="仓库容量" width="100"></el-table-column>
      <el-table-column prop="balance" label="仓库余量" width="100"></el-table-column>
      <el-table-column prop="stName" label="管理员姓名" width="100"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button v-if="scope.row.st_id === form.stId" type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
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

    <el-dialog title="仓库信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="100px" size="small">
        <el-form-item label="仓库编号">
          <el-input v-model="form.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="仓库地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="仓库管理员ID">
          <el-input v-model="form.stId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="仓库容量">
          <el-input v-model="form.capacity" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item  label="仓库空间余量">
          <el-input v-model="form.balance" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false;this.isAdd = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Depot",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      id:"",
      stName:"",
      address:"",
      dialogFormVisible:false,
      form:{},
      headerBg:'headerBg'
    }
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      this.$request.get("/depot/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          id: this.id,
          stName: this.stName,
          address: this.address
        }
      })
          .then(res =>{
            this.tableData = res.data.records;
            this.total = res.data.total;
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
      this.id = ""
      this.stName = ""
      this.address = ""
      this.load()
    },
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {
        capacity : 50000,
        balance : 50000
      }
    },
    save(){
      this.$request.post("/depot",this.form)
          .then(res =>{
            if(res.code === '200'){
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
      this.dialogFormVisible = true

    },
    handleDel(id){
      this.$request.delete("/depot/" + id)
          .then(res =>{
            if(res.cdoe === '200'){
              this.$message.success("删除成功");
              this.load()
            }else{
              this.$message.error("删除失败");
            }
          })
    },
    exp(){
      window.open("http://localhost:9090/depot/export")
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