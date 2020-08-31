import request from '@/utils/request'
import qs from 'qs'

export function getArticleList(query) {
  return request({
    url: '/article/list',
    method: 'get',
    params: query
  })
}

export function getArticle(id) {
  return request({
    url: '/article',
    method: 'get',
    params: { id }
  })
}

export function postArticle(data) {
  return request({
    url: '/article',
    method: 'post',
    data
  })
}

export function deleteArticle(ids) {
  return request({
    url: '/article',
    method: 'delete',
    params: { ids },
    paramsSerializer: params => {
      return qs.stringify(params, { indices: false })
    }
  })
}

export function latestArticle(number) {
  return request({
    url: '/article/latest',
    method: 'get',
    params: { number }
  })
}

export function modifyArticle(data) {
  return request({
    url: '/article/modify',
    method: 'post',
    data
  })
}

