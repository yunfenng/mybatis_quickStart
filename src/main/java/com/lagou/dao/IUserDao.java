package com.lagou.dao;

import com.lagou.pojo.User;

import java.io.IOException;
import java.util.List;

public interface IUserDao {

    // 查询所有用户
    public List<User> findAll() throws IOException;

    // 多条件查询
    public List<User> findByCondition(User user) throws IOException;

    // 多值查询 foreach
    public List<User> findByIds(int[] arr);
}
