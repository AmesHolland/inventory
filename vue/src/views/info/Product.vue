<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入商品名称" suffix-icon="el-icon-search" v-model="name"/>
      <el-select v-model="type" placeholder="请选择商品类型">
        <el-option label="饮料" value="饮料"></el-option>
        <el-option label="食品" value="食品"></el-option>
        <el-option label="生活用品" value="生活用品"></el-option>
        <el-option label="电子产品" value="电子产品"></el-option>
        <el-option label="五金工具" value="五金工具"></el-option>
        <el-option label="文具" value="文具"></el-option>
        <el-option label="大宗货物" value="大宗货物"></el-option>
      </el-select>
      <el-input style="width: 200px" placeholder="请输入商品供应商" suffix-icon="el-icon-location" class="ml-5" v-model="suName"/>

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

      <el-upload action="http://localhost:9090/product/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
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
      <el-table-column prop="id" label="ID" width="40"></el-table-column>
      <el-table-column prop="name" label="名称" width="140"></el-table-column>
      <el-table-column prop="suName" label="供应商" width="140"></el-table-column>
      <el-table-column prop="type" label="类型" width="80"></el-table-column>
      <el-table-column prop="price" label="价格" width="60"></el-table-column>
      <el-table-column label="图片" >
        <template slot-scope="scope">
          <el-image v-if="scope.row.image" :src="scope.row.image" class="avatar"/>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
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
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品类型">
          <el-select v-model="form.type" placeholder="请选择商品类型">
            <el-option label="饮料" value="饮料"></el-option>
            <el-option label="食品" value="食品"></el-option>
            <el-option label="生活用品" value="生活用品"></el-option>
            <el-option label="电子产品" value="电子产品"></el-option>
            <el-option label="五金工具" value="五金工具"></el-option>
            <el-option label="文具" value="文具"></el-option>
            <el-option label="大宗货物" value="大宗货物"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商">
          <el-select v-model="form.suName" clearable placeholder="请选择供应商">
            <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.brand"
                :value="item.brand">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="form.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图片">
          <div style="height: 150px" >
            <el-upload
                class="avatar-uploader"
                action="http://localhost:9090/file/upload"
                :show-file-list="false"
                :on-success="handleImageSuccess">
              <img v-if="form.image" :src="form.image" class="avatar"/>
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </div>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
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
  name: "Product",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      name:"",
      type:"",
      suName:"",
      dialogFormVisible:false,
      multipleSelection:[],
      form:{},
      headerBg:'headerBg',
      options:[],
    }
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      this.$request.get("/product/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          type: this.type,
          description: this.description
        }

      })
          .then(res =>{
            this.tableData = res.data.records;
            this.total = res.data.total;
          });
      this.$request.get("/supply/brandList")
          .then(res =>{
            if(res.code === '200'){
              this.options = res.data
            }
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

      this.options.map((item) =>{
        if(item.brand === this.form.suName){
          this.form.suId = item.id
        }
      })
      //console.log(this.form)
      // 可以给取消按钮加个@click="cancel"，
      // 然后定义cancel方法，cancel() { this.dialogFormVisible=false  this.load() },
      this.$request.post("/product",this.form)
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
      this.$request.delete("/product/" + id)
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
      this.$request.post("/product/del/batch",ids)
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
      window.open("http://localhost:9090/product/export")
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