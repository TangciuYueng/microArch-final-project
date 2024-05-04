import axios from 'axios';

const chatInstance = axios.create({
    baseURL: "http://localhost:8890/api",
    timeout: 30000
});

chatInstance.interceptors.request.use(
    config => {
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

chatInstance.interceptors.response.use(
    response => {
        return response.data;
    },
    error => {
        return Promise.reject(error);
    }
);

export function getNewChatRoom(data) {
    return chatInstance.put("/chat", data);
}

export function defaultPort() {
    return chatInstance.post("/chat");
}