import request from '@/utils/request'

export function getSpider() {
  return request({
    url: '/spider',
    method: 'get'
  })
}

export function putSpider(id) {
  return request({
    url: '/spider',
    method: 'put',
    params: { id }
  })
}

export function postSpider(data) {
  return request({
    url: '/spider',
    method: 'post',
    data
  })
}

export function deleteSpider(id) {
  return request({
    url: '/spider',
    method: 'delete',
    params: { id }
  })
}

export function spiderArticle(data) {
  return request({
    url: '/spider/spiderArticle',
    method: 'post',
    params: data
  })
}

