import request from '@/utils/request'

export function uploadImage(data) {
  return request({
    url: '/uploadImage',
    method: 'post',
    data,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}
