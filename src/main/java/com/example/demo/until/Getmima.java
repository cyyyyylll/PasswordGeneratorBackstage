package com.example.demo.until;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by wangxiang on 2018/9/5.
 */
public class Getmima {
    String result="";
    public String  getmima(String image,String webadds,String worder){
        String[] arguments = new String[]{"python","/root/InnovationProject/sec.py",image,webadds,worder};
        try{
            Process process = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                result = line;
            }
            //System.out.println(result);
            in.close();
            int re = process.waitFor();
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return result;
        }
    }
}
