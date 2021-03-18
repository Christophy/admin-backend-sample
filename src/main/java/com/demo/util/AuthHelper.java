package com.demo.util;


import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.stream.Collectors;

public class AuthHelper {
    public static Boolean isAdmin(Authentication authentication){
        List<String> authorities =
                authentication.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toList());
        if(authorities.contains("ROLE_ADMIN")){
           return true;
        }
        return false;
    }

}
