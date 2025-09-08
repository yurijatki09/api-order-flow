package order.flow.api.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import order.flow.api.domain.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

}
