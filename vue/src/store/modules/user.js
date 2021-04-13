import { login, logout } from '@/api/login'
import { getUser } from '@/api/user'
import { getGlobal } from '@/api/config'
import { getToken, setToken, removeToken } from '@/utils/auth'

const user = {
  state: {
    token: getToken(),
    user: null,
    global: null
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_USER: (state, user) => {
      state.user = user
    },
    SET_GLOBAL: (state, global) => {
      state.global = global
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(response => {
          commit('SET_TOKEN', response.data)
          setToken(response.data)
          resolve()
        }).catch(error => {
          console.log(error)
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetUserInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getUser().then(response => {
          commit('SET_USER', response.data)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取全局参数
    GetGlobalInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getGlobal().then(response => {
          commit('SET_GLOBAL', response.data)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit }) {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          commit('SET_TOKEN', '')
          commit('SET_USER', null)
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_USER', null)
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
