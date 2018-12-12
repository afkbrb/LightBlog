package com.iustu.service.impl;

import com.iustu.entity.User;
import com.iustu.entity.UserExample;
import com.iustu.mapper.UserMapper;
import com.iustu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUsername(String username) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> list = userMapper.selectByExampleWithBLOBs(example);
        if(list != null && list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }

    }

    @Override
    public User getUserById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKeyWithBLOBs(user);
    }
}
