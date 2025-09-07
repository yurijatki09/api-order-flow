package order.flow.api.aplication.mapper;

import order.flow.api.aplication.dto.ProductDTO;
import order.flow.api.domain.model.Product;

public class ProductMapper {
    
    public static Product toEntity(ProductDTO dto) {
        if (dto == null) return null;

        return Product.builder()
                    .nome(dto.getNome())
                    .descricao(dto.getDescricao())
                    .preco(dto.getPreco())
                    .categoria(dto.getCategoria())
                    .quantidade(dto.getQuantidade())
                .build();
    }

    public static ProductDTO toDTO(Product entity) {
        if (entity == null) return null;

        return ProductDTO.builder()
                    .id(entity.getId())
                    .nome(entity.getNome())
                    .descricao(entity.getDescricao())
                    .preco(entity.getPreco())
                    .categoria(entity.getCategoria())
                    .quantidade(entity.getQuantidade())
                .build();
    }
}
