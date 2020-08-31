<template>
  <el-dialog
    :close-on-click-modal="false"
    :visible.sync="visible"
    title="回复评论">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="100px" @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="评论文章" prop="parentArticle">
        <el-input v-model="dataForm.parentArticle" :disabled="true"/>
      </el-form-item>
      <el-form-item label="评论人" prop="parentAuthor">
        <el-input v-model="dataForm.parentAuthor" :disabled="true"/>
      </el-form-item>
      <el-form-item label="评论内容" prop="parentContent">
        <el-input v-model="dataForm.parentContent" :disabled="true"/>
      </el-form-item>
      <el-form-item label="回复内容" prop="content">
        <el-input v-model="dataForm.content" placeholder="请输入回复内容"/>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { getComment, postComment } from '@/api/comment'

export default {
  name: 'ReplyComment',
  data() {
    return {
      visible: false,
      dataForm: {
        articleId: 0,
        parentId: 0,
        parentContent: '',
        parentAuthor: '',
        parentArticle: '',
        content: ''
      },
      dataRule: {
        content: [
          { required: true, message: '回复内容不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init(id) {
      this.dataForm.parentId = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.parentId) {
          getComment(id).then(response => {
            this.dataForm.parentContent = response.data.content
            this.dataForm.parentAuthor = response.data.author
            this.dataForm.parentArticle = response.data.article.title
            this.dataForm.articleId = response.data.article.id
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          postComment(this.dataForm).then(response => {
            this.$message.success(response.msg)
            this.visible = false
            this.$emit('refreshDataList')
          })
        }
      })
    }
  }
}
</script>
