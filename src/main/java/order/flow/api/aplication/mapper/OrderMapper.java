package order.flow.api.aplication.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import order.flow.api.aplication.dto.OrderDTO;
import order.flow.api.aplication.dto.OrderProductDTO;
import order.flow.api.aplication.dto.ProductDTO;
import order.flow.api.aplication.dto.ProductSummaryDTO;
import order.flow.api.domain.model.Order;
import order.flow.api.domain.model.OrderProduct;
import order.flow.api.domain.model.Product;
import order.flow.api.domain.repository.OrderRepository;

public class OrderMapper {

    @Autowired
    OrderRepository orderRepository;

    public static Order toEntity(OrderDTO dto, List<Product> produtos) {
        if (dto == null) return null;

        Order order = new Order();
        order.setId(dto.getId());
        order.setStatus(dto.getStatus());
        order.setCreatedAt(dto.getCreatedAt());
        order.setUpdatedAt(dto.getUpdatedAt());
        order.setMensagem(dto.getMensagem());

        // mapear os produtos
        List<OrderProduct> orderProducts = dto.getProdutos().stream()
        .map(opDto -> {
            OrderProduct op = new OrderProduct();
            op.setOrder(order);
            op.setQuantidade(opDto.getQuantidade());
            op.setPrecoUnitario(opDto.getPrecoUnitario());

            Product product = produtos.stream()
                    .filter(p -> p.getId().equals(opDto.getProduct().getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            op.setProduct(product);
            return op;
        })
        .collect(Collectors.toList());

        order.setProdutos(orderProducts);

        BigDecimal total = orderProducts.stream()
                .map(op -> op.getPrecoUnitario().multiply(BigDecimal.valueOf(op.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setPrecoTotal(total);

        return order;
    }

    public static OrderDTO toDto(Order entity) {
        if (entity == null) return null;

        return OrderDTO.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .total(entity.getPrecoTotal())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .quantidade(
                    entity.getProdutos().stream()
                            .mapToInt(OrderProduct::getQuantidade)
                            .sum()
                )
                .produtos(
                        entity.getProdutos().stream()
                                .map(op -> OrderProductDTO.builder()
                                        .quantidade(op.getQuantidade())
                                        .precoUnitario(op.getPrecoUnitario())
                                        .product(ProductSummaryDTO.builder()
                                                .nome(op.getProduct().getNome())
                                                .preco(op.getProduct().getPreco())
                                                .categoria(op.getProduct().getCategoria())
                                                .build()
                                        )
                                        .build()
                                )
                                .collect(Collectors.toList())
                )
                .userId(entity.getUser().getId())
                .userNome(entity.getUser().getNome())
                .mensagem(entity.getMensagem())
                .build();
    }
}
