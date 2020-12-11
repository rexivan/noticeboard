package com.example.noticeboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NoticeboardController {

    @Autowired
    private AdvertRepository advertRepository = new AdvertRepository();
    private UserRepository userRepository = new UserRepository();

    @GetMapping("/")
    public String home(Model model) {
        List<Advert> ads=advertRepository.advertList;
        model.addAttribute("advertList", ads);
        return "home";
    }

    @GetMapping("/advert/{id}")
    public String advert(Model model, @PathVariable int id) {
        Advert advert = advertRepository.findById(id);
        model.addAttribute("advert", advert);

        User user = userRepository.findById(advert.getUserId());
        model.addAttribute("user", user);
        return "advert";
    }

    @GetMapping("/addadvert")
    public String addAdvert(Model model)   {
        Advert advert = new Advert(4, "", "", 0, "", 0, 0, 1, 0);
        model.addAttribute("advert", advert);
        return "AddAdvert";
    }

    @PostMapping("/addadvert")
    public String addAdvert(Model model, Advert advert)   {
        if (advert != null)
           System.out.println("Got post, header:" + advert.getHeader());
        else
            System.out.println("Got post, NULL!");
        advertRepository.save(advert);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/signUp")
    public String signUp(){
        return "signUp";

    }

    @GetMapping("/checkEmail")
    public String checkEmail(){
        return "checkEmail";
    }

    @GetMapping("/myadverts")
    public String myAdverts(){
        return "myadverts";
    }

    @GetMapping("/changePwd")
    public String changePwd(){
        return "changePwd";
    }

}
