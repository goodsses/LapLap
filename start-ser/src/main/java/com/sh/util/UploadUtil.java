package com.sh.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UploadUtil {

    public static boolean uploadFile(byte[] file, String filePath, String fileName) {
        //默认文件上传成功
        boolean flag = true;
        //new一个文件对象实例
        File targetFile = new File(filePath);
        //如果当前文件目录不存在就自动创建该文件或者目录
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            //通过文件流实现文件上传
            FileOutputStream fileOutputStream = new FileOutputStream(filePath + fileName);
            fileOutputStream.write(file);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在异常");
            flag = false;
        } catch (IOException ioException) {
            System.out.println("javaIO流异常");
            flag = false;
        }
        return flag;
    }
}
