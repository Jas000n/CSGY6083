<template>
  <div class="app-container">
    <!-- 搜索框和按钮的容器 -->
    <div class="top-controls">
      <!-- 添加设备按钮 -->
      <el-button type="primary" @click="openAddDeviceForm">Add Device</el-button>
      <!-- 搜索框 -->
      <el-input
        v-model="search"
        placeholder="Enter search content"
        class="filter-item"
        @keyup.enter.native="handleSearch"
      >
        <el-button slot="append" icon="el-icon-search" @click="handleSearch" />
      </el-input>
    </div>

    <!-- 添加和修改设备的模态框 -->
    <el-dialog :title="formTitle" :visible.sync="showDeviceModel">
      <el-form :model="deviceForm">
        <!-- 表单内容 -->
        <el-form-item label="UID">
          <el-select v-model="deviceForm.uid" :disabled="isUser">
            <!-- 如果isUser为true，只显示当前用户的UID -->
            <template v-if="isUser">
              <el-option
                :key="deviceForm.uid"
                :label="deviceForm.uid"
                :value="deviceForm.uid"
              />
            </template>

            <!-- 如果isUser不是true，循环显示所有的UIDs -->
            <template v-else>
              <el-option
                v-for="uid in uids"
                :key="uid"
                :label="uid"
                :value="uid"
              />
            </template>
          </el-select>

        </el-form-item>

        <el-form-item label="Service Location">
          <el-select v-model="deviceForm.serviceLocation">
            <el-option v-for="location in locations" :key="location" :label="location" :value="location" />
          </el-select>
        </el-form-item>

        <el-form-item label="Device Type">
          <el-select v-model="deviceForm.deviceType">
            <el-option v-for="type in deviceTypes" :key="type" :label="type" :value="type" />
          </el-select>
        </el-form-item>

        <el-form-item label="Model">
          <el-select v-model="deviceForm.model">
            <el-option v-for="model in models" :key="model" :label="model" :value="model" />
          </el-select>
        </el-form-item>
      <!-- ... -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showDeviceModel = false">Cancel</el-button>
        <el-button type="primary" @click="submitDeviceForm">Confirm</el-button>
      </div>
    </el-dialog>

    <!-- 表格 -->
    <el-table :data="filteredData.slice((currentPage-1)*pageSize, currentPage*pageSize)" style="width: 70%">
      <el-table-column prop="id" label="ID" width="60" sortable />
      <el-table-column prop="deviceType" label="DeviceType" width="135" sortable />
      <el-table-column prop="deviceModel" label="DeviceModel" width="150" sortable />
      <el-table-column prop="deviceLocation" label="DeviceLocation" width="250" sortable />
      <el-table-column prop="deviceStatus" label="DeviceStatus" width="140" sortable />
      <el-table-column label="Operation" width="300">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleView(scope.row)">Check</el-button>
          <el-button size="mini" type="warning" @click="openModifyDeviceForm(scope.row)">Modify</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
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
      tableData: [

      ],

      // ... 其他数据不变 ...
      showDeviceModel: false,
      formTitle: 'Add New Device', // 标题可以动态更改
      deviceForm: {
        // 表单数据模型
        uid: '',
        serviceLocation: '',
        deviceType: '',
        model: ''
      },
      formMode: 'add' // 'add' 或 'modify' 来区分表单模式

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
    // 打开添加设备的模态框
    openAddDeviceForm() {
      this.formMode = 'add'
      this.formTitle = 'Add New Device'
      this.resetDeviceForm()

      // 检查localStorage中保存的用户角色
      const userRole = localStorage.getItem('userRole')
      // 如果用户角色是'USER'，那么设置isUser为true，并设置UID为该用户的UID
      this.isUser = userRole === 'USER'
      if (this.isUser) {
      // 假设用户的UID是登录时保存在localStorage的
        this.deviceForm.uid = localStorage.getItem('userId')
      }

      this.showDeviceModel = true
    },

    // 打开修改设备的模态框
    openModifyDeviceForm(device) {
      this.formMode = 'modify'
      this.formTitle = 'Modify Device'
      this.deviceForm = { ...device } // 复制设备数据到表单
      this.showDeviceModel = true
    },

    // 提交表单
    submitDeviceForm() {
      if (this.formMode === 'add') {
        // 添加设备的逻辑
      } else {
        // 修改设备的逻辑
      }
    },

    // 重置表单
    resetDeviceForm() {
      this.deviceForm = {
        uid: '',
        serviceLocation: '',
        deviceType: '',
        model: ''
      }
    },

    fetchDevices() {
      const userRole = localStorage.getItem('userRole')
      const userId = localStorage.getItem('userId')

      let url
      if (userRole === 'ADMIN') {
        // 如果是管理员，获取所有设备
        url = 'http://localhost:8080/device/all'
      } else {
        // 如果是普通用户，只获取与该用户相关的设备
        // 假设后端API允许通过用户ID来获取设备
        url = `http://localhost:8080/device/user/${userId}`
      }
      axios.get(url) // 这个URL应该匹配你的后端API端点
        .then(response => {
          console.log(response.data)
          const devices = response.data.data.device
          this.tableData = [] // 确保tableData是空的，然后开始填充数据

          for (let i = 0; i < devices.length; i++) {
            const statusText = devices[i].status === '1' ? 'On' : 'Off'
            this.tableData.push({
              id: devices[i].did,
              deviceType: devices[i].name,
              deviceModel: devices[i].model,
              deviceLocation: devices[i].address,
              deviceStatus: statusText
            })
          }
        })
        .catch(error => {
          console.error('Error fetching devices:', error)
        })
    },

    handleSearch() {
      // 根据search进行搜索，该方法将重新计算filteredData
      this.currentPage = 1 // 重置当前页码为1
    },
    handleSizeChange(newSize) {
      this.pageSize = newSize
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage
    },
    handleView(row) {
      // 查看操作的逻辑
      console.log('查看操作', row)
    },
    handleEdit(row) {
      // 修改操作的逻辑
      console.log('修改操作', row)
      // 假设有一个用于显示修改表单的标志变量，例如 `isEditFormVisible`
      // 设置该变量为 true 并将当前行的数据传递给表单
      this.isEditFormVisible = true
      this.currentEditRow = row // 假设有一个变量来存储当前编辑的行
    },
    handleDelete(row) {
      // 删除操作的逻辑
      console.log('删除操作', row)
      const index = this.tableData.indexOf(row)
      if (index !== -1) {
        this.tableData.splice(index, 1)
      }
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

.top-controls {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 70%; /* 这应该与.el-table的宽度相匹配 */
  margin-bottom: 20px; /* 与表格的间距 */
}

/* 搜索框样式 */
.filter-item {
  width: 300px; /* 搜索框的宽度 */
  margin-right: 35%; /* 推动搜索框向右对齐 */
}

/* 添加设备按钮样式 */
.add-device-button {
  margin-right: calc(100% - 300px - 60px); /* 搜索框宽度 + ID列宽度 */
}

/* 表格样式 */
.el-table {
  width: calc(100% - 2px);
  margin: 5px 0;
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

