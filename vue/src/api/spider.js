import request from '@/utils/request'

export function getSpiderList() {
  return request({
    url: '/spider/list',
    method: 'get'
  })
}

export function getSpider(id) {
  return request({
    url: '/spider',
    method: 'get',
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

