<template>
  <el-card class="introduce">
    <div class="order">
      <el-card class="order-item">
        <template #header>
          <div class="card-header">
            <span>总用户数</span>
          </div>
        </template>
        <div class="item">{{ dashBoardData.user }}</div>
      </el-card>
      <el-card class="order-item">
        <template #header>
          <div class="card-header">
            <span>总藏品数</span>
          </div>
        </template>
        <div class="item">{{ dashBoardData.collection }}</div>
      </el-card>
      <el-card class="order-item">
        <template #header>
          <div class="card-header">
            <span>总商品数</span>
          </div>
        </template>
        <div class="item">{{ dashBoardData.product }}</div>
      </el-card>
    </div>
    <div id="zoom"></div>
  </el-card>
</template>

<script setup>
import {onMounted, onUnmounted, reactive, ref} from 'vue'
import {getChartData, getSysInfo} from "../../api/home";

let myChart = null

const dashBoardData = reactive({
  product: 0,
  collection: 0,
  user: 0
})

const order_num = ref([])
const order_money = ref([])

onMounted(() => {
  const initDashBoard = async function () {
    const res = await getSysInfo()
    console.log(res)
    dashBoardData.product = res.product
    dashBoardData.collection = res.collection
    dashBoardData.user = res.user
  }
  initDashBoard()

  function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  function generatePastSevenDays() {
    const dates = [];
    for (let i = 6; i >= 0; i--) {
      const currentDate = new Date();
      currentDate.setDate(currentDate.getDate() - i);
      const formattedDate = formatDate(currentDate);
      dates.push(formattedDate);
    }
    return dates;
  }

  if (window.echarts) {
    // 基于准备好的dom，初始化echarts实例
    myChart = window.echarts.init(document.getElementById('zoom'))

    // 使用刚指定的配置项和数据显示图表。
    const processChartData = async function (days) {
      const res = await getChartData(days)
      let t_on = []
      let t_om = []


      for (let i = days-1; i >= 0; i --) {
        t_on.push(res[i].order_num)
        t_om.push(res[i].order_money)
      }

      order_num.value = t_on
      order_money.value = t_om

      // 指定图表的配置项和数据
      const option = {
        title: {
          text: '订单数与订单金额每日变化'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          data: ['订单数', '订单金额']
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: generatePastSevenDays()//['2021-03-11', '2021-03-12', '2021-03-13', '2021-03-14', '2021-03-15', '2021-03-16', '2021-03-17']
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '订单数',
            type: 'line',
            // stack: '总量',
            areaStyle: {},
            emphasis: {
              focus: 'series'
            },
            data: order_num.value //[20, 132, 101, 134, 90, 230, 210]
          },
          {
            name: '订单金额',
            type: 'line',
            // stack: '总量',
            areaStyle: {},
            emphasis: {
              focus: 'series'
            },
            data: order_money.value // [220, 182, 191, 234, 290, 330, 310]
          }
        ]
      }
      myChart.setOption(option)
    }
    processChartData(7)
  } 
})
onUnmounted(() => {
  myChart.dispose()
})
</script>

<style scoped>
  .introduce .order {
    display: flex;
    margin-bottom: 50px;
  }
  .introduce .order .order-item {
    flex: 1;
    margin-right: 20px;
  }
  .introduce .order .order-item:last-child{
    margin-right: 0;
  }
  #zoom {
    min-height: 300px;
  }
</style>