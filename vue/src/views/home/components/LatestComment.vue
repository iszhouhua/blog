<template>
  <el-table :data="list" style="width: 100%;">
    <el-table-column :show-overflow-tooltip="true" label="评论人" width="100" align="center">
      <template slot-scope="scope">
        {{ scope.row.user.nickname }}
      </template>
    </el-table-column>
    <el-table-column :show-overflow-tooltip="true" label="评论文章" min-width="100" align="center">
      <template slot-scope="scope">
        {{ scope.row.article.title }}
      </template>
    </el-table-column>
    <el-table-column :show-overflow-tooltip="true" label="评论内容" min-width="100" align="center">
      <template slot-scope="scope">
        {{ scope.row.content }}
      </template>
    </el-table-column>
    <el-table-column :show-overflow-tooltip="true" label="评论时间" width="140" align="center">
      <template slot-scope="scope">
        {{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}
      </template>
    </el-table-column>
    <el-table-column label="状态" width="100" align="center">
      <template slot-scope="scope">
        <el-tag :type="scope.row.status===1?'success':'danger'" size="mini"> {{ scope.row.status===1?'已发布':'待审核' }}</el-tag>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { latestComment } from '@/api/comment'
import { parseTime } from '@/utils'

export default {
  filters: {
    parseTime
  },
  data() {
    return {
      number: 12,
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
