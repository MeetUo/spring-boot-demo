package com.rsna.demo.service.Impl;

import com.rsna.demo.dao.UserMapper;
import com.rsna.demo.entity.User;
import com.rsna.demo.entity.UserExample;
import com.rsna.demo.service.IUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserRegisterServiceImpl implements IUserRegisterService {
    @Resource
    private UserMapper userMapper;
    public int add(User user){
      return  userMapper.insert(user);
    }
    public boolean select(String username){
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> UserList = userMapper.selectByExample(example);
        if (UserList.isEmpty()) return false;
        return true;
    }
}
