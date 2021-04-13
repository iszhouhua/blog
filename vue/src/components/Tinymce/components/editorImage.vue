<template>
  <div class="upload-container">
    <el-upload :before-upload="beforeImageUpload" :http-request="uploadImg" :show-file-list="false" action="" accept="image/*">
      <el-button style="{background:#1890ff,borderColor:#1890ff}" icon="el-icon-upload" size="mini" type="primary">
        上传图片
      </el-button>
    </el-upload>
  </div>
</template>

<script>
import { uploadImage } from '@/api/upload'

export default {
  name: 'EditorSlideUpload',
  methods: {
    // 验证图片上传格式
    beforeImageUpload(file) {
      const isImage = /^image\/*/.test(file.type)
      if (!isImage) {
        this.$message.error('上传文件只能是图片!')
      }
      return isImage
    },
    // 上传图片
    uploadImg(data) {
      var formdata = new FormData()
      formdata.append('image', data.file)
      uploadImage(formdata).then(response => {
        this.$message.success('上传成功')
        this.$emit('success', response.data)
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.editor-slide-upload {
  margin-bottom: 20px;
  /deep/ .el-upload--picture-card {
    width: 100%;
  }
}
</style>
