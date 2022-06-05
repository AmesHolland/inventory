<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入采购员姓名" suffix-icon="el-icon-phone" class="ml-5" v-model="stName"/>
      <el-input style="width: 200px" placeholder="请输入提交状态" suffix-icon="el-icon-location" class="ml-5" v-model="submitState"/>
      <el-input style="width: 200px" placeholder="请输入处理状态" suffix-icon="el-icon-location" class="ml-5" v-model="procState"/>
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
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column prop="id" label="入库单编号" width="50"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="150"></el-table-column>
      <el-table-column prop="stName" label="采购员姓名" width="100"></el-table-column>
      <el-table-column prop="submitState" label="提交状态" width="100"></el-table-column>
      <el-table-column prop="procState" label="处理状态" width="100"></el-table-column>
      <el-table-column prop="deId" label="存货仓库" width="80"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column label="操作" width="350px" align="center">
        <template slot-scope="scope" style="text-align: center">
          <el-popconfirm
              class="ml-5"
              confirm-button-text="确定"
              cancel-button-text="取消"
              icon="el-icon-info"
              icon-color="red"
              title="确定提交吗？"
              @confirm="handleSub(scope.row, '已提交')"
          >
            <el-button v-if="scope.row.submitState === '未提交'" type="success" slot="reference">提交<i class="el-icon-success"></i></el-button>
          </el-popconfirm>

          <el-button style="margin-left: 10px;" type="primary"  @click="$router.push('/ib_order_detail?id='+ scope.row.id)">查看订单详情<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text="确定"
              cancel-button-text="取消"
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="handleDel(scope.row.id)"
          >
            <el-button v-if="scope.row.procState === '未处理'" style="margin-left: 5px" type="danger" slot="reference">删除<i class="el-icon-delete"></i></el-button>
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

    <el-dialog title="新建入库订单信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="100px" size="small">
        <el-form-item label="采购员编号">
          <el-input v-model="form.stId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="采购员姓名">
          <el-input v-model="form.stName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="采购订单选择">
          <el-select v-model="form.purOrdId" @change="selectDepotList" clearable placeholder="请选择订单">
            <el-option
                v-for="item in options"
                :key="item.id"
                :label="'订单编号: ' + item.id +' 商品总数: ' +  item.totalNum + ' 备注: ' + item.remark"
                :value="item.id"
                v-if="item.isIn === false"
                >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="仓库选择">
          <el-select v-model="form.deId" clearable placeholder="请选择订单">
            <el-option
                v-for="item in depotList"
                :key="item.id"
                :label="'仓库编号: ' + item.id +' 仓库地址: ' +  item.address + ' 仓库空间余量: ' + item.balance"
                :value="item.id"
                v-if="item.balance >= pur_order.totalNum"
            >
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
  name: "IbOrder",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      purSubmitState:"已提交",
      submitState:"",
      auditState: "审核通过",
      verifyState: "核验通过",
      procState:"",
      stName:"",
      dialogFormVisible:false,
      multipleSelection:[],
      form:{},
      staff:localStorage.getItem("staff") ? JSON.parse(localStorage.getItem("staff")) :{},
      headerBg:'headerBg',
      options:[],
      totalNum : 0,
      pur_order:{},
      pur_order_id: 0,
      depotList:[]
    }
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      this.$request.get("/ib_order/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          submitState : this.submitState,
          procState : this.procState,
          stName:this.stName
        }
      })
          .then(res =>{
            this.tableData = res.data.records;
            this.total = res.data.total;
          })

      this.$request.get("/pur_order/page",{
        params:{
          pageNum: 1,
          pageSize: 10,
          submitState : this.purSubmitState,
          auditState :  this.auditState,
          verifyState :  this.verifyState,
          stName:""
        }
      }).then(res =>{
        this.options = res.data.records
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
      this.$request.post("/ib_order",this.form)
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

      this.$request.post("/ib_order",this.form)
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
      this.$request.delete("/ib_order/" + id)
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
      this.multipleSelection = val;
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id) //[{},{},{}] => [ , , ,]
      this.$request.post("/ib_order/del/batch",ids)
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
      window.open("http://localhost:9090/ib_order/export")
    },
    handleExcelImportSuccess(){
      this.$message.success("导入成功");
      this.load();
    },
    selectDepotList(val){

      this.pur_order_id = val

      this.$request.get("/pur_order/" + this.pur_order_id)
          .then(res =>{
            this.pur_order = res.data
          })

      this.$request.get("/depot/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          id: "",
          stName: "",
          address: ""
        }
      })
          .then(res =>{
            this.depotList = res.data.records;
            this.total = res.data.total;
          })
    }
  }
}
</script>

<style>
.headerBg{
  background-color: lightgray!important;
}

</style>