<template>
  <el-input v-model="image" placeholder="图片地址">
    <el-upload slot="append" :before-upload="beforeImageUpload" :http-request="uploadImg" :show-file-list="false" action="" accept="image/*">
      <el-button type="primary">上传图片<i class="el-icon-upload el-icon--right"/></el-button>
    </el-upload>
  </el-input>
</template>

<script>
import { uploadImage } from '@/api/upload'
export default {
  name: 'UploadImage',
  props: {
    value: {
      type: String,
      default: ''
    }
  },
  computed: {
    image: {
      get() {
        return this.value
      },
      set(val) {
        this.$emit('input', val)
      }
    }
  },
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
        this.$message.success(response.msg)
        this.image = response.data
      })
    }
  }
}
</script>

<style scoped>
.svg-icon {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}
</style>
