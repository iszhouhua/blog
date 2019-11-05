<template>
  <div class="dashboard-editor-container">

    <panel-group/>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <browser-chart/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <operating-system-chart/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <city-chart/>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="8">
      <el-col :span="8" style="margin-bottom:30px;">
        <latest-comment/>
      </el-col>
      <el-col :span="8" style="margin-bottom:30px;">
        <latest-article/>
      </el-col>
      <el-col :span="8" style="margin-bottom:30px;">
        <latest-log/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import PanelGroup from './components/PanelGroup'
import BrowserChart from './components/BrowserChart'
import OperatingSystemChart from './components/OperatingSystemChart'
import CityChart from './components/CityChart'
import LatestComment from './components/LatestComment'
import LatestLog from './components/LatestLog'
import LatestArticle from './components/LatestArticle'

export default {
  name: 'Home',
  components: {
    PanelGroup,
    BrowserChart,
    OperatingSystemChart,
    CityChart,
    LatestComment,
    LatestLog,
    LatestArticle
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}
</style>
