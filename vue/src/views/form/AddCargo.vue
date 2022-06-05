<template>
  <div>
    <div style=" border-bottom: 1px solid #ccc;display:flex;padding-bottom: 5px;height: 90px">
      <el-descriptions class="margin-top" title="添加货物信息" :column="3" border style="width: 50%">
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            订单编号
          </template>
          {{ this.sale_order.id }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            售货员姓名
          </template>
          {{ this.sale_order.stName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            出货仓库编号
          </template>
          {{ this.sale_order.deId }}
        </el-descriptions-item>
      </el-descriptions>
      <span style="font-size: 30px;color: orange;margin: 30px 10px 10px 30px">
        订单总金额: ￥ {{ this.sale_order.totalAmount }}
      </span>
      <span style="margin: 40px 10px">
        <el-button @click="$router.go(-1)" type="primary" size="small" ><i class="el-icon-back"></i>返回上一个页面</el-button>
      </span>
      <span style="margin: 40px 10px">
        <el-button type="success" @click="orderDetail()" size="small" >订单详情页</el-button>
      </span>

    </div>


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

      <el-select disabled style="margin-left: 10px" v-model="deId" placeholder="请选择仓库">
          <el-option
              v-for="item in depotList"
              :key="item.id"
              :label="item.id + ' ' + item.address + ' ' + item.remark"
              :value="item.id">
          </el-option>
      </el-select>

      <el-button class="ml-5"  type="primary" @click="load()">搜索</el-button>
      <el-button type="warning" @click="reset()">重置</el-button>
    </div>
    <el-table
        :data="tableData"
        stripe border height="500"
        :header-cell-class-name="headerBg"
        @selection-change="handleSelectionChange">
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
      <el-table-column label="货物数量">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.num" :min="1" :max="scope.row.stock" label="描述文字"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column prop="stock" label="库存余量"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="warning" @click="addProduct(scope.row)">添加货物<i class="el-icon-plus"></i></el-button>
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
  name: "AddProduct",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      name:"",
      type:"",
      stock:"",
      deId:"",
      dialogFormVisible:false,
      multipleSelection:[],
      form:{},
      headerBg:'headerBg',
      options:[],
      sale_ord_id : 0,
      sale_order:{},
      depotList:[],
    }
  },
  created(){
    this.sale_ord_id = this.$route.query.id
    this.deId = this.$route.query.deId
    this.load()
  },
  methods:{
    load(){
      this.$request.get("/sale_order/" + this.sale_ord_id)
          .then(res =>{
            this.sale_order = res.data

          })
      this.$request.get("/depot/list")
          .then(res =>{
            this.depotList = res.data
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
      this.load()
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val;
    },
    addProduct(row){

      this.form = {
        saleOrdId : this.sale_ord_id,
        caId : row.id,
        caNum : row.num,
      }

      this.$request.post("/sale_order_detail", this.form)
          .then(res =>{
            if(res.code === '200'){
              this.$message.success("保存成功");
              this.load()
            }else{
              this.$message.error("保存失败");
            }
          })
    },
    orderDetail(){
      this.$router.push('/sale_order_detail?id='+ this.sale_ord_id)
    }
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