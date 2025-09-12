package order.flow.api.domain.repository;

import java.math.BigDecimal;

public interface UserPurchaseSummaryProjection {

    String getUserNome();
    BigDecimal getPrecoTotal();
}
