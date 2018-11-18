package com.example.demo.controller;


import com.example.demo.until.Getmima;
import com.example.demo.User;
import com.example.demo.Webadd;
import com.example.demo.until.Miuntil;
import com.example.demo.repository.UserRepository;
import com.example.demo.until.pyFace;
import com.example.demo.repository.WebRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.example.demo.until.ChangeImage;
import com.example.demo.until.AES;

import java.io.File;
import java.io.*;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

/**
 * Created by wangxiang on 2018/8/22.
 */
@RestController
@EnableSwagger2
@Transactional
@RequestMapping("/image")
public class PhotoController extends HttpServlet {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WebRepository webRepository;

    @RequestMapping(value = "/yzimage", method = RequestMethod.POST)
    @Transactional
    public String yzimage(HttpServletRequest request,
     @RequestParam  MultipartFile file) throws Exception,NullPointerException{
        String key="1100101101011101010101";//密钥

        if (file.isEmpty()) {  //检查前端传来的图片文件是否存在
            return "文件为空";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        //logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        //String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String username = request.getHeader("filename");//获取用户名

        Getmima mima = new Getmima();
        String webadds1=request.getHeader("webadds");//从报文中取得网址
        String webadds1de=AES.decrypt(key,webadds1);//解密加密的网址
        String worder=request.getHeader("worder");//从报文取得口令
        String worderde=AES.decrypt(key,worder);//解密加密的口令
        Webadd webadd = new Webadd();
        Webadd webadd1 = new Webadd();
        //logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = "/root/InnovationProject/imageTemp/";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + username+".jpg");
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {  //检查文件夹是否存在
            dest.getParentFile().mkdirs();  //如果不存在则创建文件夹
        }
        try {
            file.transferTo(dest); //将文件保存到文件中

            User user2 = new User();
            String newimage = filePath + fileName;//临时添加的照片的路径
            String changenew = (filePath+"_"+username+".jpg");//保存路径名
            ChangeImage changeImage = new ChangeImage();//转换图片
            changeImage.Change(newimage,changenew);
            user2 = userRepository.findByName(username);
            //通过name寻找User
            String oldimagepath = user2.getPhoto();//找到该User的存储的image
            oldimagepath=AES.decrypt(key,oldimagepath);
            if (oldimagepath == null) {   //检查是否存在图片
                return oldimagepath;
            }
            pyFace p = new pyFace();
            String result = p.getFaceString(oldimagepath,changenew);//通过人脸识别算法验证是不是该用户
            if (!result.equals("Ture")){
                return "false"; //识别失败则返回false
            }else {
                //对该User查找添加Web
                webadd1 = webRepository.findByWebaddsAndUser(webadds1, user2);
                if (webadd1 == null) {
                    //如果无该web的密码。则添加，并获取密码
                    String shuiji = Miuntil.getRandomString(6);
                    String finalworder=(worderde+shuiji).toString();
                    webadd.setWorder(worder);//添加order
                    webadd.setWebadd(webadds1);//添加web地址
                    webadd.setUser(user2);//添加web所属User
                    webadd.setShuiji(shuiji);
                    String finalmima = mima.getmima(oldimagepath,finalworder,webadds1de);//通过加密算法得到密码
                    finalmima= finalmima.substring(2,14);//截取13位的密码作为最后密码
                    finalmima=AES.encrypt(key,finalmima);//
                    webadd.setMima(finalmima);//
                    webRepository.save(webadd);
                    return webadd.getMima();
                }else {
                    //如果有该web，则返回该web密码
                     webadd = webRepository.findMimaByWebaddsAndUser(webadds1, user2);//根据网址和用户查找
                     if(webadd.getWorder().equals(worder)){
                         return webadd.getMima();//取得密码
                     }else {
                         return "orderError";//如果错误则返回orderError
                     }
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
       return webadd.getMima();
     }


//    static void ByteToFile(byte[] bytes) throws Exception {
//        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//        BufferedImage bi1 = ImageIO.read(bais);
//        try {
//            File w2 = new File("/Users/wangxiang/Desktop/test/a.jpg");//可以是jpg,png,gif格式
//            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            bais.close();
//        }
//    }
    @RequestMapping(value = "/setimage3",method = RequestMethod.POST)
    public String upload(HttpServletRequest request,
            @RequestParam  MultipartFile file) {
        String key = "1100101101011101010101";
        if (file.isEmpty()) {
            return "文件为空";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        //logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = "/root/InnovationProject/image/";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            User user = new User();
            User user1=new User();
            user1 = userRepository.findByName(request.getHeader("filename"));
            if (user1==null){
                return "namefalse";
            }else {
            user = userRepository.findByName(request.getHeader("filename"));
            String s = ("/root/InnovationProject/image/" + fileName).toString();
            String ss=("/root/InnovationProject/image/"+"_"+fileName).toString();
            ChangeImage newimage = new ChangeImage();
            newimage.Change(s,ss);
            ss=AES.encrypt(key,ss);
            user.setPhoto(ss);
            userRepository.save(user);
            if (user.getPhoto().equals(null)) {
                return "photofalse";
            } else {
                return "true";
            }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

//    @RequestMapping(value = "/setimage2", method = RequestMethod.POST)
//    @ResponseBody
//    public void setimage2(HttpServletRequest request
//    ) throws ServletException, IOException, FileUploadException {
//        System.out.println(request.getHeader("filename") + "  a");
//        InputStream is = request.getInputStream();
//        byte[] b = new byte[1024];
//        System.out.println(b.length);
//        int y=is.read(b);
//        System.out.println(y);
//        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
//        //上传时生成的临时文件保存目录
//        String tempPath = "/Users/wangxiang/Desktop/test/";
//        File tmpFile = new File(tempPath);
//        if (!tmpFile.exists()) {
//            //创建临时目录
//            tmpFile.mkdir();
//        }

//        //消息提示
//        String message = "";
//
////        try {
//            System.out.println("try");
//            //使用Apache文件上传组件处理文件上传步骤：
//            //1、创建一个DiskFileItemFactory工厂
//            DiskFileItemFactory factory = new DiskFileItemFactory();
//            //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
//            factory.setSizeThreshold(1024 * 100);//设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
//            //设置上传时生成的临时文件的保存目录
//            factory.setRepository(tmpFile);
//            //2、创建一个文件上传解析器
//            ServletFileUpload upload = new ServletFileUpload(factory);
//            //监听文件上传进度
////                        upload.setProgressListener(new ProgressListener(){
////                            public void update(long pBytesRead, long pContentLength, int arg2) {
//                                System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
    /**
     * 文件大小为：14608,当前已处理：4096
     文件大小为：14608,当前已处理：7367
     文件大小为：14608,当前已处理：11419
     文件大小为：14608,当前已处理：14608
     */
//                                float f = pBytesRead/pContentLength;
//                                try {
//                                    response.getWriter().write(f+"");
//                                } catch (IOException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        });
//            //解决上传文件名的中文乱码
//            upload.setHeaderEncoding("UTF-8");
//            //3、判断提交上来的数据是否是上传表单的数据
//            if (!ServletFileUpload.isMultipartContent(request)) {
//                //按照传统方式获取数据
////                return"false";
//            }
//
//            //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
//            upload.setFileSizeMax(1024 * 1024);
//            //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
//            upload.setSizeMax(1024 * 1024 * 10);
//            System.out.println("==============================");
//            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
//            //FileItemIterator fileItemIterator = upload.getItemIterator(request);
//            //FileItemStream inputStream = fileItemIterator.next();
//            //System.out.println(fileItemIterator);
//
//            System.out.println("------------------------------");
////            //for (FileItem item : fileItemIterator) {
//                System.out.println("------------------------------");
//                //如果fileitem中封装的是普通输入项的数据
//                if (fileItemIterator.isFormField()) {
//                    String name = item.getFieldName();
//                    System.out.println(name);
//                    //解决普通输入项的数据的中文乱码问题
//                    String value = item.getString("UTF-8");
//                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
//                    System.out.println(name + "=" + value);
//                } else {//如果fileitem中封装的是上传文件
//                    //得到上传的文件名称，
//                    String filename = item.getName();
////                                filename = filename.substring(9,41);
//                    String username = filename.substring(9, 41);
//                    System.out.println(filename);
//                    if (filename == null || filename.trim().equals("")) {
//
//                    }
//                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
//                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
//                    filename = filename.substring(filename.lastIndexOf("/") + 1);
//                    //得到上传文件的扩展名
//                    String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
//                    //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
//                    System.out.println("上传的文件的扩展名是：" + fileExtName);
//                    //获取item中的上传文件的输入流
//                    InputStream in = item.getInputStream();
//                    byte[] ba = new byte[1024];
//                    int y = in.read(ba);
//                    System.out.println(y);
//                    //得到文件保存的名称
//                    String saveFilename = makeFileName(filename);
//                    //得到文件的保存目录
//                    String realSavePath = makePath(saveFilename, savePath);
//                    System.out.println("saveFilename" + saveFilename + "realSavePath" + realSavePath);
//                    //创建一个文件输出流
//                    //FileOutputStream out = new FileOutputStream(realSavePath + "\\" + filename);
//                    FileOutputStream out = new FileOutputStream(savePath + "/" + filename);
//                    //创建一个缓冲区
//                    byte buffer[] = new byte[1024];
//                    //判断输入流中的数据是否已经读完的标识
//                    int len = 0;
//                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
//                    while ((len = in.read(buffer)) > 0) {
//                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
//                        out.write(buffer, 0, len);
//                    }
//                    //关闭输入流
//                    in.close();
//                    //关闭输出流
//                    out.close();
//                    User user = new User();
//                    user = userRepository.findByName(username);
//                    user.setPhoto(saveFilename);
//                    userRepository.save(user);
//                    if (user.getPhoto().equals(null)) {
//                        return;
//                    } else {
//                        return;
//                    }
//
//                }
//            }
//            //}catch (FileUploadBase.FileSizeLimitExceededException e) {
//            //   e.printStackTrace();
//            //   request.setAttribute("message", "单个文件超出最大值！！！");
//            //   request.getRequestDispatcher("/message.jsp").forward(request, response);
//            //   return;
//            //}catch (FileUploadBase.SizeLimitExceededException e) {
//            //    e.printStackTrace();
//            //    request.setAttribute("message", "上传文件的总的大小超出限制的最大值！！！");
//            //    request.getRequestDispatcher("/message.jsp").forward(request, response);
//            //    return;
//        } catch (Exception e) {
//            message = "文件上传失败！";
//            e.printStackTrace();
//        }
//        request.setAttribute("message", message);
//        // request.getRequestDispatcher("/message.jsp").forwa
//////        return message;}
//            return;
//        }


    //    @RequestMapping(value = "/setimage", method = RequestMethod.POST)
//    @ResponseBody
//    public int setimage(HttpServletRequest request
//    ) throws Exception {
//        //FileItemStream  inputStream = request.
//        String filename = request.getHeader("filename");
//        //Integer integer = request.getContentLength();
//        ///System.out.println(integer);
//        //byte[] feiwu = new byte[170];
//        //int y =inputStream.read(feiwu);
//        //System.out.println(y);
//
//        byte[] b = new byte[1024];
//
//        String ss = "/opt/image/";
//        if (inputStream.equals(null)) {
//            //return y;
//        }
//        File photoFlie = new File(ss);
//        if (!photoFlie.exists()) {
//            photoFlie.mkdir();
//        }
//        FileOutputStream fos = new FileOutputStream(photoFlie + "/" + filename + ".jpg");
//        if (fos.equals(null)) {
//            //return y;
//        }
//        BufferedOutputStream outputStream = new BufferedOutputStream(fos);
//        int nRead = 0;
//        while ((nRead = inputStream.read(b)) != -1) {
//            System.out.println(nRead);
//            fos.write(b);
//        }
//
//        fos.flush();
//        fos.close();
//        inputStream.close();
//
//        User user = new User();
//        user = userRepository.findByName(request.getHeader("filename"));
//        String s = ("/opt/image/" + filename + ".jpg").toString();
//        user.setPhoto(s);
//        userRepository.save(user);
//        if (user.getPhoto().equals(null)) {
//            //return y;
//        } else {
//            //return y;
//        }
//        return 1;
//    }





   // private String makeFileName(String filename) {  //2.jpg
//        //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
//        return "tomcat" + "_" + filename;
//    }
//
//    private String makePath(String filename, String savePath) {
//        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
//        int hashcode = filename.hashCode();
//        int dir1 = hashcode & 0xf;  //0--15
//        int dir2 = (hashcode & 0xf0) >> 4;  //0-15
//        //构造新的保存目录
//        String dir = savePath;//+ "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5
//        //File既可以代表文件也可以代表目录
//        File file = new File(dir);
//        //如果目录不存在
//        if (!file.exists()) {
//            //创建目录
//            file.mkdirs();
//        }
//        return dir;
//    }
}