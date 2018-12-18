package com.rsna.demo.controller;


import com.rsna.demo.service.IUserService;
import com.rsna.demo.utils.RSNAResult;
import com.rsna.demo.utils.SaveImage;
import com.rsna.demo.utils.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
@RequestMapping(value="/userinfo")
public class UserUpdateController {
    @Autowired
    private IUserService userService;

    // 修改个人信息
    @RequestMapping(value = "/update.do", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public RSNAResult userUpdate(@RequestBody UserMessage userMessage, HttpServletRequest request) throws Exception {
       String username =(String)request.getAttribute("username");
       if (username == null) return RSNAResult.build(123,"please login first");
       if (userService.update(userMessage,username)) return RSNAResult.ok();
       else return RSNAResult.build(123,"user update fail");
    }

    //修改头像
    @RequestMapping(value = "/headup.do", method = {RequestMethod.POST})
    @ResponseBody
    public RSNAResult headUpdate(@RequestParam("myHead")MultipartFile Imagefile, HttpServletRequest request) throws Exception {
        String username =(String)request.getAttribute("username");
        if (username == null) return RSNAResult.build(123,"please login first");
        String urlPrefix = "HeadImage/";
        String upLoadPath = request.getServletContext().getRealPath("/") +urlPrefix;
        File file = SaveImage.getImageFile(Imagefile,upLoadPath);
        if(file!=null && userService.updateHead(username,SaveImage.getUrl(file,urlPrefix)))
            return RSNAResult.ok();
        else return RSNAResult.build(123,"image update fail");
    }

    //修改密码
    @RequestMapping(value = "/password.do", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public RSNAResult passUpdate(@RequestParam("newpassword")String newpassword,
                                 @RequestParam("oldpassword")String oldpassword,
                                 HttpServletRequest request) throws Exception {
        String username =(String)request.getAttribute("username");
        if (username == null) return RSNAResult.build(123,"please login first");
        if (userService.updatePassword(username,newpassword,oldpassword)) return RSNAResult.ok();
        else return RSNAResult.build(123,"password update fail");
    }
}
