<template>
  <div class="register-container">
    <div class="register-box">
      <h2>註冊系統</h2>
      <form @submit.prevent="handleregister">
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
        
        <div class="input-group">
          <label for="confirmPassword">確認密碼</label>
          <input 
            type="password" 
            id="confirmPassword" 
            v-model="confirmPassword" 
            placeholder="請再次輸入密碼"
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
          class="register-btn"
          :disabled="isLoading"
        >
          {{ isLoading ? '註冊中...' : '註冊' }}
        </button>

                <!-- 方法一：使用 router-link -->
        <router-link to="/login" class="login-link">
          已有帳號？點此登入
        </router-link>
        
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'register',
  data() {
    return {
      username: '',
      password: '',
      isLoading: false,
      errorMessage: ''
    }
  },
  methods: {
    async handleregister() {
      this.isLoading = true
      this.errorMessage = ''
      if (this.password !== this.confirmPassword) {
        this.errorMessage = '密碼不一致'
        this.isLoading = false
        return
      }
      try {
        const response = await axios.post('http://127.0.0.1:8080/api/user/register', {
          user: this.username,
          password: this.password
        }, {
          headers: {
            'Content-Type': 'application/json'
          }
        })

        if(response.status === 200){
          alert('註冊成功,即將跳轉至登入頁面')
          this.$router.push('/login')
        }
        
      } catch (error) {
        if(error.response.status === 400){
          alert('帳號已存在,請重新輸入')
        }else{
          this.errorMessage = error.response?.data?.message || '註冊失敗，請稍後再試'
          console.error('註冊失敗：', error)
        }
        
      } finally {
        this.isLoading = false
      }
    }
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f5f5;
}

.register-box {
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

.register-btn {
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

.register-btn:hover {
  background-color: #45a049;
}

.error-message {
  color: #ff4444;
  margin-bottom: 1rem;
  text-align: center;
}

.register-btn:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}
</style>