package cn.edu.tongji.login.utils;


import java.util.concurrent.ThreadLocalRandom;

public class RandomSmsCode {
    // 更适合并发环境的随机数生成器
    private final static ThreadLocalRandom random = ThreadLocalRandom.current();
    public static String getCode() {
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; ++i) {
            System.out.println(getCode());
        }
    }
}
