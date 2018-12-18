package com.rsna.demo.controller;

import com.rsna.demo.entity.User;
import com.rsna.demo.service.IUserRegisterService;
import com.rsna.demo.utils.MD5;
import com.rsna.demo.utils.RSNAResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="/user")
public class UserRegisterContoller {
    @Autowired
    private IUserRegisterService userRegisterService;
    @RequestMapping(value = "/register.do", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public RSNAResult handleRequest(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        String UserName=request.getParameter("username");
        String PassWord =request.getParameter("password");
        String UserType=request.getParameter("usertype");
        boolean res = userRegisterService.select(UserName);
        if (res==false) {
            User user = new User();
            user.setUsername(UserName);
            // MD5 加密密码
            PassWord = MD5.string2MD5(PassWord);
            user.setPassword(PassWord);
            user.setUsertype(UserType);
            user.setScore(0);
            int msg = userRegisterService.add(user);
            return RSNAResult.build(200, String.valueOf(msg));
        }
        else return RSNAResult.build(123, "user has exit");
    }
    }
