<template>
  <div class="app-container">
    <!-- 搜索框 -->
    <el-input
      v-model="search"
      placeholder="Enter search content"
      class="filter-item"
      style="width: 300px;"
      @keyup.enter.native="handleSearch"
    >
      <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
    </el-input>

    <!-- 表格 -->
    <el-table :data="filteredData.slice((currentPage-1)*pageSize, currentPage*pageSize)" style="width: 60%">
      <el-table-column prop="id" label="ID" width="60" sortable></el-table-column>
      <el-table-column prop="deviceType" label="DeviceType" width="130" sortable></el-table-column>
      <el-table-column prop="deviceModel" label="DeviceModel" width="150" sortable></el-table-column>
      <el-table-column prop="deviceLocation" label="DeviceLocation" width="250" sortable></el-table-column>
      <el-table-column prop="deviceStatus" label="DeviceStatus" width="140" sortable></el-table-column>
      <el-table-column label="Operation" width="300">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleView(scope.row)">Check</el-button>
          <el-button size="mini" type="warning" @click="handleEdit(scope.row)">Modify</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="filteredData.length">
    </el-pagination>
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
      tableData: [
  
      ]
    }
  },
  computed: {
    filteredData() {
      if (this.search) {
        return this.tableData.filter(data =>
          data.deviceType.includes(this.search) ||
          data.deviceModel.includes(this.search) ||
          data.deviceLocation.includes(this.search) ||
          data.deviceStatus.includes(this.search)
        )
      } else {
        return this.tableData
      }
    }
  },
  mounted() {
    this.fetchDevices()
  },

  methods: {
    fetchDevices() {
      axios.get('http://localhost:8080/device') // 这个URL应该匹配你的后端API端点
        .then(response => {
          console.log(response.data);
          console.log(response.data.device);
          const devices = response.data.data.device;
          this.tableData = []; // 确保tableData是空的，然后开始填充数据

        for (let i = 0; i < devices.length; i++) {
          let statusText = devices[i].status === '1' ? 'On' : 'Off';
          this.tableData.push({
          id: devices[i].did,
          deviceType: devices[i].name,
          deviceModel: devices[i].model,
          deviceLocation: devices[i].address,
          deviceStatus: statusText
        });
      }

        
          
        })
        .catch(error => {
          console.error('Error fetching devices:', error);
          
        })
        
    },

    handleSearch() {
      // 根据search进行搜索，该方法将重新计算filteredData
      this.currentPage = 1; // 重置当前页码为1
    },
    handleSizeChange(newSize) {
      this.pageSize = newSize;
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
    },
    handleView(row) {
      // 查看操作的逻辑
      console.log('查看操作', row);
    },
    handleEdit(row) {
      // 修改操作的逻辑
      console.log('修改操作', row);
      // 假设有一个用于显示修改表单的标志变量，例如 `isEditFormVisible`
      // 设置该变量为 true 并将当前行的数据传递给表单
      this.isEditFormVisible = true
      this.currentEditRow = row; // 假设有一个变量来存储当前编辑的行
    },
    handleDelete(row) {
      // 删除操作的逻辑
      console.log('删除操作', row);
      const index = this.tableData.indexOf(row);
      if (index !== -1) {
        this.tableData.splice(index, 1);
      }
    }
  }
}
</script>

<style>
.app-container {
  display: flex;
  flex-direction: column;
  align-items: center; /* 使得子项居中对齐 */
  width: 100%; /* 容器宽度为100% */
}

.el-table {
  width: calc(100% - 2px); /* 表格宽度稍小于容器宽度，留出边距 */
  margin: 20px; /* 表格上下边距为20px */
}

.filter-item {
  width: 100%;
  max-width: 300px; /* 搜索框最大宽度为300px */
  margin-bottom: 20px; /* 与表格间距为20px */
}

.el-pagination {
  margin-top: 20px; /* 分页组件与表格间距为20px */
}

/* 响应式布局：当屏幕尺寸减小时，表格宽度调整 */
@media screen and (max-width: 600px) {
  .el-table {
    width: 100%; /* 屏幕较小时，表格宽度为100% */
    margin: 10px 0; /* 上下边距调整为10px */
  }
}
</style>

