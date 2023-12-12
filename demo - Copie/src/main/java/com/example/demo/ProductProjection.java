package com.example.demo;


import com.example.demo.entity.Product;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "fullProduct", types = Product.class)
public interface ProductProjection extends Projection {
    public Long getId();
    public String getName();
    public Double getPrice();
}
