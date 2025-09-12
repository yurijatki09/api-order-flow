package order.flow.api.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import order.flow.api.aplication.dto.UserPurchaseSummaryDTO;
import order.flow.api.aplication.dto.UserTicketDTO;
import order.flow.api.domain.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByUserId(UUID uuid);

    @Query(value = """
    SELECT u.nome AS userNome,
           SUM(o.total) AS precoTotal
    FROM orders o
    JOIN users u ON u.id = o.user_id
    WHERE o.status = 'PAGO'
    GROUP BY u.nome
    ORDER BY precoTotal DESC
    """, nativeQuery = true)
    List<UserPurchaseSummaryProjection> findTop5Users(Pageable pageable);

     @Query(value = """
        SELECT u.nome AS userNome,
               AVG(o.total) AS ticketMedio
        FROM orders o
        JOIN users u ON u.id = o.user_id
        WHERE o.status = 'PAGO'
        GROUP BY u.nome
        ORDER BY ticketMedio DESC
        """, nativeQuery = true)
    List<UserTicketProjection> findTicketAveragePerUser();

    @Query(value = """
        SELECT COALESCE(SUM(o.total), 0) AS totalFaturado
        FROM orders o
        WHERE o.status = 'PAGO'
          AND MONTH(o.created_at) = MONTH(CURRENT_DATE())
          AND YEAR(o.created_at) = YEAR(CURRENT_DATE())
        """, nativeQuery = true)
    BigDecimal findMonthlyRevenue();

}
