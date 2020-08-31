import request from '@/utils/request'

export function getUser() {
  return request({
    url: '/user',
    method: 'get'
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
