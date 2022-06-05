<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入销售员姓名" suffix-icon="el-icon-phone" class="ml-5" v-model="stName"/>
      <el-select v-model="submitState" placeholder="请选择提交状态" clearable>
        <el-option label="未提交" value="未提交"></el-option>
        <el-option label="已提交" value="已提交"></el-option>
      </el-select>
      <el-select v-model="auditState" placeholder="请选择审核状态" clearable>
        <el-option label="未审核" value="未审核"></el-option>
        <el-option label="审核通过" value="审核通过"></el-option>
        <el-option label="审核不通过" value="审核不通过"></el-option>
      </el-select>
      <el-input style="width: 200px" placeholder="请输入客户姓名" suffix-icon="el-icon-phone" class="ml-5" v-model="clName"/>
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

<!--      <el-upload action="http://localhost:9090/client/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">-->
<!--        <el-button style="margin-left: 10px" type="primary">导入<i class="el-icon-bottom"></i></el-button>-->
<!--      </el-upload>-->
<!--      <el-button style="margin-left: 10px" type="primary" @click="exp">导出<i class="el-icon-top"></i></el-button>-->
    </div>

    <el-table
        :data="tableData"
        stripe border height="500"
        :header-cell-class-name="headerBg"
        @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="id" label="销售订单编号" width="50"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="150"></el-table-column>
      <el-table-column prop="stName" label="售货员姓名"></el-table-column>
      <el-table-column prop="submitState" label="提交状态" width="60"></el-table-column>
      <el-table-column prop="auditState" label="审核状态" width="80"></el-table-column>
      <el-table-column prop="deId" label="出货仓库编号" width="50"></el-table-column>
      <el-table-column prop="totalAmount" label="总金额" ></el-table-column>
      <el-table-column prop="remark" label="备注" width="150"></el-table-column>
      <el-table-column prop="clName" label="客户姓名"></el-table-column>
      <el-table-column label="操作" width="430px" align="center">
        <template slot-scope="scope" style="text-align: center">
          <el-popconfirm
              confirm-button-text="确定"
              cancel-button-text="取消"
              icon="el-icon-info"
              icon-color="red"
              title="确定提交吗？"
              @confirm="handleSub(scope.row, '已提交')"
          >
            <el-button v-if="scope.row.submitState === '未提交'" type="success" slot="reference">提交<i class="el-icon-success"></i></el-button>
          </el-popconfirm>
          <el-button style="margin-left: 10px " v-if="scope.row.submitState === '未提交'" type="warning"  @click="$router.push({path:'/add_cargo', query:{id: scope.row.id, deId :scope.row.deId }})">添加商品<i class="el-icon-edit"></i></el-button>


          <el-button style="margin-left: 10px" type="primary"  @click="$router.push('/sale_order_detail?id='+ scope.row.id)">修改/查看订单详情<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
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

    <el-dialog title="新建销售订单信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="100px" size="small">
        <el-form-item label="销售员编号">
          <el-input v-model="form.stId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="销售员姓名">
          <el-input v-model="form.stName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="客户姓名">
          <el-select v-model="form.clId" clearable placeholder="请选择客户姓名">
            <el-option
                v-for="item in clientList"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出货仓库">
          <el-select v-model="form.deId" placeholder="请选择仓库">
            <el-option
                v-for="item in depotList"
                :key="item.id"
                :label="item.id + ' ' + item.address + ' ' + item.remark"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="textarea" v-model="form.remark" autocomplete="off"></el-input>
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
  name: "SaleOrder",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      submitState:"",
      auditState:"",
      clName:"",
      stName:"",
      clId:0,
      dialogFormVisible:false,
      multipleSelection:[],
      form:{},
      staff:localStorage.getItem("staff") ? JSON.parse(localStorage.getItem("staff")) :{},
      headerBg:'headerBg',
      options:[],
      clientList:[],
      depotList:[]
    }
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      this.$request.get("/sale_order/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          submitState : this.submitState,
          auditState : this.auditState,
          clName : this.clName,
          stName:this.stName
        }
      })
          .then(res =>{
            this.tableData = res.data.records;
            this.total = res.data.total;
          })
      this.$request.get("/client/clientList")
          .then(res =>{
            this.clientList= res.data;
          })
      this.$request.get("/depot/list")
          .then(res =>{
            this.depotList = res.data
            console.log(this.depotList)
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
      this.createTime = ""
      this.stName = ""
      this.load()
    },
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {
        stId : this.staff.id,
        stName : this.staff.name
      }
    },
    handleSub(row,state){
      this.form = JSON.parse(JSON.stringify(row))
      this.form.submitState = state
      this.$request.post("/sale_order",this.form)
          .then(res =>{
            if(res.code === '200'){
              this.$message.success("提交成功");
              this.dialogFormVisible = false;
              this.load()
            }else{
              this.$message.error("提交失败");
            }
          })

    },
    verify(row, state){
      this.form = JSON.parse(JSON.stringify(row))
      this.form.verifyState = state
      this.$request.post("/sale_order",this.form)
          .then(res =>{
            if(res.code === '200'){
              this.$message.success("修改成功");
              this.dialogFormVisible = false;
              this.load()
            }else{
              this.$message.error("修改失败");
            }
          })
    },
    save(){

      // 可以给取消按钮加个@click="cancel"，
      // 然后定义cancel方法，cancel() { this.dialogFormVisible=false  this.load() },
      this.$request.post("/sale_order",this.form)
          .then(res =>{
            if(res.code === '200'){
              this.$message.success("新增成功");
              this.dialogFormVisible = false;
              this.load()
            }else{
              this.$message.error("新增失败");
            }
          })
    },
    handleDel(id){
      this.$request.delete("/sale_order/" + id)
          .then(res =>{
            if(res.code == '200'){
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
      this.$request.post("/sale_order/del/batch",ids)
          .then(res => {
            if(res.code === '200'){
              this.$message.success("批量删除成功");
              this.load()
            }else{
              this.$message.error("批量删除失败");
            }
          })
    },
    exp(){
      window.open("http://localhost:9090/sale_order/export")
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