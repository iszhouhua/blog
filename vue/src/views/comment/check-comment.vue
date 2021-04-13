<template>
  <el-dialog
    :title="dataForm.status!==0 ? '查看' : '审核'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" label-width="100px">
      <el-form-item label="文章ID" prop="article.id">
        <el-input v-model="dataForm.article.id"/>
      </el-form-item>
      <el-form-item label="评论文章" prop="article.title">
        <el-input v-model="dataForm.article.title"/>
      </el-form-item>
      <el-form-item label="评论人ID" prop="user.id">
        <el-input v-model="dataForm.user.id"/>
      </el-form-item>
      <el-form-item label="评论人" prop="user.nickname">
        <el-input v-model="dataForm.user.nickname"/>
      </el-form-item>
      <el-form-item label="邮箱" prop="user.email">
        <el-input v-model="dataForm.user.email"/>
      </el-form-item>
      <el-form-item label="评论内容" prop="content">
        <el-input v-model="dataForm.content"/>
      </el-form-item>
      <template v-if="dataForm.replyUser">
        <el-form-item label="回复的人ID" prop="replyUser.id">
          <el-input v-model="dataForm.replyUser.id"/>
        </el-form-item>
        <el-form-item label="回复的人" prop="replyUser.nickname">
          <el-input v-model="dataForm.replyUser.nickname"/>
        </el-form-item>
        <el-form-item label="回复的人邮箱" prop="replyUser.email">
          <el-input v-model="dataForm.replyUser.email"/>
        </el-form-item>
      </template>
      <el-form-item label="IP地址" prop="ip">
        <el-input v-model="dataForm.ip"/>
      </el-form-item>
      <el-form-item label="浏览器信息" prop="userAgent">
        <el-input v-model="dataForm.userAgent"/>
      </el-form-item>
      <el-form-item label="评论时间" prop="createTime">
        <el-input v-model="dataForm.createTime"/>
      </el-form-item>
    </el-form>
    <span v-if="dataForm.status===0" slot="footer" class="dialog-footer">
      <el-button @click="handleModifyStatus(2)">审核不通过</el-button>
      <el-button type="primary" @click="handleModifyStatus(1)">审核通过</el-button>
    </span>
    <span v-else slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { getComment, postComment } from '@/api/comment'
import { parseTime } from '@/utils'

export default {
  name: 'CheckComment',
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
      }
    }
  },
  methods: {
    init(id) {
      this.visible = true
      this.$nextTick(() => {
        // this.$refs['dataForm'].resetFields()
        getComment(id).then(response => {
          this.dataForm = response.data
          this.dataForm.createTime = parseTime(response.data.createTime)
        })
      })
    },
    // 修改状态
    handleModifyStatus(status) {
      postComment({ 'id': this.dataForm.id, 'status': status }).then(response => {
        this.$message.success('修改成功')
        this.visible = false
        this.$emit('refreshDataList')
      })
    }
  }
}
</script>
