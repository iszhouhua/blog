<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="100px" @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="菜单名" prop="name">
        <el-input v-model="dataForm.name" placeholder="请输入菜单名"/>
      </el-form-item>
      <el-form-item label="菜单链接" prop="url">
        <el-input v-model="dataForm.url" placeholder="请输入菜单链接"/>
      </el-form-item>
      <el-form-item label="菜单图标" prop="icon">
        <el-input v-model="dataForm.icon" placeholder="请输入Font Awesome图标" style="padding-right: 10px;width:95%"/>
        <el-tooltip class="item" effect="dark" placement="right">
          <div slot="content">图标为
            <a href="http://www.fontawesome.com.cn/" style="color:red;">Font Awesome</a>
            图标</div>
          <i class="el-icon-question"/>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="菜单排序" prop="sort">
        <el-input v-model.number="dataForm.sort" type="number" style="padding-right: 10px;width:95%"/>
        <el-tooltip class="item" effect="dark" content="数值越小越靠前" placement="right">
          <i class="el-icon-question"/>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="新窗口打开" prop="isBlank">
        <el-switch v-model="dataForm.isBlank"/>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { getMenu, postMenu, putMenu } from '@/api/menu'

export default {
  data() {
    return {
      visible: false,
      dataForm: {
        id: 0,
        name: '',
        url: '',
        icon: null,
        sort: 0,
        isBlank: false
      },
      dataRule: {
        name: [
          { required: true, message: '菜单名不能为空', trigger: 'blur' }
        ],
        url: [
          { required: true, message: '菜单链接不能为空', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '排序不能为空', trigger: 'blur' },
          { type: 'number', message: '排序必须为数字值', trigger: 'change' }
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
          getMenu(id).then(response => {
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
            putMenu(this.dataForm).then(response => {
              this.$message.success('修改目录成功')
              this.visible = false
              this.$emit('refreshDataList')
            })
          } else {
            postMenu(this.dataForm).then(response => {
              this.$message.success('添加目录成功')
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
