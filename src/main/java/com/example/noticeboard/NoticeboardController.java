package com.example.noticeboard;

import com.example.noticeboard.storage.StorageFileNotFoundException;
import com.example.noticeboard.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class NoticeboardController {

    @Autowired
    private AdvertRepository advertRepository;// = new AdvertRepository();

    @Autowired
    private UserRepository userRepository;// = new UserRepository();

    //*** From FileUploadController
    private String lastUploadedFile="";
    private final StorageService storageService;

    @Autowired
    public NoticeboardController(StorageService storageService) {
        this.storageService = storageService;
    }


    //*** End

    //EmailClient emailClient = new EmailClient();

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        String username = (String)session.getAttribute("username");
        if (username == null) {
            return "login";
        }

        List<Advert> ads=advertRepository.getAdverts();
        //advertRepository.getAllLists();

        model.addAttribute("advertList", ads);
        return "Home";
    }

    @GetMapping("/fbs")
    public String fbshome(Model model, HttpSession session) {

        List<Advert> ads=advertRepository.getAdverts();
        //advertRepository.getAllLists();

        model.addAttribute("advertList", ads);
        return "Home";
    }

    @GetMapping("/myadverts")
    public String myadvert(Model model, HttpSession session) {

        String username = (String)session.getAttribute("username");
        if (username == null) {
            return "login";
        }
        List<Advert> ads=advertRepository.getMyAdverts(userRepository.userId);
        List<String> advertTypeList  = advertRepository.readList("adtype");
        List<String>  advertCategoryList = advertRepository.readList("category");
        List<String>  advertLocationList = advertRepository.readList("location");

         model.addAttribute("advertList", ads);
        return "myadverts";
    }

    @GetMapping("/advert/{id}")
    public String advert(Model model, @PathVariable int id, HttpSession session) {
        String username = (String)session.getAttribute("username");
        if (username == null) {
            return "login";
        }
        Advert advert = advertRepository.findById(id);
        model.addAttribute("advert", advert);

        User user = userRepository.findById(advert.getUserId());
        model.addAttribute("user", user);
        return "advert";
    }

    @GetMapping("/AddAdvert")
    public String getaddAdvert(Model model, HttpSession session)   {
        String username = (String)session.getAttribute("username");
        if (username == null) {
            return "login";
        }
        List<String> advertTypeList  = advertRepository.readList("adtype");
        List<String>  advertCategoryList = advertRepository.readList("category");
        List<String>  advertLocationList = advertRepository.readList("location");
        model.addAttribute("adtype", advertCategoryList);
        model.addAttribute("category", advertTypeList);
        model.addAttribute("location", advertLocationList);

        Advert advert = new Advert(4, "", "", 0, "", 1, 0, 1, 0);
        model.addAttribute("advert", advert);
        return "AddAdvert";
    }

    @PostMapping("/AddAdvert")
    public String postaddAdvert(Model model, Advert advert, HttpSession session)   {
        String username = (String)session.getAttribute("username");
        if (username == null) {
            return "login";
        }
        if (advert != null)
           System.out.println("Got post, header:" + advert.getHeader());
        else
            System.out.println("Got post, NULL!");
        advert.setUserId(userRepository.userId);
        advertRepository.addAdvert(advert);
        return "redirect:/";
    }

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
        List<User> userList = userRepository.getUsers();

        String usrname = (String)session.getAttribute("username");
     /*   if (usrname == null) {
            session.removeAttribute("username"); // this would be an ok solution
            session.invalidate(); // you could also invalidate the whole session, a new session will be created the next request
            Cookie cookie = new Cookie("JSESSIONID", "");
            cookie.setMaxAge(0);
        }
        */

        for(User usr : userList)   {
            System.out.println("User:" +usr.getEmail() + " Password: " +usr.getPassword());
            if (usr.getEmail().equals(username) && usr.getPassword().equals(password))   {
                session.setAttribute("username", username);
                userRepository.userId = usr.getId();
                return "redirect:/";
            }
        }
        System.out.println("User / password not found:" + username);

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse res){
        doLogout(session, res);
        return "login";
    }

    public void doLogout(HttpSession session, HttpServletResponse res) {
        session.removeAttribute("username"); // this would be an ok solution
        session.invalidate(); // you could also invalidate the whole session, a new session will be created the next request
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setMaxAge(0);
        res.addCookie(cookie);
    }

    @GetMapping("/deleteadvert/{id}")
    public String deleteAdd(Model model, @PathVariable int id, HttpSession session) {

        Advert advert = advertRepository.findById(id);
        if (advert.getUserId() == userRepository.userId) // Create by me...
            advertRepository.deleteAdvert(id);
        return "redirect:/myadverts";
    }
    @GetMapping("/banner")
    public String banner() {
        return "banner";
    }
        //****** Insert 13:20

     @GetMapping("/new")
        public String listUploadedFiles(Model model) throws IOException {
            //   storageService.deleteAll();
            SmallAdvert smalladvert = new SmallAdvert("Title", "Descr", 10, 1);
            model.addAttribute("smalladvert", smalladvert);
            model.addAttribute("files", storageService.loadAll().map(
                    path -> MvcUriComponentsBuilder.fromMethodName(NoticeboardController.class,
                            "serveFile", path.getFileName().toString()).build().toUri().toString())
                    .collect(Collectors.toList()));
            return "upload";
        }

        @GetMapping("/files/{filename:.+}")
        @ResponseBody
        public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
            System.out.println("--- File to load as resource:" + filename);
            Resource file = storageService.loadAsResource(filename);// Save under unique name:  ImgAdvertId.jpg
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        }

        @PostMapping("/new")
        public String handleFileUpload(@RequestParam("file") MultipartFile file,
                RedirectAttributes redirectAttributes) {

            // lastUploadedFile = file.getOriginalFilename();
            System.out.println("*** File to upload:" + lastUploadedFile);

            storageService.store(file);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
            //  lastAddedImageList.add(file.getOriginalFilename());

            return "redirect:/new";
        }

        @PostMapping ("AddUploadAdvert")
        public String handleFileUploadAddAdv(Model model, SmallAdvert smallAdvert , HttpSession session, @RequestParam("file") MultipartFile file,
                RedirectAttributes redirectAttributes) throws IOException {
            System.out.println("*** File to upload:" + file);
            System.out.println("Small advert, Header:" + smallAdvert.getHeader());
            System.out.println("Small advert, Price:"  + smallAdvert.getPrice());
            System.out.println("Small advert, Descr:"  + smallAdvert.getDescription());
            String url = "";
            if (file != null) {
                storageService.store(file);
                Path aPath = storageService.load("");

                String fullPath =  new java.io.File( "." ).getCanonicalPath() + "\\" + aPath;
                String localFileName = fullPath + "\\" +  file.getOriginalFilename();   //"C:\\Users\\frbul\\Documents\\noticeboard\\upload-dir\\"+ file.getOriginalFilename();
                System.out.println("Local FULL file name:" + localFileName);

                AzureBlob.uploadFileToBlobStorage("hfsgUsJdgDUYrIXc8OefW2iYDUzrxKY7Pps4OSg8DogrXv5DYLh0NQaXd9xYeHVbGoJcncqh7bC7ZDUC2Z2lag==",
                        localFileName, file.getOriginalFilename());
                url =  "https://advertimages.blob.core.windows.net/images/" + file.getOriginalFilename();
            }
            Advert newAdvert = new Advert(0, smallAdvert.getHeader(), smallAdvert.getDescription(), smallAdvert.getPrice(), url, 1, 1, userRepository.userId, smallAdvert.getAdvertType());
                 advertRepository.addAdvert(newAdvert);

            return "redirect:/new";
        }

        @ExceptionHandler(StorageFileNotFoundException.class)
        public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
            return ResponseEntity.notFound().build();
        }

}




