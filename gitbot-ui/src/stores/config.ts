import { createPinia, defineStore } from 'pinia'
import { reactive } from 'vue'

export const useMainStore = defineStore('main', {
  state: () => ({
    ticket: reactive({
      id: '',
      accessToken: ''
    })
  }),
  getters: {},
  actions: {
    setTicket(id: string, accessToken: string) {
      this.ticket.id = id
      this.ticket.accessToken = accessToken
    }
  }
})

const mainStore = useMainStore(createPinia())

// 订阅缓存的修改
mainStore.$subscribe((mutation, state) => {
  // 将缓存的修改放入本地缓存中
  localStorage.setItem(mainStore.$id, JSON.stringify({ ...state }))
})

// 获取历史缓存
const useStoreOld = localStorage.getItem(mainStore.$id)
if (useStoreOld) {
  // 返回已存在的缓存
  mainStore.$state = JSON.parse(useStoreOld)
}

// 注意，在使用时，不用构造方法，直接调用即可
export { mainStore }
