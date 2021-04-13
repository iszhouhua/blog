<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button class="filter-item" type="primary" icon="el-icon-rank" @click="addOrUpdateHandle()">新增</el-button>
    </div>
    <el-table v-loading="listLoading" :default-sort = "{prop: 'id', order: 'descending'}" :data="list" border fit highlight-current-row style="width: 100%" @sort-change="sortChange">
      <el-table-column align="center" label="ID" prop="id" width="150" sortable/>

      <el-table-column align="center" label="链接名" min-width="100" prop="name"/>

      <el-table-column align="center" label="链接地址" min-width="100">
        <template slot-scope="scope">
          <a :href="scope.row.url" style="color: #337ab7;" target="_blank">{{ scope.row.url }}</a>
        </template>
      </el-table-column>

      <el-table-column align="center" label="链接类型" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.type===1? 'success' : 'primary'">{{ scope.row.type===1?'友情链接':'个人链接' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column width="200" align="center" label="操作">
        <template slot-scope="scope">
          <el-button size="mini" icon="el-icon-edit" type="primary" @click="addOrUpdateHandle(scope.row.id)">修改
          </el-button>
          <el-button size="mini" icon="el-icon-delete" style="margin-left: 10px;" type="danger" @click="removeLink(scope.row.id)">删除
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
import { getLinkList, deleteLink } from '@/api/link'
import Pagination from '@/components/Pagination'
export default {
  name: 'LinkList',
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
      getLinkList(this.listQuery).then(response => {
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
      this.getList()
    },
    // 删除标签
    removeLink(id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteLink(id).then(response => {
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

