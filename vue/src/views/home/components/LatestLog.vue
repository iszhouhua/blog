<template>
  <el-table :data="list" style="width: 100%;padding-top: 15px;padding-left: 12px;">
    <el-table-column label="IP地址" width="130" align="center">
      <template slot-scope="scope">
        {{ scope.row.ip }}
      </template>
    </el-table-column>
    <el-table-column label="所在城市" width="80" align="center">
      <template slot-scope="scope">
        {{ scope.row.city }}
      </template>
    </el-table-column>
    <el-table-column label="访问时间" min-width="100" align="center">
      <template slot-scope="scope">
        {{ scope.row.createTime | formatTime }}
      </template>
    </el-table-column>
    <el-table-column align="center" label="响应结果" width="100">
      <template slot-scope="scope">
        <el-tag :type="scope.row.isNormal? 'success' : 'danger'">
          {{ scope.row.isNormal?'正常':'异常' }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column label="响应时长" min-width="100" align="center">
      <template slot-scope="scope">
        {{ scope.row.duration }}
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { latestLog } from '@/api/log'
import { parseTime } from '@/utils'

export default {
  filters: {
    formatTime(time) {
      return parseTime(time)
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
      latestLog(this.number).then(response => {
        this.list = response.data
      })
    }
  }
}
</script>
