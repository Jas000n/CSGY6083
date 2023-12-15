<template>
  <div class="app-container">
    <!-- Search Box -->
    <el-input
      v-model="search"
      placeholder="Enter search content"
      class="filter-item"
      style="width: 300px;"
      @keyup.enter.native="handleSearch"
    >
      <el-button slot="append" icon="el-icon-search" @click="handleSearch" />
    </el-input>

    <!-- Price Table -->
    <el-table :data="filteredData.slice((currentPage-1)*pageSize, currentPage*pageSize)" style="width: 50%">
      <el-table-column prop="epid" label="EP ID" width="80" sortable />
      <el-table-column prop="zipcode" label="Zipcode" width="120" sortable />
      <el-table-column prop="price" label="Price ($)" width="120" sortable />
      <el-table-column prop="time" label="Time" width="250" sortable />
      <el-table-column prop="isDeleted" label="Deleted" width="100" sortable />
    </el-table>

    <!-- Pagination -->
    <el-pagination
      :current-page="currentPage"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="filteredData.length"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import axios from 'axios'

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
  },
  methods: {
    fetchPrices() {
      axios.get('http://localhost:8080/price/all')
        .then(response => {
          console.log(response.data)
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
