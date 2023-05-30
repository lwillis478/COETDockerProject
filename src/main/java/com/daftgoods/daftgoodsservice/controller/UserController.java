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

    @Autowired
    private BCryptPasswordEncoder passHasher;

    @GetMapping("/user")
    public Map<String, String> getUser(HttpSession currentSession)
    {
        List<String> attrNames = Collections.list(currentSession.getAttributeNames());

        if(attrNames.contains("userid"))
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
    public Map<String, String> loginUser(@RequestBody User toLogin, HttpServletRequest loginRequest)
    {
        String hashedPass = passHasher.encode(toLogin.getPassword());
        toLogin.setPassword(hashedPass);
        User createdUser = userRepository.save(toLogin);

        loginRequest.getSession().setAttribute("username", createdUser.getUsername());
        loginRequest.getSession().setAttribute("userid", createdUser.getId());

        return new HashMap<>() {
            { put("id", toLogin.getId().toString()); }
            { put("username", toLogin.getUsername()); }
        };
    }

    @DeleteMapping("/logout")
    public Map<String, String> logoutUser(HttpSession currentSession)
    {
//        userRepository.delete(userRepository.findFirstBy().orElseThrow(() -> new UserLoginException()));

        String res = "";
        String statusCode = "";
        List<String> attrNames = Collections.list(currentSession.getAttributeNames());

        if(attrNames.contains("userid"))
        {
            String username = (String) currentSession.getAttribute("username");
            currentSession.removeAttribute("userid");
            currentSession.removeAttribute("username");
            res = "Logged out user " + username;
            statusCode = "200";
        }

        else
        {
            res = "No user currently logged in";
            statusCode = "404";
        }

        String statusCodeFinal = statusCode;
        String resFinal = res;

        return new HashMap<>() {
            { put("statuscode", statusCodeFinal); }
            { put("message", resFinal); }
        };
    }
}
