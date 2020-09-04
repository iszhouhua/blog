<template>
  <el-tabs value="baseInfo" type="card" class="user-info">
    <el-tab-pane label="基本信息" name="baseInfo" class="tag-option">
      <el-form ref="userForm" :model="userForm" :rules="userRules" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username" class="user-input" placeholder="请输入用户名"/>
          <el-tooltip class="item" effect="dark" content="可用于登录" placement="right">
            <i class="el-icon-question"/>
          </el-tooltip>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userForm.email" class="user-input" placeholder="请输入邮箱"/>
          <el-tooltip class="item" effect="dark" content="可用于登录" placement="right">
            <i class="el-icon-question"/>
          </el-tooltip>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="userForm.nickname" class="user-input" placeholder="请输入昵称"/>
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <upload-image v-model="userForm.avatar"/>
        </el-form-item>
        <el-form-item>
          <el-button v-loading="loading" type="primary" @click="modifyUserInfo">修改</el-button>
        </el-form-item>
      </el-form>
    </el-tab-pane>
    <el-tab-pane label="修改密码" name="changePass" class="tag-option">

      <el-form ref="passForm" :model="passForm" :rules="passRules" label-width="100px">
        <el-form-item label="原密码" prop="oldPass">
          <el-input v-model="passForm.oldPass" type="password" class="user-input" placeholder="请输入原密码"/>
        </el-form-item>
        <el-form-item label="新密码:" prop="newPass">
          <el-input v-model="passForm.newPass" type="password" class="user-input" placeholder="请输入新密码"/>
        </el-form-item>
        <el-form-item label="确认密码:" prop="checkPass">
          <el-input v-model="passForm.checkPass" type="password" class="user-input" placeholder="请再次输入密码"/>
        </el-form-item>
        <el-form-item>
          <el-button v-loading="loading" type="primary" @click="modifyPassword">修改</el-button>
        </el-form-item>
      </el-form>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import { putUser, changePass } from '@/api/user'
import UploadImage from '@/components/UploadImage'

export default {
  components: { UploadImage },
  data() {
    return {
      loading: false,
      userForm: this.$store.getters.user,
      passForm: {
        oldPass: '',
        newPass: '',
        checkPass: ''
      },
      userRules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        nickname: [{ required: true, message: '请输入用户昵称', trigger: 'blur' }],
        email: [{ required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }]
      },
      passRules: {
        oldPass: [{ required: true, message: '请输入旧密码', trigger: 'blur' },
          { min: 6, message: '密码不能小于6位', trigger: 'blur' }],
        newPass: [{ required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, message: '密码不能小于6位', trigger: 'blur' }],
        checkPass: [{ required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: (rule, value, callback) => {
            if (value !== this.passForm.newPass) {
              callback(new Error('两次输入密码不一致'))
            } else {
              callback()
            }
          }, trigger: 'blur' }]
      }
    }
  },
  methods: {
    modifyUserInfo() {
      this.$refs.userForm.validate((valid) => {
        if (valid) {
          this.loading = true
          putUser(this.userForm).then(response => {
            this.$notify({
              title: '成功',
              message: '用户信息修改成功',
              type: 'success',
              duration: 2000
            })
            this.loading = false
            // 重新获取用户信息
            this.$store.dispatch('GetUserInfo').then((response) => {
              this.userForm = response.data
            })
          }).catch(err => {
            this.loading = false
            console.log(err)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    modifyPassword() {
      this.$refs.passForm.validate((valid) => {
        if (valid) {
          this.loading = true
          changePass(this.passForm).then(response => {
            this.$notify({
              title: '成功',
              message: '密码修改成功',
              type: 'success',
              duration: 2000
            })
            this.loading = false
            // 密码修改后需要重新登录
            this.$store.dispatch('FedLogOut').then(() => {
              this.$router.push({ path: '/login' })
            })
          }).catch(err => {
            this.loading = false
            console.log(err)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.user-info {
  padding-left:20px;
  padding-top:10px;
  .tag-option {
    width: 800px;
    .user-input {
      padding-right: 10px;
      width: 650px;
    }
  }
}
</style>
