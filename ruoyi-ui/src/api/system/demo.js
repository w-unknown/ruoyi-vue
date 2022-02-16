import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

export function fields() {
  return request({
    url: '/system/demo/fields',
    method: 'get',
  })
}

export function listDemo(query) {
  return request({
    url:'/system/demo/list',
    method:'get',
    params:query,
  })
}

export function listChart(query) {
  return request({
    url:'/system/demo/chart',
    method:'get',
    params:query,
  })
}
