package order.flow.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import order.flow.api.aplication.dto.UserPurchaseSummaryDTO;
import order.flow.api.aplication.dto.UserTicketDTO;
import order.flow.api.domain.repository.OrderRepository;
import order.flow.api.domain.repository.UserPurchaseSummaryProjection;
import order.flow.api.domain.repository.UserTicketProjection;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    OrderRepository orderRepository;

    public ReportController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/top-users")
    public List<UserPurchaseSummaryProjection> getTopUsers() {
        return orderRepository.findTop5Users(PageRequest.of(0, 5));
    }

    @GetMapping("/average-ticket")
    public List<UserTicketProjection> getAverageTicket() {
        return orderRepository.findTicketAveragePerUser();
    }

    @GetMapping("/monthly-revenue")
    public BigDecimal getMonthlyRevenue() {
        return orderRepository.findMonthlyRevenue();
    }
}
