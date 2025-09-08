package order.flow.api.controller;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import order.flow.api.aplication.dto.CreateOrderDTO;
import order.flow.api.aplication.dto.OrderDTO;
import order.flow.api.aplication.service.OrderService;
import order.flow.api.aplication.service.ProductService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Inject
    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderDTO orderDTO) throws Exception{

        return ResponseEntity.ok(orderService.create(orderDTO));
    }
}
