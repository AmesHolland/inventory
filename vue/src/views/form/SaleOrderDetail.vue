<template>
  <div>
    <div>
      <el-descriptions class="margin-top" title="销售订单详情表" :column="3" border >
        <template slot="extra">
          <el-button @click="$router.go(-1)" type="primary" size="small"><i class="el-icon-back"></i>返回上一个页面</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            订单编号
          </template>
          {{ this.sale_order.id }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-time"></i>
            订单创建时间
          </template>
          {{ this.sale_order.createTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            销售员ID
          </template>
          {{ this.sale_order.stId }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            销售员姓名
          </template>
          {{ this.sale_order.stName }}
        </el-descriptions-item>
        <el-descriptions-item >
          <template slot="label">
            <i class="el-icon-money">
              订单总金额
            </i>
          </template>
          {{this.sale_order.totalAmount}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            备注
          </template>
          {{ this.sale_order.remark }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            提交状态
          </template>
          {{this.sale_order.submitState}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            审核状态
          </template>
          {{this.sale_order.auditState}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            出货仓库编号
          </template>
          {{this.sale_order.deId}}
        </el-descriptions-item>

      </el-descriptions>
    </div>



    <div style="margin: 10px 0;" >
      <div v-if="sale_order.submitState === '未提交'">
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
        <el-button style="margin-left: 10px" type="success" slot="reference" @click="addProduct()">添加商品<i class="el-icon-plus"></i></el-button>
        <el-button style="margin-left: 10px" type="primary" slot="reference" @click="handleEditRemark()">修改备注<i class="el-icon-edit"></i></el-button>
      </div>
    </div>

    <el-table
        :data="tableData"
        stripe border height="400"
        :header-cell-class-name="headerBg"
        @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column prop="id" label="ID" width="50"></el-table-column>
      <el-table-column prop="caName" label="货物名称"></el-table-column>
      <el-table-column prop="caPrice" label="货物价格"></el-table-column>
      <el-table-column v-if="isSubmit === false" label="货物数量">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.caNum" :disabled="false" :min="1" :max="10000" label="描述文字"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column v-if="isSubmit === true" prop="caNum" label="商品数量"></el-table-column>
      <el-table-column label="操作" width="300px" v-if="sale_order.submitState === '未提交'">
        <template slot-scope="scope">
          <el-button style="margin-left: 10px" type="warning" slot="reference" @click="handleEditProduct(scope.row)">确认修改<i class="el-icon-edit"></i></el-button>
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

    <el-dialog title="销售订单备注" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="100px" size="small">
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
  name: "SaleOrderDetail",
  data(){
    return{
      tableData:[],
      total:0,
      pageNum:1,
      pageSize:10,
      sale_ord_id : null,
      sale_order:{},
      dialogFormVisible:false,
      multipleSelection:[],
      form:{},
      headerBg:'headerBg',
      isSubmit:false
    }
  },
  created(){
    this.sale_ord_id = this.$route.query.id
    this.load()
  },
  methods:{
    load(){
      this.$request.get("/sale_order/" + this.sale_ord_id)
          .then(res =>{
            this.sale_order = res.data
            if(res.data.submitState === '已提交'){
              this.isSubmit = true
            }
          })

      this.$request.get("/sale_order_detail/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          sale_ord_id : this.sale_ord_id
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
      this.stName = ""
      this.load()
    },
    save(){

      // 可以给取消按钮加个@click="cancel"，
      // 然后定义cancel方法，cancel() { this.dialogFormVisible=false  this.load() },
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
    handleEditRemark(){
      this.form = Object.assign({},this.sale_order) // 将row拷贝到空对象中 解决没点确定数据改变的问题
      this.dialogFormVisible = true

    },
    handleEditProduct(row){

      this.$request.post("/sale_order_detail/edit",row)
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


    handleDel(id){
      this.$request.delete("/sale_order_detail/" + id)
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
      this.$request.post("/sale_order_detail/del/batch",ids)
          .then(res => {
            if(res.code === '200'){
              this.$message.success("批量删除成功");
              this.load()
            }else{
              this.$message.error("批量删除失败");
            }
          })
    },
    addProduct(){
      this.$router.push({path:'/add_cargo', query:{id: this.sale_order.id, deId :this.sale_order.deId }})
    }
  }
}
</script>

<style>
.headerBg{
  background-color: lightgray!important;
}
</style>