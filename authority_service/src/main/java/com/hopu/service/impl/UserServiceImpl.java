package com.hopu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hopu.domain.Role;
import com.hopu.domain.User;
import com.hopu.domain.UserRole;
import com.hopu.mapper.UserMapper;
import com.hopu.service.IUserRoleService;
import com.hopu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    public IUserRoleService userRoleService;

    @Override
    public User getUserByName(String userName) {
        return null;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void setRole(String id, ArrayList<Role> roles) {
        userRoleService.remove(new QueryWrapper<>(new
                UserRole()).eq("user_id", id));
// 新增关联的用户角色数据
        for (Role role : roles) {
            UserRole userrole = new UserRole();
            userrole.setUserId(id);
            userrole.setRoleId(role.getId());
            userRoleService.save(userrole);
    }


}
}
