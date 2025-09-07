package order.flow.api.aplication.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import order.flow.api.aplication.dto.ProductDTO;
import order.flow.api.aplication.mapper.ProductMapper;
import order.flow.api.domain.model.Product;
import order.flow.api.domain.repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public String register(ProductDTO productDTO){
        
        Product product = ProductMapper.toEntity(productDTO);
        productRepository.save(product);
        return "Produto cadastrado com sucesso!";
    }
}
