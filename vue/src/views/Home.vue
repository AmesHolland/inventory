<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 40px">
      <el-col :span="6">
        <el-card style="color:#409EFF;">
          <div><i class="el-icon-user"></i>员工总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            {{this.totalMemeber}}
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color:#F56C6C;">
          <div><i class="el-icon-money"></i>销售总量</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
           ￥ {{ this.saleTotal }}
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color:#67C23A;">
          <div><i class="el-icon-bank-card"></i>采购总量</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            ￥ {{this.purTotal}}
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color:#E6A23C;">
          <div><i class="el-icon-s-shop"></i>仓库总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            {{ this.depotNum }}
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 500px; height: 400px"></div>
      </el-col>
      <el-col :span="12">
        <div id="pie" style="width: 500px; height: 400px"></div>
      </el-col>
    </el-row>
  </div>


</template>

<script>

import * as echarts from 'echarts'

export default {
  name: "Home",
  data(){
    return{
      purTotal : 0,
      saleTotal : 0,
      totalMemeber : 0,
      depotNum :0
    }
  },
  mounted() {
    let chartDom = document.getElementById('main');
    let myChart = echarts.init(chartDom);
    let option;

    option = {
      xAxis: {
        type: 'category',
        data: []
      },
      yAxis: {
        type: 'value'
      },
      tooltip: {
        trigger: 'item',
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name:'采购额度',
          data: [],
          type: 'line'
        },
        {
          name:'销售额度',
          data: [],
          type: 'line'
        }
      ]
    };

    let pieDom = document.getElementById('pie');
    let pieChart = echarts.init(pieDom);
    let pieOption;

    pieOption = {
      title: {
        text: '员工比例',
        subtext: '比例图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter:'{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: '员工',
          type: 'pie',
          radius: '60%',
          center:['50%', '50%'],
          data: [],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            },
          },
        },
      ]
    };

    this.$request.get("/echarts/balance")
        .then(res =>{
          option.xAxis.data = res.data.x
          option.series[0].data = res.data.pur
          option.series[1].data = res.data.sale
          this.purTotal = res.data.purTotal
          this.saleTotal = res.data.saleTotal
          this.depotNum = res.data.depotNum

          myChart.setOption(option)

        })

    this.$request.get("/echarts/members")
        .then(res =>{

          pieOption.series[0].data = res.data
          this.totalMemeber = res.data.length
          pieChart.setOption(pieOption)

        })

  }
}
</script>
