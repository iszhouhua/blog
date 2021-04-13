<template>
  <el-dialog
    :close-on-click-modal="false"
    :visible.sync="visible"
    title="回复评论">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="100px" @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="文章ID" prop="article.id">
        <el-input v-model="dataForm.article.id" :disabled="true"/>
      </el-form-item>
      <el-form-item label="评论文章" prop="article.title">
        <el-input v-model="dataForm.article.title" :disabled="true"/>
      </el-form-item>
      <el-form-item label="评论人ID" prop="user.id">
        <el-input v-model="dataForm.user.id" :disabled="true"/>
      </el-form-item>
      <el-form-item label="评论人昵称" prop="user.nickname">
        <el-input v-model="dataForm.user.nickname" :disabled="true"/>
      </el-form-item>
      <el-form-item label="邮箱" prop="user.email">
        <el-input v-model="dataForm.user.email" :disabled="true"/>
      </el-form-item>
      <el-form-item label="评论内容" prop="content">
        <el-input v-model="dataForm.content" :disabled="true"/>
      </el-form-item>
      <el-form-item label="回复内容">
        <el-input v-model="submitData.content" placeholder="请输入回复内容"/>
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
        id: 0,
        user: {
          id: null,
          nickname: '',
          email: ''
        },
        replyUser: {
          id: null,
          nickname: '',
          email: ''
        },
        ip: '',
        userAgent: '',
        content: '',
        createTime: '',
        article: { id: null, title: '' }
      },
      submitData: {
        articleId: 0,
        parentId: 0,
        targetType: 2,
        replyUserId: 0,
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
            this.dataForm = response.data
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.submitData.articleId = this.dataForm.article.id
          this.submitData.parentId = this.dataForm.parentId || this.dataForm.id
          this.submitData.replyUserId = this.dataForm.user.id
          postComment(this.submitData).then(response => {
            this.$message.success('回复成功')
            this.visible = false
            this.$emit('refreshDataList')
          })
        }
      })
    }
  }
}
</script>
