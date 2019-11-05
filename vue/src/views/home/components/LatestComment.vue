<template>
  <el-table :data="list" style="width: 100%;padding-top: 15px;padding-left: 12px;">
    <el-table-column label="评论人" width="100">
      <template slot-scope="scope">
        {{ scope.row.author }}
      </template>
    </el-table-column>
    <el-table-column label="评论文章" min-width="100" align="center">
      <template slot-scope="scope">
        {{ scope.row.article.title | orderNoFilter }}
      </template>
    </el-table-column>
    <el-table-column label="评论内容" min-width="100" align="center">
      <template slot-scope="scope">
        {{ scope.row.content | orderNoFilter }}
      </template>
    </el-table-column>
    <el-table-column label="状态" width="100" align="center">
      <template slot-scope="scope">
        <el-tag :type="scope.row.status===1?'success':'danger'"> {{ scope.row.status===1?'已发布':'待审核' }}</el-tag>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { latestComment } from '@/api/comment'

export default {
  filters: {
    orderNoFilter(str) {
      return str.substring(0, 30)
    }
  },
  data() {
    return {
      number: 8,
      list: null
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      latestComment(this.number).then(response => {
        this.list = response.data
      })
    }
  }
}
</script>
