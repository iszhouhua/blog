<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" class="form-container">

      <sticky :class-name="postForm.status===1?'sub-navbar published':'sub-navbar draft'">
        <!-- <OriginalDropdown v-model="postForm.isOriginal" :source-url.sync="postForm.sourceUrl"/> -->
        <CommentDropdown v-model="postForm.isComment" style="margin-left: 10px;"/>
        <TopDropdown v-model="postForm.isTop" style="margin-left: 10px;" />
        <!-- <el-button v-loading="loading" style="margin-left: 10px;" type="info" @click="submitForm(3)">自定义</el-button> -->
        <el-button v-loading="loading" style="margin-left: 10px;" type="warning" @click="submitForm(0)">草稿</el-button>
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm(1)">发布</el-button>
      </sticky>

      <div class="createPost-main-container">

        <el-form-item label-width="45px" label="标题:">
          <el-input :rows="1" v-model="postForm.title" type="textarea" class="article-textarea" autosize placeholder="请输入标题"/>
          <span v-show="titleLength" class="word-counter">{{ titleLength }}字</span>
        </el-form-item>

        <el-form-item label-width="45px" label="摘要:">
          <el-input :rows="1" v-model="postForm.description" type="textarea" class="article-textarea" autosize placeholder="请输入摘要"/>
          <span v-show="contentShortLength" class="word-counter">{{ contentShortLength }}字</span>
        </el-form-item>

        <div class="postInfo-container">
          <el-row>
            <el-col :span="11">
              <el-form-item label-width="60px" label="预览图:" class="postInfo-container-item">
                <upload-image v-model="postForm.image" class="postInfo-container-input"/>
              </el-form-item>
            </el-col>

            <el-col :span="11" :offset="2">
              <el-form-item label-width="80px" label="文章标签:" class="postInfo-container-item">
                <el-select v-model="postForm.tags" value-key="id" multiple filterable placeholder="选择标签" class="postInfo-container-input">
                  <el-option v-for="item in tagOptions" :key="item.id" :label="item.name" :value="item"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="5">
              <el-form-item label-width="80px" label="文章分类:" class="postInfo-container-item">
                <el-select v-model="postForm.categoryId" filterable placeholder="选择分类" class="postInfo-container-input">
                  <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id"/>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="4">
              <el-form-item label-width="80px" label="文章类型:" class="postInfo-container-item">
                <el-radio-group v-model="postForm.type">
                  <el-radio :label="0">普通</el-radio>
                  <el-radio :label="1">自定义</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>

            <el-col :span="5">
              <el-form-item label-width="80px" label="发布时间:" class="postInfo-container-item">
                <el-date-picker v-model="postForm.createTime" type="datetime" format="yyyy-MM-dd HH:mm:ss" placeholder="选择发布时间"/>
              </el-form-item>
            </el-col>

            <el-col :span="10">
              <el-form-item label-width="80px" label="访问链接:" class="postInfo-container-item">
                <el-input v-model="postForm.url" placeholder="默认使用标题作为链接" class="postInfo-container-input">
                  <template slot="prepend">{{ BLOG_URL }}</template>
                  <template slot="append">.html</template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="4">
              <el-form-item class="postInfo-container-item">
                <el-switch
                  v-model="openHtmlEdit"
                  active-text="HTML编辑器"
                  inactive-text="MarkDown编辑器"
                  @change="changeEdit"/>
              </el-form-item>
            </el-col>

            <el-col :span="4">
              <el-form-item label-width="80px" label="是否原创:" class="postInfo-container-item">
                <el-switch v-model="postForm.isOriginal" active-color="#13ce66" inactive-color="#ff4949" @change="switchIsOriginal"/>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item v-if="!postForm.isOriginal" label-width="80px" label="原文链接:" class="postInfo-container-item">
                <el-input v-model="postForm.sourceUrl" placeholder="请输入文章来源" class="postInfo-container-input"/>
              </el-form-item>
            </el-col>

            <el-col :span="7" :offset="1">
              <el-form-item v-if="!postForm.isOriginal&&postForm.sourceUrl" label-width="80px" label="抓取规则:" class="postInfo-container-item">
                <el-select v-model="spiderId" filterable placeholder="选择规则" class="postInfo-container-input" style="width:60%">
                  <el-option v-for="item in spiderArr" :key="item.id" :label="item.name" :value="item.id"/>
                </el-select>
                <el-button v-loading="loading" style="margin-left:20px;" type="primary" @click="spider">抓取</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <tinymce v-show="openHtmlEdit" ref="tinymce" v-model="postForm.content" class="html-editor" />
        <mavonEditor v-show="!openHtmlEdit" ref="md" v-model="postForm.contentMd" :toolbars="toolbars" class="markdown-editor" @imgAdd="uploadImg"/>
      </div>
    </el-form>

  </div>
</template>

<script>
import Tinymce from '@/components/Tinymce'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import toolbars from './toolbars'
import Sticky from '@/components/Sticky' // 粘性header组件
import { getArticle, postArticle } from '@/api/article'
import { getAllCategory } from '@/api/category'
import { getAllTag } from '@/api/tag'
import { CommentDropdown, TopDropdown, OriginalDropdown } from './Dropdown'
import { uploadImage } from '@/api/upload'
import UploadImage from '@/components/UploadImage'
import { getAllSpider, spiderArticle } from '@/api/spider'
import { isUrl } from '@/utils'
import TurndownService from 'turndown'
const turndownService = new TurndownService()

