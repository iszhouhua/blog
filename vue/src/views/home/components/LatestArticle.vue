<template>
  <el-table :data="list" style="width: 100%;">
    <el-table-column :show-overflow-tooltip="true" label="文章标题" min-width="100" align="center">
      <template slot-scope="scope">
        {{ scope.row.title }}
      </template>
    </el-table-column>
    <el-table-column :show-overflow-tooltip="true" label="链接" min-width="100" align="center">
      <template slot-scope="scope">
        <a :href="BLOG_URL+scope.row.url+'.html'" class="link-type" target="_blank">{{ BLOG_URL+scope.row.url }}.html</a>
      </template>
    </el-table-column>
    <el-table-column :show-overflow-tooltip="true" label="发表时间" width="140" align="center">
      <template slot-scope="scope">
        {{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}
      </template>
    </el-table-column>
    <el-table-column :show-overflow-tooltip="true" label="浏览量" width="80" align="center">
      <template slot-scope="scope">
        {{ scope.row.visits }}
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { parseTime } from '@/utils'
import { latestArticle } from '@/api/article'

export default {
  filters: {
    parseTime
  },
  data() {
    return {
      number: 12,
      list: null,
      BLOG_URL: process.env.BLOG_URL
    }
  },
  created() {
    latestArticle(this.number).then(response => {
      this.list = response.data
    })
  }
}
</script>
