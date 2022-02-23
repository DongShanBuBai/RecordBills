package com.lil.maven.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lil.maven.dao.mapper.UserMapper;
import com.lil.maven.pojo.User;
import com.lil.maven.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
}
