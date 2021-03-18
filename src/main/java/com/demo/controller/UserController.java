package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.domain.modle.User;
import com.demo.security.SecurityUser;
import com.demo.security.UserRole;
import com.demo.service.UserService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;


/**
 * @author
 *
 */
@RequestMapping(value = "/user", produces = "application/json; charset=UTF-8")
@RestController
public class UserController {


    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/info")
    public SecurityUser login(Authentication authentication) {
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        return user;
    }

    @PostMapping(value = "")
    @UserRole("ROLE_ADMIN")
    public String create(@RequestParam String username, @RequestParam String password,
                         @RequestParam(defaultValue = "false") Boolean isAdmin){
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(isAdmin?"ROLE_ADMIN":"ROLE_USER");
        userService.save(user);
        return "success";
    }

    @GetMapping(value = "")
    @UserRole("ROLE_ADMIN")
    public IPage<User> list(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize){
        IPage<User> userPage = new Page<>(pageNum, pageSize);
        return userService.page(userPage, null);
    }





}
