package order.flow.api.aplication.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class UserPurchaseSummaryDTO {

    private String userNome;
    private BigDecimal precoTotal;

    public UserPurchaseSummaryDTO(String userNome, BigDecimal precoTotal) {
        this.userNome = userNome;
        this.precoTotal = precoTotal;
    }

    public String getUserNome() { return userNome; }
    public BigDecimal getPrecoTotal() { return precoTotal; }
}
