<template>
  <div>
    <div style=" border-bottom: 1px solid #ccc;display:flex;padding-bottom: 5px;height: 90px">
      <el-descriptions class="margin-top" title="添加商品信息" :column="3" border style="width: 50%">
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            订单编号
          </template>
          {{ this.pur_order.id }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            采购员姓名
          </template>
          {{ this.pur_order.stName }}
        </el-descriptions-item>
      </el-descriptions>
      <span style="font-size: 30px;color: orange;margin: 30px 10px 10px 30px">
        订单总金额: ￥ {{ this.pur_order.totalAmount }}
      </span>
      <span style="margin: 40px 10px">
        <el-button @click="$router.go(-1)" type="primary" size="small" ><i class="el-icon-back"></i>返回上一个页面</el-button>
      </span>
      <span style="margin: 40px 10px">
        <el-button type="success" @click="orderDetail()" size="small" >订单详情页</el-button>
      </span>

    </div>


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
    <el-table
        :data="tableData"
        stripe border height="500"
        :header-cell-class-name="headerBg"
        @selection-change="handleSelectionChange">
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
      <el-table-column label="商品数量">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.num"  :min="1" :max="10000" label="描述文字"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="warning" @click="addProduct(scope.row)">添加商品<i class="el-icon-plus"></i></el-button>
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
      suName:"",
      dialogFormVisible:false,
      multipleSelection:[],
      form:{},
      headerBg:'headerBg',
      options:[],
      pur_ord_id : 0,
      pur_order:{},
    }
  },
  created(){
    this.pur_ord_id = this.$route.query.id
    this.load()
  },
  methods:{
    load(){
      this.$request.get("/pur_order/" + this.pur_ord_id)
          .then(res =>{
            this.pur_order = res.data
          })

      this.$request.get("/product/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          type: this.type,
          suName: this.suName
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
      this.suName = ""
      this.load()
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val;
    },
    addProduct(row){

      this.form = {
        purOrdId : this.pur_ord_id,
        prId : row.id,
        prNum : row.num,
      }

      this.$request.post("/pur_order_detail", this.form)
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
      this.$router.push('/pur_order_detail?id='+ this.pur_ord_id)
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