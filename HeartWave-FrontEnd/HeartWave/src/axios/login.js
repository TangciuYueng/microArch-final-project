import axios from 'axios';

const loginInstance = axios.create({
    //各接口
    //  登录：/api/login/user
    baseURL: "http://localhost:8887/api",
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
    return loginInstance.get("/login/user", {
        params: params
    });
}

export function getVerifyInfo() {
    return loginInstance.get("/login/verify-info");
}

export function sendSmsRegister(phone) {
    return loginInstance.get("/user/sms/register/" + phone);
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