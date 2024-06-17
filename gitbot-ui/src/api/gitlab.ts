import request, { type AjaxResponse, type Page } from '../utils/request'
import type { AxiosResponse } from 'axios'

export interface GlInstanceVO {
  host: string
  sum: number
}

export interface GlProjectBO {
  current: number
  size: number
  host: string
  name: string
}

export interface GlProjectVO {
  id: string
  host: string
  name: String
  webUrl: string
}

/**
 * GitLab 实例列表
 */
export const getInstance = async function (): Promise<AjaxResponse<GlInstanceVO[]>> {
  return request
    .get(`/gl/instance`)
    .then((response: AxiosResponse<AjaxResponse<GlInstanceVO[]>>) => {
      return response.data
    })
}

/**
 * GitLab 项目列表
 */
export const getProject = async function (
  bo: GlProjectBO
): Promise<AjaxResponse<Page<GlProjectVO>>> {
  return request
    .get(`/gl/project`, { params: bo })
    .then((response: AxiosResponse<AjaxResponse<Page<GlProjectVO>>>) => {
      return response.data
    })
}
