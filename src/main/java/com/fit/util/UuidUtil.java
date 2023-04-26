package com.fit.util;

import java.util.Random;
import java.util.UUID;

public class UuidUtil {

    /**
     * 随机生成六位数验证码
     */
    public static int getRandomNum() {
        return getRandomNum(900000, 100000);
    }

    public static int getRandomNum(int max, int min) {
        Random r = new Random();
        return r.nextInt(max) + min;
    }

    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    public static void main(String[] args) {
        System.out.println(get32UUID());
    }
}

