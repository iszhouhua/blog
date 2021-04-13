<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button class="filter-item" type="primary" icon="el-icon-rank" @click="addOrUpdateHandle()">新增</el-button>
    </div>
    <el-table :default-sort = "{prop: 'id', order: 'descending'}" :data="list" border fit highlight-current-row style="width: 100%" @sort-change="sortChange">
      <el-table-column align="center" label="ID" prop="id" width="150" sortable/>

      <el-table-column align="center" label="参数名" min-width="100" prop="name"/>

      <el-table-column :show-overflow-tooltip="true" align="center" label="参数值" min-width="150" prop="value"/>

      <el-table-column :show-overflow-tooltip="true" align="center" label="描述信息" min-width="150" prop="description"/>

      <el-table-column align="center" label="参数类型" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.type===1? 'success' : 'primary'">{{ scope.row.type===1?'全局变量':'系统配置' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column width="200" align="center" label="操作">
        <template slot-scope="scope">
          <el-button size="mini" icon="el-icon-edit" type="primary" @click="addOrUpdateHandle(scope.row.id)">修改
          </el-button>
          <el-button size="mini" icon="el-icon-delete" style="margin-left: 10px;" type="danger" @click="removeTag(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.size" @pagination="getList" />

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getList"/>
  </div>
</template>

<script>
import AddOrUpdate from './add-or-update'
import { getConfigList, deleteConfig } from '@/api/config'
import Pagination from '@/components/Pagination'
export default {
  name: 'ConfigList',
  components: { AddOrUpdate, Pagination },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      addOrUpdateVisible: false,
      listQuery: {
        current: 1,
        size: 10,
        ascs: undefined,
        descs: undefined
      }
    }
  },
  // created() {
  //   this.getList()
  // },
  methods: {
    getList() {
      this.listLoading = true
      getConfigList(this.listQuery).then(response => {
        if (response.data) {
          this.list = response.data.records
          this.total = response.data.total
        }
        this.listLoading = false
      })
    },
    // 删除配置
    removeTag(id) {
      this.$confirm('删除配置可能造成不可预计的后果, 是否继续?', '提示', {
        confirmButtonText: '继续',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        deleteConfig(id).then(response => {
          this.$message.success('删除成功')
          this.getList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 排序
    sortChange(data) {
      if (data.order === 'ascending') {
        this.listQuery.descs = undefined
        this.listQuery.ascs = data.prop.replace(/([A-Z])/g, '_$1').toLowerCase()
      } else {
        this.listQuery.ascs = undefined
        this.listQuery.descs = data.prop.replace(/([A-Z])/g, '_$1').toLowerCase()
      }
      this.getList()
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    }
  }
}
</script>
<style scoped>
.filter-container{
  float: right;
  margin-bottom: 15px;
}
</style>

