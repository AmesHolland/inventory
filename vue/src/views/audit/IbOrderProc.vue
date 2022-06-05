<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入采购员姓名" suffix-icon="el-icon-phone" class="ml-5" v-model="stName"/>
      <el-input style="width: 200px" placeholder="请输入提交状态" suffix-icon="el-icon-location" class="ml-5" v-model="submitState"/>
      <el-input style="width: 200px" placeholder="请输入处理状态" suffix-icon="el-icon-location" class="ml-5" v-model="procState"/>
      <el-button class="ml-5"  type="primary" @click="load()">搜索</el-button>
      <el-button type="warning" @click="reset()">重置</el-button>
    </div>

    <el-table
        :data="tableData"
        stripe border height="500"
        :header-cell-class-name="headerBg"
        >

      <el-table-column prop="id" label="入库单编号" width="50"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="150"></el-table-column>
      <el-table-column prop="stName" label="采购员姓名" width="100"></el-table-column>
      <el-table-column prop="submitState" label="提交状态" width="100"></el-table-column>
      <el-table-column prop="procState" label="处理状态" width="100"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column label="操作" width="350px" align="center">
        <template slot-scope="scope" style="text-align: center">
          <el-popconfirm
              class="ml-5"
              confirm-button-text="确定"
              cancel-button-text="取消"
              icon="el-icon-info"
              icon-color="red"
              title="确定入库吗？"
              @confirm="handleInbound(scope.row,'已处理')"
          >
            <el-button v-if="(staff.role === '仓库管理员' || staff.role === '管理员') && scope.row.procState === '未处理'" type="success" slot="reference">一键入库<i class="el-icon-success"></i></el-button>
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
            <el-button style="margin-left: 5px" type="danger" slot="reference">删除<i class="el-icon-delete"></i></el-button>
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

  </div>
</template>

<script>
export default {
  name: "IbOrderProc",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      purSubmitState:"已提交",
      submitState:"已提交",
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

    handleInbound(row,state){
      this.form = JSON.parse(JSON.stringify(row))
      this.form.procState = state
      this.$request.post("/ib_order",this.form)
          .then(res =>{
            if(res.code === '200'){
              this.$message.success("入库成功");
              this.dialogFormVisible = false;
              this.load()
            }else{
              this.$message.error("入库失败");
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

  }
}
</script>

<style>
.headerBg{
  background-color: lightgray!important;
}
</style>