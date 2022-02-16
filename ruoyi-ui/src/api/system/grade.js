import request from '@/utils/request'
import {parseStrEmpty} from "@/utils/ruoyi";

//查询学生成绩
export function listGrade(query) {
  return request({
    url: '/system/grade/list',
    method: 'get',
    params: query,
  })
}
export function getGrade(id) {
  return request({
    url: '/system/grade/sysGrade/'+parseStrEmpty(id),
    method:'get',
  })
}
//更新学生成绩
export function updateGrade(data) {
  return request({
    url: '/system/grade/edit',
    method: 'put',
    data: data,
  })
}

//添加学生成绩
export function addGrade(data) {
  return request({
    url: '/system/grade/add',
    method: 'post',
    data: data,
  })
}

//删除学生成绩
export function deleteGrade(ids) {
  return request({
    url: '/system/grade/delete/'+ids,
    method: 'delete',
  })
}

//根据科目查询学生成绩信息
export function getSysGradeByProject(project) {
  return request({
    url:'/system/grade/echarts/'+project,
    method:'get',
  })
}
