<template>
  <div>
    <div style="margin: 10px 0">

      <el-input style="width: 200px" placeholder="请输入采购员姓名" suffix-icon="el-icon-phone" class="ml-5" v-model="stName"/>
      <el-select v-model="auditState" placeholder="请选择审核状态" clearable>
        <el-option label="未审核" value="未审核"></el-option>
        <el-option label="审核通过" value="审核通过"></el-option>
        <el-option label="审核不通过" value="审核不通过"></el-option>
      </el-select>
      <el-button class="ml-5"  type="primary" @click="load()">搜索</el-button>
      <el-button type="warning" @click="reset()">重置</el-button>
    </div>
    <el-table
        :data="tableData"
        stripe border height="500"
        :header-cell-class-name="headerBg">
      <el-table-column prop="id" label="采购订单编号" width="50"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="200"></el-table-column>
      <el-table-column prop="stName" label="采购员姓名"></el-table-column>
      <el-table-column prop="auditState" label="审核状态"></el-table-column>
      <el-table-column prop="totalAmount" label="总金额"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column label="审核" width="400" align="center">
        <template slot-scope="scope" >
          <el-button type="primary"  @click="$router.push('/pur_order_detail?id='+ scope.row.id)">修改/查看订单详情<i class="el-icon-edit"></i></el-button>

          <span>
            <el-popconfirm
                class="ml-5"
                confirm-button-text="确定"
                cancel-button-text="取消"
                icon="el-icon-info"
                icon-color="red"
                title="确定通过吗？"
                @confirm="audit(scope.row,'审核通过')"
            >
            <el-button  style="margin-left: 10px" type="success" slot="reference">审核通过<i class="el-icon-success"></i></el-button>
          </el-popconfirm>

          <el-popconfirm
              class="ml-5"
              confirm-button-text="确定"
              cancel-button-text="取消"
              icon="el-icon-info"
              icon-color="red"
              title="确定不通过吗？"
              @confirm="audit(scope.row,'审核不通过')"
          >
            <el-button style="margin-left: 5px" type="danger" slot="reference">审核不通过<i class="el-icon-error"></i></el-button>
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
  </div>
</template>

<script>
export default {
  name: "PurOrderAudit",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      submitState:"已提交",
      auditState:"",
      verifyState:"未核验",
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
          submitState :this.submitState,
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
      this.auditState = ""
      this.stName = ""
      this.load()
    },
    audit(row, state){
      this.form = JSON.parse(JSON.stringify(row))
      this.form.auditState = state
      this.$request.post("/pur_order",this.form)
          .then(res =>{
            if(res.code === '200'){
              this.$message.success("审核成功");
              this.dialogFormVisible = false;
              this.load()
            }else{
              this.$message.error("审核失败");
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