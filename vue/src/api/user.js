import request from '@/utils/request'

export function putUser() {
  return request({
    url: '/user',
    method: 'put'
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
