import request from '@/utils/request'

export function getComment(query) {
  return request({
    url: '/comment',
    method: 'get',
    params: query
  })
}

export function putComment(id) {
  return request({
    url: '/comment',
    method: 'put',
    params: { id }
  })
}

export function postComment(data) {
  return request({
    url: '/comment',
    method: 'post',
    data
  })
}

export function commentCount() {
  return request({
    url: '/comment/commentCount',
    method: 'get'
  })
}

export function latestComment(number) {
  return request({
    url: '/comment/latest',
    method: 'get',
    params: { number }
  })
}
