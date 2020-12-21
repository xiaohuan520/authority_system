package com.hopu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hopu.domain.Role;
import com.hopu.domain.User;

import java.util.ArrayList;


public interface IUserService extends IService<User> {
    User getUserByName(String userName);

    void setRole(String id, ArrayList<Role> roles);
}
