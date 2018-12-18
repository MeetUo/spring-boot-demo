package com.rsna.demo.controller;


import com.rsna.demo.service.IImageRegService;
import com.rsna.demo.utils.LocalRequest;
import com.rsna.demo.utils.RSNAResult;
import com.rsna.demo.utils.SaveImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
@RequestMapping(value="/image")
public class ImageUploadContoller {
    @Autowired
    IImageRegService imageRegService;
    @RequestMapping(value = "/imageUpload.do", method = RequestMethod.POST)
    @ResponseBody
    public RSNAResult handleRequest(@RequestParam("myImage")MultipartFile Imagefile, HttpServletRequest request) throws Exception {
        String urlPrefix = "ImageData/";
        String upLoadPath = request.getServletContext().getRealPath("/") +urlPrefix;
        System.out.println(upLoadPath);
        String username =(String) request.getAttribute("username");
        if (username==null) return RSNAResult.build(123,"login first");
        File file = SaveImage.getImageFile(Imagefile,upLoadPath);
        if(file!=null)
            return imageRegService.getRes(file,username,SaveImage.getUrl(file,urlPrefix));
        return RSNAResult.build(123,"image update fail");
    }
    @RequestMapping(value = "/imageUpdate.do",  method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public RSNAResult imageUpdate(@RequestBody LocalRequest localRequest) throws Exception {
        if (imageRegService.addNewRes(localRequest)) return RSNAResult.ok();
        else return RSNAResult.build(123,"update fail");
    }
}
