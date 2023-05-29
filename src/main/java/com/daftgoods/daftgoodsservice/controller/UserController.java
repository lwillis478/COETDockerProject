package com.daftgoods.daftgoodsservice.controller;


import com.daftgoods.daftgoodsservice.UserLoginException;
import com.daftgoods.daftgoodsservice.core.user.User;
import com.daftgoods.daftgoodsservice.core.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public Map<String, String> getUser(HttpSession currentSession)
    {
        List<String> attrNames = Collections.list(currentSession.getAttributeNames());

        if(attrNames.contains("userid") && attrNames.contains("username"))
        {
            String currentUserName = (String) currentSession.getAttribute("username");
            String currentUserID = currentSession.getAttribute("userid").toString();
            return new HashMap<>() {
                { put("id", currentUserID); }
                { put("username", currentUserName); }
            };
        }

        else throw new UserLoginException("Not logged in");
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User toLogin, HttpServletRequest loginRequest)
    {
        BCryptPasswordEncoder passHasher = new BCryptPasswordEncoder(10);
        String hashedPass = passHasher.encode(toLogin.getPassword());
        toLogin.setPassword(hashedPass);
        User createdUser = userRepository.save(toLogin);

        loginRequest.getSession().setAttribute("username", createdUser.getUsername());
        loginRequest.getSession().setAttribute("userid", createdUser.getId());
        return "Logged in as " + loginRequest.getSession().getAttribute("username");
    }

    @DeleteMapping("/logout")
    public Integer logoutUser()
    {
//        userRepository.delete(userRepository.findFirstBy().orElseThrow(() -> new UserLoginException()));
        return userRepository.deleteFirstBy();
    }
}
