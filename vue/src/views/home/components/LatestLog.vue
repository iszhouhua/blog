<template>
  <el-table :data="list" style="width: 100%;">
    <el-table-column :show-overflow-tooltip="true" label="请求地址" min-width="100" align="center">
      <template slot-scope="scope">
        <a :href="scope.row.url" class="link-type" target="_blank">{{ scope.row.url }}</a>
      </template>
    </el-table-column>
    <el-table-column :show-overflow-tooltip="true" label="IP归属地" width="80" align="center">
      <template slot-scope="scope">
        {{ scope.row.region }}
      </template>
    </el-table-column>
    <el-table-column label="访问时间" width="140" align="center">
      <template slot-scope="scope">
        {{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}
      </template>
    </el-table-column>
    <el-table-column :show-overflow-tooltip="true" label="响应时长" width="80" align="center">
      <template slot-scope="scope">
        {{ scope.row.duration }}
      </template>
    </el-table-column>
    <el-table-column align="center" label="响应结果" width="80">
      <template slot-scope="scope">
        <el-tag :type="scope.row.isNormal? 'success' : 'danger'" size="mini">
          {{ scope.row.isNormal?'正常':'异常' }}
        </el-tag>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { latestLog } from '@/api/log'
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
      latestLog(this.number).then(response => {
        this.list = response.data
      })
    }
  }
}
</script>
