<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.content" placeholder="评论内容" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.targetType" placeholder="目标类型" clearable class="filter-item" style="width: 130px">
        <el-option key="0" label="文章" value="1" />
        <el-option key="1" label="评论" value="2" />
      </el-select>
      <el-select v-model="listQuery.status" placeholder="评论状态" clearable class="filter-item" style="width: 130px">
        <el-option key="0" label="待审核" value="0" />
        <el-option key="1" label="已发布" value="1" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-table v-loading="listLoading" :data="list" :default-sort="{prop: 'id', order: 'descending'}" border fit highlight-current-row style="width: 100%" @sort-change="sortChange">
      <el-table-column align="center" label="ID" sortable="custom" prop="id" width="100" />

      <el-table-column header-align="center" label="评论文章" min-width="80">
        <template slot-scope="scope">
          <a :href="BLOG_URL+scope.row.article.url+'.html'" class="link-type" target="_blank">
            {{ scope.row.article.title }}
          </a>
        </template>
      </el-table-column>

      <el-table-column align="center" label="评论人" width="100" prop="user.nickname" />

      <el-table-column align="center" label="邮箱" min-width="88" prop="user.email" />

      <el-table-column align="center" label="头像" width="88">
        <template slot-scope="scope">
          <el-popover placement="left" trigger="hover">
            <img :src="scope.row.user.avatar" width="600">
            <img slot="reference" :src="scope.row.user.avatar" width="60">
          </el-popover>
        </template>
      </el-table-column>

      <el-table-column :show-overflow-tooltip="true" header-align="center" min-width="150" label="评论内容" prop="content" />

      <el-table-column align="center" label="回复的人" width="100" prop="replyUser.nickname" />

      <el-table-column :show-overflow-tooltip="true" label="浏览器信息" align="center" prop="userAgent" min-width="100" />

      <el-table-column label="IP地址" align="center" prop="ip" min-width="150" />

      <el-table-column :formatter="formatTime" width="102" align="center" sortable="custom" label="评论时间" prop="createTime" />

      <el-table-column align="center" label="评论人类型" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.user.isAdmin? 'danger' : 'info'">
            {{ scope.row.user.isAdmin?'管理员':'普通用户' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="状态" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status===1? 'success' : 'info'">
            {{ scope.row.status===1?'已发布':scope.row.status===2?'已删除':'待审核' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column width="280" align="center" label="操作">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status!==2" type="primary" size="mini" icon="el-icon-edit" @click="replyCommentHandle(scope.row.id)">
            回复
          </el-button>
          <el-button v-if="scope.row.status===0" size="mini" icon="el-icon-check" style="margin-left: 10px;" type="success" @click="checkCommentHandle(scope.row.id)">
            审核
          </el-button>
          <el-button v-else size="mini" icon="el-icon-zoom-in" style="margin-left: 10px;" type="info" @click="checkCommentHandle(scope.row.id)">
            查看
          </el-button>
          <el-button v-if="scope.row.status!==2" size="mini" icon="el-icon-delete" style="margin-left: 10px;" type="danger" @click="deleteComment(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.size" @pagination="getList" />

    <!-- 回复评论 -->
    <reply-comment v-if="replyCommentVisible" ref="replyComment" @refreshDataList="getList"/>
    <!-- 查看或审核评论 -->
    <check-comment v-if="checkCommentVisible" ref="checkComment" @refreshDataList="getList"/>
  </div>
</template>

<script>
import { getCommentList, putComment } from '@/api/comment'
import Pagination from '@/components/Pagination'
import { parseTime } from '@/utils'
import checkComment from './check-comment'
import replyComment from './reply-comment'
export default {
  name: 'CommentList',
  components: { Pagination, checkComment, replyComment },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      replyCommentVisible: false,
      checkCommentVisible: false,
      listQuery: {
        current: 1,
        size: 10,
        content: undefined,
        status: undefined,
        ascs: undefined,
        descs: undefined
      },
      BLOG_URL: process.env.BLOG_URL
    }
  },
  methods: {
    // 获得数据集合
    getList() {
      this.listLoading = true
      getCommentList(this.listQuery).then(response => {
        if (response.data) {
          this.list = response.data.records
          this.total = response.data.total
        }
        this.listLoading = false
      })
    },
    // 搜索
    handleFilter() {
      this.listQuery.current = 1
      this.getList()
    },
    // 修改状态
    handleModifyStatus(id, status) {
      this.listLoading = true
      putComment({ 'id': id, 'status': status }).then(response => {
        this.$message.success('修改成功')
        this.listLoading = false
        this.getList()
      })
    },
    deleteComment(id) {
      this.$confirm('你确定要删除么?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        this.handleModifyStatus(id, 2)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 格式化时间
    formatTime(row, column, cellValue) {
      return parseTime(cellValue)
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
    replyCommentHandle(id) {
      this.replyCommentVisible = true
      this.$nextTick(() => {
        this.$refs.replyComment.init(id)
      })
    },
    checkCommentHandle(id) {
      this.checkCommentVisible = true
      this.$nextTick(() => {
        this.$refs.checkComment.init(id)
      })
    }
  }
}
</script>

<style scoped>
.edit-input {
  padding-right: 100px;
}
.cancel-btn {
  position: absolute;
  right: 15px;
  top: 10px;
}
.link-type{
  color: #337ab7;
}
.el-tag+.el-tag {
    margin-left: 10px;
}
.el-button+.el-button{
    margin-left: 0px;
}
.filter-container {
    padding-bottom: 20px;
}
</style>
