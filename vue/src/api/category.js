import request from '@/utils/request'

export function getCategory() {
  return request({
    url: '/category',
    method: 'get'
  })
}

export function putCategory(id) {
  return request({
    url: '/category',
    method: 'put',
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

export function deleteCategory(id) {
  return request({
    url: '/category',
    method: 'delete',
    params: { id }
  })
}

