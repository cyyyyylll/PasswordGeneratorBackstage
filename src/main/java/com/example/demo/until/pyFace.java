package com.example.demo.until;

import java.io.*;
public class pyFace{
    String result="NULL";
    public pyFace(){ }
    public String  getFaceString(String oldimage,String newImage){
        String[] arguments = new String[]{"python","/root/InnovationProject/first.py",oldimage,newImage};//要调用的程序类型，路径以及所需参数
        try{
            Process process = Runtime.getRuntime().exec(arguments);//调用python程序
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));//得到结果输出流
            String line = null;
            while ((line = in.readLine()) != null) {
                result = line; //转化结果格式
            }
            //System.out.println(result);
            in.close();
            int re = process.waitFor();
            return result;//输出结果
        }catch(Exception e){
            e.printStackTrace();
            return result;
        }
    }
}
