package com.example.demo.until;

import java.util.Random;

/**
 * Created by wangxiang on 2018/8/20.
 */
public class RandomUtil {
    public static String getRandomString(int length){
        String str="0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(10);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
