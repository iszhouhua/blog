<template>
  <div class="dashboard-editor-container">

    <panel-group/>

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
import LatestComment from './components/LatestComment'
import LatestLog from './components/LatestLog'
import LatestArticle from './components/LatestArticle'

export default {
  name: 'Home',
  components: {
    PanelGroup,
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
