import request from '@/utils/request'

export function getLinkList(query) {
  return request({
    url: '/link/list',
    method: 'get',
    params: query
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
export function putLink(data) {
  return request({
    url: '/link',
    method: 'put',
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

