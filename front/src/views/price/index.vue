<template>
  <div class="app-container">
    <div ref="chart" style="width: 600px; height: 400px;"></div>

    <!-- Search Box -->
    <el-input v-model="search" placeholder="Enter search content" class="filter-item" style="width: 300px;"
      @keyup.enter.native="handleSearch">
      <el-button slot="append" icon="el-icon-search" @click="handleSearch" />
    </el-input>

    <!-- Price Table -->
    <el-table :data="filteredData.slice((currentPage - 1) * pageSize, currentPage * pageSize)" style="width: 50%">
      <el-table-column prop="epid" label="EP ID" width="80" sortable />
      <el-table-column prop="zipcode" label="Zipcode" width="120" sortable />
      <el-table-column prop="price" label="Price ($)" width="120" sortable />
      <el-table-column prop="time" label="Time" width="250" sortable />
      <el-table-column prop="isDeleted" label="Deleted" width="100" sortable />
    </el-table>

    <!-- Pagination -->
    <el-pagination :current-page="currentPage" :page-sizes="[5, 10, 20, 50]" :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="filteredData.length" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" />
  </div>
</template>

<script>
import axios from 'axios'
import * as echarts from 'echarts';

export default {
  data() {
    return {
      search: '',
      currentPage: 1,
      pageSize: 10,
      tableData: []
    }
  },
  computed: {
    filteredData() {
      if (this.search) {
        return this.tableData.filter(data =>
          Object.values(data).some(value =>
            value != null && value.toString().toLowerCase().includes(this.search.toLowerCase())
          )
        )
      } else {
        return this.tableData
      }
    }
  },
  mounted() {
    this.fetchPrices()
    // this.initChart();
    this.fetchDataAndDrawChart();

  },
  methods: {


    fetchDataAndDrawChart() {
      // 硬编码的电费数据
      var morningPrices = []; // 早晨电费
      var eveningPrices = []; // 晚间电费
      const userId = localStorage.getItem('userId');
      const userRole = localStorage.getItem('userRole');
      var url = "";
      if (userRole === 'USER') {
        url = 'http://localhost:8080/price/getRecentByID/'+userId;
      } else {
        url = 'http://localhost:8080/price/getRecent';
      }
      axios.get(url)
        .then(response => {
          console.log(userId)
          console.log(response.data)
          console.log(userRole)
          var pricess = response.data.data.prices;
          for (let i = 0; i < response.data.data.prices.length; i++) {
            if (pricess[i].timeSlot === "DayTime") {
              morningPrices.push(pricess[i].price)
            } else {
              eveningPrices.push(pricess[i].price)
            }

          }
          console.log(morningPrices)

          // 基于准备好的dom，初始化echarts实例
          const chart = echarts.init(this.$refs.chart);

          // 指定图表的配置项和数据
          const option = {
            title: {
              text: 'Last 7 Days Price'
            },
            tooltip: {
              trigger: 'axis'
            },
            legend: {
              data: ['Day Time Energy Price', 'Night Time Energy Price']
            },
            xAxis: {
              type: 'category',
              boundaryGap: false,
              data: ['6 Day ago', '5 Days ago', '4 Days ago', '3 Days ago', '2 Days ago', '1 Days ago', 'Today']
            },
            yAxis: {
              type: 'value',
              min: 0,
              max: 2
            },
            series: [
              {
                name: 'Day Time',
                type: 'line',
                data: morningPrices
              },
              {
                name: 'Night Time',
                type: 'line',
                data: eveningPrices
              }
            ]
          };

          // 使用刚指定的配置项和数据显示图表。
          chart.setOption(option);

        })
        .catch(error => {
          console.error('Error fetching prices:', error)
        })


    },
    fetchPrices() {
      axios.get('http://localhost:8080/price/all')
        .then(response => {
          //console.log(response.data)
          this.tableData = response.data.data.prices.map(price => ({
            epid: price.epid,
            zipcode: price.zipcode,
            price: price.price,
            time: price.time,
            isDeleted: price.isDeleted ? 'Yes' : 'No'
          }))
        })
        .catch(error => {
          console.error('Error fetching prices:', error)
        })
    },
    
    handleSearch() {
      this.currentPage = 1
    },
    handleSizeChange(newSize) {
      this.pageSize = newSize
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage
    }
  }
}

</script>

<style>
.app-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.filter-item {
  margin-bottom: 20px;
}
</style>
