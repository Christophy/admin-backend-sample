package com.demo.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.demo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


/**
 * @author Jonsy
 *
 */
@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    protected UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<com.demo.domain.modle.User> example = new LambdaQueryWrapper();
        example.eq(com.demo.domain.modle.User::getUsername, username);
        com.demo.domain.modle.User user=userRepository.selectOne(example);
        if(user==null){
            throw new UsernameNotFoundException("no user");
        }
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole());
        grantedAuthorities.add(grantedAuthority);
        SecurityUser userDetails = new SecurityUser(user.getId(), username, user.getPassword(),
                !user.isDisabled(), true, true, true, grantedAuthorities);
        return userDetails;
    }


}