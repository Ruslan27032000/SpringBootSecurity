package com.example.demo.repositories;

import com.example.demo.entities.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Items, Long> {
    List<Items> getAllByInTopIsTrue();
    List<Items> getItemsByNameIsLikeOrderByPriceAsc(String name);
    List<Items> getItemsByNameIsLikeOrderByPriceDesc(String name);
    List<Items> getItemsByNameIsLikeAndPriceIsBetweenOrderByPriceAsc(String name, int priceFrom, int priceTo);
    List<Items> getItemsByNameIsLikeAndPriceIsBetweenOrderByPriceDesc(String name, int priceFrom, int priceTo);
    List<Items> getItemsByBrand_NameAndNameIsLikeOrderByPriceAsc(String brand, String name);
    List<Items> getItemsByBrand_NameAndNameIsLikeOrderByPriceDesc(String brand, String name);
    List<Items> getItemsByBrand_NameAndNameIsLikeAndPriceIsBetweenOrderByPriceAsc(String brand, String name, int priceFrom, int priceTo);
    List<Items> getItemsByBrand_NameAndNameIsLikeAndPriceIsBetweenOrderByPriceDesc(String brand, String name, int priceFrom, int priceTo);

}
