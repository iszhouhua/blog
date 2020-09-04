import request from '@/utils/request'

export function getCategoryList(query) {
  return request({
    url: '/category/list',
    method: 'get',
    params: query
  })
}

export function getCategory(id) {
  return request({
    url: '/category',
    method: 'get',
    params: { id }
  })
}

export function postCategory(data) {
  return request({
    url: '/category',
    method: 'post',
    data
  })
}

export function putCategory(data) {
  return request({
    url: '/category',
    method: 'put',
    data
  })
}

export function deleteCategory(id) {
  return request({
    url: '/category',
    method: 'delete',
    params: { id }
  })
}

