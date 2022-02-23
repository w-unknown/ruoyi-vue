import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

export function listFile(query){
  return request({
    url:"system/file/list",
    method:'get',
    params:query,
  })
}
