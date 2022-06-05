<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"/>
      <el-input style="width: 200px" placeholder="请输入描述" suffix-icon="el-icon-location" class="ml-5" v-model="description"/>
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
    </div>

    <el-table
        :data="tableData"
        stripe border height="500"
        :header-cell-class-name="headerBg"
        @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="名称" ></el-table-column>
      <el-table-column prop="flag" label="唯一标识" ></el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-button style="margin-left: 10px" type="info" @click="selectMenu(scope.row)" slot="reference">分配菜单<i class="el-icon-menu"></i></el-button>
          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
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

    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="唯一标识">
          <el-input v-model="form.flag" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="菜单分配" :visible.sync="menuDialogVisible" width="30%">
      <el-tree
          :data="menuData"
          :props="props"
          node-key="id"
          ref="tree"
          :default-expanded-keys="expands"
          :default-checked-keys="checks"
          show-checkbox
         >
        <span class="custom-tree-node" slot-scope="{node, data}">
          <span ><i :class="data.icon"></i> {{data.name}}</span>
        </span>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Role",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      name:"",
      description:"",
      dialogFormVisible:false,
      menuDialogVisible:false,
      multipleSelection:[],
      form:{},
      headerBg:'headerBg',
      menuData:[],
      props:{
        label:'name',
      },
      expands:[],
      checks:[],
      roleId:0 ,
      roleName:'',
      ids:[]

    }
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      this.$request.get("/role/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          description: this.description,
          // address: this.address
        }
      }).then(res =>{
            this.tableData = res.data.records;
            this.total = res.data.total;
          })
      this.$request.get("/menu/ids").then(r =>{
        this.ids = r.data
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
      this.name = "",
          this.description = "",
          this.load()
    },
    handleAdd(){
      this.dialogFormVisible = true,
          this.form = {}
    },
    save(){

      // 可以给取消按钮加个@click="cancel"，
      // 然后定义cancel方法，cancel() { this.dialogFormVisible=false  this.load() },
      this.$request.post("/role",this.form)
          .then(res =>{
            if(res.data){
              this.$message.success("保存成功");
              this.dialogFormVisible = false;
              this.load()
            }else{
              this.$message.error("保存失败");
            }
          })
    },
    handleEdit(row){
      this.form = Object.assign({},row) // 将row拷贝到空对象中 解决没点确定数据改变的问题

      this.dialogFormVisible = true

    },
    handleDel(id){
      this.$request.delete("/user/" + id)
          .then(res =>{
            if(res.data){
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
      this.$request.post("/role/del/batch",ids)
          .then(res => {
            if(res.data){
              this.$message.success("批量删除成功");
              this.load()
            }else{
              this.$message.error("批量删除失败");
            }
          })
    },
    async selectMenu(role){

      this.roleId = role.id;
      this.roleName = role.name;
      //请求菜单数据
      this.$request.get("/menu")
          .then(res =>{
            this.menuData = res.data;
            this.expands = this.menuData.map(v => v.id);
          })

      this.$request.get("/role/roleMenu/" + this.roleId)
          .then(res =>{
            this.checks = res.data;
            this.ids.forEach(id =>{
              if(!this.checks.includes(id)){
                this.$nextTick(() => {
                  this.$refs.tree.setChecked(id, false)
                })
              }
            })
          })
      this.menuDialogVisible = true;
    },

    saveRoleMenu(){
      this.$request.post("/role/roleMenu/" + this.roleId,this.$refs.tree.getCheckedKeys())
      .then(res =>{
        if(res.code === '200'){
          this.$message.success("绑定成功");
          this.menuDialogVisible = false
        }else{
          this.$message.error("绑定失败");
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