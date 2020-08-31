<template>
  <el-card class="box-card-component" style="margin-left:8px;">
    <div slot="header" class="box-card-header">
      <img :src="headerImg">
    </div>
    <div style="position:relative;">
      <el-table :data="list" :show-header="false" style="width: 100%;">
        <el-table-column label="文章标题" width="100">
          <template slot-scope="scope">
            {{ scope.row.title }}
          </template>
        </el-table-column>
        <el-table-column label="访问链接" min-width="100" align="center">
          <template slot-scope="scope">
            <a :href="BLOG_URL+scope.row.url+'.html'" class="link-type" target="_blank">{{ BLOG_URL+scope.row.url }}.html</a>
          </template>
        </el-table-column>
        <el-table-column label="发表时间" width="95" align="center">
          <template slot-scope="scope">
            {{ scope.row.createTime | formatTime }}
          </template>
        </el-table-column>
        <el-table-column label="浏览次数" width="50" align="center">
          <template slot-scope="scope">
            {{ scope.row.visits }}
          </template>
        </el-table-column>
      </el-table>
    </div>
  </el-card>
</template>

<script>
import { parseTime } from '@/utils'
import { latestArticle } from '@/api/article'

export default {
  filters: {
    formatTime(time) {
      return parseTime(time)
    }
  },
  data() {
    return {
      number: 8,
      headerImg: 'https://wpimg.wallstcn.com/e7d23d71-cf19-4b90-a1cc-f56af8c0903d.png',
      list: null,
      BLOG_URL: process.env.BLOG_URL
    }
  },
  created() {
    latestArticle(this.number).then(response => {
      if (response.data[0].image) {
        this.headerImg = response.data[0].image
      }
      this.list = response.data
    })
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" >
.box-card-component{
  .el-card__header {
    padding: 0px!important;
  }
}
</style>
<style rel="stylesheet/scss" lang="scss" scoped>
.box-card-component {
  .box-card-header {
    position: relative;
    height: 220px;
    img {
      width: 100%;
      height: 100%;
      transition: all 0.2s linear;
      &:hover {
        transform: scale(1.1, 1.1);
        filter: contrast(130%);
      }
    }
  }
}
</style>
