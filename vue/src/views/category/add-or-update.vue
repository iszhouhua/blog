<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="100px" @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="分类名" prop="name">
        <el-input v-model="dataForm.name" placeholder="请输入分类名"/>
      </el-form-item>
      <el-form-item label="分类链接" prop="url">
        <el-input v-model="dataForm.url" placeholder="请输入分类链接"/>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { getCategory, postCategory, putCategory } from '@/api/category'

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
          { required: true, message: '分类名不能为空', trigger: 'blur' }
        ],
        url: [
          { required: true, message: '分类链接不能为空', trigger: 'blur' }
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
          getCategory(id).then(response => {
            this.dataForm = response.data
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.dataForm.id) {
            putCategory(this.dataForm).then(response => {
              this.$message.success('修改分类成功')
              this.visible = false
              this.$emit('refreshDataList')
            })
          } else {
            postCategory(this.dataForm).then(response => {
              this.$message.success('添加分类成功')
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
