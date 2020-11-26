package com.example.demo.controllers;

import com.example.demo.entities.*;
import com.example.demo.services.ItemService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping(value = "/")
    public String home(Model model) {
        List<Items> items = itemService.getTopItems();
        model.addAttribute("items", items);
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
    public String login(Model model) {
        return "login";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model) {
        model.addAttribute("currentUser",getUserData());
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
}
