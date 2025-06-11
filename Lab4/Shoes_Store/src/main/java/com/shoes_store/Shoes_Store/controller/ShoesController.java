package com.shoes_store.Shoes_Store.controller;

import com.shoes_store.Shoes_Store.entity.Shoes;
import com.shoes_store.Shoes_Store.service.ShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shoes")
public class ShoesController {
    @Autowired
    private ShoesService productService;

    @GetMapping("/all")
    public List<Shoes> getAllShoes() {
        return productService.getAllShoes();
    }
}