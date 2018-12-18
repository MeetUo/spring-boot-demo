package com.rsna.demo.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class SaveImage {
    public static File getImageFile(MultipartFile Imagefile, String upLoadPath){
        File dir = new File(upLoadPath);
        if (!dir.exists())
        {
            dir.mkdir();
        }
        //0，判断是否为空
        if(Imagefile!=null && !Imagefile.isEmpty())
        {
            /**
             * 对文件名进行操作防止文件重名
             */
            //1，获取原始文件名
            String originalFilename = Imagefile.getOriginalFilename();
            //2,截取源文件的文件名前缀,不带后缀
            String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
            //3,加工处理文件名，原文件加上时间戳
            String newFileNamePrefix  = fileNamePrefix + System.currentTimeMillis();
            //4,得到新文件名
            String newFileName = newFileNamePrefix + originalFilename.substring(originalFilename.lastIndexOf("."));
            //5,构建文件对象
            File file = new File(upLoadPath + newFileName);
            //6,执行上传操作
            try {
                Imagefile.transferTo(file);
                //返回
               return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static String getUrl(File file,String urlPrefix){
        return urlPrefix+file.getName();
    }
}
