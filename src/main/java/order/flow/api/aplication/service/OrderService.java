package order.flow.api.aplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import order.flow.api.aplication.dto.CreateOrderDTO;
import order.flow.api.aplication.dto.OrderDTO;
import order.flow.api.aplication.mapper.OrderMapper;
import order.flow.api.domain.model.Order;
import order.flow.api.domain.model.OrderProduct;
import order.flow.api.domain.model.Product;
import order.flow.api.domain.model.Status;
import order.flow.api.domain.repository.OrderRepository;
import order.flow.api.domain.repository.ProductRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderDTO create(CreateOrderDTO createOrderDTO) {
        // 1. Cria o pedido com status PENDENTE
        Order order = Order.builder()
                .status(Status.PENDENTE)
                .build();

        // 2. Monta os OrderProducts a partir dos IDs
        List<OrderProduct> orderProducts = createOrderDTO.getProdutos().stream()
                .map(req -> {
                    Product product = productRepository.findById(req.getProductId())
                            .orElseThrow(() -> new RuntimeException(
                                    "Produto não encontrado: " + req.getProductId()));

                    OrderProduct op = OrderProduct.builder()
                            .product(product)
                            .quantidade(req.getQuantidade())
                            .precoUnitario(product.getPreco())
                            .order(order)
                            .build();
                    return op;
                })
                .collect(Collectors.toList());

        order.setProdutos(orderProducts);

        // 3. Salva o pedido
        Order savedOrder = orderRepository.save(order);

        // 4. Converte para DTO (com total e quantidade calculados)
        return OrderMapper.toDto(savedOrder);
    }

}
