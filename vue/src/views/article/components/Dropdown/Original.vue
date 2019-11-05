<template>
  <el-dropdown :show-timeout="100" trigger="click">
    <el-button plain>
      {{ is_original?'原创文章':'转载文章' }}
      <i class="el-icon-caret-bottom el-icon--right"/>
    </el-button>
    <el-dropdown-menu slot="dropdown" class="no-padding no-border">
      <el-from-item>
        <el-radio-group v-model="is_original" style="padding: 10px;">
          <el-radio :label="true">原创文章</el-radio>
          <el-radio :label="false">转载文章</el-radio>
        </el-radio-group>
      </el-from-item>
      <el-from-item v-if="!is_original" label-width="0px" style="margin-bottom: 0px" prop="source_url">
        <el-input v-model="source_url" placeholder="请输入文章来源">
          <template slot="prepend">原文链接</template>
        </el-input>
      </el-from-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
export default {
  props: {
    value: {
      type: Boolean,
      default: true
    },
    sourceUrl: {
      type: String,
      default: null
    }
  },
  computed: {
    is_original: {
      get() {
        return this.value
      },
      set(val) {
        this.$emit('input', val)
      }
    },
    source_url: {
      get() {
        return this.sourceUrl
      },
      set(val) {
        this.$emit('update:sourceUrl', val)
      }
    }
  }
}
</script>
