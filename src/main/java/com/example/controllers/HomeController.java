package com.example.controllers;

import com.example.entities.*;
import com.example.services.ItemService;
import com.example.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Value("${file.avatar.viewPath}")
    private String viewPath;

    @Value("${file.avatar.uploadPath}")
    private String uploadPath;

    @Value("${file.avatar.defaultPicture}")
    private String defaultPicture;

    @GetMapping(value = "/")
    public String home(Model model) {

        List<Items> items = itemService.getTopItems();
        model.addAttribute("items", items);
        model.addAttribute("currentUser", getUserData());
        return "index";
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String admin(Model model) {
        List<Items> items = itemService.getAllItems();
        model.addAttribute("items", items);

        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);

        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("categories", categories);
        return "adminItems";
    }

    @PostMapping(value = "/addItem")
    public String addItem(@RequestParam(name = "name", defaultValue = "") String name,
                          @RequestParam(name = "description", defaultValue = "") String description,
                          @RequestParam(name = "price", defaultValue = "0") int price,
                          @RequestParam(name = "amount", defaultValue = "0") int amount,
                          @RequestParam(name = "stars", defaultValue = "0") int stars,
                          @RequestParam(name = "smallPic", defaultValue = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Keen_Bild_Taxon.svg/1200px-Keen_Bild_Taxon.png") String smallPic,
                          @RequestParam(name = "largePic", defaultValue = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Keen_Bild_Taxon.svg/1200px-Keen_Bild_Taxon.png") String largePic,
                          @RequestParam(name = "addedDate", defaultValue = "2020-11-10") String addedDate,
                          @RequestParam(name = "inTOP") boolean inTOP,
                          @RequestParam(name = "brand_id", defaultValue = "0") Long brand_id
    ) throws ParseException {

        Brands brand = itemService.getBrand(brand_id);

        if (brand != null) {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(addedDate);
            Items item = new Items();
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            item.setAmount(amount);
            item.setStars(stars);
            item.setSmallPic(smallPic);
            item.setLargePic(largePic);
            item.setAddedDate(date);
            item.setInTop(inTOP);
            item.setBrand(brand);

            itemService.addItem(item);
        }

        return "redirect:/admin";
    }

    @PostMapping(value = "/editItem")
    public String editItem(@RequestParam(name = "id") Long id,
                           @RequestParam(name = "name", defaultValue = "") String name,
                           @RequestParam(name = "description", defaultValue = "") String description,
                           @RequestParam(name = "price", defaultValue = "0") int price,
                           @RequestParam(name = "amount", defaultValue = "0") int amount,
                           @RequestParam(name = "stars", defaultValue = "0") int stars,
                           @RequestParam(name = "smallPic", defaultValue = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Keen_Bild_Taxon.svg/1200px-Keen_Bild_Taxon.png") String smallPic,
                           @RequestParam(name = "largePic", defaultValue = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Keen_Bild_Taxon.svg/1200px-Keen_Bild_Taxon.png") String largePic,
                           @RequestParam(name = "addedDate") String addedDate,
                           @RequestParam(name = "inTOP") boolean inTOP,
                           @RequestParam(name = "brand_id") Long brand_id,
                           @RequestParam(name = "act") String act
    ) throws ParseException {

        Items item = itemService.getItem(id);

        if (act.equals("edit")) {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(addedDate);
            Brands brand = itemService.getBrand(brand_id);
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            item.setAmount(amount);
            item.setStars(stars);
            item.setSmallPic(smallPic);
            item.setLargePic(largePic);
            item.setAddedDate(date);
            item.setInTop(inTOP);
            item.setBrand(brand);

            itemService.editItem(item);
        } else if (act.equals("delete")) {
            itemService.deleteItem(item);
        }

        return "redirect:/admin";
    }

    @GetMapping(value = "/adminBrands")
    public String adminBrands(Model model) {
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);

        List<Countries> countries = itemService.getAllCountries();
        model.addAttribute("countries", countries);
        return "adminBrands";
    }

    @PostMapping(value = "/addBrand")
    public String addBrand(@RequestParam(name = "name", defaultValue = "") String name,
                           @RequestParam(name = "country_id", defaultValue = "0") Long country_id
    ) {

        Countries country = itemService.getCountry(country_id);

        if (country != null) {
            Brands brand = new Brands();
            brand.setName(name);
            brand.setCountry(country);

            itemService.addBrand(brand);
        }

        return "redirect:/adminBrands";
    }

    @PostMapping(value = "/editBrand")
    public String editBrand(@RequestParam(name = "id") Long id,
                            @RequestParam(name = "name", defaultValue = "") String name,
                            @RequestParam(name = "country_id", defaultValue = "0") Long country_id,
                            @RequestParam(name = "act") String act
    ) {

        Brands brand = itemService.getBrand(id);

        if (act.equals("edit")) {
            Countries country = itemService.getCountry(country_id);
            brand.setName(name);
            brand.setCountry(country);
            itemService.editBrand(brand);
        } else if (act.equals("delete")) {
            itemService.deleteBrand(brand);
        }

        return "redirect:/adminBrands";
    }


    @GetMapping(value = "/adminCountries")
    public String adminCountry(Model model) {
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);

        List<Countries> countries = itemService.getAllCountries();
        model.addAttribute("countries", countries);
        return "adminCountries";
    }

    @PostMapping(value = "/addCountry")
    public String addCountry(@RequestParam(name = "name", defaultValue = "") String name,
                             @RequestParam(name = "code", defaultValue = "") String code
    ) {

        Countries country = new Countries();
        country.setName(name);
        country.setCode(code);

        itemService.addCountry(country);

        return "redirect:/adminCountries";
    }

    @PostMapping(value = "/editCountry")
    public String editCountry(@RequestParam(name = "id") Long id,
                              @RequestParam(name = "name", defaultValue = "") String name,
                              @RequestParam(name = "code", defaultValue = "") String code,
                              @RequestParam(name = "act") String act
    ) {

        Countries country = itemService.getCountry(id);

        if (act.equals("edit")) {
            country.setName(name);
            country.setCode(code);
            itemService.editCountry(country);
        } else if (act.equals("delete")) {
            itemService.deleteCountry(country);
        }

        return "redirect:/adminCountries";
    }


    @GetMapping(value = "/adminCategories")
    public String adminCategories(Model model) {
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("categories", categories);
        return "adminCategories";
    }

    @PostMapping(value = "/addCategories")
    public String addCategories(@RequestParam(name = "name", defaultValue = "") String name,
                                @RequestParam(name = "logo", defaultValue = "") String logo
    ) {

        Categories categories = new Categories();
        categories.setName(name);
        categories.setLogo(logo);

        itemService.addCategory(categories);

        return "redirect:/adminCategories";
    }

    @PostMapping(value = "/editCategories")
    public String editCategories(@RequestParam(name = "id") Long id,
                                 @RequestParam(name = "name", defaultValue = "") String name,
                                 @RequestParam(name = "logo", defaultValue = "") String logo,
                                 @RequestParam(name = "act") String act
    ) {

        Categories categories = itemService.getCategory(id);

        if (act.equals("edit")) {
            categories.setName(name);
            categories.setLogo(logo);
            itemService.editCategory(categories);
        } else if (act.equals("delete")) {
            itemService.deleteCategory(categories);
        }

        return "redirect:/adminCategories";
    }

    @PostMapping(value = "/assignCategory")
    public String assignCategory(@RequestParam(name = "item_id") Long item_id,
                                 @RequestParam(name = "category_id") Long category_id,
                                 @RequestParam(name = "act") String act) {
        Categories category = itemService.getCategory(category_id);
        if (category != null) {

            Items item = itemService.getItem(item_id);
            if (item != null) {

                if (act.equals("add")) {
                    List<Categories> categories = item.getCategories();
                    if (categories == null) {
                        categories = new ArrayList<>();
                    }
                    categories.add(category);
                    itemService.editItem(item);
                } else if (act.equals("delete")) {
                    List<Categories> categories = item.getCategories();
                    if (categories == null) {
                        categories = new ArrayList<>();
                    }
                    categories.remove(category);
                    itemService.editItem(item);
                }
            }
        }
        return "redirect:/admin";
    }

    @GetMapping(value = "/details/{item_id}")
    public String itemDetails(Model model, @PathVariable(name = "item_id") Long id) {
        Items item = itemService.getItem(id);
        List<Comments> comments = itemService.getAllCommentsById(id);
        System.out.println(comments);
        model.addAttribute("comments", comments);
        model.addAttribute("item", item);
        return "details";
    }

    @GetMapping(value = "/search")
    public String advSearch(Model model, @RequestParam(name = "searchByName", defaultValue = "") String search,
                            @RequestParam(name = "searchByFrom", defaultValue = "-1") int from,
                            @RequestParam(name = "searchByTo", defaultValue = "-1") int to,
                            @RequestParam(name = "option", defaultValue = "asc") String option,
                            @RequestParam(name = "searchByBrand", defaultValue = "-") String brand) {
        List<Items> items = new ArrayList<>();

        if (brand.equals("-")) {
            if (from == -1 && to == -1) {
                if (option.equals("asc"))
                    items = itemService.searchByNameAsc("%" + search + "%");
                else
                    items = itemService.searchByNameDesc("%" + search + "%");
            } else if (from != -1 && to != -1) {
                if (option.equals("asc"))
                    items = itemService.searchByNamePriceAsc(("%" + search + "%"), from, to);
                else
                    items = itemService.searchByNamePriceDesc(("%" + search + "%"), from, to);
            }
        } else {
            if (from == -1 && to == -1) {
                if (option.equals("asc"))
                    items = itemService.searchByBrandNameAsc(brand, "%" + search + "%");
                else
                    items = itemService.searchByBrandNameDesc(brand, "%" + search + "%");
            } else if (from != -1 && to != -1) {
                if (option.equals("asc"))
                    items = itemService.searchByBrandNamePriceAsc(brand, "%" + search + "%", from, to);
                else
                    items = itemService.searchByBrandNamePriceDesc(brand, "%" + search + "%", from, to);
            }
        }

        model.addAttribute("items", items);
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands", brands);
        model.addAttribute("search_name", search);
        model.addAttribute("search_brand", brand);
        System.out.println(brand);
        return "search";
    }

    @GetMapping(value = "/403")
    public String accessDenied(Model model) {
        return "403";
    }

    @GetMapping(value = "/login")
    public String login(Model model,@RequestParam(name = "status",defaultValue = "") String status,RedirectAttributes redirAttrs) {
        if(status.equals("error")){
            model.addAttribute("errorData", "Ошибка авторизации");
        }
        return "login";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model) {
        model.addAttribute("currentUser", getUserData());
        return "profile";
    }

    private Users getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User secUser = (User) authentication.getPrincipal();
            Users myUser = userService.getUserByEmail(secUser.getUsername());
            return myUser;
        }
        return null;
    }

    @GetMapping(value = "/registration")
    public String registrationPage(Model model) {
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String toRegistration(@RequestParam(name = "user_email") String email,
                                 @RequestParam(name = "user_password") String password,
                                 @RequestParam(name = "re-user_password") String rePassword,
                                 @RequestParam(name = "user_full_name") String fullName,
                                 RedirectAttributes redirAttrs) {

        if (password.equals(rePassword)) {
            Users newUser = new Users();
            newUser.setFullName(fullName);
            newUser.setPassword(password);
            newUser.setEmail(email);
            if (userService.createUser(newUser) != null) {
                redirAttrs.addFlashAttribute("success", "Успешно зарегестрированы");
                return "redirect:/registration";
            }
        }

        redirAttrs.addFlashAttribute("error", "Ошибка регистрации");
        return "redirect:/registration";

    }


    @GetMapping(value = "/changePas")
    @PreAuthorize("isAuthenticated()")
    public String toChangePas(Model model) {
        model.addAttribute("currentUser", getUserData());
        return "changePassword";
    }

    @PostMapping(value = "/changePas")
    public String changePas(@RequestParam(name = "user_password") String password,
                            @RequestParam(name = "re_user_password") String re_password) {
        if (password.equals(re_password)) {
            Users currentUser = getUserData();
            currentUser.setPassword(password);
            if (userService.saveUser(currentUser) != null) {
                return "redirect:/";
            }
        }


        return "redirect:/changePas?error";
    }

    @PostMapping(value = "/uploadFile")
    @PreAuthorize("isAuthenticated()")
    public String uploadFile(@RequestParam(name = "file") MultipartFile file) {


        if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/jpeg")) {
            try {

                Users currentUser = getUserData();
                String picName = DigestUtils.sha1Hex("avatar_" + currentUser.getId() + "_!Picture");
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadPath + picName + ".jpg");
                Files.write(path, bytes);

                currentUser.setUserAvatar(picName);
                userService.saveFile(currentUser);
                return "redirect:/";

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "redirect:/";
    }

    @GetMapping(value = "/viewPhoto/{url}", produces = {MediaType.IMAGE_JPEG_VALUE})
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    byte[] viewProfilePhoto(@PathVariable(name = "url") String url) throws IOException {
        String pictureUrl = viewPath + defaultPicture;
        if (url != null && !url.equals(("null"))) {
            pictureUrl = viewPath + url + ".jpg";
        }

        InputStream in;

        try {

            ClassPathResource resource = new ClassPathResource(pictureUrl);
            in = resource.getInputStream();

        } catch (Exception e) {

            ClassPathResource resource = new ClassPathResource(viewPath + defaultPicture);
            in = resource.getInputStream();
            e.printStackTrace();
        }

        return IOUtils.toByteArray(in);
    }

    @PostMapping(value = "leaveComment")
    public String uploadFile(@RequestParam(name = "comment") String comment,
                             @RequestParam(name = "itemId") Long itemId) {
        Comments comments = new Comments();
        Items item = itemService.getItem(itemId);
        comments.setAuthor(getUserData());
        comments.setComment(comment);
        comments.setItems(item);
        itemService.addComment(comments);
        return "redirect:/details/" + itemId;

    }

    @GetMapping(value = "/deleteComment")
    public String DeleteComment(@RequestParam(name = "commentId") Long commentId,
                                @RequestParam(name = "itemId") Long itemId) {

        Comments comments = itemService.getCommentById(commentId);
        itemService.deleteComment(comments);

        return "redirect:/details/" + itemId;
    }

}
