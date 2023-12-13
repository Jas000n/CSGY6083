/* __placeholder__ */
export default (await import('vue')).defineComponent({
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
};
},
computed: {
filteredData() {
if (this.search) {
return this.tableData.filter(data => data.deviceType.includes(this.search) ||
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
handleEdit(row) {
// 修改操作的逻辑
console.log('修改操作', row);
// 假设有一个用于显示修改表单的标志变量，例如 `isEditFormVisible`
// 设置该变量为 true 并将当前行的数据传递给表单
this.isEditFormVisible = true;
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
});
