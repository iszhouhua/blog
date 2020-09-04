import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    children: [{
      path: 'home',
      name: 'home',
      component: () => import('@/views/home/index'),
      meta: { title: '首页', icon: 'home' }
    }]
  },
  {
    path: '/article',
    component: Layout,
    redirect: '/article/list',
    name: 'article',
    meta: {
      title: '文章管理',
      icon: 'article'
    },
    children: [
      {
        path: 'create',
        component: () => import('@/views/article/create'),
        name: 'createArticle',
        meta: { title: '创建文章', icon: 'edit' }
      },
      {
        path: 'edit/:id(\\d+)',
        component: () => import('@/views/article/edit'),
        name: 'editArticle',
        meta: { title: '编辑文章', noCache: true },
        hidden: true
      },
      {
        path: 'list',
        component: () => import('@/views/article/list'),
        name: 'articleList',
        meta: { title: '文章列表', icon: 'list' }
      },
      // {
      //   path: 'custom',
      //   component: () => import('@/views/article/custom'),
      //   name: 'customList',
      //   meta: { title: '自定义文章列表', icon: 'custom' }
      // },
      {
        path: 'recycle',
        component: () => import('@/views/article/recycle'),
        name: 'recycleBin',
        meta: { title: '回收站', icon: 'recycle-bin' }
      }
    ]
  },
  {
    path: '/comment',
    component: Layout,
    redirect: '/comment/list',
    children: [{
      path: 'list',
      name: 'commentList',
      component: () => import('@/views/comment/index'),
      meta: { title: '评论管理', icon: 'comment', noCache: true }
    }]
  },
  {
    path: '/category',
    component: Layout,
    redirect: '/category/list',
    children: [{
      path: 'list',
      name: 'categoryList',
      component: () => import('@/views/category/index'),
      meta: { title: '分类管理', icon: 'category', noCache: true }
    }]
  },
  {
    path: '/tag',
    component: Layout,
    redirect: '/tag/list',
    children: [{
      path: 'list',
      name: 'tagList',
      component: () => import('@/views/tag/index'),
      meta: { title: '标签管理', icon: 'tag', noCache: true }
    }]
  },
  {
    path: '/menu',
    component: Layout,
    redirect: '/menu/list',
    children: [{
      path: 'list',
      name: 'menuList',
      component: () => import('@/views/menu/index'),
      meta: { title: '目录管理', icon: 'menu', noCache: true }
    }]
  },
  {
    path: '/spider',
    component: Layout,
    redirect: '/spider/list',
    children: [{
      path: 'spider',
      name: 'spiderList',
      component: () => import('@/views/spider/index'),
      meta: { title: '规则管理', icon: 'spider', noCache: true }
    }]
  },
  {
    path: '/link',
    component: Layout,
    redirect: '/link/list',
    children: [{
      path: 'list',
      name: 'linkList',
      component: () => import('@/views/link/index'),
      meta: { title: '链接管理', icon: 'link', noCache: true }
    }]
  },
  {
    path: '/user',
    component: Layout,
    redirect: '/user/list',
    children: [{
      path: 'list',
      name: 'userList',
      component: () => import('@/views/user/index'),
      meta: { title: '用户管理', icon: 'user', noCache: true }
    }, {
      path: 'info',
      name: 'userInfo',
      component: () => import('@/views/user/user-info'),
      hidden: true,
      meta: { title: '用户信息', icon: 'user', noCache: true }
    }]
  },
  {
    path: '/config',
    component: Layout,
    redirect: '/config/info',
    children: [{
      path: 'info',
      name: 'configInfo',
      component: () => import('@/views/config/index'),
      meta: { title: '配置管理', icon: 'config', noCache: true }
    }]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
