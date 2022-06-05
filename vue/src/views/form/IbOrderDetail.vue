<template>
  <div>
    <div>
      <el-descriptions class="margin-top" title="入库单详情表" :column="3" border >
        <template slot="extra">
          <el-button @click="$router.go(-1)" type="primary" size="small"><i class="el-icon-back"></i>返回上一个页面</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            订单编号
          </template>
          {{ this.ib_order.id }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-time"></i>
            订单创建时间
          </template>
          {{ this.ib_order.createTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            采购员ID
          </template>
          {{ this.ib_order.stId }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            采购员姓名
          </template>
          {{ this.ib_order.stName }}
        </el-descriptions-item>
        <el-descriptions-item span="3">
          <template slot="label">
            <i class="el-icon-tickets"></i>
            备注
          </template>
          {{ this.ib_order.remark }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            提交状态
          </template>
          {{this.ib_order.submitState}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            处理状态
          </template>
          {{this.ib_order.procState}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            存放仓库
          </template>
          {{this.ib_order.deId}}
        </el-descriptions-item>
      </el-descriptions>
    </div>



    <div style="margin: 10px 0">
      <el-button style="margin-left: 10px" type="primary" slot="reference" @click="handleEditRemark()">修改备注<i class="el-icon-edit"></i></el-button>
    </div>

    <el-table
        :data="tableData"
        stripe border height="400"
        :header-cell-class-name="headerBg"
        >
      <el-table-column prop="id" label="ID" width="50"></el-table-column>
      <el-table-column prop="prId" label="商品编号"></el-table-column>
      <el-table-column prop="prNum" label="商品数量"></el-table-column>
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

    <el-dialog title="采购订单备注" :visible.sync="dialogFormVisible" width="30%">
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
  name: "IbOrderDetail",
  data(){
    return{
      tableData:[],
      total:0,
      pageNum:1,
      pageSize:10,
      ib_ord_id : null,
      ib_order:{},
      dialogFormVisible:false,
      form:{},
      headerBg:'headerBg',
      isSubmit:false
    }
  },
  created(){
    this.ib_ord_id = this.$route.query.id
    this.load()
  },
  methods:{
    load(){
      this.$request.get("/ib_order/" + this.ib_ord_id)
          .then(res =>{
            this.ib_order = res.data
            if(res.data.submitState === '已提交'){
              this.isSubmit = true
            }
          })

      this.$request.get("/ib_order_detail/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          ibOrdId : this.ib_ord_id
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
    handleEditRemark(){
      this.form = Object.assign({},this.ib_order) // 将row拷贝到空对象中 解决没点确定数据改变的问题
      this.dialogFormVisible = true

    },
    handleEditProduct(row){

      this.$request.post("/ib_order_detail/edit",row)
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

    addProduct(){
      this.$router.push('/add_product?id=' + this.pur_ord_id)
    }
  }
}
</script>

<style>
.headerBg{
  background-color: lightgray!important;
}
</style>