import request from '@/utils/request'

export function visitsCount() {
  return request({
    url: '/log/visitsCount',
    method: 'get'
  })
}

export function latestLog(number) {
  return request({
    url: '/log/latest',
    method: 'get',
    params: { number }
  })
}

export function statBrowser() {
  return request({
    url: '/log/browser',
    method: 'get'
  })
}

export function statOperatingSystem() {
  return request({
    url: '/log/operatingSystem',
    method: 'get'
  })
}

export function statCity() {
  return request({
    url: '/log/city',
    method: 'get'
  })
}