const defaultForm = {
  title: '', // 文章题目
  description: '', // 文章摘要
  content: '', // 文章内容
  contentMd: '', // markdown格式的文章内容
  categoryId: 1, // 文章分类
  image: undefined, // 文章预览图
  url: undefined, // 文章访问链接
  type: 0,
  tags: [],
  id: undefined,
  isOriginal: true,
  sourceUrl: undefined,
  isComment: true,
  isTop: false,
  visits: 0,
  status: 0,
  createTime: new Date()
}

export default {
  name: 'ArticleDetail',
  components: { Tinymce, mavonEditor, Sticky, CommentDropdown, TopDropdown, OriginalDropdown, UploadImage },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      postForm: Object.assign({}, defaultForm),
      categoryOptions: [],
      tagOptions: [],
      loading: false,
      tempRoute: {},
      toolbars: toolbars,
      spiderArr: [],
      spiderId: null,
      openHtmlEdit: false,
      BLOG_URL: process.env.BLOG_URL
    }
  },
  computed: {
    contentShortLength() {
      return this.postForm.description.length
    },
    titleLength() {
      return this.postForm.title.length
    }
  },
  created() {
    if (this.isEdit) {
      const id = this.$route.params && this.$route.params.id
      this.fetchData(id)
    } else {
      this.postForm = Object.assign({}, defaultForm)
    }

    // Why need to make a copy of this.$route here?
    // Because if you enter this page and quickly switch tag, may be in the execution of the setTagsViewTitle function, this.$route is no longer pointing to the current page
    // https://github.com/PanJiaChen/vue-element-admin/issues/1221
    this.tempRoute = Object.assign({}, this.$route)

    getAllCategory().then(response => {
      this.categoryOptions = response.data
    }).catch()
    getAllTag().then(response => {
      this.tagOptions = response.data
    }).catch()
  },
  methods: {
    switchIsOriginal(isOriginal) {
      if (!isOriginal && this.spiderArr.length === 0) {
        getAllSpider().then(response => {
          this.spiderArr = response.data
        })
      }
    },
    // 上传图片
    uploadImg(filename, file) {
      var formdata = new FormData()
      formdata.append('image', file)
      uploadImage(formdata).then(response => {
        this.$message.success('上传成功')
        this.$refs.md.$img2Url(filename, response.data)
      })
    },
    fetchData(id) {
      getArticle(id).then(response => {
        this.postForm = response.data
        // Just for test
        // this.postForm.title += `   Article Id:${this.postForm.id}`
        // this.postForm.description += `   Article Id:${this.postForm.id}`
        // Set tagsview title
        if (this.postForm.status === 3) {
          this.openHtmlEdit = true
        }
        this.setTagsViewTitle()
      }).catch(err => {
        console.log(err)
      })
    },
    setTagsViewTitle() {
      const title = '编辑文章'
      const route = Object.assign({}, this.tempRoute, { title: `${title}-${this.postForm.id}` })
      this.$store.dispatch('updateVisitedView', route)
    },
    submitForm(status) {
      if (!this.postForm.title) {
        this.$message.warning('标题不能为空')
        return false
      }
      if (!this.postForm.contentMd) {
        this.$message.warning('文章内容不能为空')
        return false
      }
      if (!this.postForm.isOriginal && !this.postForm.sourceUrl) {
        this.$message.warning('转载文章请填写文章来源')
        return false
      }
      this.loading = true
      this.postForm.status = status
      this.postForm.content = this.$refs.md.d_render
      postArticle(this.postForm).then(response => {
        this.$notify({
          title: '成功',
          message: '保存文章成功',
          type: 'success',
          duration: 2000
        })
        // 保存成功之后直接关闭当前页
        this.closeCurrTag()
      }).catch(err => {
        this.loading = false
        console.log(err)
      })
    },
    closeCurrTag() {
      this.$store.dispatch('delView', this.$route).then(({ visitedViews }) => {
        const latestView = visitedViews.slice(-1)[0]
        if (latestView) {
          this.$router.push(latestView)
        } else {
          this.$router.push('/')
        }
      })
    },
    spider() {
      if (!this.spiderId) {
        this.$message.warning('请选择爬虫规则')
        return
      }
      if (!this.postForm.sourceUrl || !isUrl(this.postForm.sourceUrl)) {
        this.$message.warning('请输入正确的文章来源链接')
        return
      }
      spiderArticle({ id: this.spiderId, url: this.postForm.sourceUrl }).then(response => {
        this.$message.success('抓取成功')
        Object.assign(this.postForm, response.data)
      })
    },
    changeEdit(openHtmlEdit) {
      if (openHtmlEdit) {
        this.postForm.content = this.$refs.md.d_render
        this.$refs.tinymce.setContent(this.postForm.content)
      } else {
        this.postForm.contentMd = turndownService.turndown(this.postForm.content)
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "~@/styles/mixin.scss";
.createPost-container {
  position: relative;
  .createPost-main-container {
    padding: 40px 45px 20px 50px;
    .postInfo-container {
      position: relative;
      @include clearfix;
      margin-bottom: 10px;
      .postInfo-container-item {
        width: 100%;
        float: left;
        .postInfo-container-input{
          width: 100%;
        }
      }
    }
    .markdown-editor {
      min-height: 500px;
      z-index:0;
    }
  }
  .word-counter {
    width: 40px;
    position: absolute;
    right: -10px;
    top: 0px;
  }
}
</style>
