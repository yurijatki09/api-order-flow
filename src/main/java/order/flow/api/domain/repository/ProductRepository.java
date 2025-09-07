package order.flow.api.domain.repository;

import org.springframework.stereotype.Repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import order.flow.api.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

}
