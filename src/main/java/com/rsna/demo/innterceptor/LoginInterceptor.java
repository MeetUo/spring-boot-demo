package com.rsna.demo.innterceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // 执行完毕，返回前拦截
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // 在处理过程中，执行拦截
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        // 在拦截点执行前拦截，如果返回true则不拦截
        // 返回false则执行拦截
        boolean flag = false;
        String username = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
//                System.out.println(URLDecoder.decode(cookie.getName(), "utf-8"));
                if (URLDecoder.decode(cookie.getName(), "utf-8").equals("username")) { // 表明已经登陆过了，就直接跳转了
                    flag = true;
                    username = cookie.getValue();
                }
            }
        }
        //String uri = request.getRequestURI(); // 获取登录的uri，这个是不进行拦截的
        //if(session.getAttribute("LOGIN_USER")!=null || uri.indexOf("system/login")!=-1) {// 说明登录成功 或者 执行登录功能
        if(flag) {
            // 登录成功不拦截
            request.setAttribute("username",username);
            return true;
        }else {
            // 拦截后进入登录页面
            response.getWriter().write("please login first");
            return false;
        }
    }
}
