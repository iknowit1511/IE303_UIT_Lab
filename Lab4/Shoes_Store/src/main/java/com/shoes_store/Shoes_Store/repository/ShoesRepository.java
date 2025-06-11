package com.shoes_store.Shoes_Store.repository;

import com.shoes_store.Shoes_Store.entity.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoesRepository extends JpaRepository<Shoes, Long> {
}