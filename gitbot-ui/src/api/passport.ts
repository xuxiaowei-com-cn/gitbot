import request, { type AjaxResponse } from '../utils/request'
import type { AxiosResponse } from 'axios'
import type { LocationQueryValue } from 'vue-router'

/**
 * 兑换票据
 * @param ticket
 */
export const ticket = async function (
  ticket: string | LocationQueryValue[]
): Promise<AjaxResponse<string>> {
  return request
    .get(`/login/ticket?ticket=${ticket}`)
    .then((response: AxiosResponse<AjaxResponse<string>>) => {
      return response.data
    })
}
