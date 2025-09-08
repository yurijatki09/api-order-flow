package order.flow.api.aplication.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import order.flow.api.aplication.dto.OrderDTO;
import order.flow.api.aplication.dto.ProductDTO;
import order.flow.api.domain.model.Order;
import order.flow.api.domain.model.OrderProduct;

public class OrderMapper {

    public static Order toEntity(OrderDTO dto) {
        if (dto == null) return null;

        Order order = Order.builder()
                .status(dto.getStatus())
                .build();

        if (dto.getProdutos() != null) {
            List<OrderProduct> orderProducts = dto.getProdutos().stream()
                    .map(p -> {
                        p.setOrder(order); // garante referência do pedido
                        return p;
                    })
                    .collect(Collectors.toList());
            order.setProdutos(orderProducts);
        }

        return order;
    }

    public static OrderDTO toDto(Order entity) {
        if (entity == null) return null;

        List<OrderProduct> produtos = null;
        int quantidade = 0;
        BigDecimal total = BigDecimal.ZERO;

        if (entity.getProdutos() != null) {
            // clonamos a lista para o DTO
            produtos = entity.getProdutos().stream()
                    .map(op -> {
                        OrderProduct copy = new OrderProduct();
                        copy.setProduct(op.getProduct());
                        copy.setQuantidade(op.getQuantidade());
                        copy.setPrecoUnitario(op.getPrecoUnitario());
                        return copy;
                    })
                    .collect(Collectors.toList());

            quantidade = entity.getProdutos().stream()
                    .mapToInt(OrderProduct::getQuantidade)
                    .sum();

            total = entity.getProdutos().stream()
                    .map(op -> op.getPrecoUnitario().multiply(BigDecimal.valueOf(op.getQuantidade())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return OrderDTO.builder()
                .id(entity.getId())
                .produtos(produtos)
                .quantidade(quantidade)
                .total(total)
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
