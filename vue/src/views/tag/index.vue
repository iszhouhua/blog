<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button class="filter-item" type="primary" icon="el-icon-rank" @click="addOrUpdateHandle()">新增</el-button>
    </div>
    <el-table :default-sort = "{prop: 'id', order: 'descending'}" :data="list" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label="ID" prop="id" width="150" sortable/>

      <el-table-column align="center" label="标签" min-width="100" prop="name"/>

      <el-table-column align="center" label="链接" min-width="100">
        <template slot-scope="scope">
          <a :href="$store.getters.global.BLOG_URL+'tag/'+scope.row.url+'/'" style="color: #337ab7;" target="_blank">{{ scope.row.url }}</a>
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
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getList"/>
  </div>
</template>

<script>
import AddOrUpdate from './add-or-update'
import { getTag, deleteTag } from '@/api/tag'
import Pagination from '@/components/Pagination'
export default {
  name: 'TagList',
  components: { AddOrUpdate, Pagination },
  data() {
    return {
      list: [],
      addOrUpdateVisible: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      getTag(this.listQuery).then(response => {
        this.list = response.data
      })
    },
    // 删除标签
    removeTag(id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteTag(id).then(response => {
          this.$message.success(response.msg)
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

