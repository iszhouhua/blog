<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.nickname" placeholder="用户昵称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.email" placeholder="用户邮箱" type="email" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.isDisable" placeholder="用户状态" clearable class="filter-item" style="width: 110px">
        <el-option :key="false" :value="false" label="正常" />
        <el-option :key="true" :value="true" label="禁用" />
      </el-select>
      <el-select v-model="listQuery.status" placeholder="用户身份" clearable class="filter-item" style="width: 110px">
        <el-option :key="false" :value="false" label="普通用户" />
        <el-option :key="true" :value="true" label="管理员" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button class="filter-item" style="float: right;margin-right: 10px;" type="primary" icon="el-icon-rank" @click="addOrUpdateHandle()">新增</el-button>
    </div>

    <el-table v-loading="listLoading" :default-sort = "{prop: 'id', order: 'descending'}" :data="list" border fit highlight-current-row style="width: 100%" @sort-change="sortChange">
      <el-table-column align="center" label="用户ID" prop="id" width="150" sortable/>

      <el-table-column align="center" label="用户名" min-width="100" prop="username"/>

      <el-table-column align="center" label="用户昵称" min-width="100" prop="nickname"/>

      <el-table-column align="center" label="邮箱" min-width="100" prop="email"/>

      <el-table-column align="center" label="头像" width="88">
        <template slot-scope="scope">
          <el-popover placement="left" trigger="hover">
            <img :src="scope.row.avatar" width="600">
            <img slot="reference" :src="scope.row.avatar" width="60">
          </el-popover>
        </template>
      </el-table-column>

      <el-table-column align="center" label="用户身份" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isAdmin? 'danger' : 'warning'">
            {{ scope.row.isAdmin?'管理员':'普通用户' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="是否禁用" prop="isDisable" width="120" sortable>
        <template slot-scope="scope">
          <el-tag :type="scope.row.isDisable? 'danger' : 'success'">
            {{ scope.row.isDisable?'禁用':'正常' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column :formatter="formatTime" width="102" align="center" sortable="custom" label="注册时间" prop="createTime"/>

      <el-table-column :formatter="formatTime" width="102" align="center" sortable="custom" label="最近登录" prop="lastLoginTime"/>

      <el-table-column width="320" align="center" label="操作">
        <template slot-scope="scope">
          <template v-if="scope.row.isAdmin">
            <el-button size="mini" icon="el-icon-arrow-down" type="info" @click="updateUserType(scope.row.id,false)">取消管理员
            </el-button>
          </template>
          <template v-else>
            <el-button size="mini" icon="el-icon-arrow-up" type="warning" @click="updateUserType(scope.row.id,true)">设为管理员
            </el-button>
          </template>
          <el-button size="mini" icon="el-icon-edit" type="primary" @click="addOrUpdateHandle(scope.row.id)">修改
          </el-button>
          <template v-if="scope.row.isDisable">
            <el-button size="mini" icon="el-icon-refresh" style="margin-left: 10px;" type="success" @click="updateUserStatus(scope.row.id,false)">启用
            </el-button>
          </template>
          <template v-else>
            <el-button size="mini" icon="el-icon-delete" style="margin-left: 10px;" type="danger" @click="updateUserStatus(scope.row.id,true)">禁用
            </el-button>
          </template>
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
import { getUserList, putUser } from '@/api/user'
import Pagination from '@/components/Pagination'
import { parseTime } from '@/utils'
export default {
  name: 'UserList',
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
      getUserList(this.listQuery).then(response => {
        if (response.data) {
          this.list = response.data.records
          this.total = response.data.total
        }
        this.listLoading = false
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
      this.handleFilter()
    },
    // 搜索
    handleFilter() {
      this.listQuery.current = 1
      this.getList()
    },
    // 格式化时间
    formatTime(row, column, cellValue) {
      return parseTime(cellValue)
    },
    // 禁用/启用用户
    updateUserStatus(id, isDisable) {
      this.$confirm('确定要' + (isDisable ? '禁用' : '启用') + '该用户么?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        putUser({ id, isDisable }).then(response => {
          this.$message.success('操作成功')
          this.getList()
        })
      })
    },
    // 设置/取消管理员
    updateUserType(id, isAdmin) {
      putUser({ id, isAdmin }).then(response => {
        this.$message.success('操作成功')
        this.getList()
      })
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
.filter-container {
    padding-bottom: 20px;
}
</style>
