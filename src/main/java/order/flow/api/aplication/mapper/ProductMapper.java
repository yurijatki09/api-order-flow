package order.flow.api.aplication.mapper;

import java.util.List;
import java.util.stream.Collectors;

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
                    .ativo(dto.getAtivo())
                .build();
    }

    public static ProductDTO toDto(Product entity) {
        if (entity == null) return null;

        return ProductDTO.builder()
                    .id(entity.getId())
                    .nome(entity.getNome())
                    .descricao(entity.getDescricao())
                    .preco(entity.getPreco())
                    .categoria(entity.getCategoria())
                    .quantidade(entity.getQuantidade())
                    .ativo(entity.getAtivo())
                    .dataCriacao(entity.getDataCriacao())       // <---
                    .dataAtualizacao(entity.getDataAtualizacao())
                .build();
    }

    public static Product updateEntityFromDto(ProductDTO dto, Product entity) {
        return entity.builder()
                    .id(entity.getId())
                    .nome(dto.getNome() != null ? dto.getNome() : entity.getNome())
                    .descricao(dto.getDescricao() != null ? dto.getDescricao() : entity.getDescricao())
                    .preco(dto.getPreco() != null ? dto.getPreco() : entity.getPreco())
                    .categoria(dto.getCategoria() != null ? dto.getCategoria() : entity.getCategoria())
                    .quantidade(dto.getQuantidade() != null ? dto.getQuantidade() : entity.getQuantidade())
                    .ativo(dto.getAtivo() != null ? dto.getAtivo() : entity.getAtivo())
                .build();
    }

    public static List<ProductDTO> toDtos(List<Product> products){
        return products.stream().map(product -> toDto(product)).collect(Collectors.toList());
    }
}
