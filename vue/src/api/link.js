import request from '@/utils/request'

export function getLink() {
  return request({
    url: '/link',
    method: 'get'
  })
}

export function putLink(id) {
  return request({
    url: '/link',
    method: 'put',
    params: { id }
  })
}

export function postLink(data) {
  return request({
    url: '/link',
    method: 'post',
    data
  })
}

export function deleteLink(id) {
  return request({
    url: '/link',
    method: 'delete',
    params: { id }
  })
}

