<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="100px" @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="参数名" prop="name">
        <el-input :disabled="dataForm.id>0" v-model="dataForm.name" placeholder="请输入参数名"/>
      </el-form-item>
      <template v-if="CONFIG_KEY.BACKGROUND_LIST===dataForm.name">
        <el-form-item
          v-for="(item, index) in dataForm.valueArr"
          :label="'值' + index"
          :key="index"
          :prop="'valueArr.' + index + '.value'">
          <upload-image v-model="item.value" style="padding-right: 10px;width:90%"/>
          <el-button @click.prevent="removeValue(item)">删除</el-button>
        </el-form-item>
        <el-button style="display:block;margin:0 auto;margin-bottom: 20px;" @click="addValue()">新增</el-button>
      </template>
      <template v-else-if="CONFIG_KEY.BLOG_SCRIPT===dataForm.name||CONFIG_KEY.BLOG_HEAD===dataForm.name">
        <el-form-item
          v-for="(item, index) in dataForm.valueArr"
          :label="'代码' + index"
          :key="index"
          :prop="'valueArr.' + index + '.value'">
          <el-input v-model="item.value" placeholder="请输入代码" style="padding-right: 10px;width:90%"/>
          <el-button @click.prevent="removeValue(item)">删除</el-button>
        </el-form-item>
        <el-button style="display:block;margin:0 auto;margin-bottom: 20px;" @click="addValue()">新增</el-button>
      </template>
      <oss-config v-else-if="CONFIG_KEY.FILE_STORAGE===dataForm.name" v-model="dataForm.value"/>
      <template v-else>
        <el-form-item label="参数值" prop="value">
          <template v-if="[CONFIG_KEY.BLOG_AVATAR, CONFIG_KEY.DEFAULT_IMAGE].indexOf(dataForm.name)>-1">
            <upload-image v-model="dataForm.value"/>
          </template>
          <template v-else>
            <el-input v-model="dataForm.value" placeholder="请输入参数值"/>
          </template>
        </el-form-item>
      </template>
      <el-form-item label="描述信息" prop="description">
        <el-input v-model="dataForm.description" placeholder="请输入描述内容"/>
      </el-form-item>
      <el-form-item label="参数类型" prop="type">
        <el-radio-group v-model="dataForm.type">
          <el-radio :label="1">全局变量</el-radio>
          <el-radio :label="2">系统配置</el-radio>
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
import { getConfig, postConfig, putConfig } from '@/api/config'
import UploadImage from '@/components/UploadImage'
import OssConfig from './oss-config'

const defaultForm = {
  id: 0,
  name: '',
  value: '',
  description: '',
  type: 1,
  valueArr: []
}
// 一些特殊处理的cofig key
const CONFIG_KEY = {
  BLOG_SCRIPT: 'BLOG_SCRIPT',
  BLOG_HEAD: 'BLOG_HEAD',
  BACKGROUND_LIST: 'BACKGROUND_LIST',
  BLOG_AVATAR: 'BLOG_AVATAR',
  DEFAULT_IMAGE: 'DEFAULT_IMAGE',
  FILE_STORAGE: 'FILE_STORAGE'
}
export default {
  components: { UploadImage, OssConfig },
  data() {
    return {
      visible: false,
      dataForm: defaultForm,
      dataRule: {
        name: [
          { required: true, message: '参数名不能为空', trigger: 'blur' }
        ]
      },
      CONFIG_KEY: CONFIG_KEY
    }
  },
  methods: {
    init(id) {
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (id) {
          getConfig(id).then(response => {
            response.data.valueArr = []
            this.dataForm = response.data
            if (this.dataForm.name === CONFIG_KEY.BACKGROUND_LIST) {
              const valueArr = JSON.parse(this.dataForm.value)
              for (const val of valueArr) {
                this.addValue(val)
              }
            } else if (this.dataForm.name === CONFIG_KEY.BLOG_SCRIPT) {
              const reg = /<script[^>]*?>([\s\S]*?)<\/script>/g
              const valueArr = this.dataForm.value.match(reg)
              for (const val of valueArr) {
                this.addValue(val)
              }
            } else if (this.dataForm.name === CONFIG_KEY.BLOG_HEAD) {
              const reg = /(<meta[^>]*?>)|(<link[^>]*?>)|(<style[^>]*?>([\s\S]*?)<\/style>)|(<script[^>]*?>([\s\S]*?)<\/script>)/g
              const valueArr = this.dataForm.value.match(reg)
              for (const val of valueArr) {
                this.addValue(val)
              }
            }
          })
        } else {
          this.dataForm = defaultForm
        }
      })
    },
    addValue(val) {
      this.dataForm.valueArr.push({
        value: val || ''
      })
    },
    removeValue(item) {
      var index = this.dataForm.valueArr.indexOf(item)
      if (index !== -1) {
        this.dataForm.valueArr.splice(index, 1)
      }
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.dataForm.name === CONFIG_KEY.BACKGROUND_LIST) {
            var arr = []
            for (const val of this.dataForm.valueArr) {
              if (val.value) {
                arr.push(val.value + '\n')
              }
            }
            this.dataForm.value = JSON.stringify(arr)
            this.dataForm.valueArr = []
          } else if (this.dataForm.name === CONFIG_KEY.BLOG_SCRIPT || this.dataForm.name === CONFIG_KEY.BLOG_HEAD) {
            var temp = ''
            for (const val of this.dataForm.valueArr) {
              if (val.value) {
                temp += val.value + '\n'
              }
            }
            this.dataForm.value = temp
            this.dataForm.valueArr = []
          }
          if (this.dataForm.id) {
            putConfig(this.dataForm).then(response => {
              this.$message.success('修改配置成功')
              this.visible = false
              this.$emit('refreshDataList')
            })
          } else {
            postConfig(this.dataForm).then(response => {
              this.$message.success('添加配置成功')
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
