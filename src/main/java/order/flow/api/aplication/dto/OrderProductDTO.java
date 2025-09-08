package order.flow.api.aplication.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderProductDTO {

    private Integer quantidade;
    private BigDecimal precoUnitario;
    private ProductSummaryDTO product;
}