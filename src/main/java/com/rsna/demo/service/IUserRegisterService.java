package com.rsna.demo.service;

import com.rsna.demo.entity.User;

public interface IUserRegisterService {
    public int add(User user);
    public boolean select(String username);
}
