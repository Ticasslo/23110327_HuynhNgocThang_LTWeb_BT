package vn.ngocthang.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductByPriceController {

    @GetMapping("/products-by-price")
    public String productsByPrice() {
        return "web/products-by-price";
    }
}
