package com.shoes_store.Shoes_Store.entity;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "shoes")
public class Shoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shoesId;

    @Column(name = "shoes_name", nullable = false)
    private String shoesName;

    @Column(name = "shoes_price", nullable = false)
    private Double shoesPrice;

    @Column(name = "shoes_brand", nullable = false)
    private String shoesBrand;

    @Column(name = "shoes_description")
    private String shoesDescription;

    @Column(name = "shoes_img")
    private String shoesImgPath;

}
