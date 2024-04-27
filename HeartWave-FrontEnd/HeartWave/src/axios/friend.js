import axios from 'axios';

const friendInstance = axios.create({
    baseURL: "http://localhost:8885/api",
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

export function updateChatTime(data) {
    return friendInstance.post("/chat/time", data);
}