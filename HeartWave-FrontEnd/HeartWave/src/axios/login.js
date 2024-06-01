import axios from 'axios';
import { loginServer } from '@/main';

const loginInstance = axios.create({
  baseURL: "http://" + loginServer + ":8887/api",
  timeout: 30000
});

loginInstance.interceptors.request.use(
  config => {
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

loginInstance.interceptors.response.use(
  response => {
    return response.data;
  },
  error => {
    return Promise.reject(error);
  }
);

export function login(params) {
  return loginInstance.get("/login", {
    params: params
  });
}

export function getVerifyInfo() {
  return loginInstance.get("/login/verify-info");
}

export function sendSmsRegister(phone) {
  return loginInstance.get("/user/sms/register/" + phone);
}

export function updateUserInfo(data) {
  return loginInstance.put("/user", data);
}

export function register(data) {
  return loginInstance.post("/user", data);
}

export function sendSmsSetPassword(params) {
  return loginInstance.get("/user/sms/reset-password", {
    params: params
  })
}

export function resetPassword(data) {
  return loginInstance.put("/user/reset-password", data);
}