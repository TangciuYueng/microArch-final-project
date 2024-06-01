import axios from 'axios';
import { friendServer } from '@/main';

const friendInstance = axios.create({
  baseURL: "http://" + friendServer + ":8885/api",
  timeout: 30000
});

friendInstance.interceptors.request.use(
  config => {
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

friendInstance.interceptors.response.use(
  response => {
    return response.data;
  },
  error => {
    return Promise.reject(error);
  }
);

export function getFriends(params) {
  return friendInstance.get("/friend", {
    params: params
  });
}

export function addFriend(data) {
  return friendInstance.post("/friend", data);
}

export function addChatRecord(data) {
  return friendInstance.post("/chat", data);
}

export function getFriendCount(params) {
  return friendInstance.get("/friend/count", {
    params: params
  });
}

export function getChatRecord(params) {
  return friendInstance.get("/chat", {
    params: params
  });
}