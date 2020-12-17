package com.example.repositories;

import com.example.entities.Brands;
import com.example.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> getAllByItemsId(Long items_id);
}
