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
  
      <!-- Event Table -->
      <el-table :data="filteredData.slice((currentPage-1)*pageSize, currentPage*pageSize)" style="width: 60%">
        <el-table-column prop="eventID" label="Event ID" width="60" sortable />
        <el-table-column prop="eventLocation" label="Event Location" width="250" sortable />
        <el-table-column prop="eventDeviceModel" label="Device Model" width="150" sortable />
        <el-table-column prop="eventDevice" label="Device" width="140" sortable />
        <el-table-column prop="eventType" label="Event Type" width="140" sortable />
        <el-table-column prop="eventValue1" label="Event Value 1" width="140" sortable />
        <el-table-column prop="eventValue2" label="Event Value 2" width="140" sortable />
        <el-table-column prop="eventTime" label="Event Time" width="160" sortable />
        <el-table-column label="Operation" width="300">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleView(scope.row)">Check</el-button>
            <el-button size="mini" type="warning" @click="handleEdit(scope.row)">Modify</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">Delete</el-button>
          </template>
        </el-table-column>
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
    this.fetchEvents()
  },
  methods: {
    fetchEvents() {
      const userRole = localStorage.getItem('userRole')
      const userId = localStorage.getItem('userId')

      let url
      if (userRole === 'ADMIN') {
        url = 'http://localhost:8080/event/all'
      } else {
        url = `http://localhost:8080/event/user/${userId}`
      }
      
      axios.get(url)
        .then(response => {
          const events = response.data.data.eventList
          this.tableData = events.map(event => ({
            eventID: event.eventID,
            eventLocation: event.eventLocation,
            eventDeviceModel: event.eventDeviceModel,
            eventDevice: event.eventDevice,
            eventType: event.eventType,
            eventValue1: event.eventValue1,
            eventValue2: event.eventValue2,
            eventTime: event.eventTime
          }))
        })
        .catch(error => {
          console.error('Error fetching events:', error)
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
    },
    handleView(row) {
      console.log('View operation', row)
    },
    handleEdit(row) {
      console.log('Edit operation', row)
      // Here, you can add logic to show an edit form and pass the row data to it
    },
    handleDelete(row) {
      console.log('Delete operation', row)
      const index = this.tableData.indexOf(row)
      if (index !== -1) {
        this.tableData.splice(index, 1)
      }
    }
  }
}
</script>
