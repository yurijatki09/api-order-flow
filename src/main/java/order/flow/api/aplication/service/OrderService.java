package order.flow.api.aplication.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import order.flow.api.aplication.dto.CreateOrderDTO;
import order.flow.api.aplication.dto.OrderDTO;
import order.flow.api.aplication.mapper.OrderMapper;
import order.flow.api.domain.model.Order;
import order.flow.api.domain.model.OrderProduct;
import order.flow.api.domain.model.Product;
import order.flow.api.domain.model.Status;
import order.flow.api.domain.model.User;
import order.flow.api.domain.repository.OrderRepository;
import order.flow.api.domain.repository.ProductRepository;
import order.flow.api.domain.repository.UserRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderDTO create(CreateOrderDTO createOrderDTO) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =  userRepository.findUserByEmail(email);

        if (createOrderDTO == null || createOrderDTO.getProdutos() == null || createOrderDTO.getProdutos().isEmpty()) {
                throw new IllegalArgumentException("A lista de produtos não pode ser vazia.");
        }

        Order order = Order.builder()
                .status(Status.PENDENTE)
                .user(user)
                .build();

        List<UUID> productIds = createOrderDTO.getProdutos().stream()
                .map(CreateOrderDTO.ProductRequest::getProductId)
                .toList();

        List<Product> produtos = productRepository.findAllById(productIds);
        if (produtos.size() != productIds.size()) {
                throw new RuntimeException("Um ou mais produtos não foram encontrados.");
        }

        AtomicBoolean noStock = new AtomicBoolean(false);

        List<OrderProduct> orderProducts = createOrderDTO.getProdutos().stream()
                .map(req -> {
                        Product product = produtos.stream()
                                .filter(p -> p.getId().equals(req.getProductId()))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + req.getProductId()));

                        if (product.getQuantidade() < req.getQuantidade()) {
                                noStock.set(true);
                        }

                        return OrderProduct.builder()
                                .order(order)
                                .product(product)
                                .quantidade(req.getQuantidade())
                                .precoUnitario(product.getPreco())
                                .build();
                })
                .toList();

        order.setProdutos(orderProducts);

        BigDecimal total = orderProducts.stream()
            .map(op -> op.getPrecoUnitario().multiply(BigDecimal.valueOf(op.getQuantidade())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setPrecoTotal(total);

        if (noStock.get()) {
                order.setStatus(Status.CANCELADO);
                order.setMensagem("Um ou mais produtos não possuem estoque suficiente. Pedido cancelado.");
        }else{
                order.setMensagem("Pedido criado com sucesso e está pendente de pagamento.");
        }

        Order savedOrder = orderRepository.save(order);

        return OrderMapper.toDto(savedOrder);
    }

    public List<OrderDTO> getAllOrders() {
        // TODO Auto-generated method stub

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =  userRepository.findUserByEmail(email);

        List<Order> orders = orderRepository.findByUserId(user.getId());
        return orders.stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());

    }

    @Transactional
    public String payOrder(UUID orderId) throws Exception {
        // TODO Auto-generated method stub
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

        if(order.getStatus() != Status.PENDENTE){
                return "Pedido não está pendente de pagamento.";
        }
        List<Product> produtosParaAtualizar = order.getProdutos().stream()
            .map(item -> {
                Product product = item.getProduct();
                product.setQuantidade(product.getQuantidade() - item.getQuantidade());
                return product;
            })
            .toList();

        productRepository.saveAll(produtosParaAtualizar);
        order.setStatus(Status.PAGO);
        order.setMensagem("Pagamento realizado com sucesso!");
        orderRepository.save(order);
        return "Pagamento realizado com sucesso!";
    }

}
