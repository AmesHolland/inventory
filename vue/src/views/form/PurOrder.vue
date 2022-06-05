<template>
  <div>
    <div style="margin: 10px 0">
<!--      <el-input style="width: 200px" placeholder="请输入创建时间" suffix-icon="el-icon-search" v-model="createTime"/>-->
      <el-input style="width: 200px" placeholder="请输入采购员姓名" suffix-icon="el-icon-phone" class="ml-5" v-model="stName"/>
      <el-select v-model="submitState" placeholder="请选择提交状态" clearable>
        <el-option label="未提交" value="未提交"></el-option>
        <el-option label="已提交" value="已提交"></el-option>
      </el-select>
      <el-select v-model="auditState" placeholder="请选择审核状态" clearable>
        <el-option label="未审核" value="未审核"></el-option>
        <el-option label="审核通过" value="审核通过"></el-option>
        <el-option label="审核不通过" value="审核不通过"></el-option>
      </el-select>
      <el-select v-model="verifyState" placeholder="请选择核验状态" clearable>
        <el-option label="未核验" value="未核验"></el-option>
        <el-option label="核验通过" value="核验通过"></el-option>
        <el-option label="核验不通过" value="核验不通过"></el-option>
      </el-select>
      <el-button class="ml-5"  type="primary" @click="load()">搜索</el-button>
      <el-button type="warning" @click="reset()">重置</el-button>
    </div>

    <div style="margin: 10px 0;">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
    </div>

    <el-table
        :data="tableData"
        stripe border height="500"
        :header-cell-class-name="headerBg"
        @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column prop="id" label="采购订单编号" width="50"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="150"></el-table-column>
      <el-table-column prop="stName" label="采购员姓名"></el-table-column>
      <el-table-column prop="submitState" label="提交状态"></el-table-column>
      <el-table-column prop="auditState" label="审核状态"></el-table-column>
      <el-table-column prop="verifyState" label="核验状态"></el-table-column>
      <el-table-column prop="totalAmount" label="总金额"></el-table-column>
      <el-table-column prop="remark" label="备注" width="150"></el-table-column>
      <el-table-column label="操作" width="500px" align="left">
        <template slot-scope="scope" style="text-align: left">
          <el-button style="margin-left: 10px" type="primary"  @click="$router.push('/pur_order_detail?id='+ scope.row.id)">修改/查看订单详情<i class="el-icon-edit"></i></el-button>
          <span v-if="scope.row.submitState === '未提交'" >
            <el-popconfirm
                confirm-button-text="确定"
                cancel-button-text="取消"
                icon="el-icon-info"
                icon-color="red"
                title="确定提交吗？"
                @confirm="handleSub(scope.row)"
            >
            <el-button style="margin-left: 10px" type="success" slot="reference">提交<i class="el-icon-success"></i></el-button>
          </el-popconfirm>
          <el-button style="margin-left: 10px " type="warning"  @click="$router.push('/add_product?id=' + scope.row.id)">添加商品<i class="el-icon-edit"></i></el-button>
          </span>
          <span v-if="!scope.row.isIn ">
            <el-popconfirm
                confirm-button-text="确定"
                cancel-button-text="取消"
                icon="el-icon-info"
                icon-color="red"
                title="确定通过吗？"
                @confirm="verify(scope.row,'核验通过')"
            >
            <el-button v-if="scope.row.auditState === '审核通过'" style="margin-left: 10px" type="success" slot="reference">核验通过<i class="el-icon-success"></i></el-button>
          </el-popconfirm>
            <el-popconfirm
              confirm-button-text="确定"
              cancel-button-text="取消"
              icon="el-icon-info"
              icon-color="red"
              title="确定不通过吗？"
              @confirm="verify(scope.row,'核验不通过')"
          >
            <el-button style="margin-left: 10px " v-if="scope.row.auditState === '审核通过'" type="danger" slot="reference">核验不通过<i class="el-icon-error"></i></el-button>
          </el-popconfirm>
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
          </span>

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

    <el-dialog title="新建采购订单信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="100px" size="small">
        <el-form-item label="采购员编号">
          <el-input v-model="form.stId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="采购员姓名">
          <el-input v-model="form.stName" autocomplete="off"></el-input>
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
  name: "PurOrder",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      submitState:"",
      auditState:"",
      verifyState:"",
      stName:"",
      dialogFormVisible:false,
      multipleSelection:[],
      form:{},
      staff:localStorage.getItem("staff") ? JSON.parse(localStorage.getItem("staff")) :{},
      headerBg:'headerBg'
    }
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      this.$request.get("/pur_order/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          submitState : this.submitState,
          auditState : this.auditState,
          verifyState : this.verifyState,
          stName:this.stName
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
      this.submitState = ""
      this.auditState = ""
      this.verifyState = ""
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
    handleSub(row){
      this.$request.post("/pur_order/sub" , row)
          .then(res =>{
            if(res.code == '200'){
              this.$message.success("提交成功");
              this.load()
            }else{
              this.$message.error("提交失败");
            }
          })

    },
    verify(row, state){
      this.form = JSON.parse(JSON.stringify(row))
      this.form.verifyState = state
      this.$request.post("/pur_order",this.form)
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
      this.$request.post("/pur_order",this.form)
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
      this.$request.delete("/pur_order/" + id)
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
      this.$request.post("/pur_order/del/batch",ids)
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
      window.open("http://localhost:9090/pur_order/export")
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