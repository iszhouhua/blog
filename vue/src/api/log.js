import request from '@/utils/request'

export function latestLog(number) {
  return request({
    url: '/log/latest',
    method: 'get',
    params: {
      number
    }
  })
}
