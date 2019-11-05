<template>
  <article-list :article-status="3"/>
</template>

<script>
import ArticleList from './list'

export default {
  name: 'CustomList',
  components: { ArticleList }
}
</script>

