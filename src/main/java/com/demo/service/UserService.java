package com.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.domain.modle.User;
import com.demo.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserService extends ServiceImpl<UserRepository, User> {

}
