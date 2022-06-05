<template>
  <div>
    <el-descriptions class="margin-top" title="仓库详情表" :column="3" border >
      <template slot="extra">
        <el-button @click="$router.go(-1)" type="primary" size="small"><i class="el-icon-back"></i>返回上一个页面</el-button>
      </template>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          仓库编号
        </template>
        {{ this.depot.id }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location"></i>
          仓库地址
        </template>
        {{ this.depot.address }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          仓库管理员ID
        </template>
        {{ this.depot.stId }}
      </el-descriptions-item>
      <el-descriptions-item >
        <template slot="label">
          <i class="el-icon-money">
            仓库容量
          </i>
        </template>
        {{this.depot.capacity}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          空间余量
        </template>
        {{ this.depot.balance }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          备注
        </template>
        {{ this.depot.remark }}
      </el-descriptions-item>



    </el-descriptions>

    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入货物名称" suffix-icon="el-icon-search" v-model="name"/>
      <el-select style="margin-left: 10px" v-model="type" placeholder="请选择货物类型">
        <el-option label="饮料" value="饮料"></el-option>
        <el-option label="食品" value="食品"></el-option>
        <el-option label="生活用品" value="生活用品"></el-option>
        <el-option label="电子产品" value="电子产品"></el-option>
        <el-option label="五金工具" value="五金工具"></el-option>
        <el-option label="文具" value="文具"></el-option>
        <el-option label="大宗货物" value="大宗货物"></el-option>
      </el-select>

      <el-button class="ml-5"  type="primary" @click="load()">搜索</el-button>
      <el-button type="warning" @click="reset()">重置</el-button>
    </div>


    <el-table
        :data="tableData"
        stripe border height="450"
        :header-cell-class-name="headerBg"
        @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column prop="id" label="ID" width="40"></el-table-column>
      <el-table-column prop="name" label="名称" width="140"></el-table-column>
      <el-table-column prop="type" label="类型" width="80"></el-table-column>
      <el-table-column prop="price" label="价格" width="60"></el-table-column>
      <el-table-column label="图片" >
        <template slot-scope="scope">
          <el-image v-if="scope.row.image" :src="scope.row.image" class="avatar"/>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column prop="stock" label="库存量"></el-table-column>

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

    <el-dialog title="货物信息" :visible.sync="dialogFormVisible" width="30%">
      <el-input v-form="form.id" placeholder="请输入对应商品ID，系统自动导入货物信息" autocomplete="off"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Product",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:5,
      name:"",
      type:"",
      deId :"A01",
      dialogFormVisible:false,
      multipleSelection:[],
      form:{},
      headerBg:'headerBg',
      depotList:[],
      depot:{}
    }
  },
  created(){
    this.deId = this.$route.query.id
    this.load()
  },
  methods:{
    load(){
      this.$request.get("/depot/" + this.deId)
          .then(res =>{
            this.depot = res.data
          })
      this.$request.get("/cargo/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          type: this.type,
          deId : this.deId
        }
      })
          .then(res =>{
            this.tableData = res.data.records;
            this.total = res.data.total;
          });
      this.$request.get("/depot/list")
          .then(res =>{
            this.depotList = res.data
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
      this.suName = ""
      this.load()
    },
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    save(){

      this.$request.post("/cargo",this.form)
          .then(res =>{
            if(res.data){
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
      this.$request.delete("/cargo/" + id)
          .then(res =>{
            if(res.data){
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
      this.$request.post("/cargo/del/batch",ids)
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
      window.open("http://localhost:9090/cargo/export")
    },
    handleExcelImportSuccess(){
      this.$message.success("导入成功");
      this.load();
    },
    handleImageSuccess(res) {
      this.form.image = res
    },
  }
}
</script>

<style>
.headerBg{
  background-color: lightgray!important;
}

 .avatar-uploader{
   text-align: center;
   border: 1px dashed #d9d9d9;
   border-radius: 6px;
   cursor: pointer;
   position: relative;
   overflow: hidden;
   margin-right: 50%;

 }
.avatar-uploader:hover {
  border-color: #409EFF;

}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 130px;
  height: 130px;
  line-height:158px;

}
.avatar {
  width: 130px;
  height: 130px;
  display: block;
}

</style>