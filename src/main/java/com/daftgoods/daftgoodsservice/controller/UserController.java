package com.daftgoods.daftgoodsservice.controller;


import com.daftgoods.daftgoodsservice.UserLoginException;
import com.daftgoods.daftgoodsservice.core.user.User;
import com.daftgoods.daftgoodsservice.core.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public String getUser(HttpSession currentSession)
    {
        String currentUser = (String) currentSession.getAttribute("username");

        if (currentUser != null) return "Logged in as " + currentUser;

        else return "Not logged in";
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User toPost)
    {
        BCryptPasswordEncoder passHasher = new BCryptPasswordEncoder(10);
        String hashedPass = passHasher.encode(toPost.getPassword());
        toPost.setPassword(hashedPass);
        return userRepository.save(toPost);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User toLogin, HttpServletRequest loginRequest)
    {
        User loginAs = userRepository.findById(toLogin.getId()).orElseThrow(() -> new UserLoginException("User not found"));
        BCryptPasswordEncoder passHasher = new BCryptPasswordEncoder(10);
        if(passHasher.matches(toLogin.getPassword(), loginAs.getPassword()))
        {
            loginRequest.getSession().setAttribute("username", loginAs.getUsername());
            return "Logged in as " + loginAs.getUsername();
        }

        else return "Could not log in, password is incorrect";
    }

    @DeleteMapping("/logout")
    public Integer logoutUser()
    {
//        userRepository.delete(userRepository.findFirstBy().orElseThrow(() -> new UserLoginException()));
        return userRepository.deleteFirstBy();
    }
}
