import request from '@/utils/request'

export function getTagList() {
  return request({
    url: '/tag/list',
    method: 'get'
  })
}

export function getTag(id) {
  return request({
    url: '/tag',
    method: 'get',
    params: { id }
  })
}

export function postTag(data) {
  return request({
    url: '/tag',
    method: 'post',
    data
  })
}

export function deleteTag(id) {
  return request({
    url: '/tag',
    method: 'delete',
    params: { id }
  })
}
