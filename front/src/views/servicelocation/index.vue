<template>
  <div class="app-container">
    <!-- 搜索框和按钮的容器 -->
    <div class="top-controls">
      <!-- 添加位置按钮 -->
      <el-button type="primary" @click="openAddLocationForm">Add Location</el-button>
      <!-- 搜索框 -->
      <el-input v-model="search" placeholder="Enter search content" class="filter-item" @keyup.enter.native="handleSearch">
        <el-button slot="append" icon="el-icon-search" @click="handleSearch" />
      </el-input>
    </div>

    <!-- 添加和修改位置的模态框 -->
    <el-dialog :title="formTitle" :visible.sync="showLocationModel" width="50%">
      <el-form ref="locationForm" :model="locationForm" label-width="120px">
        <!-- UID选择 -->
        <el-form-item v-if="formMode === 'add' || formMode === 'modify' || isUser" label="User ID" :error="formErrors.cid">
          <el-select v-model="locationForm.cid" :disabled="isUser" placeholder="Select user ID" @change="onUIDChange">
            <el-option v-for="uid in uids" :key="uid" :label="uid" :value="uid" />
          </el-select>
        </el-form-item>
        <el-form-item label="State" :error="formErrors.state">
          <el-input v-model="locationForm.state" />
        </el-form-item>
        <el-form-item label="City" :error="formErrors.city">
          <el-input v-model="locationForm.city" />
        </el-form-item>
        <el-form-item label="Zip Code" :error="formErrors.zipcode">
          <el-input v-model="locationForm.zipcode" />
        </el-form-item>
        <el-form-item label="Address" :error="formErrors.address">
          <el-input v-model="locationForm.address" />
        </el-form-item>
        <el-form-item label="Square Footage" :error="formErrors.squareFootage">
          <el-input v-model="locationForm.squareFootage" type="number" />
        </el-form-item>
        <el-form-item label="Number of Bedrooms" :error="formErrors.numberOfBedrooms">
          <el-input v-model="locationForm.numberOfBedrooms" type="number" />
        </el-form-item>
        <el-form-item label="Number of Occupants" :error="formErrors.numberOfOccupants">
          <el-input v-model="locationForm.numberOfOccupants" type="number" />
        </el-form-item>
        <el-form-item label="Start Date" :error="formErrors.startDate">
          <el-date-picker v-model="locationForm.startDate" type="date" placeholder="Choose date" />
        </el-form-item>
      </el-form>
      <!-- 按钮 -->
      <div slot="footer" class="dialog-footer">
        <el-button @click="showLocationModel = false">Cancel</el-button>
        <el-button type="primary" @click="submitLocationForm">Confirm</el-button>
      </div>
    </el-dialog>

    <!-- 表格显示位置信息 -->
    <div class="el-table-wrapper">
      <el-table :data="filteredData" style="width: 100%" height="100%" border>
        <el-table-column prop="slid" label="ID" width="60" sortable />
        <el-table-column prop="state" label="State" sortable />
        <el-table-column prop="city" label="City" width="100" sortable />
        <el-table-column prop="zipcode" label="Zip Code" width="100" sortable />
        <el-table-column prop="address" label="Address" width="200" sortable />
        <el-table-column prop="squareFootage" label="Square Footage" sortable />
        <el-table-column prop="numberOfBedrooms" label="Bedrooms" width="100" sortable />
        <el-table-column prop="numberOfOccupants" label="Occupants" width="100" sortable />
        <el-table-column prop="startDate" label="Start Date" width="100" sortable />
        <el-table-column prop="cid" label="User ID" width="60" sortable />
        <el-table-column label="Operation" width="180">
          <template slot-scope="scope">
            <el-button size="mini" type="warning" @click="openModifyLocationForm(scope.row)">Modify</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <el-pagination
      :current-page="currentPage"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="pageSize"
      :total="totalItems"
      layout="total, sizes, prev, pager, next, jumper"
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
      allData: [],
      totalItems: 0,
      search: '',
      currentPage: 1,
      pageSize: 10,
      tableData: [],
      showLocationModel: false,
      formTitle: '',
      locationForm: {
        cid: '',
        state: '',
        city: '',
        zipcode: '',
        address: '',
        squareFootage: 0,
        numberOfBedrooms: 0,
        numberOfOccupants: 0,
        isDeleted: 0,
        startDate: new Date().toISOString().substring(0, 10) // 设置为当前日期
      },
      formErrors: {},
      formMode: 'add', // 'add' 或 'modify'
      uids: [],
      isUser: false // 标识是否为普通用户
    }
  },
  computed: {
    filteredData() {
      // 这里可以添加搜索逻辑
      return this.tableData
    }
  },
  mounted() {
    this.isUser = localStorage.getItem('userRole') === 'USER'
    if (!this.isUser) {
      this.fetchUIDs()
    }
    this.fetchLocations()
  },
  methods: {
    handleSizeChange(newSize) {
      this.pageSize = newSize
      this.updatePageData()
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage
      this.updatePageData()
    },
    updatePageData() {
      const startIndex = (this.currentPage - 1) * this.pageSize
      const endIndex = startIndex + this.pageSize
      this.tableData = this.allData.slice(startIndex, endIndex)
    },
    fetchUIDs() {
      axios.get('http://localhost:8080/customer/all')
        .then(response => {
          this.uids = response.data.data.cids // 确保后端返回的数据结构是 { data: uid数组 }
        })
        .catch(error => {
          console.error('Error fetching UIDs:', error)
        })
    },
    fetchLocations() {
      // 获取服务位置的逻辑，这里是示例代码，请根据实际API调整
      const url = this.isUser ? `http://localhost:8080/servicelocation/userlocation/${localStorage.getItem('userId')}` : 'http://localhost:8080/servicelocation/alllocations'
      axios.get(url).then(response => {
        console.log(response)
        this.allData = response.data.data.locations // 确保后端返回的数据结构是 { data: 服务位置数组 }
        this.totalItems = this.allData.length
        this.updatePageData()
      })
    },
    openAddLocationForm() {
      this.formMode = 'add'
      this.formTitle = 'Add New Location'
      this.resetForm()
      // 设置UserID为当前用户的ID
      this.locationForm.cid = localStorage.getItem('userId')

      // 根据用户角色来决定是否禁用UserID字段
      this.isUser = localStorage.getItem('userRole') === 'USER'
      this.showLocationModel = true
    },
    openModifyLocationForm(location) {
      this.formMode = 'modify'
      this.formTitle = 'Modify Location'
      this.locationForm = { ...location }
      this.showLocationModel = true
    },
    handleDelete(location) {
      // 删除服务位置的逻辑，这里是示例代码，请根据实际API调整
      axios.delete(`http://localhost:8080/servicelocation/delete/${location.slid}`).then(() => {
        this.fetchLocations()
      })
    },
    validateForm() {
      // 表单验证逻辑
      this.formErrors = {} // 重置表单错误
      let isValid = true
      if (!this.locationForm.state) {
        this.formErrors.state = "State can't be empty"
        isValid = false
      }
      // ...其他验证...
      return isValid
    },
    submitLocationForm() {
      if (!this.validateForm()) {
        return
      }

      const url = this.formMode === 'add'
        ? 'http://localhost:8080/servicelocation/add'
        : `http://localhost:8080/servicelocation/update/${this.locationForm.slid}`

      const payload = {
        cid: this.locationForm.cid,
        state: this.locationForm.state,
        city: this.locationForm.city,
        zipcode: this.locationForm.zipcode,
        address: this.locationForm.address,
        squareFootage: this.locationForm.squareFootage,
        numberOfBedrooms: this.locationForm.numberOfBedrooms,
        numberOfOccupants: this.locationForm.numberOfOccupants,
        startDate: this.locationForm.startDate
      }

      axios.put(url, payload)
        .then((response) => {
          console.log(response)
          this.showLocationModel = false
          this.fetchLocations()
        })
        .catch(error => {
          console.error('Error submitting location form:', error)
        })
    },
    resetForm() {
      this.locationForm = {
        cid: '',
        state: '',
        city: '',
        zipcode: '',
        address: '',
        squareFootage: 0,
        numberOfBedrooms: 0,
        numberOfOccupants: 0,
        isDeleted: 0,
        startDate: new Date().toISOString().substring(0, 10)
      }
    }
  }
}
</script>

<style>
.el-table-wrapper {
  height: 550px; /* 或者您想要的任何高度 */
}

/* 响应式布局 */
@media screen and (max-width: 650px) {
  .el-table-wrapper {
    height: auto; /* 在小屏幕上可能需要不同的高度 */
  }
}
</style>
