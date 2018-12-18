package com.rsna.demo.dao;
import com.rsna.demo.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserInfoDAO {
    public void add(UserInfo info);
}
