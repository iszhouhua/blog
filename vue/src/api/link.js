import request from '@/utils/request'

export function getLinkList() {
  return request({
    url: '/link',
    method: 'get'
  })
}

export function getLink(id) {
  return request({
    url: '/link',
    method: 'get',
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

