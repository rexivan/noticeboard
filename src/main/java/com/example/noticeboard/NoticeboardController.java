package com.example.noticeboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NoticeboardController {

//    @Autowired
    private AdvertRepository advertRepository = new AdvertRepository();

    @GetMapping("/")
    public String home(Model model) {
        List<Advert> ads=advertRepository.advertList;
        model.addAttribute("advertList", ads);
        return "home";
    }
}
