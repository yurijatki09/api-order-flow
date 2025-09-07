package order.flow.api.controller;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import order.flow.api.aplication.dto.ProductDTO;
import order.flow.api.aplication.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
    
    @Inject
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody ProductDTO productDTO){
        
        return ResponseEntity.ok(productService.register(productDTO));
    }

}
