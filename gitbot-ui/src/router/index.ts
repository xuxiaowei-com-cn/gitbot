import {
  createRouter,
  createWebHistory,
  type NavigationGuardNext,
  type RouteLocationNormalized
} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import { ticket } from '@/api/passport'
import { mainStore } from '@/stores/config'
import { ElMessage } from 'element-plus'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    }
  ]
})

router.beforeEach(
  (to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext) => {
    const queryTicket = to.query.ticket
    if (queryTicket) {
      console.log(queryTicket)

      if (mainStore.ticket.id == queryTicket) {
        next()
      } else {
        ticket(queryTicket).then((response: any) => {
          console.log(response)

          const success = response.success
          const message = response.message
          if (success) {
            ElMessage({
              message: message,
              type: 'success'
            })

            mainStore.setTicket(queryTicket.toString(), response.data)

            next()
          } else {
            ElMessage.error(message)
          }
        })
      }
    } else {
      next()
    }
  }
)

export default router
