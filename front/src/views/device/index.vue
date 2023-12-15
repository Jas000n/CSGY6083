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

    <!-- 设备详情模态框 -->
    <el-dialog :title="`Device Details`" :visible.sync="showDetailsModal" @close="closeDetailsModal">
      <div v-if="currentDeviceDetails">
        <p><strong>Device Type:</strong> {{ currentDeviceDetails.name }}</p>
        <p><strong>Model:</strong> {{ currentDeviceDetails.model }}</p>
        <!-- 条件渲染不同的设备类型 -->
        <div v-if="currentDeviceDetails.name === 'AC'">
          <p><strong>Set Temperature:</strong> {{ currentDeviceDetails.value1 }}</p>
          <p><strong>AC Mode:</strong> {{ currentDeviceDetails.value2 }}</p>
        </div>

        <div v-else-if="currentDeviceDetails.name === 'Drier'">
          <p><strong>Drying Mode:</strong> {{ currentDeviceDetails.value1 }}</p>
        </div>

        <div v-else-if="currentDeviceDetails.name === 'Dish Washer'">
          <p><strong>Washing Mode:</strong> {{ currentDeviceDetails.value1 }}</p>
        </div>

        <div v-else-if="currentDeviceDetails.name === 'Dish Washer'">
          <p><strong>Washing Mode:</strong> {{ currentDeviceDetails.value1 }}</p>
        </div>

        <div v-if="currentDeviceDetails.name === 'Light'">
          <p><strong>Brightness:</strong> {{ currentDeviceDetails.value1 }}</p>
          <p><strong>Color Temperature:</strong> {{ currentDeviceDetails.value2 }}</p>
        </div>

        <div v-if="currentDeviceDetails.name === 'Refrigerator'">
          <p><strong>Freezer Temperature:</strong> {{ currentDeviceDetails.value1 }}</p>
          <p><strong>Refrigerator Temperature:</strong> {{ currentDeviceDetails.value2 }}</p>
        </div>

        <!-- 可以添加其他设备类型的条件 -->
        <!-- <div v-else>
          <p><strong>Value 1:</strong> {{ currentDeviceDetails.value1 }}</p>
          <p><strong>Value 2:</strong> {{ currentDeviceDetails.value2 }}</p>
        </div> -->
      </div>
    </el-dialog>

    <!-- 添加和修改设备的模态框 -->
    <el-dialog :title="formTitle" :visible.sync="showDeviceModel">
      <el-form :model="deviceForm">
        <!-- 表单内容 -->
        <el-form-item v-if="formMode === 'add'" label="UID">
          <el-select v-model="deviceForm.uid" :disabled="isUser" @change="onUIDChange">
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
          <el-select v-model="deviceForm.deviceType" @change="onDeviceTypeChange">
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
      showDetailsModal: false,
      currentDeviceDetails: null,
      currentDeviceID: null,
      search: '',
      currentPage: 1,
      pageSize: 10,
      tableData: [
      ],
      showDeviceModel: false,
      formTitle: 'Add New Device', // 标题可以动态更改
      deviceForm: {
        // 表单数据模型
        uid: '',
        serviceLocation: '',
        deviceType: '',
        model: ''
      },
      formMode: 'add', // 'add' 或 'modify' 来区分表单模式
      uids: [], // 存储UIDs的数组
      locations: [], // 存储ServiceLocation的数组
      deviceTypes: [], // 存储DeviceType的数组
      models: [] // 存储Model的数组
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
    const userRole = localStorage.getItem('userRole')
    const userId = localStorage.getItem('userId')
    this.isUser = userRole === 'USER'
    if (this.isUser) {
      this.deviceForm.uid = userId
      // 如果是普通用户，只获取该用户的地址
      this.fetchLocationsForUser(userId)
    } else {
    // 如果是管理员，获取所有地址
      this.fetchLocations()
    }
    this.fetchDevices()
    this.fetchUIDs()
    this.fetchDeviceTypes()
    this.fetchModels()
  },

  methods: {
    closeDetailsModal() {
      this.showDetailsModal = false
    },
    fetchUIDs() {
    // 从后端获取UIDs
      axios.get('http://localhost:8080/customer/all') // 替换为后端实际的API端点
        .then(response => {
          this.uids = response.data.data.cids
        })
        .catch(error => {
          console.error('Error fetching UIDs:', error)
        })
    },
    fetchLocations() {
    // 从后端获取ServiceLocation数据
      axios.get('http://localhost:8080/servicelocation/all') // 替换为后端实际的API端点
        .then(response => {
          this.locations = response.data.data.address
        })
        .catch(error => {
          console.error('Error fetching locations:', error)
        })
    },
    fetchDeviceTypes() {
    // 从后端获取DeviceType数据
      axios.get('http://localhost:8080/devicetype/name') // 替换为后端实际的API端点
        .then(response => {
          const uniqueDeviceTypes = new Set(response.data.data.name)
          this.deviceTypes = Array.from(uniqueDeviceTypes)
        })
        .catch(error => {
          console.error('Error fetching device types:', error)
        })
    },
    onUIDChange() {
      const selectedUID = this.deviceForm.uid
      if (selectedUID) {
        this.fetchLocationsForUser(selectedUID)
      } else {
        this.locations = [] // 如果没有选中的UID，则清空地址选项
      }
    },
    fetchLocationsForUser(uid) {
      axios.get(`http://localhost:8080/servicelocation/user/${uid}`) // 后端API端点
        .then(response => {
          console.log(response.data)
          this.locations = response.data// 假设后端返回的是地址数组
        })
        .catch(error => {
          console.error('Error fetching locations for user:', error)
        })
    },
    // 当选中的设备类型变化时，更新型号的下拉选项
    onDeviceTypeChange() {
      if (this.deviceForm.deviceType) {
        this.fetchModelsByType(this.deviceForm.deviceType)
      } else {
        this.models = [] // 如果没有选中类型，则清空型号选项
      }
    },
    fetchModels() {
    // 从后端获取Model数据
      axios.get('http://localhost:8080/devicetype/model') // 替换为后端实际的API端点
        .then(response => {
          this.models = response.data.data.model
        })
        .catch(error => {
          console.error('Error fetching models:', error)
        })
    },
    fetchModelsByType(type) {
      axios.get(`http://localhost:8080/devicetype/${type}/models`) // 确保这个URL与后端API匹配
        .then(response => {
          console.log(response)
          this.models = response.data.data.models // 假设后端返回的是一个型号的数组
        })
        .catch(error => {
          console.error(`Error fetching models for device type ${type}:`, error)
        })
    },

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
      console.log(device)
      this.formMode = 'modify'
      this.formTitle = 'Modify Device'
      this.currentDeviceID = device.id
      // 确保你的device对象有正确的属性名
      this.deviceForm = {
        serviceLocation: device.deviceLocation, // 或其他对应的字段名
        deviceType: device.deviceType, // 或其他对应的字段名
        model: device.deviceModel // 或其他对应的字段名
      }
      // this.deviceForm = { ...device } // 复制设备数据到表单
      this.showDeviceModel = true
    },

    // 提交表单
    submitDeviceForm() {
      let payload
      let url
      const method = this.formMode === 'add' ? 'post' : 'put'

      if (this.formMode === 'add') {
        // 构建添加模式下的payload
        payload = {
          name: this.deviceForm.deviceType,
          model: this.deviceForm.model,
          address: this.deviceForm.serviceLocation
        }
        url = 'http://localhost:8080/device/add' // 添加设备的API端点
      } else if (this.formMode === 'modify') {
        // 构建修改模式下的payload
        payload = {
          name: this.deviceForm.deviceType,
          model: this.deviceForm.model,
          address: this.deviceForm.serviceLocation
        }
        url = `http://localhost:8080/device/update/${this.currentDeviceID}` // 更新设备的API端点
      }

      axios({
        method: method,
        url: url,
        data: payload
      })
        .then(response => {
          console.log(this.currentDeviceID)
          this.showDeviceModel = false
          this.fetchDevices() // 重新获取设备列表以显示新添加或更新的设备
        })
        .catch(error => {
          console.error('Error submitting device form:', error)
        })
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
    handleView(device) {
      // 假设后端有一个API端点可以通过设备ID获取设备的详细信息
      axios.get(`http://localhost:8080/device/details/${device.id}`)
        .then(response => {
          this.currentDeviceDetails = response.data.data.deviceDetails
          this.showDetailsModal = true // 打开模态框
        })
        .catch(error => {
          console.error('Error fetching device details:', error)
        })
    },
    handleEdit(row) {
      // 修改操作的逻辑
      console.log('修改操作', row)
      // 假设有一个用于显示修改表单的标志变量，例如 `isEditFormVisible`
      // 设置该变量为 true 并将当前行的数据传递给表单
      this.isEditFormVisible = true
      this.currentEditRow = row // 假设有一个变量来存储当前编辑的行
    },
    handleDelete(device) {
      axios.delete(`http://localhost:8080/device/delete/${device.id}`)
        .then(response => {
          // 处理成功的响应
          console.log('Device deleted successfully')
          this.fetchDevices() // 重新加载设备列表
        })
        .catch(error => {
          console.error('Error deleting device:', error)
        })
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

