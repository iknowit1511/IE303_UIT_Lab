package com.shoes_store.Shoes_Store.service;

import com.shoes_store.Shoes_Store.entity.Shoes;
import com.shoes_store.Shoes_Store.repository.ShoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoesService {
    @Autowired
    private ShoesRepository productRepository;

    public List<Shoes> getAllShoes() {
        return productRepository.findAll();
    }
}
