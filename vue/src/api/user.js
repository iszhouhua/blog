import request from '@/utils/request'

export function getUserList(query) {
  return request({
    url: '/user/list',
    method: 'get',
    params: query
  })
}

export function getUser(id) {
  return request({
    url: '/user',
    method: 'get',
    params: { id }
  })
}

export function putUser(data) {
  return request({
    url: '/user',
    method: 'put',
    data
  })
}

export function postUser(data) {
  return request({
    url: '/user',
    method: 'post',
    data
  })
}

export function changePass(data) {
  return request({
    url: '/user/changePass',
    method: 'post',
    params: data
  })
}
