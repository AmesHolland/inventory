<template>
  <div>
    <div style="margin: 10px 0">
      <el-date-picker
          v-model="mydate"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          format="yyyy 年 MM 月 dd 日"
          value-format="yyyy-MM-dd"
          @change="get()"
      >
      </el-date-picker>
      <el-button style="margin-left: 10px" class="ml-5"  type="primary" @click="load()">搜索</el-button>
      <el-button type="warning" @click="reset()">重置</el-button>

    </div>

    <div style="margin: 10px 0;">
      <el-button style="margin-left: 10px" type="primary" @click="exp">导出<i class="el-icon-top"></i></el-button>
    </div>

    <el-table
        :data="tableData"
        stripe border height="500"
        :header-cell-class-name="headerBg"
        >

      <el-table-column prop="id" label="ID" width="140"></el-table-column>
      <el-table-column prop="date" label="日期" width="140"></el-table-column>
      <el-table-column prop="pur" label="支出金额"></el-table-column>
      <el-table-column prop="sale" label="支出金额"></el-table-column>
      <el-table-column prop="balance" label="盈利"></el-table-column>

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
  name: "Data",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      year:"",
      month:"",
      day:"",
      dialogFormVisible:false,
      multipleSelection:[],
      form:{},
      headerBg:'headerBg',
      mydate:["",""],
      begin:"",
      end:""

    }
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      this.$request.get("/data/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          begin: this.begin,
          end : this.end
        }
      })
          .then(res =>{
            this.tableData = res.data.records;
            this.total = res.data.total;
          })
    },
    get(){
      this.begin = this.mydate[0]
      this.end = this.mydate[1]
    }
    ,
    handleSizeChange(pageSize){
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum;
      this.load();
    },
    reset(){
      this.mydate = ["",""]
      this.begin = ""
      this.end = ""
      this.load()
    },
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },

    exp(){
      window.open("http://localhost:9090/data/export")
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