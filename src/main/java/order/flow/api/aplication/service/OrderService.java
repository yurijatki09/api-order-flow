package order.flow.api.aplication.service;

import java.util.List;
import java.util.UUID;
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
        if (createOrderDTO == null || createOrderDTO.getProdutos() == null || createOrderDTO.getProdutos().isEmpty()) {
                throw new IllegalArgumentException("A lista de produtos não pode ser vazia.");
        }

        Order order = Order.builder()
                .status(Status.PENDENTE)
                .build();

        List<UUID> productIds = createOrderDTO.getProdutos().stream()
                .map(CreateOrderDTO.ProductRequest::getProductId)
                .toList();

        List<Product> produtos = productRepository.findAllById(productIds);
        if (produtos.size() != productIds.size()) {
                throw new RuntimeException("Um ou mais produtos não foram encontrados.");
        }

        List<OrderProduct> orderProducts = createOrderDTO.getProdutos().stream()
                .map(req -> {
                        Product product = produtos.stream()
                                .filter(p -> p.getId().equals(req.getProductId()))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + req.getProductId()));

                        return OrderProduct.builder()
                                .order(order)
                                .product(product)
                                .quantidade(req.getQuantidade())
                                .precoUnitario(product.getPreco())
                                .build();
                })
                .toList();

        order.setProdutos(orderProducts);

        Order savedOrder = orderRepository.save(order);

        return OrderMapper.toDto(savedOrder);
    }

    public List<OrderDTO> getAllOrders() {
        // TODO Auto-generated method stub
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());

    }

}
