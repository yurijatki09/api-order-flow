package order.flow.api.controller;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import order.flow.api.aplication.dto.ProductDTO;
import order.flow.api.aplication.dto.ProductListDTO;
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
    public ResponseEntity<String> register(@RequestBody ProductDTO productDTO) throws Exception{
        
        return ResponseEntity.ok(productService.register(productDTO));
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<String> patchProduct(@PathVariable("id") UUID id, @RequestBody ProductDTO productDTO) throws Exception{

        return ResponseEntity.ok(productService.patch(id, productDTO));
    }

    @GetMapping
    public ResponseEntity<List<ProductListDTO>  > getAllProtucts() throws Exception{

        return ResponseEntity.ok(productService.getProtucts());
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        
        return ResponseEntity.ok( productService.deleteById(id));
        
    }

}
