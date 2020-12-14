package com.example.noticeboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class NoticeboardController {

    @Autowired
    private AdvertRepository advertRepository;// = new AdvertRepository();

    @Autowired
    private UserRepository userRepository;// = new UserRepository();

    //EmailClient emailClient = new EmailClient();
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        String username = (String)session.getAttribute("username");
        if (username == null) {
            return "login";
        }


        List<Advert> ads=advertRepository.getAdverts();
        //advertRepository.getAllLists();
        List<String> advertTypeList  = advertRepository.readList("adtype");
        List<String>  advertCategoryList = advertRepository.readList("category");
        List<String>  advertLocationList = advertRepository.readList("location");

        System.out.println("Number of types: " + advertTypeList.size());
        System.out.println("Number of Categories: " + advertCategoryList.size());
        System.out.println("Number of Locations: " + advertLocationList.size());
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
        advertRepository.addAdvert(advert);
        return "redirect:/";
    }
/*
    @GetMapping("/login")
    public String login(){
        return "login";
    }
*/
    @GetMapping("/saveusers")
    public String saveusers(){
        userRepository.addUserlistoDB();
        return "Users saved to DB";
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

    @GetMapping("/sendmail") //TESTING...
        public String sendMail() throws MessagingException {
        Mailer.send("XXXmilkcellobus@gmail.com", "XXX", "f.bull.simonsen@gmail.com", "Du har signat upp för en tjänst", "Ditt lösenord är secret123");
        return "redirect:/";
    }

    @GetMapping("/changePwd")
    public String changePwd(){
        return "changePwd";
    }

    @GetMapping("/contactSeller")
    public String contactSeller(){
        return "contactSeller";
    }

    @GetMapping("/login")
    public String level1(){
        return "login";
    }

    @PostMapping("/login")
    public String level1post(HttpSession session, @RequestParam String username, @RequestParam String password){
        System.out.println("Trying to login: " + username);
        if (username.equals("admin@hm.com") && password.equals("123")) {
            System.out.println("login OK! ");
            session.setAttribute("username", username);
            return "redirect:/";
        }

        return "login";
    }
}
/*
package com.example.JavaWeb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class Level2Controller {

    @GetMapping("/login")
    public String level1(){
        return "login";
    }

    @PostMapping("/login")
    public String level1post(HttpSession session, @RequestParam String username, @RequestParam String password){
        if (username.equals("admin") && password.equals("123")) {
            session.setAttribute("username", username);
            return "secret";
        }

        return "login";
    }

    @GetMapping("/secret")
    public String level1(HttpSession session){
        String username = (String)session.getAttribute("username");
        if (username != null) {
            return "secret";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse res){
        session.removeAttribute("username"); // this would be an ok solution
        session.invalidate(); // you could also invalidate the whole session, a new session will be created the next request
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setMaxAge(0);
        res.addCookie(cookie);
        return "login";
    }
}



 */
