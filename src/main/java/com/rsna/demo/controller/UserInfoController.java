package com.rsna.demo.controller;

import com.rsna.demo.entity.UserInfo;
import com.rsna.demo.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="/test")
public class UserInfoController{
    @Autowired
    private IUserInfoService service;

    @RequestMapping(value = "/insert.do")
    @ResponseBody
    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        String uname=request.getParameter("name");
        Integer uage=Integer.valueOf(request.getParameter("age"));

        UserInfo info=new UserInfo();
        info.setAge(uage);
        info.setName(uname);

        service.add(info);
        return new ModelAndView("/index");
    }
    public IUserInfoService getService() {
        return service;
    }
    public void setService(IUserInfoService service) {
        this.service = service;
    }

}
