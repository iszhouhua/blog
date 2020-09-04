<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="100px" @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="dataForm.username" :disabled="dataForm.id>0" placeholder="请输入用户名"/>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="dataForm.email" type="emial" placeholder="请输入邮箱"/>
      </el-form-item>
      <el-form-item label="用户昵称" prop="nickname">
        <el-input v-model="dataForm.nickname" type="nickname" placeholder="请输入昵称"/>
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <upload-image v-model="dataForm.avatar"/>
      </el-form-item>
      <el-form-item v-if="!dataForm.id" label="密码" prop="password">
        <el-input v-model="dataForm.password" type="password" placeholder="请输入密码"/>
      </el-form-item>
      <el-form-item label="管理员权限" prop="isAdmin">
        <el-switch v-model="dataForm.isAdmin"/>
      </el-form-item>
      <el-form-item label="禁用账号" prop="isDisable">
        <el-switch v-model="dataForm.isDisable"/>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { getUser, putUser, postUser } from '@/api/user'
import UploadImage from '@/components/UploadImage'
const defaultDataForm = { id: 0, username: '', nickname: '', email: '', avatar: undefined, password: '', isAdmin: false, isDisable: false }

export default {
  components: { UploadImage },
  data() {
    return {
      visible: false,
      dataForm: defaultDataForm,
      dataRule: {
        username: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          { max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '邮箱不能为空', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '用户昵称不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init(id) {
      this.dataForm.id = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          getUser(id).then(response => {
            this.dataForm = response.data
          })
        } else {
          this.dataForm = defaultDataForm
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (!this.dataForm.avatar) {
            this.dataForm.avatar = undefined
          }
          if (this.dataForm.id) {
            putUser(this.dataForm).then(response => {
              this.$message.success('修改用户成功')
              this.visible = false
              this.$emit('refreshDataList')
            })
          } else {
            postUser(this.dataForm).then(response => {
              this.$message.success('添加用户成功')
              this.visible = false
              this.$emit('refreshDataList')
            })
          }
        }
      })
    }
  }
}
</script>
