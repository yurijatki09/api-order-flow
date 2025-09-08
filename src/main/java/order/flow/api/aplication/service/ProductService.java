package order.flow.api.aplication.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import order.flow.api.aplication.dto.ProductDTO;
import order.flow.api.aplication.dto.ProductListDTO;
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
    public String register(ProductDTO productDTO) throws Exception{
        try{
            
            Product product = ProductMapper.toEntity(productDTO);
            productRepository.save(product);
        }catch(Exception e){
            return "Erro ao cadastrar!";
        }
        return "Produto cadastrado com sucesso!";
    }

    public String patch(UUID id, ProductDTO productDTO) {
        // TODO Auto-generated method stub
        try{
            Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            
            product = ProductMapper.updateEntityFromDto(productDTO, product);
            productRepository.save(product);
        }catch(Exception ex){
            return "Erro ao atualizar produto!";
        }
        return "Produto atualizado com sucesso!";
    }

    public List<ProductListDTO> getProtucts() {
        // TODO Auto-generated method stub
        return productRepository.getProducts();
    }

    public String deleteById(UUID id) {
        // TODO Auto-generated method stub
        if (!productRepository.existsById(id)) {
            return "Produto não existe";
        }
        Product product = productRepository.findByActiveId(id);
        try{
            if(product != null) product = ProductMapper.updateEntityFromDto(ProductDTO.builder().ativo(Boolean.FALSE).build(), product);
            else return "Produto não existe"; 
            productRepository.save(product);
        }catch(Exception ex){
            return "Erro ao excluir produto!";
        }
       return "Produto excluído com sucesso!";
    }
}
