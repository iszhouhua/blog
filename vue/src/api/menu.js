import request from '@/utils/request'

export function getMenu() {
  return request({
    url: '/menu',
    method: 'get'
  })
}

export function putMenu(id) {
  return request({
    url: '/menu',
    method: 'put',
    params: { id }
  })
}

export function postMenu(data) {
  return request({
    url: '/menu',
    method: 'post',
    data
  })
}

export function deleteMenu(id) {
  return request({
    url: '/menu',
    method: 'delete',
    params: { id }
  })
}
