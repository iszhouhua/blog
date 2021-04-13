<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.title" placeholder="标题" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-select v-model="listQuery.categoryId" filterable clearable placeholder="选择分类" class="filter-item">
        <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id"/>
      </el-select>
      <el-select v-if="articleStatus===0" v-model="listQuery.status" placeholder="文章状态" clearable class="filter-item" style="width: 130px">
        <el-option key="0" label="草稿" value="0"/>
        <el-option key="1" label="已发布" value="1"/>
      </el-select>
      <el-select v-model="listQuery.type" placeholder="文章类型" clearable class="filter-item" style="width: 130px">
        <el-option key="0" label="普通文章" value="0"/>
        <el-option key="1" label="自定义" value="1"/>
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>
      <el-button style="float: right;margin-right: 10px;" type="danger" icon="el-icon-delete" @click="deleteArticle(multipleSelection)">删除已选</el-button>
    </div>

    <el-table v-loading="listLoading" :data="list" :default-sort = "{prop: 'id', order: 'descending'}" border fit highlight-current-row style="width: 100%" @sort-change="sortChange" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"/>

      <el-table-column align="center" label="ID" sortable="custom" prop="id" width="50"/>

      <el-table-column align="center" label="链接" width="100">
        <template slot-scope="scope">
          <a :href="BLOG_URL+scope.row.url+'.html'" class="link-type" target="_blank">{{ scope.row.url }}</a>
        </template>
      </el-table-column>

      <el-table-column header-align="center" label="标题" min-width="100">
        <template slot-scope="scope">
          <a :href="BLOG_URL+scope.row.url+'.html'" class="link-type" target="_blank">{{ scope.row.title }}</a>
          <el-tag v-if="scope.row.type===0">{{ categoryFilter(scope.row.categoryId) }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column v-if="articleStatus===0" align="center" label="标签" min-width="100">
        <template slot-scope="scope">
          <el-tag v-for="(tag, index) in scope.row.tags" :key="tag.id" :type="tagType(index)">
            {{ tag.name }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column :show-overflow-tooltip="true" header-align="center" min-width="100" label="描述" prop="description"/>

      <el-table-column v-if="articleStatus>0" :show-overflow-tooltip="true" header-align="center" min-width="150" label="内容" prop="contentMd"/>

      <el-table-column v-if="articleStatus!=3" align="center" label="预览图" width="80">
        <template slot-scope="scope">
          <el-popover
            placement="left"
            trigger="hover">
            <img :src="scope.row.image" width="600">
            <img slot="reference" :src="scope.row.image" :alt="scope.row.image" width="60">
          </el-popover>
        </template>
      </el-table-column>

      <el-table-column label="阅读量" sortable="custom" align="center" prop="visits" width="95"/>

      <el-table-column :formatter="formatTime" width="102" align="center" sortable="custom" label="发布时间" prop="createTime"/>

      <el-table-column :formatter="formatTime" width="102" align="center" sortable="custom" label="更新时间" prop="updateTime"/>

      <el-table-column v-if="articleStatus===0" align="center" label="状态" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status===1? 'success' : 'info'">{{ scope.row.status===1?'已发布':'草稿' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="文章类型" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.type===0? 'Info' : 'danger'">{{ scope.row.type===0?'普通文章':'自定义' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column v-if="articleStatus===0" align="center" label="置顶" width="80">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.isTop" @change="switchIsTop(scope.row.id,$event)"/>
        </template>
      </el-table-column>

      <el-table-column v-if="articleStatus!=2" align="center" label="评论" width="80">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.isComment" active-color="#13ce66" inactive-color="#ff4949" @change="switchIsComment(scope.row.id,$event)"/>
        </template>
      </el-table-column>

      <el-table-column :width="articleStatus===3?200:300" align="center" label="操作">
        <template slot-scope="scope">
          <router-link :to="'/article/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑</el-button>
          </router-link>
          <template v-if="articleStatus===2">
            <el-button size="mini" icon="el-icon-refresh" style="margin-left: 10px;" type="success" @click="handleModifyStatus(scope.row.id,1)">还原
            </el-button>
          </template>
          <template v-else-if="articleStatus!==3">
            <el-button icon="el-icon-refresh" size="mini" style="margin-left: 10px;" type="warning" @click="handleModifyStatus(scope.row.id,2)">回收
            </el-button>
          </template>
          <el-button size="mini" icon="el-icon-delete" style="margin-left: 10px;" type="danger" @click="deleteArticle(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.size" @pagination="getList" />

  </div>
</template>

<script>
import { getArticleList, modifyArticle, deleteArticle } from '@/api/article'
import { getCategoryList } from '@/api/category'
import Pagination from '@/components/Pagination'
import { parseTime } from '@/utils'
const tagTypes = ['', 'success', 'info', 'warning', 'danger']
export default {
  name: 'ArticleList',
  components: { Pagination },
  props: {
    articleStatus: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        current: 1,
        size: 10,
        title: undefined,
        status: undefined,
        categoryId: undefined,
        ascs: undefined,
        descs: undefined
      },
      categoryOptions: [],
      multipleSelection: [],
      BLOG_URL: process.env.BLOG_URL
    }
  },
  created() {
    if (this.articleStatus > 0) {
      this.listQuery.status = this.articleStatus
    }
    getCategoryList().then(response => {
      this.categoryOptions = response.data.records
    })
  },
  methods: {
    // 获得数据集合
    getList() {
      this.listLoading = true
      getArticleList(this.listQuery).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
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
    // 获得分类
    categoryFilter(categoryId) {
      if (!this.categoryOptions) return ''
      for (var category of this.categoryOptions) {
        if (category.id === categoryId) {
          return category.name
        }
      }
    },
    // 修改状态
    handleModifyStatus(id, status) {
      this.listLoading = true
      modifyArticle({ 'id': id, 'status': status }).then(response => {
        this.$message.success('修改成功')
        this.listLoading = false
        this.getList()
      })
    },
    // 删除文章
    deleteArticle(ids) {
      if (ids instanceof Array && ids.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择需要删除的对象'
        })
        return
      }
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        deleteArticle(ids).then(response => {
          this.$message.success('删除成功')
          this.listLoading = false
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
      this.handleFilter()
    },
    tagType(index) {
      return tagTypes[index % tagTypes.length]
    },
    switchIsTop(id, isTop) {
      modifyArticle({ 'id': id, 'isTop': isTop }).then(response => {
        this.$message.success(isTop ? '置顶成功' : '取消置顶成功')
      })
    },
    switchIsComment(id, isComment) {
      modifyArticle({ 'id': id, 'isComment': isComment }).then(response => {
        this.$message.success(isComment ? '成功开启评论' : '成功关闭评论')
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val.map(function(v) { return v.id })
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
