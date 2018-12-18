package com.rsna.demo.service.Impl;
import com.rsna.demo.dao.IUserInfoDAO;
import com.rsna.demo.entity.UserInfo;
import com.rsna.demo.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements IUserInfoService {
    @Resource
    private IUserInfoDAO dao;
    @Override
    public void add(UserInfo info) {
        dao.add(info);
    }

    public IUserInfoDAO getDao() {
        return dao;
    }

    public void setDao(IUserInfoDAO dao) {
        this.dao = dao;
    }
}
