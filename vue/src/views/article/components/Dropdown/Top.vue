<template>
  <el-dropdown :show-timeout="100" trigger="click">
    <el-button plain>{{ is_top?'文章已置顶':'文章未置顶' }}
      <i class="el-icon-caret-bottom el-icon--right"/>
    </el-button>
    <el-dropdown-menu slot="dropdown" class="no-padding">
      <el-dropdown-item>
        <el-radio-group v-model="is_top" style="padding: 10px;">
          <el-radio :label="true">置顶文章</el-radio>
          <el-radio :label="false">关闭置顶</el-radio>
        </el-radio-group>
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
export default {
  props: {
    value: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    is_top: {
      get() {
        return this.value
      },
      set(val) {
        this.$emit('input', val)
      }
    }
  }
}
</script>
