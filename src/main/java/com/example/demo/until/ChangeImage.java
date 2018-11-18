package com.example.demo.until;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
/**
 * Created by wangxiang on 2018/9/9.
 */
public class ChangeImage {


        String result="NULL";
        public ChangeImage(){
        }
        public void  Change(String oldimage,String imagename) throws IOException {
            String[] arguments = new String[]{"python", "/root/InnovationProject/imageFilp.py", oldimage, imagename};
            Process process = Runtime.getRuntime().exec(arguments);
        }
    }


