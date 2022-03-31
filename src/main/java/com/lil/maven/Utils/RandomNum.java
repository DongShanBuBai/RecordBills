package com.lil.maven.Utils;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Author:lil
 * @Date: 2022-03-31
 */
@Component
public class RandomNum {
    public String getRandomNum(int couts){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0;i < couts;i++){
            int a = random.nextInt(10);
            builder.append(a);
        }
        return builder.toString();
    }
}
