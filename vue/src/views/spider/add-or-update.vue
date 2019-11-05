<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="100px" @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="爬虫名" prop="name">
        <el-input v-model="dataForm.name" placeholder="请输入爬虫名"/>
      </el-form-item>
      <el-form-item label="标题规则" prop="titleRule">
        <el-input v-model="dataForm.titleRule" placeholder="请输入爬虫标题规则" style="padding-right: 10px;width:95%"/>
        <el-tooltip class="item" effect="dark" content="规则为css选择器" placement="right">
          <i class="el-icon-question"/>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="内容规则" prop="contentRule">
        <el-input v-model="dataForm.contentRule" placeholder="请输入爬虫内容规则" style="padding-right: 10px;width:95%"/>
        <el-tooltip class="item" effect="dark" content="规则为css选择器" placement="right">
          <i class="el-icon-question"/>
        </el-tooltip>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { putSpider, postSpider } from '@/api/spider'

export default {
  data() {
    return {
      visible: false,
      dataForm: {
        id: 0,
        name: '',
        titleRule: '',
        contentRule: ''
      },
      dataRule: {
        name: [
          { required: true, message: '爬虫名不能为空', trigger: 'blur' }
        ],
        titleRule: [
          { required: true, message: '爬虫标题规则不能为空', trigger: 'blur' }
        ],
        contentRule: [
          { required: true, message: '爬虫内容规则不能为空', trigger: 'blur' }
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
          putSpider(id).then(response => {
            this.dataForm = response.data
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          postSpider(this.dataForm).then(response => {
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
