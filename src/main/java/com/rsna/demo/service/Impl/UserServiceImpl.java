package com.rsna.demo.service.Impl;

import com.rsna.demo.dao.UserMapper;
import com.rsna.demo.entity.User;
import com.rsna.demo.entity.UserExample;
import com.rsna.demo.service.IUserService;
import com.rsna.demo.utils.MD5;
import com.rsna.demo.utils.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    public User selectUserByName(String username){
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> UserList = userMapper.selectByExample(example);
        if (UserList==null || UserList.isEmpty()) return null;
        else return UserList.get(0);
    }

    @Override
    public boolean isLogin(String username,String password){
        User user = selectUserByName(username);
        if (user == null) return false;
        if (user.getPassword().equals(password)) return true;
        else return false;
    }
    @Override
    public boolean isVcodeTure(HttpSession session, String vcode){
        if (session==null) return false;
        String code =(String) session.getAttribute("Vcode");
        if (code.equals(vcode)) return true;
        else return  false;
    }
    @Override
    public String isLogined(Cookie[] cookies)  throws Exception{
        String username = "";
        boolean flag = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
//                System.out.println(URLDecoder.decode(cookie.getName(), "utf-8"));
                if (URLDecoder.decode(cookie.getName(), "utf-8").equals("username")) { // 表明已经登陆过了，就直接跳转了
                    flag = true;
                    username = URLDecoder.decode(cookie.getValue(), "utf-8");
                }
            }
        }
        if (!flag) return null;
        else return username;
    }

    @Override
    public boolean update(UserMessage userMessage,String username) {
        User user = selectUserByName(username);
        user = userMessage.setMeassge(user);
        int f = userMapper.updateByPrimaryKey(user);
        if (f<=0)
            return false;
        else return true;
    }

    @Override
    public boolean updateHead(String username, String url) {
        User user = selectUserByName(username);
        user.setHeadpic(url);
        int f = userMapper.updateByPrimaryKey(user);
        if (f<=0)
            return false;
        else return true;
    }

    @Override
    public boolean updatePassword(String username, String newPassword, String oldPassword) {
        User user = selectUserByName(username);
        if (user.getPassword().equals(MD5.string2MD5(oldPassword))) {
            user.setPassword(MD5.string2MD5(newPassword));
            userMapper.updateByPrimaryKey(user);
            return true;
        }
        else return false;
    }
}
