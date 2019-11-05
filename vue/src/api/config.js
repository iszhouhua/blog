import request from '@/utils/request'

export function getConfig() {
  return request({
    url: '/config',
    method: 'get'
  })
}

export function putConfig(id) {
  return request({
    url: '/config',
    method: 'put',
    params: { id }
  })
}

export function postConfig(data) {
  return request({
    url: '/config',
    method: 'post',
    data
  })
}

export function deleteConfig(id) {
  return request({
    url: '/config',
    method: 'delete',
    params: { id }
  })
}

export function getGlobal() {
  return request({
    url: '/config/global',
    method: 'get'
  })
}
