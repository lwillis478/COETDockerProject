package com.daftgoods.daftgoodsservice.controller;


import com.daftgoods.daftgoodsservice.UserLoginException;
import com.daftgoods.daftgoodsservice.core.user.User;
import com.daftgoods.daftgoodsservice.core.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public User getUser()
    {
        return userRepository.findFirstBy().orElseThrow(() -> new UserLoginException());
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User toLogin)
    {
        userRepository.deleteAll();
        return userRepository.save(toLogin);
    }

    @DeleteMapping("/logout")
    public Integer logoutUser()
    {
//        userRepository.delete(userRepository.findFirstBy().orElseThrow(() -> new UserLoginException()));
        return userRepository.deleteFirstBy();
    }
}
