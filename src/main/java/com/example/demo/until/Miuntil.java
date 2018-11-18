package com.example.demo.until;

import java.util.Random;

/**
 * Created by wangxiang on 2018/9/10.
 */
public class Miuntil {
    public static String getRandomString(int length){
        String str="qazwsxedcrfvtgbyhnujmikolpQAZWSXEDCRFVTGBYHNUJMIKOLP";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(52);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
