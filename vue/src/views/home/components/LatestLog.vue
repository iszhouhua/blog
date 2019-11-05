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
        {{ scope.row.updateTime | formatTime }}
      </template>
    </el-table-column>
    <el-table-column label="创建时间" min-width="100" align="center">
      <template slot-scope="scope">
        {{ scope.row.createTime | formatTime }}
      </template>
    </el-table-column>
    <el-table-column label="访问次数" width="100" align="center">
      <template slot-scope="scope">
        {{ scope.row.visits }}
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
