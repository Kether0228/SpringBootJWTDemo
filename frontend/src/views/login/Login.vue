<template>
  <div class="login-container">
    <div class="login-box">
      <h2>系統登入</h2>
      <form @submit.prevent="handleLogin">
        <div class="input-group">
          <label for="username">帳號</label>
          <input 
            type="text" 
            id="username" 
            v-model="username" 
            placeholder="請輸入帳號"
            required
            :disabled="isLoading"
          >
        </div>
        <div class="input-group">
          <label for="password">密碼</label>
          <input 
            type="password" 
            id="password" 
            v-model="password" 
            placeholder="請輸入密碼"
            required
            :disabled="isLoading"
          >
        </div>
        
        <!-- 錯誤訊息顯示 -->
        <div v-if="errorMessage" class="error-message">
          {{ errorMessage }}
        </div>
        
        <button 
          type="submit" 
          class="login-btn"
          :disabled="isLoading"
        >
          {{ isLoading ? '登入中...' : '登入' }}
        </button>

        
        <router-link to="/register" class="login-link">
          尚未註冊？點此註冊
        </router-link>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
      isLoading: false,
      errorMessage: ''
    }
  },
  methods: {
    async handleLogin() {
      this.isLoading = true
      this.errorMessage = ''
      
      try {
        const response = await axios.post('http://127.0.0.1:8080/api/user/login', {
          user: this.username,
          password: this.password
        }, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        
        console.log('登入成功：', response.data)
        
        // 如果後端返回 token，可以儲存它
        if (response.data.token) {
          localStorage.setItem('token', response.data.token)
        }
        
        // 登入成功後的處理
        // this.$router.push('/dashboard') // 導向儀表板頁面
        
      } catch (error) {
        this.errorMessage = error.response?.data?.message || '登入失敗，請稍後再試'
        console.error('登入失敗：', error)
      } finally {
        this.isLoading = false
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f5f5;
}

.login-box {
  background: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

h2 {
  text-align: center;
  color: #333;
  margin-bottom: 2rem;
}

.input-group {
  margin-bottom: 1.5rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  color: #666;
}

input {
  width: 100%;
  padding: 0.8rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

input:focus {
  outline: none;
  border-color: #4CAF50;
}

.login-btn {
  width: 100%;
  padding: 0.8rem;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

.login-btn:hover {
  background-color: #45a049;
}

.error-message {
  color: #ff4444;
  margin-bottom: 1rem;
  text-align: center;
}

.login-btn:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}
</style>