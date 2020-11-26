package com.example.demo.services;

import com.example.demo.entities.Brands;
import com.example.demo.entities.Categories;
import com.example.demo.entities.Countries;
import com.example.demo.entities.Items;

import java.util.List;

public interface ItemService {
    Items addItem(Items item);
    List<Items> getAllItems();
    List<Items> getTopItems();
    Items getItem(Long id);
    Items editItem(Items item);
    void deleteItem(Items item);
    List<Items> searchByNameAsc(String name);
    List<Items> searchByNameDesc(String name);
    List<Items> searchByNamePriceAsc(String name, int priceFrom, int priceTo);
    List<Items> searchByNamePriceDesc(String name, int priceFrom, int priceTo);
    List<Items> searchByBrandNameAsc(String brand, String name);
    List<Items> searchByBrandNameDesc(String brand, String name);
    List<Items> searchByBrandNamePriceAsc(String brand, String name, int priceFrom, int priceTo);
    List<Items> searchByBrandNamePriceDesc(String brand, String name, int priceFrom, int priceTo);

    List<Countries> getAllCountries();
    Countries addCountry(Countries country);
    Countries editCountry(Countries country);
    Countries getCountry(Long id);
    void deleteCountry(Countries country);

    List<Brands> getAllBrands();
    Brands addBrand(Brands brand);
    Brands editBrand(Brands brand);
    Brands getBrand(Long id);
    void deleteBrand(Brands brand);

    List<Categories> getAllCategories();
    Categories getCategory(Long id);
    Categories editCategory(Categories category);
    Categories addCategory(Categories category);
    void deleteCategory(Categories category);
}
