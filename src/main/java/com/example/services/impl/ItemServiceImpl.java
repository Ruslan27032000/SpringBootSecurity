package com.example.services.impl;

import com.example.entities.*;
import com.example.repositories.*;
import com.example.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Items addItem(Items item) {
        return itemRepository.save(item);
    }

    @Override
    public List<Items> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<Items> getTopItems() {
        return itemRepository.getAllByInTopIsTrue();
    }

    @Override
    public Items getItem(Long id) {
        return itemRepository.getOne(id);
    }

    @Override
    public Items editItem(Items item) {
        return itemRepository.save(item);
    }

    @Override
    public void deleteItem(Items item) {
        itemRepository.delete(item);
    }

    @Override
    public List<Items> searchByNameAsc(String name) {
        return itemRepository.getItemsByNameIsLikeOrderByPriceAsc(name);
    }

    @Override
    public List<Items> searchByNameDesc(String name) {
        return itemRepository.getItemsByNameIsLikeOrderByPriceDesc(name);
    }

    @Override
    public List<Items> searchByNamePriceAsc(String name, int priceFrom, int priceTo) {
        return itemRepository.getItemsByNameIsLikeAndPriceIsBetweenOrderByPriceAsc(name, priceFrom, priceTo);
    }

    @Override
    public List<Items> searchByNamePriceDesc(String name, int priceFrom, int priceTo) {
        return itemRepository.getItemsByNameIsLikeAndPriceIsBetweenOrderByPriceDesc(name, priceFrom, priceTo);
    }

    @Override
    public List<Items> searchByBrandNameAsc(String brand, String name) {
        return itemRepository.getItemsByBrand_NameAndNameIsLikeOrderByPriceAsc(brand, name);
    }

    @Override
    public List<Items> searchByBrandNameDesc(String brand, String name) {
        return itemRepository.getItemsByBrand_NameAndNameIsLikeOrderByPriceDesc(brand, name);
    }

    @Override
    public List<Items> searchByBrandNamePriceAsc(String brand, String name, int priceFrom, int priceTo) {
        return itemRepository.getItemsByBrand_NameAndNameIsLikeAndPriceIsBetweenOrderByPriceAsc(brand, name, priceFrom, priceTo);
    }

    @Override
    public List<Items> searchByBrandNamePriceDesc(String brand, String name, int priceFrom, int priceTo) {
        return itemRepository.getItemsByBrand_NameAndNameIsLikeAndPriceIsBetweenOrderByPriceDesc(brand, name, priceFrom, priceTo);
    }


    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Countries> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Countries addCountry(Countries country) {
        return countryRepository.save(country);
    }

    @Override
    public Countries editCountry(Countries country) {
        return countryRepository.save(country);
    }

    @Override
    public Countries getCountry(Long id) {
        return countryRepository.getOne(id);
    }

    @Override
    public void deleteCountry(Countries country) {
        countryRepository.delete(country);
    }


    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brands> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brands addBrand(Brands brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brands editBrand(Brands brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brands getBrand(Long id) {
        return brandRepository.getOne(id);
    }

    @Override
    public void deleteBrand(Brands brand) {
        brandRepository.delete(brand);
    }


    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    @Override
    public Categories getCategory(Long id) {
        return categoriesRepository.getOne(id);
    }

    @Override
    public Categories editCategory(Categories category) {
        return categoriesRepository.save(category);
    }

    @Override
    public Categories addCategory(Categories category) {
        return categoriesRepository.save(category);
    }

    @Override
    public void deleteCategory(Categories category) {
        categoriesRepository.delete(category);
    }


    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public List<Comments> getAllCommentsById(Long id) {
        return commentsRepository.getAllByItemsId(id);
    }

    public Comments getCommentById(Long id){
        return commentsRepository.getOne(id);
    }

    @Override
    public Comments addComment(Comments comments) {
        return commentsRepository.save(comments);
    }

    @Override
    public void deleteComment(Comments comments) {
        commentsRepository.delete(comments);
    }
}
