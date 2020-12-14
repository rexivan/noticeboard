package com.example.noticeboard;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @GetMapping("/signUp")
    public String hello(HttpSession session, @RequestParam String username) {
        // set an attribute in the session
        session.setAttribute("username", username);
        return "signUp";
    }
}
