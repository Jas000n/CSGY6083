<template>
  <div class="app-container">
    <!-- 搜索框 -->
    <el-input
      v-model="search"
      placeholder="请输入搜索内容"
      class="filter-item"
      style="width: 300px;"
      @keyup.enter.native="handleSearch"
    >
      <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
    </el-input>

    <!-- 表格 -->
    <el-table :data="filteredData.slice((currentPage-1)*pageSize, currentPage*pageSize)" style="width: 50%">
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="deviceType" label="设备类型" width="120"></el-table-column>
      <el-table-column prop="deviceModel" label="设备型号" width="200"></el-table-column>
      <el-table-column prop="deviceLocation" label="设备位置" width="120"></el-table-column>
      <el-table-column prop="deviceStatus" label="设备状态" width="100"></el-table-column>
      <el-table-column label="操作" width="180">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleView(scope.row)">查看</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
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
export default {
  data() {
    return {
      search: '',
      currentPage: 1,
      pageSize: 10,
      tableData: [
        // 填入您的数据
        { id: 1, deviceType: '类型1', deviceModel: '型号1', deviceLocation: '位置1', deviceStatus: '状态1' },
        { id: 1, deviceType: '类型1', deviceModel: '型号1', deviceLocation: '位置1', deviceStatus: '状态1' },
        { id: 1, deviceType: '类型1', deviceModel: '型号1', deviceLocation: '位置1', deviceStatus: '状态1' },
        { id: 1, deviceType: '类型1', deviceModel: '型号1', deviceLocation: '位置1', deviceStatus: '状态1' },
        { id: 1, deviceType: '类型1', deviceModel: '型号1', deviceLocation: '位置1', deviceStatus: '状态1' },
        { id: 1, deviceType: '类型1', deviceModel: '型号1', deviceLocation: '位置1', deviceStatus: '状态1' },
        // ...更多数据
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
        );
      } else {
        return this.tableData;
      }
    }
  },
  methods: {
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
  width: calc(100% - 40px); /* 表格宽度稍小于容器宽度，留出边距 */
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

