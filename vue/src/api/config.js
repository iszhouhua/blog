import request from '@/utils/request'

export function getConfigList() {
  return request({
    url: '/config/list',
    method: 'get'
  })
}

export function getConfig(id) {
  return request({
    url: '/config',
    method: 'get',
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
