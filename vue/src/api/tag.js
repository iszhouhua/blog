import request from '@/utils/request'

export function getTagList(query) {
  return request({
    url: '/tag/list',
    method: 'get',
    params: query
  })
}

export function getAllTag() {
  return request({
    url: '/tag/all',
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

export function putTag(data) {
  return request({
    url: '/tag',
    method: 'put',
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
