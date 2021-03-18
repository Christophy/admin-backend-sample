package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.domain.modle.Article;
import com.demo.domain.modle.User;
import com.demo.security.UserRole;
import com.demo.service.ArticleService;
import com.demo.service.UserService;
import com.demo.util.AuthHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author
 *
 */
@RequestMapping(value = "/article", produces = "application/json; charset=UTF-8")
@RestController
public class ArticleController {


    public static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;


    @PostMapping(value = "")
    public String create(@RequestBody Article article, Authentication authentication){
        Boolean isAdmin = AuthHelper.isAdmin(authentication);
        if(!isAdmin && article.getStatus() == 0){
            return "fail";
        }
        if(article.getPublisher() == null){
            article.setPublisher(authentication.getName());
        }
        articleService.saveOrUpdate(article);
        return "success";

    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable("id") String id, Authentication authentication){
        // ignore concurrency problems
        Article article = articleService.getById(id);
        // already published - only admin can delete
        if(article.getStatus() == 0) {
            if(!AuthHelper.isAdmin(authentication)){
                return "fail";
            }
        }
        articleService.removeById(id);
        return "success";
    }

    @GetMapping(value = "")
    public IPage<Article> list(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize){
        IPage<Article> userPage = new Page<>(pageNum, pageSize);
        return articleService.page(userPage, null);
    }

    @GetMapping(value = "/{id}")
    public Article list(@PathVariable String id){
        return articleService.getById(id);
    }




}
