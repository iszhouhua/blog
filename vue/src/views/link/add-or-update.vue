<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="100px" @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="链接名" prop="name">
        <el-input v-model="dataForm.name" placeholder="请输入链接名"/>
      </el-form-item>
      <el-form-item label="链接地址" prop="url">
        <el-input v-model="dataForm.url" placeholder="请输入链接地址"/>
      </el-form-item>
      <el-form-item label="链接类型" prop="type">
        <el-radio-group v-model="dataForm.type">
          <el-radio :label="1">友情链接</el-radio>
          <el-radio :label="2">个人链接</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { putLink, postLink } from '@/api/link'

export default {
  data() {
    return {
      visible: false,
      dataForm: {
        id: 0,
        name: '',
        url: ''
      },
      dataRule: {
        name: [
          { required: true, message: '链接名不能为空', trigger: 'blur' }
        ],
        url: [
          { required: true, message: '链接地址不能为空', trigger: 'blur' }
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
          putLink(id).then(response => {
            this.dataForm = response.data
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          postLink(this.dataForm).then(response => {
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
