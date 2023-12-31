<template>
  <div>
    <!-- 管理员的欢迎信息 -->
    <div v-if="userId === 'admin'" class="welcome-message admin">
      <i class="el-icon-s-custom admin-icon"></i>
      <p>Welcome Back, Administrator!</p>
      <p>Ready to manage today's challenges?</p>
    </div>

    <!-- 普通用户的欢迎信息 -->
    <div v-else class="welcome-message user">
      <i class="el-icon-user user-icon"></i>
      <p>Hello, User {{ userId }}!</p>
      <p>Welcome to your dashboard. Your insights await!</p>
    </div>

    <!-- 普通用户表格 -->
    <el-table v-if="userRole === 'USER'" :data="userData" style="width: 100%">
      <!-- 表格列 -->
      <el-table-column prop="totalConsumption" label="Total Consumption"></el-table-column>
      <el-table-column prop="did" label="Device ID"></el-table-column>
      <el-table-column prop="slid" label="Service Location ID"></el-table-column>
      <el-table-column prop="address" label="Address"></el-table-column>
      <el-table-column prop="squarefootage" label="Square Footage"></el-table-column>
      <el-table-column label="Energy Consumption Progress">
        <template v-slot="{ row }">
          <el-progress type="circle" :percentage="calculatePercentage(row.totalConsumption)"
            :status="determineStatus(row.totalConsumption)">
          </el-progress>
        </template>
      </el-table-column>
    </el-table>

    <!-- 管理员表格 -->
    <div v-if="userRole === 'ADMIN'">
      <div v-for="item in allUserConsumption" :key="item.cid" class="admin-progress-bar">
        <p class="user-title">User ID: {{ item.cid }}</p>
        <el-progress :percentage="getAdminPercentage(item.totalConsumption)"
          :status="getAdminStatus(getAdminPercentage(item.totalConsumption))"></el-progress>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userId: '',
      userData: [],
      adminData: [],
      userRole: '',
      totalConsumptionSum: 0, // 存储所有记录的总能耗
      allUserConsumption: [],
    };
  },
  mounted() {
    this.initData();
  },
  methods: {
    initData() {
      const userId = localStorage.getItem('userId');
      this.userId = userId;
      this.userRole = localStorage.getItem('userRole');

      if (userId && this.userRole === 'USER') {
        this.fetchUserData(userId);
      } else if (userId && this.userRole === 'ADMIN') {
        this.fetchAdminData();
      }
    },
    fetchAdminData() {
      axios.get('http://localhost:8080/dashboard/PreviousConsumption/')
        .then(response => {
          this.allUserConsumption = response.data.data.EnergyConsumption;
          this.totalConsumptionSum = this.allUserConsumption.reduce(
            (sum, item) => sum + parseFloat(item.totalConsumption), 0);
          console.log(allUserConsumption.length)
        })
        .catch(error => {
          console.error('Error fetching admin data:', error);
        });
    },
    getAdminPercentage(value) {
      return (parseFloat(value) / this.totalConsumptionSum) * 100;
    },
    getAdminStatus(percentage) {
      if (percentage < (1/this.allUserConsumption.length*100)) return 'success';
      else if (percentage >30) return 'exception';
      return 'warning';
    },
    fetchUserData(userId) {
      axios.get('http://localhost:8080/dashboard/PreviousConsumptionID/' + userId)
        .then(response => {
          const energyData = response.data.data.EnergyConsumption;
          this.userData = energyData;
          this.totalConsumptionSum = energyData.reduce(
            (sum, item) => sum + parseFloat(item.totalConsumption), 0);
        })
        .catch(error => {
          console.error('Error fetching user data:', error);
        });
    },
    calculatePercentage(totalConsumption) {
      const value = parseFloat(totalConsumption);
      return ((value / this.totalConsumptionSum) * 100).toFixed(2);
    },
    determineStatus(totalConsumption) {
      const percentage = this.calculatePercentage(totalConsumption);
      if (percentage > 60) return 'exception';
      else if (percentage > 30) return 'warning';
    }
  }
}

</script>
<style>
.welcome-message {
  padding: 20px;
  margin-bottom: 30px;
  text-align: center;
  font-size: 1.4em;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.admin {
  background-color: #673AB7;
  /* Deep purple background for admin */
  color: white;
}

.admin-icon {
  color: #FFEB3B;
  /* Yellow color for admin icon */
  font-size: 2em;
}

.user {
  background-color: #03A9F4;
  /* Light blue background for regular user */
  color: white;
}

.user-icon {
  color: #FFC107;
  /* Amber color for user icon */
  font-size: 2em;
}

.welcome-message p {
  margin: 10px 0;
  font-size: 0.8em;
}

.admin-progress-bar {
  margin-bottom: 20px;
}

.user-title {
  margin-bottom: 5px;
  font-weight: bold;
  color: #333;
}
</style>