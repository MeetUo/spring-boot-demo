package com.rsna.demo.controller;

import cn.dsna.util.images.ValidateCode;
import com.rsna.demo.entity.User;
import com.rsna.demo.service.IUserService;
import com.rsna.demo.utils.MD5;
import com.rsna.demo.utils.RSNAResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;


@Controller
@RequestMapping(value="/user")
public class UserContoller {
    @Autowired
    private IUserService userService;
    @RequestMapping(value = "/login.do", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public RSNAResult handleRequest(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        String UserName=request.getParameter("username");
        String PassWord =request.getParameter("password");
        PassWord = MD5.string2MD5(PassWord);
        String Vcode=request.getParameter("vcode");
        HttpSession session = request.getSession(true);
        if (!userService.isVcodeTure(session,Vcode))return RSNAResult.build(123, "vcode is wrong");
        if (!userService.isLogin(UserName,PassWord)) return RSNAResult.build(123, "user not exit or password is wrong");
        Cookie cookie = new Cookie("username",UserName );
        cookie.setMaxAge(86400); // 设置24小时有效
        cookie.setPath("/");
        response.addCookie(cookie); // 服务器返回给浏览器cookie以便下次判断
        return RSNAResult.ok(userService.selectUserByName(UserName));
    }

    @RequestMapping(value = "/islogin.do", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public RSNAResult islogin(HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        String username = userService.isLogined(cookies);
        if (username!=null) {
            User user = userService.selectUserByName(username);
            return RSNAResult.ok(user);
        }
        else {
            return RSNAResult.build(123,"login first");
        }
    }

    @RequestMapping(value = "/vcode.do",  method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void getVcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        //创建验证码图片流
        ValidateCode validateCode = new ValidateCode(120,50,4,20);
        validateCode.write(response.getOutputStream());
        //将验证码存入session中
        session.setAttribute("Vcode", validateCode.getCode());
    }
    @RequestMapping(value = "/clearCookie.do",  method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public RSNAResult clearCookie(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
//                System.out.println(URLDecoder.decode(cookie.getName(), "utf-8"));
                if (URLDecoder.decode(cookie.getName(), "utf-8").equals("username")) { // 表明已经登陆过了，就直接跳转了
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        return RSNAResult.ok();
    }
}
